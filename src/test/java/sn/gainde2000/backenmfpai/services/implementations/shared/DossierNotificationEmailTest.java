package sn.gainde2000.backenmfpai.services.implementations.shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.security.jwt.JwtProvider;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DossierNotificationEmailTest {
    @Mock private MailService mailService;
    @Mock private JwtProvider jwtProvider;
    @InjectMocks private NotificationServiceImpl service;

    @AfterEach
    void clearTransaction() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    @Test
    void sendsConfirmationToAgentAsynchronously() {
        service.sendNotificationDossierCreated("agent@example.com");

        ArgumentCaptor<MailInfosDTO> mail = ArgumentCaptor.forClass(MailInfosDTO.class);
        verify(mailService).sendASynchronousMail(mail.capture());
        assertThat(mail.getValue().destinataire()).isEqualTo("agent@example.com");
        assertThat(mail.getValue().subject()).isEqualTo("Création de votre dossier agent");
        assertThat(mail.getValue().text()).contains("Votre dossier agent a été créé");
    }

    @Test
    void waitsUntilTransactionCommits() {
        TransactionSynchronizationManager.initSynchronization();
        service.sendNotificationDossierCreated("agent@example.com");
        verifyNoInteractions(mailService);

        TransactionSynchronizationManager.getSynchronizations().forEach(TransactionSynchronization::afterCommit);

        verify(mailService).sendASynchronousMail(any(MailInfosDTO.class));
    }

    @Test
    void rollbackDoesNotSendEmail() {
        TransactionSynchronizationManager.initSynchronization();
        service.sendNotificationDossierCreated("agent@example.com");

        TransactionSynchronizationManager.getSynchronizations().forEach(
                callback -> callback.afterCompletion(TransactionSynchronization.STATUS_ROLLED_BACK));

        verifyNoInteractions(mailService);
    }

    @Test
    void missingEmailDoesNotSend() {
        service.sendNotificationDossierCreated(null);
        service.sendNotificationDossierCreated("  ");

        verifyNoInteractions(mailService);
    }
}
