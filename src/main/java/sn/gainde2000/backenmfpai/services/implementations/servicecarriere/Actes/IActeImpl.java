package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.NumberPath;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.commons.Notification.INotification;
import sn.gainde2000.backenmfpai.commons.Notification.Notification;
import sn.gainde2000.backenmfpai.commons.Notification.NotificationRepository;
import sn.gainde2000.backenmfpai.commons.exception.GenericApiException;
import sn.gainde2000.backenmfpai.commons.utils.JasperGenerator;
import sn.gainde2000.backenmfpai.commons.utils.helpers.Utilitaire;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.OrigineDemandeurLog;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.Files.FileMapper;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Actes.ActeMapper;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.PieceJointesMapper;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.implementations.files.FileImpl;
import sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur.UtilisateurImpl;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IActe;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IPieceJointes;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IStatutActe;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.ITypeActe;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.StatistiquesActeRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ValidesActesDate;

import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class IActeImpl implements IActe {
    private final ActeMapper acteMapper;
    private final PieceJointesMapper pieceJointesMapper;
    private final ActeRepository acteRepository;
    private final BordereauRepository bordereauRepository;
    private final FileRepository fileRepository;
    private final StatutActeRepository statutActeRepository;
    private final TypeActeRepository typeActeRepository;
    private final TypeAARepository typeAARepository;
    private final TypeAGRepository typeAGRepository;
    private final IUtilisateurRepository utilisateurRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final ObjectMapper objectMapper;
    private final FileMapper fileMapper;
    private final IStatutActe iStatutActe;
    private final ITypeActe iTypeActe;
    private final IUtilisateur iUtilisateur;
    private final FileService fileService;
    private final FileImpl fileImpl;
    private final MailService mailService;
    private final UtilisateurImpl iUser;
    private final IPieceJointes iPieceJointes;
    private final JasperGenerator jasperGenerator;
    private final TraitementActeRepository traitementActeRepository;
    private final IProfilRepository iProfilRepository;
    private final NotificationRepository notificationRepository;
    private final INotification iNotification;
    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public Response<Object> createActe(ActeDTO acteDTO/*,MultipartFile[] files*/) {
        try{
        log.info("------------------------INIT CREATION ACTE-----------------------");
        String niveau;
        Acte acte = new Acte();
        System.out.println(acteDTO.toString());
        Utilisateur agent = utilisateurRepository.findById(acteDTO.getIdAgent()).orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));
        acte.setAgent(agent);
        List<Profile> profiles = new ArrayList<>(agent.getProfils());
        System.out.println(profiles.toString());
        Profile p = profiles.get(0);
        if (agent.getTypeUser().equals("CEN")) {
            niveau="CEN";
            acte.setTypeAgent("CEN");
            acte.setDirection(acteDTO.getDirection());
            CentralLevel centralLevel = iUtilisateur.getUserCentral(agent.getId());
            if (centralLevel.getDirection().getCode().equalsIgnoreCase("DRH")) {
                acte.setDivision(acteDTO.getDivision());
            }
            else {
                if(centralLevel.getService()!=null)
                    acte.setService(centralLevel.getService());
            }
        }
        if (agent.getTypeUser().equals("DEC")) {
            niveau="DEC";
            DeconcentratedLevel deconcentratedLevel = iUtilisateur.getUserDeconected(agent.getId());
            acte.setIa(deconcentratedLevel.getIa());
            acte.setIef(deconcentratedLevel.getEtablissement().getIef());
            acte.setEtablissement(deconcentratedLevel.getEtablissement());
            acte.setTypeEtablissement(deconcentratedLevel.getEtablissement().getCode());
            acte.setTypeAgent("DEC");
            System.out.println("agent" + deconcentratedLevel.toString());
        }
        TypeAA typeAA = new TypeAA();
        TypeAG typeAG = new TypeAG();
        Utilisateur utilisateurConnect = iUtilisateur.getCurrentUser();
        Profile profile = utilisateurConnect.getProfils().stream().findFirst().get();
        StatutActe statutAct = switch (profile.getCode()) {
            case "ADMIN-DRH", "Assistant-DRH", "Coordinateur", "Admin-General", "Agent-ministre", "SG-Cabinet", "Directeur-DRH",
                    "Gestionnaire" -> statutActeRepository.findByCode("RECU-DRH").get();
              case "Chef-division-dfc", "Chef-division-dgcaa", "Chef-division-das", "Chef-division-dgpeec" ->
                    statutActeRepository.findByCode("VALIDEDIV").get();
            case "Chef-etablissement" ,"Chef-cfp","Chef-EFF"-> statutActeRepository.findByCode("VALIDECE").get();
            case "Chef-service" -> statutActeRepository.findByCode("VALIDESUP").get();
            case "Representant-IA" -> statutActeRepository.findByCode("VALIDEIA").get();
            case "Représentant-IEF" -> statutActeRepository.findByCode("VALIDEIEF").get();
            default -> statutActeRepository.findByCode("SOUMIS").get();
        };

        TraitementActe traitementActe = new TraitementActe();
        traitementActe.setTraitement(statutAct.getLibelle());
        traitementActe.setTraitrant(agent);
        TypeActe typeActe = typeActeRepository
                .findAllByCodeActeIgnoreCaseOrderByIdDesc(acteDTO.getCodetypeActe().trim())
                .stream().findFirst()
                .orElseThrow(() -> new GenericApiException("Le type d'acte sélectionné n'existe pas"));
        if (acteDTO.getCodetypeActe().equalsIgnoreCase("aa")) {
            typeAA = resolveTypeAA(acteDTO.getCodeTypeActeAA(), acteDTO.getAutreTypeActe());
            acte.setTypeAA(typeAA);
        } else {
            typeAG = resolveTypeAG(acteDTO.getCodeTypeActeAG(), acteDTO.getAutreTypeActe());
            acte.setTypeAG(typeAG);
        }
        acte.setDateDemandeActe(LocalDate.now());
        acte.setStatutActe(statutAct);
        acte.setTypeActe(typeActe);
        acte.setReferenceActe(Utilitaire.genererReférence());
        acte.setCommentaire(acteDTO.getCommentaire());
        if (acteDTO.getDateDebut() != null)
            acte.setDateDebut(acteDTO.getDateDebut());
        if (acteDTO.getDateFin() != null)
            acte.setDateFin(acteDTO.getDateFin());
        acte.setProfilDevantTraiter(profilTraitant(agent, agent.getTypeUser()));

        //set le profile traitant
        String profilTraitant = profilTraitant(agent, agent.getTypeUser());
        acte.setProfilDevantTraiter(profilTraitant);

        //envoie notification
        sendNotify(acte, profilTraitant, acte.getAgent().getTypeUser());

        Acte savedActe = acteRepository.saveAndFlush(acte);
        traitementActe.setActe(savedActe);
        traitementActeRepository.save(traitementActe);
        List<String> profilesTraiteurs = new ArrayList<>();
        profilesTraiteurs.add("Chef-division");
        List<Utilisateur> usersTraiteurs = utilisateurRepository.findByProfileCodes(profilesTraiteurs);
        for (Utilisateur u : usersTraiteurs) {
            mailService.sendMail(new MailInfosDTO(null, "Bonjour " + u.getPrenom() + " " + u.getNom() + "\nLa demande d'acte N°" + acte.getReferenceActe() + "vous a été soumise.\nMerci de procéder au traitement.", "Demande d'acte", null, u.getEmail()));
        }
        return Response.ok()
                .setPayload(acteMapper.toDto(savedActe))
                .setMessage("Création acte avec Succés");
        } catch(Exception e){
            log.error("------------------Erreur lors de la création de l'acte------------------------", e);
            return Response
                    .exception()
                    .setMessage(e.getMessage() != null ? e.getMessage() : "Une erreur s'est produite lors de l'operation");
        }
    }

    TypeAA resolveTypeAA(String code, String autreTypeActe) {
        if (!"AUTRE".equalsIgnoreCase(code == null ? null : code.trim())) {
            return typeAARepository.findTypeAAByCode(code.trim()).orElseThrow();
        }
        String libelle = validateAutreTypeActe(autreTypeActe);
        return typeAARepository.findFirstByLibelleIgnoreCaseOrderByIdAsc(libelle)
                .orElseGet(() -> typeAARepository.save(TypeAA.builder()
                        .code("CUSTOM_AA_" + UUID.randomUUID()).libelle(libelle).build()));
    }

    TypeAG resolveTypeAG(String code, String autreTypeActe) {
        if (!"AUTRE".equalsIgnoreCase(code == null ? null : code.trim())) {
            return typeAGRepository.findTypeAGByCode(code.trim()).orElseThrow();
        }
        String libelle = validateAutreTypeActe(autreTypeActe);
        return typeAGRepository.findFirstByLibelleIgnoreCaseOrderByIdAsc(libelle)
                .orElseGet(() -> typeAGRepository.save(TypeAG.builder()
                        .code("CUSTOM_AG_" + UUID.randomUUID()).libelle(libelle).build()));
    }

    private String validateAutreTypeActe(String value) {
        if (value == null || value.isBlank() || value.trim().length() > 100) {
            throw new GenericApiException("Veuillez préciser un type d'acte de 1 à 100 caractères.");
        }
        return value.trim();
    }

    @Override
    public Response<Object> filtreAvances(int page, int size, long typeUserId, String reference, String date, String typeActe,String codeTypeActe, String statut,String matricule) {

        try {
        log.info("+++++++++++++++++++++++INIT LISTE ACTES+++++++++++++++++++++++++++");
        BooleanBuilder builder = new BooleanBuilder();
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        CentralLevel centralLevel = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
        Profile p = profiles.get(0);
        // Page<ActeResponseDTO> actePage;
        Page<Acte> actePage;
        if (typeUserId != 0) {
            builder.and(QActe.acte.agent.id.eq(typeUserId));
        }
        else {
            if(!p.getCode().equalsIgnoreCase("Chef-division-dgcaa"))
               builder.and((QActe.acte.agent.id.ne(utilisateurConnected.getId())));

        }
        if (utilisateurConnected.getTypeUser().equalsIgnoreCase("CEN")) {
            centralLevel = (CentralLevel) utilisateurConnected;
        } if (utilisateurConnected.getTypeUser().equalsIgnoreCase("DEC")){
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
        }
        if (p.getCode().contains("Chef-division") && !p.getCode().equalsIgnoreCase("Chef-division-dgcaa")) {
            System.out.println("CHEF");
            builder.and(QActe.acte.division.code.eq(centralLevel.getDivision().getCode()));
        }
            if (p.getCode().contains("Directeur") && !p.getCode().equalsIgnoreCase("Directeur-DRH") )  {
              builder.and(QActe.acte.direction.code.eq(centralLevel.getDirection().getCode()));
        }
        if (p.getLabel().equalsIgnoreCase("Chef-service")) {
            System.out.println("CHEF");
            builder.and(QActe.acte.direction.code.eq(centralLevel.getDirection().getCode()));
            /*if(centralLevel.getService()!=null)
                builder.and(QActe.acte.service.code.eq(centralLevel.getService().getCode()));*/

        }

        if (p.getCode().equalsIgnoreCase("Chef-etablissement")) {
            System.out.println("Here");
            builder.and(QActe.acte.etablissement.code.eq(deconcentratedLevel.getEtablissement().getCode()));

        }
        if (p.getCode().equalsIgnoreCase("Chef-cfp")) {
            builder.and(QActe.acte.etablissement.typeEtablissement.code.eq(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode())
                    .and(QActe.acte.etablissement.typeEtablissement.code.equalsIgnoreCase("cfp")));

        }

        if (p.getCode().equalsIgnoreCase("Chef-EFF")) {
            builder.and(QActe.acte.etablissement.typeEtablissement.code.eq(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode())
                    .and(QActe.acte.etablissement.typeEtablissement.code.equalsIgnoreCase("eff")));

        }

        if (p.getCode().equalsIgnoreCase("Representant-IA")) {

            builder.and(QActe.acte.ia.code.equalsIgnoreCase(deconcentratedLevel.getIa().getCode()));

        }
        if (p.getCode().equalsIgnoreCase("Représentant-IEF")) {
            builder.and(QActe.acte.ief.code.equalsIgnoreCase(deconcentratedLevel.getIef().getCode()));

        }
        if (StringUtils.isNotBlank(reference)) {
            builder.and(QActe.acte.referenceActe.containsIgnoreCase(reference));
        }
        if(StringUtils.isNotBlank(matricule)) {
            builder.and(QActe.acte.agent.matricule.containsIgnoreCase(matricule));
        }
        if (StringUtils.isNotBlank(date)) {
            builder.and(QActe.acte.dateDemandeActe.eq(stringToLocalDate(date, "yyyy-MM-dd")));
        }

        if (StringUtils.isNotBlank(typeActe)) {
            builder.and(QActe.acte.typeActe.codeActe.containsIgnoreCase(typeActe));
        }

            if (StringUtils.isNotBlank(codeTypeActe)) {
                if (StringUtils.isNotBlank(typeActe)){
                    if (typeActe.equalsIgnoreCase("aa")){
                        builder.and(QActe.acte.typeAA.code.equalsIgnoreCase(codeTypeActe));
                    }
                    else {
                        builder.and(QActe.acte.typeAG.code.equalsIgnoreCase(codeTypeActe));

                    }
            }
            }

        if (StringUtils.isNotBlank(statut)) {
            builder.and(QActe.acte.statutActe.code.like(statut+"%"));
        }

        actePage = Objects.nonNull(builder.getValue()) ?
                acteRepository.findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                //     .map(acte -> acteMapper.toDto(acte))
                :
                acteRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
        //acteRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

        // .map(avis -> acteMapper.toDto(avis))
        ;

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(actePage.getSize())
                .totalPages(actePage.getTotalPages())
                .totalElements(actePage.getTotalElements())
                .number(actePage.getNumber())
                .build();
        return Response.ok()
                .setPayload(actePage.getContent())
                .setMetadata(pageMetadata)
                .setMessage("Liste des actes");
        } catch(Exception e) {
            log.error("------------------Erreur lors de l'operation ------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Erreur sur la liste des actes avec filtres avancés ");
        }

    }

    @Override
    public Response<Object> listActesInProcess(int page, int size, long typeUserId, String reference, String date,String  codeTypeActe, String typeActe,String matricule) {
        try {
            log.info("+++++++++++++++++++++++INIT LISTE ACTES+++++++++++++++++++++++++++");
            BooleanBuilder builder = new BooleanBuilder();
            Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
            CentralLevel centralLevel = new CentralLevel();
            DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
            List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
            Profile p = profiles.get(0);

            // Page<ActeResponseDTO> actePage;

            Page<Acte> actePage;
            if (typeUserId != 0) {
                builder.and(QActe.acte.agent.id.eq(typeUserId));
            }
            else {
                if(!p.getCode().equalsIgnoreCase("Chef-division-dgcaa"))
                    builder.and((QActe.acte.agent.id.ne(utilisateurConnected.getId())));

            }
            if (utilisateurConnected.getTypeUser().equalsIgnoreCase("CEN")) {
                centralLevel = (CentralLevel) utilisateurConnected;
            } if (utilisateurConnected.getTypeUser().equalsIgnoreCase("DEC")){
                deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            }
            if (p.getCode().contains("Chef-division") && !p.getCode().equalsIgnoreCase("Chef-division-dgcaa")) {
                System.out.println("CHEF");
                builder.and(QActe.acte.division.code.eq(centralLevel.getDivision().getCode()));
            }
            if (p.getCode().contains("Directeur") && !p.getCode().equalsIgnoreCase("Directeur-DRH") )  {
                builder.and(QActe.acte.direction.code.eq(centralLevel.getDirection().getCode()));
            }
            if (p.getLabel().equalsIgnoreCase("Chef-service")) {
                System.out.println("CHEF");
                builder.and(QActe.acte.direction.code.eq(centralLevel.getDirection().getCode()));
               /* if(centralLevel.getService()!=null)
                    builder.and(QActe.acte.service.code.eq(centralLevel.getService().getCode()));*/
            }

            if (p.getCode().equalsIgnoreCase("Chef-etablissement")) {
                System.out.println("Here");
                builder.and(QActe.acte.etablissement.code.eq(deconcentratedLevel.getEtablissement().getCode()));

            }
            if (p.getCode().equalsIgnoreCase("Chef-cfp")) {
                builder.and(QActe.acte.etablissement.typeEtablissement.code.eq(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode())
                        .and(QActe.acte.etablissement.typeEtablissement.code.equalsIgnoreCase("cfp")));

            }

            if (p.getCode().equalsIgnoreCase("Chef-EFF")) {
                builder.and(QActe.acte.etablissement.typeEtablissement.code.eq(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode())
                        .and(QActe.acte.etablissement.typeEtablissement.code.equalsIgnoreCase("eff")));

            }
            if (p.getCode().equalsIgnoreCase("Representant-IA")) {

                builder.and(QActe.acte.ia.code.equalsIgnoreCase(deconcentratedLevel.getIa().getCode()));

            }
            if (p.getCode().equalsIgnoreCase("Représentant-IEF")) {
                builder.and(QActe.acte.ief.code.equalsIgnoreCase(deconcentratedLevel.getIef().getCode()));

            }
            if (StringUtils.isNotBlank(reference)) {
                builder.and(QActe.acte.referenceActe.containsIgnoreCase(reference));
            }
            if(StringUtils.isNotBlank(matricule)) {
                builder.and(QActe.acte.agent.matricule.containsIgnoreCase(matricule));
            }
            if (StringUtils.isNotBlank(date)) {
                builder.and(QActe.acte.dateDemandeActe.eq(stringToLocalDate(date, "yyyy-MM-dd")));
            }

            if (StringUtils.isNotBlank(typeActe)) {
                builder.and(QActe.acte.typeActe.codeActe.containsIgnoreCase(typeActe));
            }

            if (StringUtils.isNotBlank(codeTypeActe)) {
                if (StringUtils.isNotBlank(typeActe)){
                    if (typeActe.equalsIgnoreCase("aa")){
                        builder.and(QActe.acte.typeAA.code.equalsIgnoreCase(codeTypeActe));
                    }
                    else {
                        builder.and(QActe.acte.typeAG.code.equalsIgnoreCase(codeTypeActe));
                    }
                }
            }

                builder.and(QActe.acte.statutActe.code.notEqualsIgnoreCase("VALIDEDGCAA")
                        .and(QActe.acte.statutActe.code.notLike("INVALIDE%")));

            actePage = Objects.nonNull(builder.getValue()) ?
                    acteRepository.findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                    //     .map(acte -> acteMapper.toDto(acte))
                    :
                    acteRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
            //acteRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

            // .map(avis -> acteMapper.toDto(avis))
            ;

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(actePage.getSize())
                    .totalPages(actePage.getTotalPages())
                    .totalElements(actePage.getTotalElements())
                    .number(actePage.getNumber())
                    .build();
            return Response.ok()
                    .setPayload(actePage.getContent())
                    .setMetadata(pageMetadata)
                    .setMessage("Liste des actes encours de traitement");
        } catch(Exception e) {
            log.error("------------------Erreur lors de l'operation ------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Erreur sur la liste des actes encours de traitement avec filtres avancés ");
        }

    }


    @Override
    public Response<Object> getActeByActeId(Long id) {
        try {
            List<File> files=new ArrayList<>();
            Acte existingActe = acteRepository.findActeById(id).orElseThrow();
            return Response.ok()
                    .setPayload(existingActe)
                    .setMessage("Récupération acte avec Succés");
        }catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la recherche de l'acte------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la recherche de l'acte");
        }
    }

    @Override
    public Response<Object> traiterActe(long id,long responsableTraitement) {
        try {
            Acte existingActe = acteRepository.findActeById(id).orElseThrow();
            return Response.ok()
                    .setPayload(acteMapper.toDto(acteRepository.save(existingActe)))
                    .setMessage("Récupération acte avec Succés");
        }catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la mise a jour de l'acte------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la mise a jour de l'acte");
        }
    }


    @Override
    public Response<Object> updateActe(long id, ActeDTO acteDTO) {
        try {
            log.info("Modification");
            System.out.println(acteDTO.toString());
            Acte existingActe=acteRepository.findActeById(id).orElseThrow();
            Utilisateur agent= utilisateurRepository.findById(acteDTO.getIdAgent()).orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));
            TypeAA typeAA=new TypeAA();
            TypeAG typeAG=new TypeAG();
            TraitementActe traitementActe=new TraitementActe();
            StatutActe statutActe= new StatutActe(200L,"SOUMIS","SOUMIS");
            TypeActe typeActe=typeActeRepository.findTypeActeByCodeActe(acteDTO.getCodetypeActe()).orElseThrow();
            if(acteDTO.getCodetypeActe().equalsIgnoreCase("aa"))
                typeAA=typeAARepository.findTypeAAByLibelle(acteDTO.getCodeTypeActeAA()).orElseThrow(() -> new GenericApiException("Ce type n'existe pas"));
            else
                typeAG=typeAGRepository.findTypeAGByLibelle(acteDTO.getCodeTypeActeAG()).orElseThrow(() -> new GenericApiException("Ce type n'existe pas"));
            Acte acte = Acte.builder()
                    .id(existingActe.getId())
                    .niveau(existingActe.getNiveau())
                    .direction(existingActe.getDirection())
                    .division(existingActe.getDivision())
                    .commentaire(acteDTO.getCommentaire())
                    .dateDemandeActe(existingActe.getDateDemandeActe())
                    .agent(agent)
                    .statutActe(statutActe)
                    .motifModification(existingActe.getMotifModification())
                    .referenceActe(existingActe.getReferenceActe())
                    .typeActe(typeActe)
                    .pieceJointes(existingActe.getPieceJointes())
                    .build();
            if(acteDTO.getCodetypeActe().equalsIgnoreCase("aa"))
                acte.setTypeAA(typeAA);
            else
                acte.setTypeAG(typeAG);
            Acte savedActe=acteRepository.saveAndFlush(acte);
            traitementActe.setTraitrant(acte.getAgent());
            traitementActe.setActe(savedActe);
            traitementActe.setTraitement(savedActe.getStatutActe().getLibelle());
            traitementActe.setDateTraitement(LocalDate.now());
            traitementActeRepository.save(traitementActe);
            return Response.ok()
                    .setPayload(acteMapper.toDto(acte))
                    .setMessage("Modification acte effetuee avec succes");
        } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la mise a jour de l'acte------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la mise a jour de l'acte");
        }
    }

    @Override
    public Response<Object> deleteActe(long id) {
        try {
            acteRepository.deleteById(id);
            return Response.ok()
                    .setMessage("Acte supprimé avec succès.");
        } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la suppression de l'acte------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la suppression de l'acte");
        }

    }

    @Override
    public Response<Object> traiterActe(long idActe,long idAgent,String traitement,String motifModification, String motifRejet) throws JRException, FileNotFoundException {
        try {
        Acte existingActe=acteRepository.findActeById(idActe).orElseThrow();
        String message="";
        TraitementActe traitementActe= new TraitementActe();
        Utilisateur agent=existingActe.getAgent();
        Utilisateur userConnected= iUtilisateur.getCurrentUser();
        traitementActe.setTraitrant(userConnected);
        List<Profile> profiles = new ArrayList<>(userConnected.getProfils());
            Profile profile =profiles.get(0);
            String pro = profile.getCode();
            if(profile.getCode().contains("Chef-division") && !profile.getCode().equals("Chef-division-dgcaa"))
                pro = "Chef-division";
            if(Objects.equals(profile.getCode(), "Chef-service"))
                pro = "Chef-service";
            if(Objects.equals(profile.getCode(), "Chef-bureau"))
                pro = "Chef-bureau";

        StatutActe statutActe=new StatutActe();
        traitementActe.setTraitement(existingActe.getStatutActe().getLibelle());
        switch (traitement){
            case "modifier":
            {
                //System.out.println("Hello  Modifions");
                existingActe.setMotifModification(motifModification);
                statutActe=statutActeRepository.findByCode("AMODIFIER").get();
                mailService.sendMail(new MailInfosDTO(null, "Bonjour " + agent.getPrenom() + " " + agent.getNom() + "\n votre demande d'acte N° " + existingActe.getReferenceActe() + " vous a été renvoyée pour modification.\nMerci de procéder au traitement.", "Demande d'acte", null, agent.getEmail()));

                break;
            }
            case "rejeter": {
                System.out.println("Rejet");
                existingActe.setMotifRejetDemande(motifRejet);
                switch (pro){
                    case "Chef-division-dgcaa":{

                        System.out.println("doyene");
                        statutActe = statutActeRepository.findByCode("INVALIDEDGCAA").get();
                        break;
                    }
                    case "Directeur-DRH":{
                        statutActe = statutActeRepository.findByCode("INVALIDEDRH").get();
                        break;

                    }
                    case "Chef-division":{
                        statutActe = statutActeRepository.findByCode("INVALIDEDIV").get();
                    }
                    case "Chef-service":{
                        statutActe = statutActeRepository.findByCode("INVALIDESUP").get();
                        break;
                    }
                    case "Chef-etablissement","Chef-EFF","Chef-cfp":{
                        statutActe = statutActeRepository.findByCode("INVALIDECE").get();
                        break;
                    }
                    case "Représentant-IEF":{
                        statutActe = statutActeRepository.findByCode("INVALIDEIEF").get();
                        break;
                    }
                    case "Representant-IA":{
                        statutActe = statutActeRepository.findByCode("INVALIDEIA").get();
                        break;

                    }
                    default:{}
                }
                break;
            }
            case "rec-drh": {
                System.out.println("heerrrrrrrrrrrr2");
                System.out.println("recçueDRH");
                statutActe = statutActeRepository.findByCode("RECU-DRH").get();
                message="Votre demande a été reçue par le Directeur DRH";
                mailService.sendMail(new MailInfosDTO(null, message
                        , "Traitement demande", null, agent.getEmail()));
                break;
            }
            case "encoursDCCAA": {
                System.out.println("heerrrrrrrrrrrr2");
                System.out.println("recçueDRH");
                statutActe = statutActeRepository.findByCode("ENCOURSDGCAA").get();
                message="Votre demande est en cours de traitement au niveau de la DGCAA";
                mailService.sendMail(new MailInfosDTO(null, message
                        , "Traitement demande", null, agent.getEmail()));
                break;
            }
            default:{}
        }
            System.out.println("Statut Sortant: "+statutActe.getLibelle());
            existingActe.setStatutActe(statutActe);
            Acte savedActe = acteRepository.saveAndFlush(existingActe);
            traitementActe.setActe(savedActe);
            traitementActeRepository.save(traitementActe);
            System.out.println(acteMapper.toDto(savedActe));
        return Response.ok()
                .setPayload(acteMapper.toDto(savedActe).getStatutActe().getLibelle())
                .setMessage("Modification acte effetuee avec succes");
        } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la mise a jour de l'acte------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la mise a jour de l'acte");
        }
    }


    @Override
    public Response<Object> envoyerFP(List<Long> actes, long idAgent) throws JRException, FileNotFoundException {
        try {
        TraitementActe traitementActe= new TraitementActe();
        Utilisateur traitant=iUtilisateur.getUserId(idAgent);
        traitementActe.setTraitrant(traitant);
        StatutActe statutActe = statutActeRepository.findByCode("TRANSMISFP").orElseThrow(()->new GenericApiException("Statut introuvable"));
        List<Acte> acteList=new ArrayList<>();
        log.info(" INIT ENVOI ACTES FONCTION PUBLIQUE");
        for (long acte:actes
        ) {
            Optional<Acte> a= Optional.ofNullable(acteRepository.findActeById(acte).orElseThrow(() -> new GenericApiException("Acte introuvable")));
            if(a!=null)
                acteList.add(a.get());
        }
        for (Acte a:acteList
        ) {
            a.setStatutActe(statutActe);
            byte[] bordereauPDF = jasperGenerator.getBordereauPDF1(acteMapper.toDto(a));
            MultipartFile multipartFile = new MockMultipartFile("bordereau_"+a.getId(),bordereauPDF);
            fileImpl.uploadSingleFile(multipartFile, a.getId(),"acte");
        }
        System.out.println("on est la");
        System.out.println("on est passé");

        for (Acte a:acteList
        ) {
            a.setStatutActe(statutActe);
        }
        return Response.ok()
                .setPayload(acteMapper.toDtoList(acteList))
                .setMessage("Modification acte effetuee avec succes");
        } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la mise a jour de l'acte------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la mise a jour de l'acte");
        }
    }


    // Exécution mensuelle le premier jour de chaque mois à minuit
    @Scheduled(cron = "${cron}")
    @Override
    public void envoyerAlerte() {
        // System.out.println("Hello je suis un cron et je m'execute");
        List<Acte> actes = (List<Acte>) acteRepository.findByStatutActe(statutActeRepository.findByCode("VALIDEDGCAA").get()); // Méthode pour obtenir tous les actes de type sortie Temporaires
        for (Acte acte : actes) {
            if ((acte.getTypeActe().getCodeActe().equalsIgnoreCase("aa") && acte.getTypeAA().getTypeSortie().equals("STEM")) ||
                    (acte.getTypeActe().getCodeActe().equalsIgnoreCase("ag") && acte.getTypeAG().getTypeSortie().equals("STEM"))) {
                System.out.println(acte.getStatutActe().getLibelle());
                //  System.out.println(acte.get);
                System.out.println( "debut"+acte.getDateDebut()+"\n fin: "+acte.getDateFin());
                //System.out.println(acte.getDateFin());
            }
            if ((acte.getTypeActe().getCodeActe().equalsIgnoreCase("aa") && acte.getTypeAA().getTypeSortie().equals("STEM")) ||
                    (acte.getTypeActe().getCodeActe().equalsIgnoreCase("ag") && acte.getTypeAG().getTypeSortie().equals("STEM"))) {
                long differenceEnMois = calculerDifferenceMois(acte.getDateFin(),LocalDate.now());
                System.out.println(differenceEnMois);
                if (differenceEnMois == 1 || differenceEnMois == 2 || differenceEnMois == 3) {
                    mailService.sendMail(new MailInfosDTO(null, "Bonjour " + acte.getAgent().getPrenom() + " " + acte.getAgent().getNom() +
                            "\nLa date de votre retour est prévue dans: " + differenceEnMois +
                            "\nMerci de bien vouloir prendre vos dispositions", "Alerte fin de sortie", null, acte.getAgent().getEmail()));
                }
                System.out.println("date non encore atteine");
            }
        }
    }

    public static long calculerDifferenceMois(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Les dates ne doivent pas être nulles");
        }
        return ChronoUnit.MONTHS.between(endDate,startDate);
    }
    @Override
    public Response<Object> validerCen(long idActe, long idAgent, ValidesActesDate validesActesDate) throws JRException, FileNotFoundException {
        try {
        log.info("VALIDATION ACTE ");
        String message="";
        TraitementActe traitementActe = new TraitementActe();
        Acte existingActe = acteRepository.findActeById(idActe).orElseThrow();
        Utilisateur agentResp = iUtilisateur.getUserId(idAgent);
        traitementActe.setTraitrant(agentResp);
        StatutActe statutActe = new StatutActe();
        Utilisateur agent = existingActe.getAgent();
        Set<Profile> profiles = (Set<Profile>) agentResp.getProfils();
        switch (existingActe.getStatutActe().getCode()){
            case "SOUMIS": {

                System.out.println("soumis");
                //Si l'agent proprietaire est du niveau central
                if(agent.getTypeUser().equalsIgnoreCase("CEN")) {
                    CentralLevel cen=(CentralLevel)agentResp;
                    if(cen.getDirection().getCode().equalsIgnoreCase("DRH")) {
                        statutActe = statutActeRepository.findByCode("VALIDEDIV").get();
                        message="Votre demande a été validée par votre chef de division";
                    }
                    else {
                        statutActe = statutActeRepository.findByCode("VALIDESUP").get();
                        message="Votre demande a été validée par votre chef de Service";
                    }
                }
                //Si l'agent proprietaire est du niveau deconcentré
                if(agent.getTypeUser().equalsIgnoreCase("DEC")) {
                    statutActe = statutActeRepository.findByCode("VALIDECE").get();
                    message="Votre demande a été validée par votre chef d'établissement";

                }
                break;
            }
            case "VALIDECE": {
                System.out.println("valideCE");
                DeconcentratedLevel deconcentratedLevel=(DeconcentratedLevel) agent;
                //Si l'agent proprietaire est dans un Lycée

                if(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode().equalsIgnoreCase("LYC")) {
                    statutActe = statutActeRepository.findByCode("VALIDEIA").orElseThrow();
                    message="Votre demande a été validée par le représentant de votre ia";
                }
                //Si l'agent proprietaire est dans un CFP ou EFF
                else
                {
                    statutActe = statutActeRepository.findByCode("VALIDEIEF").orElseThrow();
                    message="Votre demande a été validée par le représentant de votre ief";
                }
                break;
            }
            case "VALIDEIEF": {
                System.out.println("valideIEF");
                statutActe = statutActeRepository.findByCode("VALIDEIA").orElseThrow();
                message="Votre demande a été validée par le représentant de votre ia";
                break;
            }

            case "ENCOURSDGCAA": {
                System.out.println("encours");
                statutActe = statutActeRepository.findByCode("VALIDEDGCAA").get();
                message="votre demande a été validée avec succès, l'acte est disponible ";
                break;
            }
            default:{}
        }

        mailService.sendMail(new MailInfosDTO(null, message
                , "Traitement demande", null, agent.getEmail()));
        //set le profile traitant
        String profilTraitant = profilTraitant(agent, agent.getTypeUser());

        existingActe.setProfilDevantTraiter(profilTraitant);

        //envoie notification
        sendNotify(existingActe, profilTraitant, existingActe.getAgent().getTypeUser());
        existingActe.setStatutActe(statutActe);
        traitementActe.setTraitement(existingActe.getStatutActe().getLibelle());
        traitementActe =   traitementActeRepository.save(traitementActe);
        Acte savedActe=acteRepository.save(existingActe);
        traitementActe.setActe(savedActe);
        traitementActeRepository.save(traitementActe);

        return Response.ok()
                .setPayload(acteMapper.toDto(savedActe))
                .setMessage("Modification acte effetuee avec succes");
        }catch (Exception e) {
                log.error("------------------Une erreur est survenue lors de la mise a jour de l'acte------------------------ : {}", e.getMessage());
                return Response
                        .exception()
                        .setMessage("Une erreur est survenue lors de la mise a jour de l'acte");
            }
    }



   /* @Override
    public Response<Object> sotieTemplaire(int page, int size, long typeUserId, String reference, String date, String typeActe, String statut) {
        return null;
    }*/

    @Override
    public Response<Object> findBySortiePage(String code, int page, int size, String filter, String type) {

        Page<Acte> actePage;
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNotBlank(type)) {

            //System.out.println(type);

            switch (type) {

                case "DDF":
                case "DMPS":
                case "DMPD":
                case "DRA":
                case "DDD":
                case "DRD":

                    builder.and(QActe.acte.typeAA.code.containsIgnoreCase(type));
                    break;

                case "DASTN":

                    builder.and(QActe.acte.typeAG.code.containsIgnoreCase(type));
                    break;

                case  "CONG":
                    builder.or(QActe.acte.typeAG.code.containsIgnoreCase("DCM"))
                            .or(QActe.acte.typeAG.code.containsIgnoreCase("DCMA"))
                            .or(QActe.acte.typeAG.code.containsIgnoreCase("DCA"))
                    ;
                    break;
            }
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(filter)) {
            builder.andAnyOf(
                    QActe.acte.agent.prenom.containsIgnoreCase(filter),
                    QActe.acte.agent.nom.containsIgnoreCase(filter),
                    QActe.acte.typeActe.libelleActe.containsIgnoreCase(filter),
                    QActe.acte.agent.fonction.label.containsIgnoreCase(filter)
            );
        }

        Pageable pageRequest = createPageRequestUsing(page, size);
        List<Acte> allActs = new ArrayList<>();
        List<Acte> pageContent = List.of();

        if( Objects.nonNull(builder.getValue())){

            allActs  = acteRepository
                    .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).toList()

                    .stream().filter(tep -> tep.getStatutActe().getCode().equals("VALIDEDGCAA")).toList();

            if(!allActs.isEmpty()) {
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), allActs.size());

                pageContent = allActs.subList(start, end);


            }


      /*  actePage = acteRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")));

       */
            actePage = new PageImpl<>(pageContent, pageRequest, allActs.size());

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(actePage.getSize())
                    .number(actePage.getNumber())
                    .totalElements(actePage.getTotalElements())
                    .totalPages(actePage.getTotalPages())
                    .build();

            return Response.ok().setPayload(actePage.getContent()).setMetadata(pageMetadata).setMessage("Liste des  demandes de plaintes");

        }else {

            allActs =   acteRepository.findByTypeAA_TypeSortieOrTypeAG_TypeSortie(code,code,  PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

                    .stream().filter(tep -> tep.getStatutActe().getCode().equals("VALIDEDGCAA")).toList();


            if(!allActs.isEmpty()) {
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), allActs.size());

                pageContent = allActs.subList(start, end);

            }

            actePage = new PageImpl<>(pageContent, pageRequest, allActs.size());
            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(actePage.getSize())
                    .number(actePage.getNumber())
                    .totalElements(actePage.getTotalElements())
                    .totalPages(actePage.getTotalPages())
                    .build();

            return Response.ok().setPayload(actePage.getContent()).setMetadata(pageMetadata).setMessage("Liste des Acts");
        }
    }



    @Override
    public Response<Object> getActeStatistiques(String codeProfile,String codeTypeActe) {
        System.out.println("++++++++++++++ :"+ codeTypeActe);
        long validAA = 0;
        long validAG = 0;
        long rejectAA = 0;
        long rejectAG = 0;
        long allAA = 0;
        long allAG = 0;
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        CentralLevel centralLevel = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (utilisateurConnected.getTypeUser().equals("CEN")) {
            centralLevel = (CentralLevel) utilisateurConnected;
            List<Profile> profiles = new ArrayList<>(centralLevel.getProfils());
            Profile profile =profiles.get(0);
            String pro = profile.getCode();
            if(profile.getCode().contains("Chef-division") && !profile.getCode().equals("Chef-division-dgcaa"))
                pro = "Chef-division";
            if(Objects.equals(profile.getCode(), "Chef-service"))
                pro = "Chef-service";
            if(Objects.equals(profile.getCode(), "Chef-bureau"))
                pro = "Chef-bureau";
            switch (pro){
                case "Chef-bureau": {
                    System.out.println("Bureau");
                    validAA = acteRepository.countValidRejectActesAABureau(centralLevel.getBureau().getCode(), "VALIDEDGCAA",codeTypeActe);
                    validAG = acteRepository.countValidRejectActesAGBureau(centralLevel.getBureau().getCode(), "VALIDEDGCAA",codeTypeActe);
                    rejectAA = acteRepository.countValidRejectActesAABureau(centralLevel.getBureau().getCode(), "INVALIDE%",codeTypeActe);
                    rejectAG = acteRepository.countValidRejectActesAGBureau(centralLevel.getBureau().getCode(), "INVALIDE%",codeTypeActe);
                    allAA = acteRepository.countAllActesAABureau(centralLevel.getBureau().getCode(),codeTypeActe);
                    allAG = acteRepository.countAllActesAGBureau(centralLevel.getBureau().getCode(),codeTypeActe);

                }
                    break;
                case "Chef-division": {
                    System.out.println("Division");
                    validAA = acteRepository.countValidRejectActesAADivision(centralLevel.getDivision().getCode(), "VALIDEDGCAA",codeTypeActe);
                    validAG = acteRepository.countValidRejectActesAGDivision(centralLevel.getDivision().getCode(), "VALIDEDGCAA",codeTypeActe);
                    rejectAA = acteRepository.countValidRejectActesAADivision(centralLevel.getDivision().getCode(), "INVALIDE%",codeTypeActe);
                    rejectAA = acteRepository.countValidRejectActesAGDivision(centralLevel.getDivision().getCode(), "INVALIDE%",codeTypeActe);
                    allAA = acteRepository.countAllActesAADivision(centralLevel.getDivision().getCode(),codeTypeActe);
                    allAG = acteRepository.countAllActesAGDivision(centralLevel.getDivision().getCode(),codeTypeActe);
                    break;
                }
                case "Chef-service": {
                        validAA = acteRepository.countValidRejectActesAAService(centralLevel.getDirection().getCode(), "VALIDEDGCAA",codeTypeActe);
                        validAA = acteRepository.countValidRejectActesAGService(centralLevel.getDirection().getCode(), "VALIDEDGCAA",codeTypeActe);
                        rejectAA = acteRepository.countValidRejectActesAAService(centralLevel.getDirection().getCode(), "INVALIDE%",codeTypeActe);
                        rejectAG = acteRepository.countValidRejectActesAGService(centralLevel.getDirection().getCode(), "INVALIDE%",codeTypeActe);
                        allAA = acteRepository.countAllActesAAService(centralLevel.getDirection().getCode(),codeTypeActe);
                        allAG = acteRepository.countAllActesAGService(centralLevel.getDirection().getCode(),codeTypeActe);
                    break;
                }

                default: {
                    System.out.println("Chefs");
                    validAA = acteRepository.countValidRejectActesAA("VALIDEDGCAA",codeTypeActe);
                    validAG = acteRepository.countValidRejectActesAG("VALIDEDGCAA",codeTypeActe);
                    rejectAA= acteRepository.countValidRejectActesAA("iNVALIDE%",codeTypeActe);
                    rejectAG= acteRepository.countValidRejectActesAG("INVALIDE%",codeTypeActe);
                    allAA = acteRepository.countAllActesAA(codeTypeActe);
                    allAG=acteRepository.countAllActesAG(codeTypeActe);
                    break;
                }
            }

        } else {
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            switch (codeProfile) {
                case "Chef-etablissement":
                case "Chef-EFF":
                case "Chef-cfp":
                {
                    System.out.println("Etablissement");
                    validAA = acteRepository.countValidRejectActesAAEtab(deconcentratedLevel.getEtablissement().getCode(),"VALIDEDGCAA",codeTypeActe);
                    validAG = acteRepository.countValidRejectActesAGEtab(deconcentratedLevel.getEtablissement().getCode(),"VALIDEDGCAA",codeTypeActe);
                    rejectAA = acteRepository.countValidRejectActesAAEtab(deconcentratedLevel.getEtablissement().getCode(),"INVALIDE%",codeTypeActe);
                    rejectAG = acteRepository.countValidRejectActesAGEtab(deconcentratedLevel.getEtablissement().getCode(),"INVALIDE%",codeTypeActe);
                    allAA = acteRepository.countAllActesAAEtab(deconcentratedLevel.getEtablissement().getCode(),codeTypeActe);
                    allAG = acteRepository.countAllActesAGEtab(deconcentratedLevel.getEtablissement().getCode(),codeTypeActe);
                    break;
                }
                case "Représentant-IEF": {
                    System.out.println("IEF");

                    validAA = acteRepository.countValidRejectActesAAIef(deconcentratedLevel.getIef().getCode(), "VALIDEDGCAA",codeTypeActe);
                    validAG = acteRepository.countValidRejectActesAGIef(deconcentratedLevel.getIef().getCode(), "VALIDEDGCAA",codeTypeActe);
                    rejectAA = acteRepository.countValidRejectActesAAIef(deconcentratedLevel.getIef().getCode(), "INVALIDE%",codeTypeActe);
                    rejectAG = acteRepository.countValidRejectActesAGIef(deconcentratedLevel.getIef().getCode(), "INVALIDE%",codeTypeActe);
                    allAA = acteRepository.countAllActesAAIef(deconcentratedLevel.getIef().getCode(),codeTypeActe);
                    allAG = acteRepository.countAllActesAGIef(deconcentratedLevel.getIef().getCode(),codeTypeActe);
                    break;
                }
                case "Representant-IA": {
                    System.out.println("Ia");
                    validAA = acteRepository.countValidRejectActesAAIa(deconcentratedLevel.getIa().getCode(), "VALIDEDGCAA",codeTypeActe);
                    validAG = acteRepository.countValidRejectActesAGIa(deconcentratedLevel.getIa().getCode(), "VALIDEDGCAA",codeTypeActe);
                    rejectAA = acteRepository.countValidRejectActesAAIa(deconcentratedLevel.getIa().getCode(), "INVALIDE%",codeTypeActe);
                    rejectAG = acteRepository.countValidRejectActesAGIa(deconcentratedLevel.getIa().getCode(), "INVALIDE%",codeTypeActe);
                    allAA = acteRepository.countAllActesAAIa(deconcentratedLevel.getIa().getCode(),codeTypeActe);
                    allAG = acteRepository.countAllActesAGIa(deconcentratedLevel.getIa().getCode(),codeTypeActe);
                    break;
                }

                default:

                    break;
            }
        }
        StatistiquesActeRspDTO statistiquesActeRspDTO = new StatistiquesActeRspDTO();
        statistiquesActeRspDTO.setSumAA(allAA);
        statistiquesActeRspDTO.setSumAG(allAG);
        statistiquesActeRspDTO.setCountValidAA(validAA);
        statistiquesActeRspDTO.setCountValidAG(validAG);
        statistiquesActeRspDTO.setCountRejectAA(rejectAA);
        statistiquesActeRspDTO.setCountRejectAG(rejectAG);
        statistiquesActeRspDTO.setCountInProcessAA( Math.abs(allAA-(validAA+rejectAA)));
        statistiquesActeRspDTO.setCountInProcessAG(Math.abs(allAG-(validAG+rejectAG)));
        return Response.ok().setMessage("Statistiques Actes").setPayload(statistiquesActeRspDTO);
    }

    @Override
    public Response<Object> listEnCours(String codeProfile) {
        return null;
    }

    @Override
    public Response<Object> getAllAA() {
        try{
        List<TypeAA> listAA=new ArrayList<>();
        listAA=typeAARepository.findAll();
        return Response.ok()
                .setPayload(listAA)
                .setMessage("Liste des actes d'administration");
    }catch (Exception e) {
        log.error("------------------Une erreur est survenue lors de la recuperation de la liste des actes d'administration------------------------ : {}", e.getMessage());
        return Response
                .exception()
                .setMessage("Une erreur est survenue lors de la recuperation de la liste des actes d'administration");
    }
    }

    @Override
    public Response<Object> getAllAG() {
        try{
            List<TypeAG> listAG=new ArrayList<>();
            listAG=typeAGRepository.findAll();
            return Response.ok()
                    .setPayload(listAG)
                    .setMessage("Liste des actes de gestion");
        }catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la recuperation de la liste des actes de gestion------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la recuperation de la liste des actes de gestion");
        }
    }

    @Override
    public Response<Object> getAllTypeActe() {
        try {
            Map<String, TypeActe> typesParCode = typeActeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                    .stream()
                    .filter(type -> type.getCodeActe() != null)
                    .collect(Collectors.toMap(
                            type -> type.getCodeActe().trim().toLowerCase(),
                            type -> type,
                            (plusRecent, ignore) -> plusRecent,
                            LinkedHashMap::new));
            List<TypeActe> typeActes = new ArrayList<>(typesParCode.values());
            return Response.ok().setPayload(typeActes).setMessage("Liste des type d'acte actes");
        }catch (Exception e){
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la recuperation de la liste des types d'actes");
        }
    }


    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }



 /*   @Override
    public List<Acte> findBySortie(String code) {
        return acteRepository.findByTypeAA_TypeSortieOrTypeAG_TypeSortie(code, code);
    }*/



    public static LocalDate stringToLocalDate(String dateString, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return LocalDate.parse(dateString, formatter);
    }




    String profilTraitant(Utilisateur demandeur, String niveau){

        List<Profile> profiles = new ArrayList<>(demandeur.getProfils());
        Profile profile =profiles.get(0);
        CentralLevel cen=new CentralLevel();
        DeconcentratedLevel dec=new DeconcentratedLevel();
        switch (niveau){
            case "CEN": {
                cen = centralLevelRepository.findById(demandeur.getId()).orElseThrow(() -> new GenericApiException("Utilisateur central introuvable"));
                break;
            }
            case "DEC": {
                 dec = deconcentratedLevelRepository.findById(demandeur.getId()).orElseThrow(() -> new GenericApiException("Utilisateur deconcentre introuvable"));;
                break;
            }
            default: throw new GenericApiException("Erreur Type utilisateur");
        }
        String profilTraitant = "" ;
        String pro = profile.getCode();
        if(profile.getCode().contains("Chef-division") && !profile.getCode().equals("Chef-division-dgcaa"))
            pro = "Chef-division";
        if(Objects.equals(profile.getCode(), "Chef-service"))
            pro = "Chef-service";

        if(niveau.equals("DEC")){
            switch (pro) {
                case "Chef-etablissement": {
                    if(dec.getEtablissement().getTypeEtablissement().equals("LYC"))
                        profilTraitant = "Representant-IA";
                    else profilTraitant = "Représentant-IEF";
                    break;
                }
                case "Chef-EFF":
                    profilTraitant = "Representant-IA";
                    break;
                case "Chef-cfp":
                    profilTraitant = "Représentant-IEF";
                    break;
                case "Représentant-IEF":
                    profilTraitant = "Representant-IA";
                    break;
                case "Representant-IA":
                    profilTraitant = "Directeur-DRH";
                    // profilTraitant = "Chef-division-dgcaa";
                    break;

                case "Directeur-DRH":
                    profilTraitant = "Chef-division-dgcaa";
                    break;
                case "Chef-division-dgcaa":
                    profilTraitant = "Chef-division-dgcaa";
                    break;
                case "Formateur-EFF":
                    profilTraitant = "Chef-EFF";
                    break;
                case "Formateur-CFP":
                    profilTraitant = "Chef-cfp";
                    break;

                default:
                    profilTraitant = "Chef-etablissement";
                    break;
            }
        }
        else {
            switch (pro){
                case "Chef-bureau":
                    profilTraitant = "Chef-division";
                    break;
                case "Chef-division":// le cas de tous les chefs de divisions
                case "Chef-service": //les chefs de service
                    profilTraitant = "Directeur-DRH";
                    //profilTraitant = "Chef-division-dgcaa";
                    break;
                case "Directeur-DRH":
                    profilTraitant = "Chef-division-dgcaa";
                    break;
                case "Chef-division-dgcaa": //le cas du chef de division DGCAA qui est le validateur final
                    profilTraitant = "Chef-division-dgcaa";
                    break;
                default:{//cas où c'est un agent
                    if(cen.getDivision() != null)
                        profilTraitant = "Chef-division-"+cen.getDivision().getCode().toLowerCase();
                    else if (!Objects.equals(cen.getDirection().getCode(), "DRH")) {
                        profilTraitant = "Chef-service";
                    }
                    else  profilTraitant = "Chef-division-dgcaa";
                    //profilTraitant = "Directeur-DRH";
                }
                break;
            }
        }
        return profilTraitant;
    }



    void sendNotify(Acte acte, String profileTraitant, String userType){
        System.out.println("Bonjour :"+profileTraitant);
        System.out.println("On envoie la notif");
        Profile profile = iProfilRepository.findAllByCodeIgnoreCaseOrderByIdDesc(profileTraitant)
                .stream().findFirst()
                .orElseThrow(() -> new GenericApiException("Le profil traitant " + profileTraitant + " n'existe pas"));
        Set<Profile> profileSet = new HashSet<>();
        profileSet.add(profile);
        Utilisateur utilisateur=acte.getAgent();
        Notification notification = new Notification();
        notification.setObjet("Traitement Acte");
        notification.setMessage("Bonjour, \n la demande d'acte "+ acte.getReferenceActe()+" vous a été trasmise. \n Merci de procéder au traitement.");
        List<DeconcentratedLevel>  deconcentratedLevel = new ArrayList<>();
        List<CentralLevel>  centralLevels = new ArrayList<>();
        if(userType.equals("CEN")){
            switch (profileTraitant) {
                case "Chef-bureau":
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
                case "Chef-division":// le cas de tous les chefs de divisions
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
                case "Chef-service":
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
                case "Directeur-DRH":
                case "Chef-division-dgcaa": //le cas du chef de division DGCAA qui est le validateur final
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
                default:
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
            }
            ///   centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
            //recupération mail traitant

        }else {

            switch (profileTraitant) {
                case "Chef-etablissement":
                case "Chef-EFF":
                case "Chef-cfp":{
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByEtablissementAndProfilsContains(acte.getEtablissement(), profile);
                    break;
                }

                case "Représentant-IEF":
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByIefAndProfilsContains(acte.getIef(), profile);
                    break;
                case "Representant-IA":
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByIaAndProfilsContains(acte.getIa(), profile);
                    break;

                case "Directeur-DRH":
                case "Chef-division-dgcaa":
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);

                    break;

                default:
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
            }
        }
        if(userType.equals("DEC")){
            for (DeconcentratedLevel deconcentratedLevel1 : deconcentratedLevel){
                notification.setIdUser(deconcentratedLevel1.getId());
               // notificationRepository.save(notification);
                iNotification.notifyUser(notification);
                Set<String>  emails = acte.getEmailsTraitant();
                emails.add(deconcentratedLevel.get(0).getEmail());
                acte.setEmailsTraitant(emails);
                acteRepository.save(acte);
            }}
        if(userType.equals("CEN")){
            for (CentralLevel centralLevel : centralLevels){
                notification.setIdUser(centralLevel.getId());
               // notificationRepository.save(notification);
                iNotification.notifyUser(notification);
                Set<String>  emails = acte.getEmailsTraitant();
                emails.add(centralLevels.get(0).getEmail());
                acte.setEmailsTraitant(emails);
                acteRepository.save(acte);
            }}

    }

}
