package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.QFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.StatutFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.serviceformation.formation.FormationMapper;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.StatutFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.TypeFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.planformation.ThemeFormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EtablissementRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IFormationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.FormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TrainingStatusCountDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@Service
@RequiredArgsConstructor
@Transactional
public class FormationServiceImpl implements IFormationService {
    private final BusinessNotificationService businessNotifications;

    private final FormationNotifications formationNotifications;

        private final FormationRepository formationRepository;
        private final ThemeFormationRepository themeFormationRepository;
        private final StatutFormationRepository statutFormationRepository;
        private final TypeFormationRepository typeFormationRepository;
        private final EtablissementRepository etablissementRepository;
        private final IUtilisateurRepository utilisateurRepository;
        private final DeconcentratedLevelRepository deconcentratedLevelRepository;
        private final FileRepository fileRepository;
        private final FileService fileService;
        private final FormationMapper formationMapper;
        private final ParticipantDefinitifServiceImpl participantDefinitifService;
        private final IUtilisateur iUtilisateur;
        private final CentralLevelRepository centralLevelRepository;
        private final IUtilisateur utilisateurService;

//        public FormationServiceImpl(ParticipantDefinitifServiceImpl participantDefinitifService,
//                        EtablissementRepository etablissementRepository, FormationMapper formationMapper,
//                        FileRepository fileRepository,
//                        FileService fileService,
//                        FormationRepository formationRepository,
//                        DeconcentratedLevelRepository deconcentratedLevelRepository,
//                        ThemeFormationRepository themeFormationRepository,
//                        StatutFormationRepository statutFormationRepository,
//                        IUtilisateurRepository utilisateurRepository, TypeFormationRepository typeFormationRepository) {
//                this.formationRepository = formationRepository;
//                this.themeFormationRepository = themeFormationRepository;
//                this.statutFormationRepository = statutFormationRepository;
//                this.utilisateurRepository = utilisateurRepository;
//                this.typeFormationRepository = typeFormationRepository;
//                this.deconcentratedLevelRepository = deconcentratedLevelRepository;
//                this.fileRepository = fileRepository;
//                this.fileService = fileService;
//                this.formationMapper = formationMapper;
//                this.etablissementRepository = etablissementRepository;
//                this.participantDefinitifService = participantDefinitifService;
//        }

        // @Override
        // public FormationDTO createFormation(FormationDTO formationDTO) {
        // Formation formation = new Formation();
        // formation.setTypeFormation(typeFormationRepository.findByCode(formationDTO.getTypeFormation().getCode()));
        // formation.setThemeFormation(themeFormationRepository.findById(formationDTO.getThemeFormationId())
        // .orElseThrow(() -> new EntityNotFoundException("Theme de formation
        // introuvable")));
        // formation.setIntitule(formationDTO.getIntitule());
        // formation.setDateDebut(formationDTO.getDateDebut());
        // formation.setDateFin(formationDTO.getDateFin());
        // formation.setDuree(formationDTO.getDuree());
        // formation.setCout(formationDTO.getCout());
        // formation.setDescription(formationDTO.getDescription());
        // formation.setStatutFormation(statutFormationRepository.findByCode(formationDTO.getStatutFormation().getCode()));
        // Formation savedFormation = formationRepository.save(formation);
        // return mapToDTO(savedFormation);
        // }

        // @Override
        // public FormationDTO createFormation(FormationDTO formationDTO) {
        // Formation formation = new Formation();
        // formation.setTypeFormation(
        // typeFormationRepository.findByCode(formationDTO.getTypeFormation().getCode()));
        // formation.setThemeFormation(themeFormationRepository.findById(formationDTO.getThemeFormation().getId())
        // .orElseThrow(() -> new EntityNotFoundException("Theme de formation
        // introuvable")));
        // formation.setIntitule(formationDTO.getIntitule());
        // formation.setDateDebut(formationDTO.getDateDebut());
        // formation.setDateFin(formationDTO.getDateFin());
        // formation.setPrestataires(formationDTO.getPrestataires());
        // formation.setDuree(formationDTO.getDuree());
        // formation.setReference(formationDTO.getReference());
        // formation.setCout(formationDTO.getCout());
        // formation.setDescription(formationDTO.getDescription());
        // formation.setStatutFormation(
        // statutFormationRepository.findByCode(formationDTO.getStatutFormation().getCode()));

        // Formation savedFormation = formationRepository.save(formation);
        // return mapToDTO(savedFormation);
        // }

