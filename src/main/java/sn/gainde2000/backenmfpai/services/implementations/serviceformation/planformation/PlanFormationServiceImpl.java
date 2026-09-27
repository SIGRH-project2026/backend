package sn.gainde2000.backenmfpai.services.implementations.serviceformation.planformation;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.BooleanBuilder;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.QPlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.mappers.serviceformation.planformation.PlanFormationMapper;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.PlanFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.StatutPlanFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation.IPlanFormationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation.IThemeFormationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationModificationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanFormationServiceImpl implements IPlanFormationService {
    private final PlanFormationRepository planFormationRepository;
    private final PlanFormationMapper planFormationMapper;
    private final IUtilisateurRepository utilisateurRepository;
    private final StatutPlanFormationRepository statutRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final StatutPlanFormationRepository statutPlanFormationRepository;
    private final IThemeFormationService iThemeFormationService;

    @Override
 //   @Transactional
    public PlanFormation savePlanFormation(MultipartFile[] files, PlanFormationDTO planFormationDTO) {
        // Vérifier les chevauchements de dates
        List<PlanFormation> plansWithOverlappingDates = planFormationRepository.findByDateDebutBetweenOrDateFinBetween(
                planFormationDTO.getDateDebut(),
                planFormationDTO.getDateFin(),
                planFormationDTO.getDateDebut(),
                planFormationDTO.getDateFin());

        if (!plansWithOverlappingDates.isEmpty()) {
            throw new RuntimeException(
                    "Les dates du nouveau plan de formation se chevauchent avec un plan de formation existant.");
        }

        // Vérifier la périodicité des plans de formation
        Date startDate = planFormationDTO.getDateDebut();
        Date endDate = DateUtils.addYears(startDate, 3); // Ajouter trois ans à la date de début

        List<PlanFormation> plansWithinThreeYears = planFormationRepository.findByDateDebutBetween(startDate, endDate);

        if (!plansWithinThreeYears.isEmpty()) {
            throw new RuntimeException(
                    "Un plan de formation existe déjà dans la période de trois ans à partir de la date de début du nouveau plan.");
        }

        // Récupérer l'utilisateur depuis l'ID
        Optional<Utilisateur> optionalUser = utilisateurRepository.findById(planFormationDTO.getCreatedByUserId());
        // Récupérer le statut depuis l'ID
        Optional<StatutPlanFormation> optionalStatut = statutRepository
                .findById(planFormationDTO.getStatutPlanFormationId());

        if (optionalUser.isPresent()) {
            Utilisateur createdByUser = optionalUser.get();

            // Créer le Plan de formation
            PlanFormation planFormation = new PlanFormation();

            planFormation.setReference(planFormationDTO.getReference());
            planFormation.setTitre(planFormationDTO.getTitre());
            planFormation.setCommentaire(planFormationDTO.getCommentaire());
            planFormation.setDateDebut(planFormationDTO.getDateDebut());
            planFormation.setDateFin(planFormationDTO.getDateFin());
            planFormation.setDatePublication(planFormationDTO.getDatePublication());

            // Associer le Plan de formation avec l'utilisateur créateur
            planFormation.setCreatedBy(createdByUser);
            planFormation.setStatutPlanFormation(optionalStatut.get());

            if (files != null && files.length > 0) {
                Set<File> uploadedFiles = new HashSet<>();
                for (MultipartFile file : files) {
                    FileRspDTO uploadedFileDto = fileService.storeFile(file, "planFormationFiles", false);

                    // Convertir FileRspDTO en File directement dans la méthode
                    File uploadedFile = new File();
                    uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                    uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                    uploadedFile.setIdAppartenance(0);
                    // Associer le fichier au plan de formation
                    // uploadedFile.setPlanFormation(planFormation);
                    uploadedFiles.add(uploadedFile);
                }
                planFormation.setFiles(uploadedFiles);
            }

            // Enregistrer le Plan de formation
            return planFormationRepository.save(planFormation);

        } else {
            // Gérer le cas où l'utilisateur n'est pas trouvé
            throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + planFormationDTO.getCreatedByUserId());
        }
    }

    @Override
    public Page<PlanFormation> getPagePlanFormation(int page, int size, String sortBy, boolean sortByDescending) {
        Sort sort = sortByDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return planFormationRepository.findAll(pageRequest);
    }

    @Override
    public PlanFormation getPlanFormationById(Long id) {
        return planFormationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlanFormation not found with ID: " + id));
    }

    @Override
 //   @Transactional
    public PlanFormation modifyPlanFormation(Long id, PlanFormationModificationDTO modificationDTO) {
        // Récupérer le plan de formation à modifier par son ID
        PlanFormation planFormation = planFormationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlanFormation not found with ID: " + id));

        // Appliquer les modifications spécifiées dans le DTO
        if (modificationDTO.getTitre() != null) {
            planFormation.setTitre(modificationDTO.getTitre());
        }
        if (modificationDTO.getCommentaire() != null) {
            planFormation.setCommentaire(modificationDTO.getCommentaire());
        }
        if (modificationDTO.getDateDebut() != null) {
            planFormation.setDateDebut(modificationDTO.getDateDebut());
        }
        if (modificationDTO.getDatePublication() != null) {
            planFormation.setDatePublication(modificationDTO.getDatePublication());
        }
        if (modificationDTO.getDateFin() != null) {
            planFormation.setDateFin(modificationDTO.getDateFin());
        }
        if (modificationDTO.getFichiersAAjouter() != null) {
            Set<File> filesToAdd = modificationDTO.getFichiersAAjouter().stream()
                    .map(fileId -> fileRepository.findById(fileId)
                            .orElseThrow(() -> new EntityNotFoundException("File not found with ID: " + fileId)))
                    .collect(Collectors.toSet());
            planFormation.getFiles().addAll(filesToAdd);
        }
        // Supprimer les fichiers spécifiés de la liste des fichiers joints
        if (modificationDTO.getFichiersASupprimer() != null) {
            Set<File> filesToRemove = modificationDTO.getFichiersASupprimer().stream()
                    .map(fileId -> fileRepository.findById(fileId)
                            .orElseThrow(() -> new EntityNotFoundException("File not found with ID: " + fileId)))
                    .collect(Collectors.toSet());
            planFormation.getFiles().removeAll(filesToRemove);
        }

        // Enregistrer et retourner le plan de formation modifié
        return planFormationRepository.save(planFormation);
    }

    @Override
   // @Transactional
    public PlanFormation changePlanFormationStatus(Long id, String statutCode) {
        // Récupérer le plan de formation à modifier par son ID
        PlanFormation planFormation = planFormationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PlanFormation not found with ID: " + id));

        // Récupérer le statut du plan de formation par son code
        StatutPlanFormation statutPlanFormation = statutPlanFormationRepository.findByCode(statutCode);
        if (statutPlanFormation == null) {
            throw new EntityNotFoundException("StatutPlanFormation not found with code: " + statutCode);
        }

        // Modifier le statut du plan de formation
        planFormation.setStatutPlanFormation(statutPlanFormation);

        // Enregistrer et retourner le plan de formation modifié
        return planFormationRepository.save(planFormation);
    }

    /*Baba dieme*/
    @Override
    public Response<Object> getPagePlanFormationEnCours(int page, int size, String sortBy, boolean sortByDescending) {
        BooleanBuilder builder = new BooleanBuilder();
        QPlanFormation qPlanFormation = QPlanFormation.planFormation;
        builder.and(
               qPlanFormation.statutPlanFormation.code.like("ENCOURS")
        );
        Sort sort = sortByDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return Response.ok().setPayload(Objects.nonNull(builder.getValue()) ? planFormationRepository.findAll(builder.getValue(),pageRequest):
                planFormationRepository.findAll(pageRequest));

    }

    /*fin*/

}