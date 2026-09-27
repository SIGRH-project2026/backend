package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.EtatCivil;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.SituationAdministrative;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.DossierAgentMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.*;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeAARepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeAGRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeActeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DossierAgentResponseDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DossierAgentImplTest {

    @Mock
    private IDossierAgentRepository dossierAgentRepository;

    @Mock
    private DossierAgentMapper dossierAgentMapper;

    @Mock
    private IUtilisateurRepository iUtilisateurRepository;

    @Mock
    private IUtilisateur iUtilisateur;

    @Mock
    private IAgentRepository iAgentRepository;

    @Mock
    private IAvancementRepository iAvancementRepository;

    @Mock
    private IDiplomeRepository iDiplomeRepository;

    @Mock
    private ISituationAdministrativeRepository iSituationAdministrativeRepository;

    @Mock
    private TypeActeRepository typeActeRepository;

    @Mock
    private TypeAARepository typeAARepository;

    @Mock
    private TypeAGRepository typeAGRepository;

    @Mock
    private DeconcentratedLevelRepository deconcentratedLevelRepository;

    // Injection manuelle au lieu de @InjectMocks
    private DossierAgentImpl dossierAgentService;

    private CentralLevel centralLevel;
    private DeconcentratedLevel deconcentratedLevel;
    private DossierAgent dossierAgent;
    private DossierAgentResponseDto dossierResponseDto;

    @BeforeEach
    void setUp() {
        // Création manuelle du service avec tous les mocks
        dossierAgentService = new DossierAgentImpl(
            dossierAgentRepository,
            dossierAgentMapper,
            iAgentRepository,
            deconcentratedLevelRepository,
            iAvancementRepository,
            iDiplomeRepository,
            iSituationAdministrativeRepository,
            iUtilisateur,
            iUtilisateurRepository,
            typeActeRepository,
            typeAARepository,
            typeAGRepository
        );

        // Initialisation des données de test
        centralLevel = new CentralLevel();
        centralLevel.setId(1L);
        centralLevel.setMatricule("TEST001");
        centralLevel.setNom("DIOP");
        centralLevel.setPrenom("Amadou");
        centralLevel.setEmail("amadou.diop@test.sn");

        deconcentratedLevel = new DeconcentratedLevel();
        deconcentratedLevel.setId(2L);
        deconcentratedLevel.setMatricule("TEST002");
        deconcentratedLevel.setNom("NDIAYE");
        deconcentratedLevel.setPrenom("Fatou");
        deconcentratedLevel.setEmail("fatou.ndiaye@test.sn");

        dossierAgent = new DossierAgent();
        dossierAgent.setId(1L);
        dossierAgent.setUtilisateur(centralLevel);
        dossierAgent.setIsDeleted(false);
        dossierAgent.setDiplomes(new ArrayList<>());
        dossierAgent.setSituationAdministrative(new ArrayList<>());
        dossierAgent.setEtatCivil(new ArrayList<>());

        dossierResponseDto = new DossierAgentResponseDto();
        dossierResponseDto.setId(1L);
        dossierResponseDto.setUtilisateur(centralLevel);
        dossierResponseDto.setHasDossier(true);
        dossierResponseDto.setCanCreateDossier(false);
        dossierResponseDto.setDiplomes(new ArrayList<>());
        dossierResponseDto.setSituationAdministrative(new ArrayList<>());
        dossierResponseDto.setEtatCivil(new ArrayList<>());
    }

    @Test
    @DisplayName("Test recherche d'un dossier existant par matricule - CentralLevel")
    void testRechercheDossierAgent_Existing_CentralLevel() {
        // Given
        when(iUtilisateurRepository.findUtilisateurByMatricule("TEST001"))
                .thenReturn(Optional.of(centralLevel));
        when(dossierAgentRepository.findByUtilisateur(centralLevel))
                .thenReturn(Optional.of(dossierAgent));
        when(dossierAgentMapper.toDto(dossierAgent)).thenReturn(dossierResponseDto);

        // When
        DossierAgentResponseDto result = dossierAgentService.rechercheDossierAgent("TEST001");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.isHasDossier()).isTrue();
        assertThat(result.getUtilisateur().getMatricule()).isEqualTo("TEST001");
        
        verify(iUtilisateurRepository).findUtilisateurByMatricule("TEST001");
        verify(dossierAgentRepository).findByUtilisateur(centralLevel);
        verify(dossierAgentMapper).toDto(dossierAgent);
    }

    @Test
    @DisplayName("Test recherche d'un dossier inexistant par matricule")
    void testRechercheDossierAgent_NotExisting() {
        // Given
        when(iUtilisateurRepository.findUtilisateurByMatricule("TEST003"))
                .thenReturn(Optional.of(centralLevel));
        when(dossierAgentRepository.findByUtilisateur(centralLevel))
                .thenReturn(Optional.empty());

        // When
        DossierAgentResponseDto result = dossierAgentService.rechercheDossierAgent("TEST003");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(0L);
        assertThat(result.isHasDossier()).isFalse();
        assertThat(result.getUtilisateur()).isEqualTo(centralLevel);
    }

    @Test
    @DisplayName("Test recherche d'un dossier avec matricule inexistant")
    void testRechercheDossierAgent_UserNotFound() {
        // Given
        when(iUtilisateurRepository.findUtilisateurByMatricule("INEXISTANT"))
                .thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> dossierAgentService.rechercheDossierAgent("INEXISTANT"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("utilisateur introuvable");
    }

    @Test
    @DisplayName("Test vérification de l'existence d'un dossier - True")
    void testHasDossier_True() {
        // Given
        when(iUtilisateurRepository.findUtilisateurByMatricule("TEST001"))
                .thenReturn(Optional.of(centralLevel));
        when(dossierAgentRepository.findByUtilisateur(centralLevel))
                .thenReturn(Optional.of(dossierAgent));

        // When
        boolean result = dossierAgentService.hasDossier("TEST001");

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Test vérification de l'existence d'un dossier - False")
    void testHasDossier_False() {
        // Given
        when(iUtilisateurRepository.findUtilisateurByMatricule("TEST002"))
                .thenReturn(Optional.of(centralLevel));
        when(dossierAgentRepository.findByUtilisateur(centralLevel))
                .thenReturn(Optional.empty());

        // When
        boolean result = dossierAgentService.hasDossier("TEST002");

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Test suppression logique d'un dossier")
    void testDeleteDossier() {
        // Given
        when(dossierAgentRepository.findById(1L)).thenReturn(Optional.of(dossierAgent));
        when(dossierAgentRepository.save(any(DossierAgent.class))).thenReturn(dossierAgent);

        // When
        DossierAgent result = dossierAgentService.deleteDossier(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.isDeleted()).isTrue();
        verify(dossierAgentRepository).save(dossierAgent);
    }

    @Test
    @DisplayName("Test récupération d'un dossier par ID - Existant")
    void testGetOneDossierAgent_Existing() {
        // Given
        when(dossierAgentRepository.findById(1L)).thenReturn(Optional.of(dossierAgent));
        when(dossierAgentMapper.toDto(dossierAgent)).thenReturn(dossierResponseDto);

        // When
        DossierAgentResponseDto result = dossierAgentService.getOneDossierAgent(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.isHasDossier()).isTrue();
    }

    @Test
    @DisplayName("Test récupération d'un dossier par ID - Inexistant")
    void testGetOneDossierAgent_NotFound() {
        // Given
        when(dossierAgentRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> dossierAgentService.getOneDossierAgent(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Dossier Agent introuvable");
    }

    @Test
    @DisplayName("Test récupération des diplômes par matricule")
    void testGetDiplomesByMatricule() {
        // Given
        List<Diplome> diplomes = new ArrayList<>();
        Diplome diplome = new Diplome();
        diplome.setId(1L);
        diplome.setDipNom("Master en Informatique");
        diplome.setDipDateObtention(LocalDate.of(2020, 6, 15));
        diplomes.add(diplome);
        
        dossierResponseDto.setDiplomes(diplomes);
        
        when(iUtilisateurRepository.findUtilisateurByMatricule("TEST001"))
                .thenReturn(Optional.of(centralLevel));
        when(dossierAgentRepository.findByUtilisateur(centralLevel))
                .thenReturn(Optional.of(dossierAgent));
        when(dossierAgentMapper.toDto(dossierAgent)).thenReturn(dossierResponseDto);

        // When
        List<Diplome> result = dossierAgentService.getDiplomesByMatricule("TEST001", 0, 10);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getDipNom()).isEqualTo("Master en Informatique");
    }

    @Test
    @DisplayName("Test filtrage des diplômes supprimés")
    void testRechercheDossierAgent_WithDeletedDiplomes() {
        // Given
        Diplome diplomeActif = new Diplome();
        diplomeActif.setId(1L);
        diplomeActif.setDipNom("Master");
        diplomeActif.setDipDateObtention(LocalDate.of(2020, 6, 15));
        diplomeActif.setIsDeleted(false);
        
        Diplome diplomeSupprime = new Diplome();
        diplomeSupprime.setId(2L);
        diplomeSupprime.setDipNom("Licence");
        diplomeSupprime.setDipDateObtention(LocalDate.of(2017, 6, 15));
        diplomeSupprime.setIsDeleted(true);
        
        List<Diplome> diplomes = new ArrayList<>();
        diplomes.add(diplomeActif);
        diplomes.add(diplomeSupprime);
        
        dossierResponseDto.setDiplomes(diplomes);
        
        when(iUtilisateurRepository.findUtilisateurByMatricule("TEST001"))
                .thenReturn(Optional.of(centralLevel));
        when(dossierAgentRepository.findByUtilisateur(centralLevel))
                .thenReturn(Optional.of(dossierAgent));
        when(dossierAgentMapper.toDto(dossierAgent)).thenReturn(dossierResponseDto);

        // When
        DossierAgentResponseDto result = dossierAgentService.rechercheDossierAgent("TEST001");

        // Then
        assertThat(result.getDiplomes()).hasSize(1);
        assertThat(result.getDiplomes().get(0).getId()).isEqualTo(1L);
        assertThat(result.getDiplomes().get(0).getDipNom()).isEqualTo("Master");
    }
}