        // @Override
        // @Transactional
        // public Formation createFormation(
        // MultipartFile cahierCharge, FormationDTO formationDTO) {

        // Formation formation = new Formation();

        // formation.setTypeFormation(
        // typeFormationRepository.findByCode(formationDTO.getTypeFormation().getCode()));

        // formation.setThemeFormation(themeFormationRepository.findById(formationDTO.getThemeFormation().getId())
        // .orElseThrow(() -> new EntityNotFoundException("Theme de formation
        // introuvable")));
        // formation.setIntitule(formationDTO.getIntitule());
        // formation.setDateDebut(formationDTO.getDateDebut());
        // formation.setDateFin(formationDTO.getDateFin());
        // formation.setPrestataires(formationDTO.getPrestataires());
        // formation.setDuree(formationDTO.getDuree());
        // formation.setReference(formationDTO.getReference());
        // formation.setCout(formationDTO.getCout());
        // formation.setDescription(formationDTO.getDescription());
        // formation.setStatutFormation(
        // statutFormationRepository.findByCode(formationDTO.getStatutFormation().getCode()));
        // // Optional<Etablissement> optionalEtablissement = etablissementRepository
        // // .findByCode(formationDTO.getEff().getCode());
        // // Etablissement etablissement = optionalEtablissement
        // // .orElseThrow(() -> new EntityNotFoundException("Etablissement
        // introuvable"));
        // // formation.setEff(etablissement);
        // formation.setEffCode(formationDTO.getEffCode());
        // formation.setSpecialiteCode(formationDTO.getSpecialiteCode());

        // if (cahierCharge != null) {
        // FileRspDTO uploadedFileDto = fileService.storeFile(cahierCharge,
        // "cahierCharge", false);
        // File uploadedFile = new File();
        // uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
        // uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
        // uploadedFile.setIdAppartenance(0);
        // fileRepository.save(uploadedFile);
        // formation.setCahierCharge(uploadedFile);
        // }

        // return formationRepository.save(formation);
        // }

        @Override
        @Transactional
        public Formation createFormation(
                        MultipartFile cahierCharge, FormationDTO formationDTO) {

                Formation formation = new Formation();

                formation.setTypeFormation(
                                typeFormationRepository.findByCode(formationDTO.getTypeFormation().getCode()));

                if (formationDTO.getThemeFormation() != null) {
                        formation.setThemeFormation(
                                        themeFormationRepository.findById(formationDTO.getThemeFormation().getId())
                                                        .orElseThrow(() -> new EntityNotFoundException(
                                                                        "Theme de formation introuvable")));
                }

                formation.setIntitule(formationDTO.getIntitule());
                formation.setDateDebut(formationDTO.getDateDebut());
                formation.setDateFin(formationDTO.getDateFin());
                formation.setDateEnvoi(formationDTO.getDateEnvoi());
                formation.setDateReception(formationDTO.getDateReception());
                formation.setPrestataires(formationDTO.getPrestataires());
                formation.setDuree(formationDTO.getDuree());
                formation.setReference(formationDTO.getReference());
                formation.setCout(formationDTO.getCout());
                formation.setDescription(formationDTO.getDescription());
                formation.setStatutFormation(
                                statutFormationRepository.findByCode(formationDTO.getStatutFormation().getCode()));
                formation.setEffCode(formationDTO.getEffCode());
                formation.setSpecialiteCode(formationDTO.getSpecialiteCode());
                formation.setNombrePlace(formationDTO.getNombrePlace());

                if (cahierCharge != null) {
                        FileRspDTO uploadedFileDto = fileService.storeFile(cahierCharge, "cahierCharge", false);
                        File uploadedFile = new File();
                        uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                        uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                        uploadedFile.setIdAppartenance(0);
                        fileRepository.save(uploadedFile);
                        formation.setCahierCharge(uploadedFile);
                }

                Formation saved = formationRepository.save(formation);
                formationNotifications.notifyConcerned(saved, "création enregistrée");
                businessNotifications.notify(iUtilisateur.getCurrentUser(), "Suivi de formation",
                        "Formation " + saved.getReference() + " : création enregistrée.");
                return saved;
        }

        @Override
        public FormationDTO getFormationById(Long id) {
                Formation formation = formationRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Formation introuvable"));
                return mapToDTO(formation);
        }

