package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Mutation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.OrigineDemandeurLog;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.StatutMutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.TraitementMutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeEtablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

class MutationWorkflowTest {
    private final MutationImpl service = mock(MutationImpl.class, CALLS_REAL_METHODS);

    @Test
    void refuseUnSecondTraitementDpeec() {
        var repository = mock(sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.ImutationRepository.class);
        org.springframework.test.util.ReflectionTestUtils.setField(service, "imutationRepository", repository);
        Mutation mutation = new Mutation();
        StatutMutation statut = new StatutMutation();
        statut.setCode("REC-DGPEEC");
        TraitementMutation traitement = new TraitementMutation();
        traitement.setStatut(statut);
        mutation.setTraitementMutation(traitement);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(mutation));
        var response = service.traitementMutation(1L, null,
                "{\"motif\":\"test\",\"idTraiteur\":2,\"codeStatutMutation\":\"REC-DGPEEC\"}", null);
        assertEquals(sn.gainde2000.backenmfpai.web.dtos.responses.Status.EXCEPTION, response.getStatus());
        verify(repository, never()).saveAndFlush(any());
    }

    private OrigineDemandeurLog origine(String codeType) {
        TypeEtablissement type = new TypeEtablissement();
        type.setCode(codeType);
        Etablissement etablissement = new Etablissement();
        etablissement.setTypeEtablissement(type);
        OrigineDemandeurLog origine = new OrigineDemandeurLog();
        origine.setOrigineUserType("DEC");
        origine.setEtablissement(etablissement);
        return origine;
    }

    @ParameterizedTest
    @CsvSource({"CFP,Chef-cfp", "EFF,Chef-EFF", "LYC,Chef-etablissement"})
    void affecteLeProfesseurAuChefDeSonEtablissement(String type, String chef) {
        assertEquals(chef, service.profilTraitant("Professeur", origine(type), "DEC"));
    }

    @ParameterizedTest
    @CsvSource({
            "SOUMISE,Chef-etablissement,Chef-cfp",
            "SOUMISE,Chef-cfp,Chef-cfp",
            "SOUMISE,Directeur-DRH,Directeur-DRH",
            "REC-DR-CFP,Représentant-IEF,Représentant-IEF",
            "VALIDER,Chef-etablissement,Chef-etablissement",
            "REJETER,Chef-etablissement,Chef-etablissement"
    })
    void corrigeLesAnciennesSoumissionsSansRevenirSurLesEtapesSuivantes(String statut, String profil, String attendu) {
        Mutation mutation = new Mutation();
        mutation.setOrigineDemandeurLog(origine("CFP"));
        mutation.setProfilDevantTraiter(profil);
        StatutMutation status = new StatutMutation();
        status.setCode(statut);
        TraitementMutation traitement = new TraitementMutation();
        traitement.setStatut(status);
        mutation.setTraitementMutation(traitement);
        assertEquals(attendu, service.profilEffectif(mutation));
    }

    @ParameterizedTest
    @CsvSource({
            "Chef-cfp,DEC,Représentant-IEF",
            "Chef-EFF,DEC,Representant-IA",
            "Chef-service,CEN,Directeur-DRH",
            "Chef-service-dfc,CEN,Directeur-DRH",
            "Directeur-DRH,CEN,Chef-division-dgpeec",
            "Chef-division-dfc,CEN,Directeur-DRH"
    })
    void transmetSelonLeRoleDeLEtape(String role, String niveau, String prochainRole) {
        assertEquals(prochainRole, service.profilTraitant(role, null, niveau));
    }

    @Test
    void affecteUnAgentDeDirectionAuChefServicePuisALaDrh() {
        var direction = new sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction();
        direction.setCode("DFC");
        OrigineDemandeurLog origine = new OrigineDemandeurLog();
        origine.setOrigineUserType("CEN");
        origine.setDirection(direction);
        String chef = service.profilTraitant("Agent", origine, "CEN");
        assertEquals("Chef-service", chef);
        assertEquals("Directeur-DRH", service.profilTraitant(chef, origine, "CEN"));

        Mutation mutation = new Mutation();
        mutation.setProfilDevantTraiter(chef);
        service.addBordereauTransmission(mutation, "bordereau.pdf", null, "CEN");
        assertEquals("bordereau.pdf", mutation.getBtCS());
        assertNull(mutation.getBtDRH());
    }

    @Test
    void rangeLeBordereauSelonLEtapeMemeSiLePremierProfilEstAgent() {
        Utilisateur traiteur = mock(Utilisateur.class);
        Profile agent = new Profile();
        agent.setCode("Agent");
        Profile chef = new Profile();
        chef.setCode("Chef-etablissement");
        when(traiteur.getProfils()).thenReturn(new LinkedHashSet<>(List.of(agent, chef)));
        Mutation mutation = new Mutation();
        mutation.setProfilDevantTraiter("Chef-etablissement");

        service.addBordereauTransmission(mutation, "bordereau.pdf", traiteur, "DEC");

        assertEquals("bordereau.pdf", mutation.getBtCE());
        assertNull(mutation.getBtDGPEEC());
    }
}
