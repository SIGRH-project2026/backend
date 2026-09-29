package sn.gainde2000.backenmfpai.services.implementations.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation.FormationNotifications;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FormationNotificationsTest {
    @Mock BusinessNotificationService notifications;
    @Mock ParticipationRepository participations;
    @Mock ParticipantDefinitifRepository participants;
    @Mock IUtilisateurRepository users;
    @InjectMocks FormationNotifications service;

    @Test void participantAndManagerReceiveOnlyOneNotificationEach() {
        CentralLevel agent = user(42L), manager = user(43L);
        Formation formation = new Formation(); formation.setId(10L); formation.setReference("F-10");
        ThemeFormation theme = new ThemeFormation(); theme.setResponsableSuivi(manager);
        PlanFormation plan = new PlanFormation(); plan.setCreatedBy(manager); theme.setPlanFormation(plan);
        formation.setThemeFormation(theme);
        Participation participation = new Participation(); participation.setCentralLevel(agent);
        ParticipantDefinitif definitive = new ParticipantDefinitif(); definitive.setMatricule("MAT42");
        when(participations.findByFormationId(10L)).thenReturn(List.of(participation, participation));
        when(participants.findByFormationId(10L)).thenReturn(List.of(definitive));
        when(users.findUtilisateurByMatricule("MAT42")).thenReturn(Optional.of(agent));

        service.notifyConcerned(formation, "une convocation est disponible");

        verify(notifications).notify(agent, "Suivi de formation", "Formation F-10 : une convocation est disponible.");
        verify(notifications).notify(manager, "Suivi de formation", "Formation F-10 : une convocation est disponible.");
        verifyNoMoreInteractions(notifications);
    }

    @Test void noRecipientDoesNotBroadcastToAllUsers() {
        Formation formation = new Formation(); formation.setId(10L);
        service.notifyConcerned(formation, "validée");
        verifyNoInteractions(notifications, users);
    }

    private CentralLevel user(long id) { CentralLevel u = new CentralLevel(); u.setId(id); return u; }
}