        // @Override
        // public List<FormationDTO> getAllFormations() {
        // List<Formation> formations = formationRepository.findAll();
        // // return formations.stream().collect(Collectors.toList());
        // // return
        // formations.stream().map(this::mapToDTO).collect(Collectors.toList());
        // formations.forEach(formation -> {
        // ThemeFormation themeFormation = formation.getThemeFormation();
        // themeFormation.getDirection().getCode(); // Charger la direction
        // themeFormation.getPlanFormation().getId(); // Charger le plan de formation
        // themeFormation.getProfils().size(); // Charger les profils
        // });

        // return formations.stream()
        // .map(formationMapper::entityToDto)
        // .collect(Collectors.toList());
        // }
        @Override
        public List<FormationDTO> getAllFormations() {
                List<Formation> formations = formationRepository.findAll();
                formations.forEach(formation -> {
                        ThemeFormation themeFormation = formation.getThemeFormation();
                        if (themeFormation != null) {
                                themeFormation.getDirection().getCode(); // Charger la direction
                                themeFormation.getPlanFormation().getId(); // Charger le plan de formation
                                themeFormation.getProfils().size(); // Charger les profils
                        }
                });

                return formations.stream()
                                .map(formationMapper::entityToDto)
                                .collect(Collectors.toList());
        }

        @Override
        public FormationDTO getFormationByReference(String reference) {
                Formation formation = formationRepository.findByReference(reference)
                                .orElseThrow(
                                                () -> new EntityNotFoundException(
                                                                "Formation introuvable avec la référence : "
                                                                                + reference));
                return mapToDTO(formation);
        }

