package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Mutation;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.*;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.MutationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Status;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutationModificationTest {
    @Test void conserveLesPiecesEtReinitialiseLaSignatureLorsDeLaResoumission() {
        MutationImpl service = mock(MutationImpl.class, CALLS_REAL_METHODS);
        ImutationRepository mutations = mock(ImutationRepository.class);
        IOrigineDemandeurLogRepository origines = mock(IOrigineDemandeurLogRepository.class);
        IStatutMutationRepository statuts = mock(IStatutMutationRepository.class);
        ITraitementMutationRepository traitements = mock(ITraitementMutationRepository.class);
        ReflectionTestUtils.setField(service, "imutationRepository", mutations);
        ReflectionTestUtils.setField(service, "iOrigineDemandeurLogRepository", origines);
        ReflectionTestUtils.setField(service, "iStatutMutationRepository", statuts);
        ReflectionTestUtils.setField(service, "iTraitementMutationRepository", traitements);
        Mutation mutation = new Mutation();
        mutation.setId(1L);
        mutation.setDossierSigne("ancienne-signature.pdf");
        mutation.setCurrentBordereauTransmission("ancien-bordereau.pdf");
        var piece = new sn.gainde2000.backenmfpai.entities.file.File();
        mutation.getPieceJointes().add(piece);
        DeconcentratedLevel demandeur = mock(DeconcentratedLevel.class);
        Profile profil = new Profile();
        profil.setCode("Formateur-CFP");
        when(demandeur.getProfils()).thenReturn(Set.of(profil));
        when(demandeur.getTypeUser()).thenReturn("DEC");
        mutation.setDemandeur(demandeur);
        when(mutations.findById(1L)).thenReturn(Optional.of(mutation));
        when(mutations.save(any())).thenAnswer(i -> i.getArgument(0));
        when(origines.saveAndFlush(any())).thenAnswer(i -> i.getArgument(0));
        when(traitements.saveAndFlush(any())).thenAnswer(i -> i.getArgument(0));
        StatutMutation soumis = new StatutMutation();
        soumis.setCode("SOUMISE");
        when(statuts.findByCode("SOUMISE")).thenReturn(soumis);
        doNothing().when(service).sendNotify(any(), anyString(), anyString(), anyString());
        MutationDTO request = new MutationDTO("DEC", "Corrigé", null, null, null, null, null, null, null, null, null);

        assertEquals(Status.OK, service.updateMutation(1L, request).getStatus());
        assertSame(piece, mutation.getPieceJointes().get(0));
        assertNull(mutation.getDossierSigne());
        assertNull(mutation.getCurrentBordereauTransmission());
        assertEquals("SOUMISE", mutation.getTraitementMutation().getStatut().getCode());
    }
}
