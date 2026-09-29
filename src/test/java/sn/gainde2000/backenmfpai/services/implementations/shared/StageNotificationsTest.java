package sn.gainde2000.backenmfpai.services.implementations.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.implementations.serviceformation.stage.AttestationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AttestationStageRequest;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StageNotificationsTest {
    @Mock DemandeStageRepository demandes;
    @Mock CentralLevelRepository users;
    @Mock AttestationStageRepository attestations;
    @Mock IUtilisateur currentUser;
    @Mock BusinessNotificationService notifications;
    @InjectMocks AttestationService service;

    @Test void attestationNotifiesApplicantInsteadOfAdministrator() {
        CentralLevel admin = new CentralLevel(); admin.setId(1L); admin.setEmail("admin@example.com");
        DemandeStage demande = new DemandeStage(); demande.setMail("applicant@example.com"); demande.setNumero("ST-9");
        when(demandes.findById(9L)).thenReturn(Optional.of(demande));
        when(currentUser.getCurrentUser()).thenReturn(admin);
        when(users.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        AttestationStageRequest request = new AttestationStageRequest(); request.setDemandeStageId(9L);

        service.saveAttestionStage(request);

        verify(notifications).sendMail(argThat(mail -> "applicant@example.com".equals(mail.destinataire())
                && mail.originalText().contains("ST-9")));
    }
    @Test void unknownApplicationDoesNotNotify() {
        AttestationStageRequest request = new AttestationStageRequest(); request.setDemandeStageId(9L);
        service.saveAttestionStage(request);
        verifyNoInteractions(notifications, attestations);
    }
}