        @Override
        public void deleteFormation(Long formationId) {
                Formation formation = formationRepository.findById(formationId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Formation introuvable avec l'ID : " + formationId));
                formationRepository.delete(formation);
        }

        // @Override
        // public FormationDTO updateFormation(Long formationId, FormationDTO
        // formationDTO) {
        // Formation formation = formationRepository.findById(formationId)
        // .orElseThrow(() -> new EntityNotFoundException(
        // "Formation introuvable avec l'ID : " + formationId));

        // // Mettez à jour les champs de la formation avec les données du DTO
        // formation.setTypeFormation(
        // typeFormationRepository.findByCode(formationDTO.getTypeFormation().getCode()));
        // formation.setThemeFormation(
        // themeFormationRepository.findById(formationDTO.getThemeFormation().getId())
        // .orElse(null));
        // formation.setIntitule(formationDTO.getIntitule());
        // formation.setDateDebut(formationDTO.getDateDebut());
        // formation.setDateFin(formationDTO.getDateFin());
        // formation.setDuree(formationDTO.getDuree());
        // formation.setPrestataires(formationDTO.getPrestataires());
        // formation.setCout(formationDTO.getCout());
        // formation.setDescription(formationDTO.getDescription());
        // formation.setEffCode(formationDTO.getEffCode());
        // formation.setSpecialiteCode(formationDTO.getSpecialiteCode());
        // formation.setStatutFormation(
        // statutFormationRepository.findByCode(formationDTO.getStatutFormation().getCode()));

        // Formation updatedFormation = formationRepository.save(formation);
        // return mapToDTO(updatedFormation);
        // }

        @Override
        public FormationDTO updateFormation(Long formationId, FormationDTO formationDTO) {
                Formation formation = formationRepository.findById(formationId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Formation introuvable avec l'ID : " + formationId));

                String previousStatus = formation.getStatutFormation() == null ? null : formation.getStatutFormation().getCode();
                formation.setTypeFormation(
                                typeFormationRepository.findByCode(formationDTO.getTypeFormation().getCode()));

                if (formationDTO.getThemeFormation() != null) {
                        formation.setThemeFormation(
                                        themeFormationRepository.findById(formationDTO.getThemeFormation().getId())
                                                        .orElseThrow(() -> new EntityNotFoundException(
                                                                        "Theme de formation introuvable")));
                } else {
                        formation.setThemeFormation(null); // Ajoutez cette ligne pour définir null si pas présent
                }

                formation.setIntitule(formationDTO.getIntitule());
                formation.setDateDebut(formationDTO.getDateDebut());
                formation.setDateFin(formationDTO.getDateFin());
                formation.setDateEnvoi(formationDTO.getDateEnvoi());
                formation.setDateReception(formationDTO.getDateReception());
                formation.setDuree(formationDTO.getDuree());
                formation.setPrestataires(formationDTO.getPrestataires());
                formation.setCout(formationDTO.getCout());
                formation.setDescription(formationDTO.getDescription());
                formation.setEffCode(formationDTO.getEffCode());
                formation.setSpecialiteCode(formationDTO.getSpecialiteCode());
                formation.setNombrePlace(formationDTO.getNombrePlace());
                formation.setStatutFormation(
                                statutFormationRepository.findByCode(formationDTO.getStatutFormation().getCode()));

                Formation updatedFormation = formationRepository.save(formation);
                if (!Objects.equals(previousStatus, updatedFormation.getStatutFormation().getCode())) {
                    formationNotifications.notifyConcerned(updatedFormation, "statut " + updatedFormation.getStatutFormation().getCode());
                }
                return mapToDTO(updatedFormation);
        }

        @Override
        public FormationDTO updateFormationStatus(Long id, String newStatutFormationCode) {
                // Rechercher la formation par son ID
                Formation formation = formationRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Formation introuvable avec l'ID : " + id));

                // Rechercher le nouveau statut de formation par son code
                StatutFormation newStatutFormation = statutFormationRepository.findByCode(newStatutFormationCode);
                if (newStatutFormation == null) {
                        throw new EntityNotFoundException(
                                        "Statut de formation introuvable avec le code : " + newStatutFormationCode);
                }

                // Mettre à jour le statut de la formation avec le nouveau statut
                boolean changed = formation.getStatutFormation() == null
                        || !Objects.equals(formation.getStatutFormation().getCode(), newStatutFormationCode);
                formation.setStatutFormation(newStatutFormation);

                // Enregistrer la formation mise à jour dans la base de données
                Formation savedFormation = formationRepository.save(formation);

                // Mapper la formation mise à jour vers le DTO et le retourner
                if (changed) formationNotifications.notifyConcerned(savedFormation, "statut " + newStatutFormationCode);
                return mapToDTO(savedFormation);
        }

        private FormationDTO mapToDTO(Formation formation) {
                FormationDTO formationDTO = new FormationDTO();
                formationDTO.setId(formation.getId());
                formationDTO.setTypeFormation(formation.getTypeFormation());
                formationDTO.setThemeFormation(formation.getThemeFormation());
                formationDTO.setIntitule(formation.getIntitule());
                formationDTO.setDateDebut(formation.getDateDebut());
                formationDTO.setDateFin(formation.getDateFin());
                formationDTO.setDateEnvoi(formation.getDateEnvoi());
                formationDTO.setDateReception(formation.getDateReception());
                formationDTO.setCout(formation.getCout());
                formationDTO.setPrestataires(formation.getPrestataires());
                formationDTO.setReference(formation.getReference());
                formationDTO.setDescription(formation.getDescription());
                formationDTO.setDuree(formation.getDuree());
                formationDTO.setStatutFormation(formation.getStatutFormation());
                formationDTO.setCahierCharge(formation.getCahierCharge());
                formationDTO.setEffCode(formation.getEffCode());
                formationDTO.setSpecialiteCode(formation.getSpecialiteCode());
                formationDTO.setNombrePlace(formation.getNombrePlace());

                if (formation.getThemeFormation() != null) {
                        formationDTO.setProfils(formation.getThemeFormation().getProfils());
                        formationDTO.setPlanFormation(formation.getThemeFormation().getPlanFormation());
                        formationDTO.setDirection(formation.getThemeFormation().getDirection());
                }

                return formationDTO;
        }

        /* by baba dieme */

        @Override
        public List<FormationDTO> getAllFormationsContinue(String type) {
                int i = 0;
                List<Formation> formations = formationRepository.findAll();
                List<Formation> formationsContinue = new ArrayList<>();
                while (i < formations.size()) {
                        if (formations.get(i).getTypeFormation().getCode().equals(type)) {
                                formationsContinue.add(formations.get(i));
                        }
                        i++;
                }
                // return formations.stream().collect(Collectors.toList());
                return formationsContinue.stream().map(this::mapToDTO).collect(Collectors.toList());
        }




        /* fin */

        @Override
        public TrainingStatusCountDTO getTrainingStatusCounts() {
                Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());
                TrainingStatusCountDTO dto = new TrainingStatusCountDTO();
                dto.setClosedTrainings(formationRepository.countByStatus("CLOTUREER"));
                dto.setPlannedTrainings(formationRepository.countByStatus("PUBLIEER"));
                dto.setOngoingTrainings(formationRepository.countByStatus("ENCOURS"));
                // un utilisateur sans direction rattachée (ex: ADMIN-DRH) voit le total global, toutes directions confondues
                String directionCode = optionalCentralLevel
                        .map(CentralLevel::getDirection)
                        .map(Direction::getCode)
                        .orElse(null);
                dto.setFormedAgents(directionCode != null
                        ? participantDefinitifService.countFormedAgentsByDirection(directionCode)
                        : participantDefinitifService.countFormedAgents());
                return dto;
        }

        @Override
        public Response<Object> formationByUser(int page, int size, String filter) {
                Page<Formation> formationDTOPage = Page.empty();
                Page<Formation> formationPage = Page.empty();
                BooleanBuilder builder = new BooleanBuilder();

                Pageable pageRequest = createPageRequestUsing(page, size);
                var utilisateurConnected = utilisateurService.getCurrentUser();
                QFormation qFormation = QFormation.formation;

                List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
                Profile profile = profiles.get(0);
                List<Formation> formations = new ArrayList<>();



                if (profile.getCode().equals("Formateur-EFF") &&
                        utilisateurConnected.getTypeMatricule().getCode().equals("MATCON")) {

                        List<Formation> diplomante = formationRepository.getFormtionDiplomanteList("DIPLOMANTE", PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

                        List<Formation> pageContent = List.of();


                        for (Formation diplomanteFormation : diplomante) {

                                if (diplomanteFormation.getSpecialiteCode()
                                        .equals(utilisateurConnected.getSpeciality().getCode())) {

                                        if (diplomanteFormation.getEffCode().equals("CFEN") ||
                                                diplomanteFormation.getEffCode().equals("ENFMEPT")) {



                                                if (Objects.nonNull(diplomanteFormation.getNombrePlace())) {



                                                        if (utilisateurService
                                                                .getPrioritaire(diplomanteFormation.getNombrePlace(),
                                                                        diplomanteFormation.getSpecialiteCode())) {
                                                             //   System.out.println("matri " + diplomanteFormation);

                                                                formations.add(diplomanteFormation);
                                                        }
                                                }
                                        }else {

                                                formations.add(diplomanteFormation);
                                        }
                                }




                        }

                        if (!formations.isEmpty()) {
                                int start = (int) pageRequest.getOffset();
                                int end = Math.min((start + pageRequest.getPageSize()), formations.size());

                                if (start < formations.size()) {
                                        pageContent = formations.subList(start, end);
                                        formationDTOPage = new PageImpl<>(pageContent, pageRequest, formations.size());

                                        if(formationDTOPage.getTotalElements() > 0){
                                                formationPage = formationDTOPage;
                                        }
                                }
                        }


                }


                        if ((profile.getCode().equals("Formateur-EFF") &&
                        utilisateurConnected.getTypeMatricule().getCode().equals("MATFONC")) ||
                        (profile.getCode().equals("Formateur-EFF") &&
                                utilisateurConnected.getTypeMatricule().getCode().equals("MATDEE")) ||
                        (profile.getCode().equals("Formateur-EFF") &&
                                utilisateurConnected.getTypeMatricule().getCode().equals("MATVAC"))) {


                        builder.and(qFormation.themeFormation.profils.contains(profile));
                        formationPage = Objects.nonNull(builder.getValue())
                                ? formationRepository.findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                                : formationRepository.getFormtionDiplomante("DIPLOMANTE", PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));
                }

                // Default case
                else if(!profile.getCode().equals("Formateur-EFF")) {

                        builder.and(qFormation.themeFormation.profils.contains(profile));
                        formationPage = Objects.nonNull(builder.getValue())
                                ? formationRepository.findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                                : formationRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));


                }

                // If still null, return an empty page
                if (formationPage == null) {

                        formationPage = Page.empty(pageRequest);
                }

                Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                        .size(formationPage.getSize())
                        .number(formationPage.getNumber())
                        .totalElements(formationPage.getTotalElements())
                        .totalPages(formationPage.getTotalPages())
                        .build();

                return Response.ok()
                        .setPayload(formationPage.getContent())
                        .setMetadata(pageMetadata)
                        .setMessage("Liste des formations");
        }


        @Override
        public Response<Object> getAllFormationsByTypeFormation(String type, int page, int size) {
                Page<Formation> formationPage = Page.empty();
                formationPage = formationRepository.getFormtionByTypeFormationPublisher(type,"PUBLIEER" ,"ENCOURS", PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

                Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                        .size(formationPage.getSize())
                        .number(formationPage.getNumber())
                        .totalElements(formationPage.getTotalElements())
                        .totalPages(formationPage.getTotalPages())
                        .build();

                return Response.ok()
                        .setPayload(formationPage.getContent())
                        .setMetadata(pageMetadata)
                        .setMessage("Liste des formations");
        }





        private Pageable createPageRequestUsing(int page, int size) {
                return PageRequest.of(page, size);
        }



}
