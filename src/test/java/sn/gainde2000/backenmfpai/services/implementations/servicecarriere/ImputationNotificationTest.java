package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import sn.gainde2000.backenmfpai.commons.Notification.*;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Imputation.ImputationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.ImputationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.implementations.servicecarriere.imputation.ImputationImpl;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation.ImputationRequestdto;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImputationNotificationTest {
    @Mock ImputationRepository repository;
    @Mock ImputationMapper mapper;
    @Mock IUtilisateurRepository users;
    @Mock IUtilisateur currentUser;
    @Mock INotification notifications;
    @Mock MailService mailService;
    @InjectMocks ImputationImpl service;
    ImputationRequestdto request;
    ImputationOuBulletin entity;
    CentralLevel agent;

    @BeforeEach void setup() {
        request = new ImputationRequestdto();
        request.setUtilisateurId(42L);
        agent = new CentralLevel();
        agent.setId(42L);
        agent.setEmail("agent@example.com");
        entity = new ImputationOuBulletin();
        entity.setNumeroDemande(123L);
        entity.setTypeDemande("Imputation budgétaire");
        when(users.findById(42L)).thenReturn(Optional.of(agent));
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
    }

    @AfterEach void cleanup() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    @Test void notifiesOwnerAndEmailsAfterCommit() {
        TransactionSynchronizationManager.initSynchronization();
        assertThat(service.createimputation(request)).isSameAs(entity);
        verify(notifications).notifyUser(argThat(n -> Long.valueOf(42L).equals(n.getIdUser())
                && n.getCodeProfile() == null && n.getMessage().contains("123 a été créée")));
        verifyNoInteractions(mailService);
        TransactionSynchronizationManager.getSynchronizations().forEach(TransactionSynchronization::afterCommit);
        verify(mailService).sendASynchronousMail(argThat(m -> "agent@example.com".equals(m.destinataire())
                && m.text().contains("123 a été créée")));
    }

    @Test void rollbackDoesNotSendEmail() {
        TransactionSynchronizationManager.initSynchronization();
        service.createimputation(request);
        TransactionSynchronizationManager.getSynchronizations().forEach(
                s -> s.afterCompletion(TransactionSynchronization.STATUS_ROLLED_BACK));
        verifyNoInteractions(mailService);
    }

    @Test void missingEmailStillNotifiesInApplication() {
        agent.setEmail(" ");
        service.createimputation(request);
        verify(notifications).notifyUser(any(Notification.class));
        verifyNoInteractions(mailService);
    }

    @Test void updateDoesNotSendCreationNotifications() {
        request.setId(5L);
        service.createimputation(request);
        verifyNoInteractions(notifications, mailService);
    }

    @Test void failedSaveDoesNotNotify() {
        when(repository.save(entity)).thenThrow(new IllegalStateException("Save failed"));
        assertThatThrownBy(() -> service.createimputation(request)).isInstanceOf(IllegalStateException.class);
        verifyNoInteractions(notifications, mailService);
    }

    @Test void bulletinUsesItsOwnLabel() {
        entity.setTypeDemande("Bulletin de visite");
        service.createimputation(request);
        verify(notifications).notifyUser(argThat(n -> n.getMessage().contains("Votre bulletin de visite n° 123 a été créé")));
        verify(mailService).sendASynchronousMail(any(MailInfosDTO.class));
    }
}
