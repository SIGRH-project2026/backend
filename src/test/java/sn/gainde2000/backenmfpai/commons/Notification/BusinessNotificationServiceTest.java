package sn.gainde2000.backenmfpai.commons.Notification;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessNotificationServiceTest {
    @Mock INotification notifications;
    @Mock IUtilisateurRepository users;
    @Mock MailService mailService;
    @InjectMocks BusinessNotificationService service;
    CentralLevel agent;

    @BeforeEach void setup() {
        agent = new CentralLevel(); agent.setId(42L); agent.setEmail("agent@example.com");
        TransactionSynchronizationManager.initSynchronization();
    }
    @AfterEach void cleanup() { TransactionSynchronizationManager.clearSynchronization(); }

    @Test void targetsOnlyOwnerAndEmailsAfterCommitWithEscapedContent() {
        service.notify(agent, "Validation", "Votre demande <123> a été validée.");
        verify(notifications).notifyUser(argThat(n -> Long.valueOf(42L).equals(n.getIdUser()) && n.getCodeProfile() == null));
        verifyNoInteractions(mailService);
        commit();
        verify(mailService).sendASynchronousMail(argThat(m -> m.destinataire().equals("agent@example.com")
                && m.text().contains("&lt;123&gt;") && m.subject().equals("Validation")));
    }
    @Test void rollbackNeverSendsEmail() {
        service.notify(agent, "Création", "Dossier créé");
        TransactionSynchronizationManager.getSynchronizations().forEach(s -> s.afterCompletion(TransactionSynchronization.STATUS_ROLLED_BACK));
        verifyNoInteractions(mailService);
    }
    @Test void agentWithoutEmailStillReceivesInAppNotification() {
        agent.setEmail(null);
        service.notify(agent, "Création", "Dossier créé"); commit();
        verify(notifications).notifyUser(any()); verifyNoInteractions(mailService);
    }
    @Test void sharedRecipientsAreDeduplicatedWithinTransaction() {
        service.notify(agent, "Création", "Dossier créé");
        service.notify(agent, "Création", "Dossier créé"); commit();
        verify(notifications, times(1)).notifyUser(any());
        verify(mailService, times(1)).sendASynchronousMail(any());
    }
    @Test void distinctEventsRemainVisible() {
        service.notify(agent, "Création", "Dossier créé");
        service.notify(agent, "Validation", "Dossier validé"); commit();
        verify(notifications, times(2)).notifyUser(any());
        verify(mailService, times(2)).sendASynchronousMail(any());
    }
    @Test void externalApplicantGetsEmailWithoutPublicNotification() {
        when(users.findUtilisateurByEmailIgnoreCase("external@example.com")).thenReturn(Optional.empty());
        service.sendMail(new MailInfosDTO(null, "Stage autorisé", "Stage", null, "external@example.com")); commit();
        verifyNoInteractions(notifications);
        verify(mailService).sendASynchronousMail(argThat(m -> m.destinataire().equals("external@example.com")));
    }
    @Test void applicantWithAccountGetsBothChannels() {
        when(users.findUtilisateurByEmailIgnoreCase(agent.getEmail())).thenReturn(Optional.of(agent));
        service.sendMail(new MailInfosDTO(null, "Stage autorisé", "Stage", null, agent.getEmail())); commit();
        verify(notifications).notifyUser(any()); verify(mailService).sendASynchronousMail(any());
    }
    @Test void invalidRecipientNeverCreatesPublicNotification() {
        agent.setId(null);
        assertThatThrownBy(() -> service.notify(agent, "Sujet", "Texte")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.notifyUser(Notification.builder().idUser(0L).build())).isInstanceOf(IllegalArgumentException.class);
        verifyNoInteractions(notifications, mailService);
    }
    @Test void failedPersistenceDoesNotScheduleMail() {
        doThrow(new IllegalStateException("Database unavailable")).when(notifications).notifyUser(any());
        assertThatThrownBy(() -> service.notify(agent, "Sujet", "Texte")).isInstanceOf(IllegalStateException.class);
        commit(); verifyNoInteractions(mailService);
    }
    private void commit() { TransactionSynchronizationManager.getSynchronizations().forEach(TransactionSynchronization::afterCommit); }
}
