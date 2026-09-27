package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

import jakarta.persistence.EntityNotFoundException;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.OffreTechniqueFinanciere;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PlanningFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.StatutOffreTechnique;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.OffreTechniqueFinanciereRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.PlanningFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.RapportRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.StatutOffreTechniqueRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IOffreTechniqueFinanciereService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IPlanningFormationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IRapportService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.PlanningFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.RapportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
// @Sl4j
public class PlanningFormationServiceImpl implements IPlanningFormationService {

    private final PlanningFormationRepository planningFormationRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FormationRepository formationRepository;

    private final PlanningFormationDTO planningFormationDTO;

    public PlanningFormationServiceImpl(PlanningFormationRepository planningFormationRepository,
            FileService fileService,
            FileRepository fileRepository, FormationRepository formationRepository,
            PlanningFormationDTO planningFormationDTO) {
        this.planningFormationRepository = planningFormationRepository;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.planningFormationDTO = planningFormationDTO;
        this.formationRepository = formationRepository;

    }

    @Override
    public PlanningFormation createPlanningFormation(MultipartFile[] files,
            PlanningFormationDTO planningFormationDTO) {
        PlanningFormation planningEntity = new PlanningFormation();

        // Récupérez l'entité Formation à partir de la base de données en fonction de
        // son identifiant
        Formation formation = formationRepository.findById(planningFormationDTO.getFormation().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Formation non trouvée avec l'ID : " + planningFormationDTO.getFormation().getId()));

        planningEntity.setFormation(formation);

        if (files != null && files.length > 0) {
            Set<File> uploadedFiles = new HashSet<>();
            for (MultipartFile file : files) {
                FileRspDTO uploadedFileDto = fileService.storeFile(file, "planningFormation", false);

                File uploadedFile = new File();
                uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                uploadedFile.setIdAppartenance(0);
                fileRepository.save(uploadedFile);
                uploadedFiles.add(uploadedFile);
            }
            planningEntity.setFiles(uploadedFiles);
        }
        planningEntity.setCommentaire(planningFormationDTO.getCommentaire());

        return planningFormationRepository.save(planningEntity);
    }

    // @Override
    // public List<OffreTechniqueFinanciere>
    // getOffreTechniqueFinanciereByFormationId(Long formationId) {
    // return offreTechniqueFinanciereRepository.findByFormationId(formationId);
    // }

    @Override
    @Transactional(readOnly = true)
    public List<PlanningFormationDTO> getPlanningByFormationId(Long planFormationId) {
        // List<PlanningFormation> offres = planningFormationRepository
        // .findByFormationId(planFormationId);

        List<PlanningFormation> offres = planningFormationRepository
                .findByFormationId(planFormationId);

        List<PlanningFormationDTO> offreDTOs = offres.stream()
                .map((PlanningFormation offre) -> {
                    PlanningFormationDTO dto = mapToDTO(offre);
                    dto.getFiles().size();
                    return dto;
                }).collect(Collectors.toList());

        return offreDTOs;
    }

    @Override
    public void deletePlanning(Long id) {
        planningFormationRepository.deleteById(id);
    }

    @Override
    public List<PlanningFormation> getAllPlanning() {
        return planningFormationRepository.findAll();
    }

    private PlanningFormationDTO mapToDTO(PlanningFormation offre) {
        PlanningFormationDTO offreDTO = new PlanningFormationDTO();
        offreDTO.setId(offre.getId());
        offreDTO.setCommentaire(offre.getCommentaire());
        offreDTO.setFormation(offre.getFormation());
        offreDTO.setFiles(offre.getFiles()); // Assurez-vous que getFiles() retourne une liste non nulle
        return offreDTO;
    }

}