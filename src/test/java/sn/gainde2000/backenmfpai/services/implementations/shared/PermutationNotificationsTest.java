package sn.gainde2000.backenmfpai.services.implementations.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Permutation.PermutationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Permutation.PermutationServiceImpl;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PermutationResponseDto;
import java.util.Optional;
import java.util.Set;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermutationNotificationsTest {
    @Mock BusinessNotificationService notifications;
    @Mock IPermutationRepository permutations;
    @Mock TraitementPermutationRepository traitements;
    @Mock IStatusPermutation statuses;
    @Mock IUtilisateur currentUser;
    @Mock PermutationMapper mapper;
    @InjectMocks PermutationServiceImpl service;

    @Test void rejectionNotifiesBothAgents() {
        CentralLevel applicant = new CentralLevel(); applicant.setId(42L);
        CentralLevel recipient = new CentralLevel(); recipient.setId(43L);
        Profile profile = new Profile(); profile.setCode("Professeur"); recipient.setProfils(Set.of(profile));
        Permutation permutation = new Permutation(); permutation.setId(10L);
        permutation.setUtilisateur1(applicant); permutation.setUtilisateur2(recipient);
        StatusPermutation rejected = new StatusPermutation(); rejected.setCode("REJETER"); rejected.setLibelle("Rejetée");
        when(currentUser.getCurrentUser()).thenReturn(recipient);
        when(permutations.findById(10L)).thenReturn(Optional.of(permutation));
        when(statuses.findByCode("REJETER")).thenReturn(rejected);
        when(traitements.save(any())).thenAnswer(i -> i.getArgument(0));
        when(permutations.save(permutation)).thenReturn(permutation);
        when(mapper.toDto(permutation)).thenReturn(new PermutationResponseDto());

        service.traiterPermutation(10L, "REJETER", "Refus", "");

        verify(notifications).notify(applicant, "Suivi de votre permutation", "Votre demande de permutation n° 10 : Rejetée.");
        verify(notifications).notify(recipient, "Suivi de votre permutation", "Votre demande de permutation n° 10 : Rejetée.");
        verifyNoMoreInteractions(notifications);
    }
}
