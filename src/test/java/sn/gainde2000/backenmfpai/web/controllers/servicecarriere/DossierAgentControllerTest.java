package sn.gainde2000.backenmfpai.web.controllers.servicecarriere;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.DossierAgentMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.IDossierAgentRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IDossierAgent;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DossierAgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DossierAgentResponseDto;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DossierAgentController.class)
class DossierAgentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IDossierAgent iDossierAgent;

    @MockBean
    private DossierAgentMapper dossierAgentMapper;

    @MockBean
    private IUtilisateur iUtilisateur;

    @MockBean
    private IDossierAgentRepository dossierAgentRepository;

    private CentralLevel centralLevel;
    private DeconcentratedLevel deconcentratedLevel;
    private DossierAgent dossierAgent;
    private DossierAgentResponseDto dossierResponseDto;
    private DossierAgentRequestDto dossierRequestDto;

    @BeforeEach
    void setUp() {
        // Initialisation de CentralLevel
        centralLevel = new CentralLevel();
        centralLevel.setId(1L);
        centralLevel.setMatricule("TEST001");
        centralLevel.setNom("DIOP");
        centralLevel.setPrenom("Amadou");
        centralLevel.setEmail("amadou.diop@test.sn");
        
        // Initialisation de DeconcentratedLevel
        deconcentratedLevel = new DeconcentratedLevel();
        deconcentratedLevel.setId(2L);
        deconcentratedLevel.setMatricule("TEST002");
        deconcentratedLevel.setNom("NDIAYE");
        deconcentratedLevel.setPrenom("Fatou");
        deconcentratedLevel.setEmail("fatou.ndiaye@test.sn");

        // Création du dossier agent
        dossierAgent = new DossierAgent();
        dossierAgent.setId(1L);
        dossierAgent.setUtilisateur(centralLevel);
        dossierAgent.setIsDeleted(false);
        dossierAgent.setDiplomes(new ArrayList<>());
        dossierAgent.setSituationAdministrative(new ArrayList<>());
        dossierAgent.setEtatCivil(new ArrayList<>());

        // Création du DTO réponse
        dossierResponseDto = new DossierAgentResponseDto();
        dossierResponseDto.setId(1L);
        dossierResponseDto.setUtilisateur(centralLevel);
        dossierResponseDto.setHasDossier(true);
        dossierResponseDto.setCanCreateDossier(false);
        dossierResponseDto.setDiplomes(new ArrayList<>());
        dossierResponseDto.setSituationAdministrative(new ArrayList<>());
        dossierResponseDto.setEtatCivil(new ArrayList<>());

        // Création du DTO requête
        dossierRequestDto = new DossierAgentRequestDto();
        dossierRequestDto.setId(0L);
        dossierRequestDto.setUtilisateurId(1L);
        dossierRequestDto.setDiplomes(new ArrayList<>());
        dossierRequestDto.setSituationAdministrative(new ArrayList<>());
        dossierRequestDto.setEtatCivil(new ArrayList<>());
    }

    @Test
    @DisplayName("Test récupération du dossier de l'utilisateur connecté")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testGetCurrentUserDossier() throws Exception {
        // Given
        when(iUtilisateur.getCurrentUser()).thenReturn(centralLevel);
        when(iDossierAgent.getDossierCurrentUser()).thenReturn(dossierResponseDto);

        // When & Then
        mockMvc.perform(get("/dossieragent/current")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.hasDossier").value(true))
                .andExpect(jsonPath("$.data.canCreateDossier").value(false))
                .andExpect(jsonPath("$.data.utilisateur.matricule").value("TEST001"));
    }

    @Test
    @DisplayName("Test récupération du dossier avec utilisateur déconcentré")
    @WithMockUser(username = "fatou.ndiaye", roles = {"USER"})
    void testGetCurrentUserDossierDeconcentrated() throws Exception {
        // Given
        DossierAgentResponseDto deconcentratedDto = new DossierAgentResponseDto();
        deconcentratedDto.setId(2L);
        deconcentratedDto.setUtilisateur(deconcentratedLevel);
        deconcentratedDto.setHasDossier(true);
        deconcentratedDto.setCanCreateDossier(false);
        
        when(iUtilisateur.getCurrentUser()).thenReturn(deconcentratedLevel);
        when(iDossierAgent.getDossierCurrentUser()).thenReturn(deconcentratedDto);

        // When & Then
        mockMvc.perform(get("/dossieragent/current")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.utilisateur.matricule").value("TEST002"))
                .andExpect(jsonPath("$.data.id").value(2L));
    }

    @Test
    @DisplayName("Test récupération du dossier quand l'utilisateur n'a pas de dossier")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testGetCurrentUserDossierNotFound() throws Exception {
        // Given
        DossierAgentResponseDto emptyDossier = new DossierAgentResponseDto();
        emptyDossier.setId(0L);
        emptyDossier.setUtilisateur(centralLevel);
        emptyDossier.setHasDossier(false);
        emptyDossier.setCanCreateDossier(false);

        when(iUtilisateur.getCurrentUser()).thenReturn(centralLevel);
        when(iDossierAgent.getDossierCurrentUser()).thenReturn(emptyDossier);

        // When & Then
        mockMvc.perform(get("/dossieragent/current")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.hasDossier").value(false))
                .andExpect(jsonPath("$.data.id").value(0L));
    }

    @Test
    @DisplayName("Test vérification de l'existence du dossier")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testHasDossier() throws Exception {
        // Given
        when(iUtilisateur.getCurrentUser()).thenReturn(centralLevel);
        when(iDossierAgent.rechercheDossierAgent(anyString())).thenReturn(dossierResponseDto);

        // When & Then
        mockMvc.perform(get("/dossieragent/has-dossier")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.hasDossier").value(true))
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.matricule").value("TEST001"));
    }

    @Test
    @DisplayName("Test vérification de l'existence du dossier - Dossier inexistant")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testHasDossierNotFound() throws Exception {
        // Given
        DossierAgentResponseDto emptyDossier = new DossierAgentResponseDto();
        emptyDossier.setId(0L);
        emptyDossier.setHasDossier(false);
        
        when(iUtilisateur.getCurrentUser()).thenReturn(centralLevel);
        when(iDossierAgent.rechercheDossierAgent(anyString())).thenReturn(emptyDossier);

        // When & Then
        mockMvc.perform(get("/dossieragent/has-dossier")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.hasDossier").value(false));
    }

    @Test
    @DisplayName("Test création d'un dossier par ADMIN-DRH")
    @WithMockUser(username = "admin", roles = {"ADMIN-DRH"})
    void testCreateDossierAgentByAdmin() throws Exception {
        // Given
        when(iDossierAgent.createDossier(any(DossierAgentRequestDto.class))).thenReturn(dossierAgent);
        when(dossierAgentMapper.toDto(any(DossierAgent.class))).thenReturn(dossierResponseDto);

        // When & Then
        mockMvc.perform(post("/dossieragent/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dossierRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("Test création d'un dossier - Accès refusé pour utilisateur simple")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testCreateDossierAgentForbidden() throws Exception {
        // When & Then
        mockMvc.perform(post("/dossieragent/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dossierRequestDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test récupération d'un dossier par son ID")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testGetOneDossierAgent() throws Exception {
        // Given
        when(iDossierAgent.getOneDossierAgent(1L)).thenReturn(dossierResponseDto);

        // When & Then
        mockMvc.perform(get("/dossieragent/dossier/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.id").value(1L));
    }

    @Test
    @DisplayName("Test récupération d'un dossier par ID inexistant")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testGetOneDossierAgentNotFound() throws Exception {
        // Given
        when(iDossierAgent.getOneDossierAgent(999L))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Dossier Agent introuvable"));

        // When & Then
        mockMvc.perform(get("/dossieragent/dossier/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test recherche par matricule")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testRechercheParMatricule() throws Exception {
        // Given
        when(iDossierAgent.rechercheDossierAgent("TEST001")).thenReturn(dossierResponseDto);

        // When & Then
        mockMvc.perform(get("/dossieragent/recherche/agent")
                .param("matricule", "TEST001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("Test suppression logique d'un dossier")
    @WithMockUser(username = "admin", roles = {"ADMIN-DRH"})
    void testDeleteDossier() throws Exception {
        // Given
        when(iDossierAgent.deleteDossier(1L)).thenReturn(dossierAgent);

        // When & Then
        mockMvc.perform(delete("/dossieragent/dossier/1")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Test récupération des diplômes par matricule")
    @WithMockUser(username = "amadou.diop", roles = {"USER"})
    void testGetDiplomesByMatricule() throws Exception {
        // Given
        List<Diplome> diplomes = new ArrayList<>();
        when(iDossierAgent.getDiplomesByMatricule("TEST001", 0, 10)).thenReturn(diplomes);

        // When & Then
        mockMvc.perform(get("/dossieragent/dossier/diplomes")
                .param("matricule", "TEST001")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Test modification d'un dossier")
    @WithMockUser(username = "admin", roles = {"ADMIN-DRH"})
    void testUpdateDossierAgent() throws Exception {
        // Given
        when(iDossierAgent.getOneDossierAgent(1L)).thenReturn(dossierResponseDto);
        when(iDossierAgent.createDossier(any(DossierAgentRequestDto.class))).thenReturn(dossierAgent);
        when(dossierAgentMapper.toDto(any(DossierAgent.class))).thenReturn(dossierResponseDto);

        // When & Then
        mockMvc.perform(put("/dossieragent/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dossierRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("Test modification d'un dossier inexistant")
    @WithMockUser(username = "admin", roles = {"ADMIN-DRH"})
    void testUpdateDossierAgentNotFound() throws Exception {
        // Given
        when(iDossierAgent.getOneDossierAgent(999L))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Dossier Agent introuvable"));

        // When & Then
        mockMvc.perform(put("/dossieragent/update/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dossierRequestDto)))
                .andExpect(status().isNotFound());
    }
}