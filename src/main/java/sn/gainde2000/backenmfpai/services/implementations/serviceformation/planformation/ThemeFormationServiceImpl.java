package sn.gainde2000.backenmfpai.services.implementations.serviceformation.planformation;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.PlanFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.ThemeFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.FonctionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DirectionRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation.IThemeFormationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.ThemeFormationDTO;

@Service
@RequiredArgsConstructor
public class ThemeFormationServiceImpl implements IThemeFormationService {

    private final ThemeFormationRepository themeFormationRepository;
    private final sn.gainde2000.backenmfpai.mappers.serviceformation.planformation.ThemeFormationMapper themeFormationMapper;

    private final DirectionRepository directionRepository;

    private final CentralLevelRepository centralLevelRepository;

    private final IUtilisateurRepository utilisateurRepository;

    private final FonctionRepository fonctionRepository;

    private final IProfilRepository profilRepository;

    private final PlanFormationRepository planFormationRepository;
    private final IUtilisateur utilisateurService;
    private final FormationRepository formationRepository;

    @Override
    //@Transactional
    public ThemeFormationDTO saveThemeFormation(ThemeFormationDTO dto) {
        Optional<Direction> optionaldirection = directionRepository.findByCode(dto.getDirection().getCode());
        Optional<Utilisateur> optionaluser = utilisateurRepository.findById(dto.getResponsableSuiviId());
        Optional<PlanFormation> optionalplanformation = planFormationRepository.findById(dto.getPlanFormationId());

        // Vérifiez si les entités Direction, Utilisateur et PlanFormation existent
        if (optionaldirection.isPresent() && optionaluser.isPresent() && optionalplanformation.isPresent()) {
            // Charger les profils depuis la base de données
            Set<Profile> profils = dto.getProfils().stream()
                    .map(profilDTO -> profilRepository.findById(profilDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(
                                    "Profile not found with ID: " + profilDTO.getId())))
                    .collect(Collectors.toSet());

            // Créer une nouvelle instance de thème de formation
            ThemeFormation entity = themeFormationMapper.toEntity(dto);

            // Associer les entités Direction, Utilisateur et PlanFormation au thème de
            // formation
            entity.setDirection(optionaldirection.get());
            entity.setResponsableSuivi(optionaluser.get());
            entity.setPlanFormation(optionalplanformation.get());
            entity.setProfils(profils);

            // Enregistrer le thème de formation dans la base de données
            ThemeFormation savedEntity = themeFormationRepository.save(entity);

            // Mapper l'entité sauvegardée vers un DTO et le retourner
            return themeFormationMapper.toDTO(savedEntity);
        } else {
            // Gérer le cas où les entités Direction, Utilisateur ou PlanFormation ne sont
            // pas trouvées
            throw new EntityNotFoundException("Direction, Utilisateur or PlanFormation not found");
        }
    }

    // @Override
    // @Transactional(readOnly = true)
    // public List<ThemeFormationDTO> getThemeFormationsByPlanFormationId(Long
    // planFormationId) {
    // // Utilisez la méthode du repository pour récupérer les thèmes de formation
    // par
    // // ID de plan de formation
    // List<ThemeFormation> themeFormations =
    // themeFormationRepository.findByPlanFormationId(planFormationId);

    // // Mapper les entités en DTOs
    // List<ThemeFormationDTO> themeFormationDTOs = themeFormations.stream()
    // .map(themeFormationMapper::toDTO)
    // .collect(Collectors.toList());

    // return themeFormationDTOs;
    // }

    // @Override
    // @Transactional(readOnly = true)
    // public List<ThemeFormationDTO> getThemeFormationsByPlanFormationId(Long
    // planFormationId) {
    // // Utilisez la méthode du repository pour récupérer les thèmes de formation
    // par
    // // ID de plan de formation
    // List<ThemeFormation> themeFormations =
    // themeFormationRepository.findByPlanFormationId(planFormationId);

    // // Charger les profils pour chaque ThemeFormation
    // themeFormations.forEach(themeFormation -> {
    // Set<Profile> profils = themeFormation.getProfils();
    // profils.size(); // Pour charger les profils depuis la base de données (s'il y
    // en a)
    // });

    // // Mapper les entités en DTOs
    // List<ThemeFormationDTO> themeFormationDTOs = themeFormations.stream()
    // .map(themeFormationMapper::toDTO)
    // .collect(Collectors.toList());

    // return themeFormationDTOs;
    // }

    @Override
   // @Transactional(readOnly = true)
    public List<ThemeFormationDTO> getThemeFormationsByPlanFormationId(Long planFormationId) {
        List<ThemeFormation> themeFormations = themeFormationRepository.findByPlanFormationId(planFormationId);

        // Charger les directions pour chaque ThemeFormation
        themeFormations.forEach(themeFormation -> {
            Direction direction = themeFormation.getDirection();
            direction.getCode(); // Pour charger la direction depuis la base de données (si nécessaire)
        });

        // Mapper les entités en DTOs
        List<ThemeFormationDTO> themeFormationDTOs = themeFormations.stream()
                .map(themeFormation -> {
                    ThemeFormationDTO dto = themeFormationMapper.toDTO(themeFormation);
                    dto.setDirection(themeFormation.getDirection());
                    dto.setFormations(formationRepository.findByThemeFormation(themeFormation));
                    dto.setResponsableSuivi(utilisateurRepository.findById(themeFormation.getResponsableSuivi().getId()).get());
                    return dto;
                })
                .collect(Collectors.toList());

        return themeFormationDTOs;
    }
    @Override
   // @Transactional(readOnly = true)
    public ThemeFormationDTO getThemeFormationById(Long id) {
        ThemeFormation themeFormation = themeFormationRepository.findById(id).orElse(null);

        Direction direction = themeFormation.getDirection();
        // Utilisez votre mapper pour mapper l'entité vers un DTO
        ThemeFormationDTO themeFormationDTO = themeFormationMapper.toDTO(themeFormation);
        // Set les informations de la direction dans le DTO
        themeFormationDTO.setDirection(direction);
        return themeFormation != null ? themeFormationMapper.toDTO(themeFormation) : null;
    }

    // @Override
    // public List<CentralLevel> getUsersByDirection(Long direction) {
    // return utilisateurService.getUsersByDirectionId(direction);
    // }

}
