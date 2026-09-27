package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.INotificationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ActivateAccountDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.Status;

@ExtendWith(MockitoExtension.class)
class AuthentificationImplTest {

    @Mock
    private IUtilisateurRepository utilisateurRepository;
    @Mock
    private IProfilRepository profilRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private INotificationService notificationService;

    @InjectMocks
    private AuthentificationImpl authentification;

    @Test
    void activationAgentCentralPreserveLeProfilAttribueParAdministrateur() {
        verifierConservationProfil(new CentralLevel(), "AGT-CENTRAL-001", "Chef-service");
    }

    @Test
    void activationAgentDeconcentrePreserveLeProfilAttribueParAdministrateur() {
        verifierConservationProfil(new DeconcentratedLevel(), "AGT-DECONCENTRE-001", "Chef-etablissement");
    }

    @Test
    void activationEnregistreEmailQuandDossierAgentNenContientPas() {
        CentralLevel utilisateur = new CentralLevel();
        utilisateur.setId(10L);
        utilisateur.setMatricule("AGT-CENTRAL-002");
        utilisateur.setFirstLog(true);
        utilisateur.setStatus(false);
        utilisateur.setProfils(new HashSet<>());
        utilisateur.getProfils().add(Profile.builder().code("Agent").build());

        when(utilisateurRepository.findUtilisateurByNormalizedMatricule("AGT-CENTRAL-002"))
                .thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.findUtilisateurByEmailIgnoreCase("nouveau@sigrh.sn"))
                .thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn("mot-de-passe-chiffre");

        Response<Object> response = authentification.activateAccount(
                new ActivateAccountDTO("AGT-CENTRAL-002", "nouveau@sigrh.sn"));

        assertEquals(Status.OK, response.getStatus());
        assertEquals("nouveau@sigrh.sn", utilisateur.getEmail());
        verify(utilisateurRepository).save(utilisateur);
        verify(notificationService).sendAccountActivationInstructions(
                eq("nouveau@sigrh.sn"), eq("AGT-CENTRAL-002"), anyString());
    }

    private void verifierConservationProfil(Utilisateur utilisateur, String matricule, String codeProfil) {
        Profile profilMetier = Profile.builder().id(42L).code(codeProfil).label("Profil métier").build();
        utilisateur.setMatricule(matricule);
        utilisateur.setEmail("agent@sigrh.sn");
        utilisateur.setFirstLog(true);
        utilisateur.setStatus(false);
        utilisateur.setProfils(new HashSet<>());
        utilisateur.getProfils().add(profilMetier);

        when(utilisateurRepository.findUtilisateurByNormalizedMatricule(matricule))
                .thenReturn(Optional.of(utilisateur));
        when(encoder.encode(anyString())).thenReturn("mot-de-passe-chiffre");

        Response<Object> response = authentification.activateAccount(
                new ActivateAccountDTO(matricule, "AGENT@SIGRH.SN"));

        assertEquals(Status.OK, response.getStatus());
        assertEquals(1, utilisateur.getProfils().size());
        assertEquals(codeProfil, utilisateur.getProfils().iterator().next().getCode());
        assertTrue(utilisateur.getFirstLog());
        assertTrue(utilisateur.getStatus());
        verify(profilRepository, never()).findByCode("Agent");
        verify(utilisateurRepository).save(any(Utilisateur.class));
        verify(notificationService).sendAccountActivationInstructions(
                eq("agent@sigrh.sn"), eq(matricule), anyString());
    }
}
