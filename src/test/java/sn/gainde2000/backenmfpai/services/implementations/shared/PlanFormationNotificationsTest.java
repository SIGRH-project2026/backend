package sn.gainde2000.backenmfpai.services.implementations.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.PlanFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.StatutPlanFormationRepository;
import sn.gainde2000.backenmfpai.services.implementations.serviceformation.planformation.PlanFormationServiceImpl;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanFormationNotificationsTest {
    @Mock PlanFormationRepository plans;
    @Mock StatutPlanFormationRepository statuses;
    @Mock BusinessNotificationService notifications;
    @InjectMocks PlanFormationServiceImpl service;

    @Test void decisionNotifiesPlanOwner() {
        PlanFormation plan = plan("SOUMIS");
        when(plans.findById(5L)).thenReturn(Optional.of(plan));
        when(statuses.findByCode("VALIDE")).thenReturn(status("VALIDE"));
        when(plans.save(plan)).thenReturn(plan);
        service.changePlanFormationStatus(5L, "VALIDE");
        verify(notifications).notify(plan.getCreatedBy(), "Suivi du plan de formation", "Plan de formation PF-5 : statut VALIDE.");
    }
    @Test void unchangedDecisionDoesNotNotifyAgain() {
        PlanFormation plan = plan("VALIDE");
        when(plans.findById(5L)).thenReturn(Optional.of(plan));
        when(statuses.findByCode("VALIDE")).thenReturn(status("VALIDE"));
        when(plans.save(plan)).thenReturn(plan);
        service.changePlanFormationStatus(5L, "VALIDE");
        verifyNoInteractions(notifications);
    }
    @Test void failedDecisionDoesNotNotify() {
        PlanFormation plan = plan("SOUMIS");
        when(plans.findById(5L)).thenReturn(Optional.of(plan));
        when(statuses.findByCode("REJETE")).thenReturn(status("REJETE"));
        when(plans.save(plan)).thenThrow(new IllegalStateException("Database unavailable"));
        assertThatThrownBy(() -> service.changePlanFormationStatus(5L, "REJETE")).isInstanceOf(IllegalStateException.class);
        verifyNoInteractions(notifications);
    }
    private PlanFormation plan(String code) {
        PlanFormation plan = new PlanFormation(); plan.setReference("PF-5");
        CentralLevel owner = new CentralLevel(); owner.setId(42L); plan.setCreatedBy(owner);
        plan.setStatutPlanFormation(status(code)); return plan;
    }
    private StatutPlanFormation status(String code) {
        StatutPlanFormation status = new StatutPlanFormation(); status.setCode(code); return status;
    }
}
