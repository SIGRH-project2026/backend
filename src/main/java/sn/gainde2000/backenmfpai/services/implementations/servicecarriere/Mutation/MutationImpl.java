package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Mutation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.commons.Notification.INotification;
import sn.gainde2000.backenmfpai.commons.Notification.Notification;
import sn.gainde2000.backenmfpai.commons.Notification.NotificationRepository;
import sn.gainde2000.backenmfpai.commons.exception.GenericApiException;
import sn.gainde2000.backenmfpai.commons.utils.JasperGenerator;
import sn.gainde2000.backenmfpai.commons.utils.helpers.Utilitaire;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;

import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Mutation.MutationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.IOrigineDemandeurLogRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.IStatutMutationRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.ITraitementMutationRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.ImutationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.implementations.files.FileImpl;
import sn.gainde2000.backenmfpai.services.implementations.shared.NotificationServiceImpl;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Mutation.Imutation;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.MutationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.TraitementMutationDTO;

import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.Status;

import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;

import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.IndicateurMutation;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.MutationRDTO;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.QMutation.mutation;


@Service
@Slf4j
@RequiredArgsConstructor
public class MutationImpl implements Imutation {
    private final BusinessNotificationService businessNotifications;

    private final ImutationRepository imutationRepository;
    private final IUtilisateurRepository iUtilisateurRepository;
    private final IStatutMutationRepository iStatutMutationRepository;
    private final MutationMapper mutationMapper;
    private final ITraitementMutationRepository iTraitementMutationRepository;
    private final MailService mailService;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final IOrigineDemandeurLogRepository iOrigineDemandeurLogRepository;
    private final JasperGenerator jasperGenerator;
    private final FileImpl fileImpl;
    private final IUtilisateur iUtilisateur;
    private final IProfilRepository iProfilRepository;
    private final NotificationRepository notificationRepository;
    private final INotification iNotification;
    @Value("${upload.path}")
    protected String uploadPath;
    @Override
    @Transactional
    public Response<Object> demandeMutation(MutationDTO mutationDTO) {
        try {

            Long idDemandeur = mutationDTO.idUserdemandeur();
            Utilisateur utilisateur = iUtilisateurRepository.findById(idDemandeur).orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));

            Mutation mutation = this.mutationMapper.toEntity(mutationDTO);
            mutation.setDemandeur(utilisateur);
            mutation.setNumeroRef(Utilitaire.genererNumeroRefMut());
            Set<String>  emailsTraitant = new HashSet<>();
            emailsTraitant.add(utilisateur.getEmail());
            mutation.setEmailsTraitant(emailsTraitant);
            //initiation du traitement avec statut soumis
            TraitementMutation traitementMutation = new TraitementMutation();
            StatutMutation statutMutation = iStatutMutationRepository.findByCode("SOUMISE");

            traitementMutation.setStatut(statutMutation);
            traitementMutation.setTraiteur(utilisateur);
            traitementMutation.setMotif("soumis");
            traitementMutation.setDateTraitementMutation(LocalDate.now());
            traitementMutation = iTraitementMutationRepository.saveAndFlush(traitementMutation);
            //save mutation
            mutation.setTraitementMutation(traitementMutation);
            mutation.setDateDemande(LocalDate.now());
            OrigineDemandeurLog origineDemandeurLog = new OrigineDemandeurLog();

            if(Objects.equals(utilisateur.getTypeUser(), "DEC")){
                DeconcentratedLevel deconcentratedLevel = (DeconcentratedLevel) mutation.getDemandeur();
                origineDemandeurLog = OrigineDemandeurLog.builder()
                        .region(deconcentratedLevel.getRegion())
                        .ia(deconcentratedLevel.getIa())
                        .ief(deconcentratedLevel.getIef())
                        .origineUserType(utilisateur.getTypeUser())
                        .etablissement(deconcentratedLevel.getEtablissement()).build();
            }else {
                CentralLevel centralLevel = (CentralLevel) mutation.getDemandeur();

                origineDemandeurLog = OrigineDemandeurLog.builder()
                        .bureau(centralLevel.getBureau())
                        .direction(centralLevel.getDirection())
                        .division(centralLevel.getDivision())
                        .service(centralLevel.getService())
                        .origineUserType(utilisateur.getTypeUser())
                        .build();
            }

            origineDemandeurLog = iOrigineDemandeurLogRepository.saveAndFlush(origineDemandeurLog);
            mutation.setOrigineDemandeurLog(origineDemandeurLog);
            //set le profile traitant
            String profilTraitant = profilTraitant(utilisateur,origineDemandeurLog, utilisateur.getTypeUser());
            Profile profile = iProfilRepository.findProfileByCode(profilTraitant);
            businessNotifications.notify(mutation.getDemandeur(), "Demande de mutation", "Bonjour " + mutation.getDemandeur().getPrenom() + " " + mutation.getDemandeur().getNom() + " votre demande de mutation N°" + mutation.getNumeroRef() + " a été envoyée à votre "+profile.getLabel()+" pour traitement.");

            mutation.setProfilDevantTraiter(profilTraitant);
            mutation = imutationRepository.saveAndFlush(mutation);

            //envoie notification
            sendNotify(mutation, profilTraitant,"", mutation.getDemandeur().getTypeUser());

            traitementMutation.setIdmutation(mutation.getId());
            iTraitementMutationRepository.save(traitementMutation);
            //send mail
        /*    List<String> profilesTraiteurs = new ArrayList<>();
            profilesTraiteurs.add("Chef-division-dgpeec");
            profilesTraiteurs.add("bureau-mo-rec");
            List<Utilisateur> usersTraiteurs = iUtilisateurRepository.findByProfileCodes(profilesTraiteurs);
            for (Utilisateur u : usersTraiteurs) {
                businessNotifications.notify(u, "Demande de mutation", "Bonjour " + u.getPrenom() + " " + u.getNom() + "\nLa demande de mutation N°" + mutation.getNumeroRef() + "vous a été soumise.\nMerci de procéder au traitement.");
            }*/
            //for test
            //  businessNotifications.sendMail(new MailInfosDTO(null, "Bonjour " + "Baba" + " " + "TOP" + "\nLa demande de mutation N°" + mutation.getNumeroRef() + "vous a été soumise.\nMerci de procéder au traitement.", "Demande de mutation", null, "top.baba@ugb.edu.sn"));

            return Response.ok().setPayload(mutationMapper.toDto(mutation))
                    .setMessage("Demande de mutation envoyée avec succés");

        } catch (Exception e) {
            log.error("Echec lors de la création de la demande de mutation", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.exception().setMessage("Une erreur est survenue lors de la création de la mutation");
        }
    }

    @Override
    @Transactional
    public Response<Object> traitementMutation(Long idMutation, MultipartFile bordereau, String traitementMutationDTOs, MultipartFile dossierSigne) {
        try{
            String generatedName = null;

            ObjectMapper objectMapper1 = new ObjectMapper();
            //conversion des données JSON recus en Java
            TraitementMutationDTO traitementMutationDTO = objectMapper1.readValue(traitementMutationDTOs, TraitementMutationDTO.class);
            Mutation mutation = this.imutationRepository.findById(idMutation).orElseThrow(() -> new GenericApiException("Mutation non enregistrée"));
            if (mutation.getTraitementMutation() != null
                    && mutation.getTraitementMutation().getStatut() != null
                    && "REC-DGPEEC".equals(mutation.getTraitementMutation().getStatut().getCode())) {
                return Response.exception().setMessage("Cette demande a déjà été traitée par la DPEEC. Veuillez poursuivre avec l'ordre de service.");
            }
            mutation.setProfilDevantTraiter(profilEffectif(mutation));
            StatutMutation statutMutation = iStatutMutationRepository.findByCode(traitementMutationDTO.codeStatutMutation());
            Utilisateur traiteur = iUtilisateurRepository.findById(traitementMutationDTO.idTraiteur()).orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));
            boolean transmission = statutMutation.getCode().startsWith("REC");
            if (transmission && (dossierSigne == null || dossierSigne.isEmpty())) {
                return Response.exception().setMessage("Veuillez joindre le dossier signé avant la transmission");
            }
            String dossierName = null;
            if (dossierSigne != null) {
                Response<Object> upload = fileImpl.uploadSingleFile(dossierSigne, idMutation, "mutation");
                if (upload.getStatus() != Status.OK || !(upload.getPayload() instanceof String)
                        || ((String) upload.getPayload()).isBlank()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Response.exception().setMessage("Le dossier signé n'a pas été enregistré");
                }
                dossierName = (String) upload.getPayload();
            }
            if (bordereau != null) {
                Response<Object> upload = fileImpl.uploadSingleFile(bordereau, idMutation, "mutation");
                if (upload.getStatus() != Status.OK || !(upload.getPayload() instanceof String)
                        || ((String) upload.getPayload()).isBlank()) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return Response.exception().setMessage("Le bordereau n'a pas été enregistré");
                }
                generatedName = (String) upload.getPayload();
            }
            if (dossierName != null) mutation.setDossierSigne(dossierName);
            if (generatedName != null && !generatedName.isEmpty() ) {
                mutation = addBordereauTransmission(mutation, generatedName,traiteur, mutation.getDemandeur().getTypeUser());
                mutation.setCurrentBordereauTransmission(generatedName);
            }
            TraitementMutation traitementMutation = TraitementMutation.builder()
                    .dossierSigne(dossierName)
                    .statut(statutMutation)
                    .motif(traitementMutationDTO.motif())
                    .dateTraitementMutation(LocalDate.now())
                    .traiteur(traiteur)
                    .idmutation(mutation.getId()).build();

            traitementMutation = iTraitementMutationRepository.saveAndFlush(traitementMutation);

            if(statutMutation.getCode().contains("REC"))
            {
                String futureTraitant= profilTraitant(mutation.getProfilDevantTraiter(), mutation.getOrigineDemandeurLog(), mutation.getDemandeur().getTypeUser());
                String ancienProfTraitan = mutation.getProfilDevantTraiter();
                mutation.setProfilDevantTraiter(futureTraitant);

                sendNotify(mutation, futureTraitant, ancienProfTraitan,  mutation.getDemandeur().getTypeUser());

            }
            mutation.setTraitementMutation(traitementMutation);
            mutation = imutationRepository.saveAndFlush(mutation);

            businessNotifications.notify(mutation.getDemandeur(), "Suivi de votre mutation",
                    "Votre demande de mutation n° " + mutation.getNumeroRef() + " : " + statutMutation.getLibelle() + ".");
            return Response.ok().setPayload(mutationMapper.toDto(mutation)).setMessage("Mutation " +statutMutation.getLibelle());
        }catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.exception().setMessage("Une erreur est survenue lors du traitement de la mutation");
        }
    }

    @Override
    public Response<Object> listMutation(int page, int pageSize, String statutMutation, String region, String ia, String ief, String etablissement,
                                         String bureau,  String direction, String division,String service,
                                         String numeroRef,Long userId, boolean pourTraitement, String filtre) {
        try {
            Page<MutationRDTO> mutationRDTOS;
            QMutation qMutation = mutation;
            BooleanBuilder conditions = new BooleanBuilder();

            Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
            CentralLevel centralLevel = new CentralLevel();
            DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
            if (utilisateurConnected.getTypeUser().equals("CEN")) {
                centralLevel = (CentralLevel) utilisateurConnected;
            } else {
                deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            }

            List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
            Profile p = profiles.get(0);

            if(!pourTraitement){

                conditions.and(qMutation.demandeur.id.eq(userId));
            }else{

                if  (p.getCode().equalsIgnoreCase("Chef-etablissement"))
                    conditions.and(qMutation.origineDemandeurLog.etablissement.code.eq(deconcentratedLevel.getEtablissement().getCode()));
                if(p.getCode().equalsIgnoreCase("Chef-cfp"))
                    conditions.and(qMutation.origineDemandeurLog.etablissement.code.eq(deconcentratedLevel.getEtablissement().getCode()));

                if(p.getCode().equalsIgnoreCase("Chef-EFF"))
                    conditions.and(qMutation.origineDemandeurLog.etablissement.code.eq(deconcentratedLevel.getEtablissement().getCode()));

                if(p.getCode().equalsIgnoreCase("Représentant-IEF")) {
                    conditions.and(qMutation.origineDemandeurLog.ief.code.eq(deconcentratedLevel.getIef().getCode()));
                }

                if(p.getCode().equalsIgnoreCase("Representant-IA"))
                    conditions.and(qMutation.origineDemandeurLog.ia.code.eq(deconcentratedLevel.getIa().getCode()));

                if(p.getCode().contains("Chef-division") && !p.getCode().equalsIgnoreCase("Chef-division-dgpeec"))
                    conditions.and(qMutation.origineDemandeurLog.division.code.eq(centralLevel.getDivision().getCode()));
                if(p.getCode().contains("Directeur") && !p.getCode().equalsIgnoreCase("Directeur-DRH"))
                    conditions.and(qMutation.origineDemandeurLog.direction.code.eq(centralLevel.getDirection().getCode()));
                if(p.getCode().contains("Chef-service")) {
                    if (centralLevel.getDirection() != null && centralLevel.getDirection().getCode() != null) {
                        conditions.and(qMutation.origineDemandeurLog.direction.code.eq(centralLevel.getDirection().getCode()));
                    } else if (centralLevel.getService() == null || centralLevel.getService().getCode() == null) {
                        conditions.and(qMutation.id.isNull());
                    } else {
                        conditions.and(qMutation.origineDemandeurLog.direction.isNull());
                        conditions.and(qMutation.origineDemandeurLog.service.code.eq(centralLevel.getService().getCode()));
                    }
                }

            }

            if(!Objects.equals(statutMutation, "") && !Objects.equals(statutMutation, "inProgress"))
                conditions.and(qMutation.traitementMutation.statut.code.eq(statutMutation));
            if (Objects.equals(statutMutation, "inProgress")) {
                conditions.and(qMutation.traitementMutation.statut.code.ne("VALIDER"));
            }
            if(!Objects.equals(numeroRef, ""))
                conditions.and(qMutation.numeroRef.eq(region));

            if(!Objects.equals(region, ""))
                conditions.and(qMutation.regionSouhaitee.label.eq(region));
            if(!Objects.equals(ia, ""))
                conditions.and(qMutation.iaSouhaitee.label.eq(ia));
            if(!Objects.equals(ief, ""))
                conditions.and(qMutation.iefSouhaitee.label.eq(ief));
            if(!Objects.equals(etablissement, ""))
                conditions.and(qMutation.etablissementSouhaitee.label.eq(etablissement));
            if(!Objects.equals(bureau, ""))
                conditions.and(qMutation.bureauSouhaite.label.eq(bureau));
            if(!Objects.equals(direction, ""))
                conditions.and(qMutation.directionSouhaitee.label.eq(direction));
            if(!Objects.equals(division, ""))
                conditions.and(qMutation.divisionSouhaitee.label.eq(division));
            if(!Objects.equals(service, ""))
                conditions.and(qMutation.serviceSouhaite.label.eq(service));

////            mutationRDTOS = imutationRepository.findAll(conditions.getValue(), PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")))
////                    .map(mutationMapper::toDto);
//
//          mutationRDTOS = Objects.nonNull(conditions.getValue())?imutationRepository
//                                   .findAll(conditions.getValue(), PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")))
//                                   .map(mutationMapper :: toDto)
//                                   :imutationRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")))
//                                   .map(mutationMapper :: toDto);

            mutationRDTOS = Objects.nonNull(conditions.getValue())?imutationRepository
                    .findAll(conditions.getValue(), PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")))
                    .map(this::toDtoPourTraitement)
                    :imutationRepository.findAll(PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")))
                    .map(this::toDtoPourTraitement);

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(mutationRDTOS.getSize())
                    .totalPages(mutationRDTOS.getTotalPages())
                    .totalElements(mutationRDTOS.getTotalElements())
                    .number(mutationRDTOS.getNumber())
                    .build();


            return Response.ok().setPayload(mutationRDTOS.getContent())
                    .setMetadata(pageMetadata).setMessage("liste des demandes de mutations");


        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de la récupération des mutation");
        }
    }

    @Override
    public Response<Object> getOneMutation(Long idMutation) {
        try {
            Mutation mutation = imutationRepository.findById(idMutation).orElseThrow(() -> new GenericApiException("Cette mutation n'existe pas"));
            mutation.setProfilDevantTraiter(profilEffectif(mutation));
            return  Response.ok().setPayload(mutation)
                    .setMessage("Mutation récupérée");
        }catch (Exception e){
            return Response.exception().setMessage("Une erreur est survenue lors récupération de la mutation");
        }
    }
    @Override
    @Transactional
    public Response<Object> updateMutation(Long idMutation, MutationDTO mutationDTO) {
        try {
            Mutation mutation = imutationRepository.findById(idMutation).orElseThrow(() -> new GenericApiException("Cette mutation n'existe pas"));

            Mutation mutation1 = mutation;
            mutation1.setCommentaire(mutationDTO.commentaire());
            mutation1.setRegionSouhaitee(mutationDTO.regionSouhaitee());
            mutation1.setDestinataireType(mutationDTO.destinataireType());
            // Une nouvelle soumission doit être signée à nouveau ; l'historique reste conservé.
            mutation1.setDossierSigne(null);
            mutation1.setCurrentBordereauTransmission(null);

            if (Objects.equals(mutationDTO.destinataireType(), "DEC")){
                mutation1.setIaSouhaitee(mutationDTO.iaSouhaitee());
                mutation1.setIefSouhaitee(mutationDTO.iefSouhaitee());
                mutation1.setEtablissementSouhaitee(mutationDTO.etablissementSouhaitee());
                mutation1.setDirectionSouhaitee(null);
                mutation1.setDivisionSouhaitee(null);
                mutation1.setBureauSouhaite(null);
                mutation1.setServiceSouhaite(null);
            }else {
                mutation1.setIaSouhaitee(null);
                mutation1.setIefSouhaitee(null);
                mutation1.setEtablissementSouhaitee(null);
                mutation1.setDirectionSouhaitee(mutationDTO.directionSouhaitee());
                mutation1.setDivisionSouhaitee(mutationDTO.divisionSouhaitee());
                mutation1.setBureauSouhaite(mutationDTO.bureauSouhaite());
                mutation1.setServiceSouhaite(mutationDTO.serviceSouhaite());

            }
            OrigineDemandeurLog origineDemandeurLog = new OrigineDemandeurLog();
            if(Objects.equals(mutation.getDemandeur().getTypeUser(), "DEC")){
                DeconcentratedLevel deconcentratedLevel = (DeconcentratedLevel) mutation.getDemandeur();
                origineDemandeurLog = OrigineDemandeurLog.builder()
                        .region(deconcentratedLevel.getRegion())
                        .ia(deconcentratedLevel.getIa())
                        .ief(deconcentratedLevel.getIef())
                        .origineUserType(mutation.getDemandeur().getTypeUser())
                        .etablissement(deconcentratedLevel.getEtablissement()).build();
            }else {
                CentralLevel centralLevel = (CentralLevel) mutation.getDemandeur();
                origineDemandeurLog = OrigineDemandeurLog.builder()
                        .bureau(centralLevel.getBureau())
                        .direction(centralLevel.getDirection())
                        .division(centralLevel.getDivision())
                        .service(centralLevel.getService())
                        .origineUserType(mutation.getDemandeur().getTypeUser())
                        .build();
            }
            origineDemandeurLog = iOrigineDemandeurLogRepository.saveAndFlush(origineDemandeurLog);
            mutation1.setOrigineDemandeurLog(origineDemandeurLog);

            TraitementMutation traitementMutation = new TraitementMutation();
            StatutMutation statutMutation = iStatutMutationRepository.findByCode("SOUMISE");

            traitementMutation.setStatut(statutMutation);
            traitementMutation.setTraiteur(mutation.getDemandeur());
            traitementMutation.setMotif("modifié et soumis");
            traitementMutation.setDateTraitementMutation(LocalDate.now());
            traitementMutation = iTraitementMutationRepository.saveAndFlush(traitementMutation);

            mutation1.setTraitementMutation(traitementMutation);

            String profilTraitant = profilTraitant(mutation.getDemandeur(),origineDemandeurLog, mutation.getDemandeur().getTypeUser());

            mutation1.setProfilDevantTraiter(profilTraitant);
            mutation1 = imutationRepository.save(mutation1);
            //envoie notification
            sendNotify(mutation, profilTraitant,"", mutation1.getDemandeur().getTypeUser());

            return  Response.ok().setPayload(mutation1)
                    .setMessage("Mutation modifiée");
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.exception().setMessage("Une erreur est survenue lors de la modification de la mutation");
        }
    }
    @Override
    public Response<Object> genererMutation(boolean allMutationAccepted, Long idMutation )throws JRException, FileNotFoundException {

        List<Mutation> mutations = new ArrayList<>();
        if(allMutationAccepted){
            BooleanBuilder builder = new BooleanBuilder();
            QMutation qMutation = mutation;

            // Une génération groupée doit couvrir les mutations prêtes pour l'OS
            // ainsi que celles déjà finalisées après signature.
            builder.and(
                    mutation.traitementMutation.statut.code.in("REC-DGPEEC", "VALIDER")
            );

            mutations = (List<Mutation>) imutationRepository.findAll(builder.getValue());
        }else {
            Mutation mutation1 = imutationRepository.findById(idMutation).orElseThrow(() -> new GenericApiException("Cette mutation n'existe pas"));
            mutations.add(mutation1);
        }
        if (mutations.isEmpty()) {
            return Response.exception().setMessage("Aucune mutation validée disponible pour générer l'ordre de service");
        }

        byte[] mutationPDF = jasperGenerator.getMutationOS(mutations);
        MultipartFile multipartFile = new MockMultipartFile("Ordre_de_service", "ordre-de-service-mutation.pdf", "application/pdf", mutationPDF);
        Response<Object> uploadResult = fileImpl.uploadSingleFile(multipartFile, 0, "mutation");
        if (uploadResult == null || uploadResult.getStatus() != Status.OK
                || !(uploadResult.getPayload() instanceof String) || ((String) uploadResult.getPayload()).isBlank()) {
            return uploadResult != null && uploadResult.getStatus() != Status.OK ? uploadResult
                    : Response.exception().setMessage("L'enregistrement de l'ordre de service a échoué");
        }

        for (Mutation mutation1 : mutations) {
            mutation1.setOsgenerated(true);
            imutationRepository.save(mutation1);
        }
        return uploadResult;
    }

    @Override
    public Response<Object> indicateurMutation(String codeProfile) {
        long valid = 0;
        long reject = 0;
        long all = 0;
        long inProgress = 0;
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        CentralLevel centralLevel = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (utilisateurConnected.getTypeUser().equals("CEN")) {
            centralLevel = (CentralLevel) utilisateurConnected;
            List<Profile> profiles = new ArrayList<>(centralLevel.getProfils());
            Profile profile =profiles.get(0);
            String pro = profile.getCode();
            if(profile.getCode().contains("Chef-division") && !profile.getCode().equals("Chef-division-dgpeec"))
                pro = "Chef-division";
            if(Objects.equals(profile.getCode(), "Chef-service"))
                pro = "Chef-service";
            if(Objects.equals(profile.getCode(), "Chef-bureau"))
                pro = "Chef-bureau";
            switch (pro){
                case "Chef-bureau":
                    valid = imutationRepository.countValidRejectMutationsBureau(centralLevel.getBureau().getCode(),"VALIDER");
                    reject = imutationRepository.countValidRejectMutationsBureau(centralLevel.getBureau().getCode(),"REJETER");
                    all = imutationRepository.countAllMutationsBureau(centralLevel.getBureau().getCode());
                    break;
                case "Chef-division":
                    valid = imutationRepository.countValidRejectMutationsDivision(centralLevel.getDivision().getCode(),"VALIDER");
                    reject = imutationRepository.countValidRejectMutationsDivision(centralLevel.getDivision().getCode(),"REJETER");
                    all = imutationRepository.countAllMutationsDivision(centralLevel.getDivision().getCode());
                    break;
                case "Chef-service":
                    valid = imutationRepository.countValidRejectMutationsService(centralLevel.getService().getCode(),"VALIDER");
                    reject = imutationRepository.countValidRejectMutationsService(centralLevel.getService().getCode(),"REJETER");
                    all = imutationRepository.countAllMutationsService(centralLevel.getService().getCode());
                    break;

                default:
                    valid = imutationRepository.countValidRejectedMutations("VALIDER");
                    reject = imutationRepository.countValidRejectedMutations("REJETER");
                    all = imutationRepository.countAllMutations();
                    break;
            }

        } else {
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            switch (codeProfile) {
                case "Chef-etablissement":
                case "Chef-EFF":
                case "Chef-cfp":
                {
                    valid = imutationRepository.countValidRejectMutationsEtab(deconcentratedLevel.getEtablissement().getCode(),"VALIDER");
                    reject = imutationRepository.countValidRejectMutationsEtab(deconcentratedLevel.getEtablissement().getCode(),"REJETER");
                    all = imutationRepository.countAllMutationsEtab(deconcentratedLevel.getEtablissement().getCode());
                    break;
                }
                case "Représentant-IEF":
                    valid = imutationRepository.countValidRejectMutationsIef(deconcentratedLevel.getIef().getCode(),"VALIDER");
                    reject = imutationRepository.countValidRejectMutationsIef(deconcentratedLevel.getIef().getCode(),"REJETER");
                    all = imutationRepository.countAllMutationsIef(deconcentratedLevel.getIa().getCode());
                    break;
                case "Representant-IA":
                    valid = imutationRepository.countValidRejectMutationsIa(deconcentratedLevel.getIa().getCode(),"VALIDER");
                    reject = imutationRepository.countValidRejectMutationsIa(deconcentratedLevel.getIa().getCode(),"REJETER");
                    all = imutationRepository.countAllMutationsIa(deconcentratedLevel.getIa().getCode());
                    break;

                default:
//                    valid = imutationRepository.countValidRejectedMutations("VALIDER");
//                    reject = imutationRepository.countValidRejectedMutations("REJETER");
//                    all = imutationRepository.countAllMutations();

                    break;
            }
        }
        IndicateurMutation indicateurMutation = new IndicateurMutation();
        indicateurMutation.setValidated(valid);
        indicateurMutation.setRejected(reject);
        indicateurMutation.setAll(all);
        indicateurMutation.setInProgress(all -(valid + reject));
        return Response.ok().setMessage("Indicateurs mutations").setPayload(indicateurMutation);
    }

    @Override
    @Transactional
    public Response<Object>  validerMutation(Long idMutation, Long idTraitant, MultipartFile file){
        try {
            Response<Object> uploadResult = fileImpl.uploadSingleFile(file, idMutation, "mutation");
            if (uploadResult == null || uploadResult.getStatus() != Status.OK
                    || !(uploadResult.getPayload() instanceof String) || ((String) uploadResult.getPayload()).isBlank()) {
                return uploadResult != null && uploadResult.getStatus() != Status.OK ? uploadResult
                        : Response.exception().setMessage("L'ordre de service signé n'a pas été enregistré");
            }
            String generatedName = (String) uploadResult.getPayload();
            Mutation mutation = this.imutationRepository.findById(idMutation).orElseThrow(() -> new GenericApiException("Mutation non enregistrée"));

            if (!generatedName.isEmpty()) {
                StatutMutation statutMutation = iStatutMutationRepository.findByCode("VALIDER");
                Utilisateur traiteur = iUtilisateurRepository.findById(idTraitant).orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));
                TraitementMutation traitementMutation = TraitementMutation.builder()
                        .statut(statutMutation)
                        .motif("Valider aprés signature du ministre")
                        .dateTraitementMutation(LocalDate.now())
                        .traiteur(traiteur)
                        .idmutation(mutation.getId()).build();
                traitementMutation = iTraitementMutationRepository.saveAndFlush(traitementMutation);

                if (Objects.equals(mutation.getDestinataireType(), "DEC")) {

                    DeconcentratedLevel demandeur = deconcentratedLevelRepository.findById(mutation.getDemandeur().getId()).orElseThrow();
                    demandeur.setIa(mutation.getIaSouhaitee());
                    demandeur.setIef(mutation.getIefSouhaitee());
                    demandeur.setEtablissement(mutation.getEtablissementSouhaitee());
                    demandeur.setRegion(mutation.getRegionSouhaitee());
                    iUtilisateurRepository.save(demandeur);
                } else {
                    CentralLevel demandeur = centralLevelRepository.findById(mutation.getDemandeur().getId()).orElseThrow();
                    demandeur.setBureau(mutation.getBureauSouhaite());
                    demandeur.setDirection(mutation.getDirectionSouhaitee());
                    demandeur.setDivision(mutation.getDivisionSouhaitee());
                    demandeur.setService(mutation.getServiceSouhaite());
                    demandeur.setRegion(mutation.getRegionSouhaitee());
                    iUtilisateurRepository.save(demandeur);
                }
                mutation.setTraitementMutation(traitementMutation);
                mutation.setOrdreService(generatedName);
                String profilTraitant = profilTraitant(traiteur,mutation.getOrigineDemandeurLog(), mutation.getDemandeur().getTypeUser());
                mutation.setProfilDevantTraiter(profilTraitant);

                imutationRepository.save(mutation);
                businessNotifications.notify(mutation.getDemandeur(), "Demande de mutation", "Bonjour " + mutation.getDemandeur().getPrenom() + " " + mutation.getDemandeur().getNom() + " votre demande de mutation N°" + mutation.getNumeroRef() +" a été validée par le chef de division de la DGPEEC.\n  L'OS vous est envoyé par mail.");

            }
            for(String mail : mutation.getEmailsTraitant()) {
                MailInfosDTO message = new MailInfosDTO(null, "Bonjour \nVeuillez recevoir en pièce jointe la liste des mutations validées.", "Mutation(s) validée(s)", null, mail);
                org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization(
                        new org.springframework.transaction.support.TransactionSynchronization() {
                            @Override public void afterCommit() {
                                mailService.sendMailWithPJ(message, generatedName);
                            }
                        });
            }

            return Response.ok().setPayload(mutation).setMessage("Ordre de service chargé");
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.exception().setMessage("Echec lors du chargement de l'ordre de service");
        }
    }

    String profilTraitant(Utilisateur demandeur,OrigineDemandeurLog origineDemandeurLog, String niveau){

        List<Profile> profiles = new ArrayList<>(demandeur.getProfils());
        Profile profile =profiles.get(0);
        return profilTraitant(profile.getCode(), origineDemandeurLog, niveau);
    }

    String profilTraitant(String codeProfil, OrigineDemandeurLog origineDemandeurLog, String niveau) {
        String profilTraitant = "" ;
        String pro = codeProfil;
        if(codeProfil.contains("Chef-division") && !codeProfil.equals("Chef-division-dgpeec"))
            pro = "Chef-division";
        if(codeProfil.startsWith("Chef-service"))
            pro = "Chef-service";

        if(niveau.equals("DEC")){
            switch (pro) {
                case "Chef-etablissement":
                    if(origineDemandeurLog.getEtablissement().getTypeEtablissement().getCode().equals("LYC"))
                        profilTraitant = "Representant-IA";
                    else profilTraitant = "Représentant-IEF";

                    break;

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
                    // profilTraitant = "Chef-division-dgpeec";
                    break;

                case "Directeur-DRH":
                    profilTraitant = "Chef-division-dgpeec";
                    break;
                case "Chef-division-dgpeec":
                    profilTraitant = "Chef-division-dgpeec";
                    break;
                case "Formateur-EFF":
                    profilTraitant = chefEtablissementOrigine(origineDemandeurLog, "Chef-EFF");
                    break;
                case "Formateur-CFP":
                    profilTraitant = chefEtablissementOrigine(origineDemandeurLog, "Chef-cfp");
                    break;

                default:
                    profilTraitant = chefEtablissementOrigine(origineDemandeurLog, "Chef-etablissement");
                    break;
            }
        }
        else {

            switch (pro){

                case "Chef-bureau":
                    profilTraitant = "Chef-division";
                    break;
                case "Chef-division":// le cas de tous les chefs de divisions
                case "Chef-service":
                    profilTraitant = "Directeur-DRH";
                    //profilTraitant = "Chef-division-dgpeec";
                    break;
                case "Directeur-DRH":
                    profilTraitant = "Chef-division-dgpeec";
                    break;
                case "Chef-division-dgpeec": //le cas du chef de division GPEEC qui est le validateur final
                    profilTraitant = "Directeur-DRH";
                    break;
                default:{//cas où c'est un agent
                    if (origineDemandeurLog.getDivision() != null)
                        profilTraitant = "Chef-division-"+origineDemandeurLog.getDivision().getCode().toLowerCase();
                    else if (origineDemandeurLog.getService() != null)
                        profilTraitant = "Chef-service";
                    else if (origineDemandeurLog.getDirection() != null
                            && !Objects.equals(origineDemandeurLog.getDirection().getCode(), "DRH")) {
                        profilTraitant = "Chef-service";
                    }
                    else  //profilTraitant = "Chef-division-dgpeec";
                        profilTraitant = "Directeur-DRH"; //prendre en compte les autres cas non pros en charges
                    if (origineDemandeurLog.getDivision() != null
                            && Objects.equals(origineDemandeurLog.getDivision().getCode(), "DGPEEC")) {
                        profilTraitant = "Directeur-DRH";
                    }
                }
                break;
            }
        }
        return profilTraitant;
    }
    private String chefEtablissementOrigine(OrigineDemandeurLog origine, String fallback) {
        if (origine == null || origine.getEtablissement() == null
                || origine.getEtablissement().getTypeEtablissement() == null) return fallback;
        String type = origine.getEtablissement().getTypeEtablissement().getCode();
        if ("CFP".equalsIgnoreCase(type)) return "Chef-cfp";
        if ("EFF".equalsIgnoreCase(type)) return "Chef-EFF";
        return "Chef-etablissement";
    }

    // Compatibilité avec les demandes soumises avant la correction du routage.
    String profilEffectif(Mutation mutation) {
        String profil = mutation.getProfilDevantTraiter();
        if (mutation.getTraitementMutation() != null
                && mutation.getTraitementMutation().getStatut() != null
                && "SOUMISE".equals(mutation.getTraitementMutation().getStatut().getCode())
                && mutation.getOrigineDemandeurLog() != null
                && "CEN".equals(mutation.getOrigineDemandeurLog().getOrigineUserType())
                && "Directeur-DRH".equals(profil)
                && mutation.getDemandeur() != null
                && "Chef-service".equals(profilTraitant(mutation.getDemandeur(), mutation.getOrigineDemandeurLog(), "CEN"))) {
            return "Chef-service";
        }
        if (mutation.getTraitementMutation() != null
                && mutation.getTraitementMutation().getStatut() != null
                && "SOUMISE".equals(mutation.getTraitementMutation().getStatut().getCode())
                && mutation.getOrigineDemandeurLog() != null
                && "DEC".equals(mutation.getOrigineDemandeurLog().getOrigineUserType())
                && ("Chef-etablissement".equals(profil) || "Chef-cfp".equals(profil) || "Chef-EFF".equals(profil))) {
            return chefEtablissementOrigine(mutation.getOrigineDemandeurLog(), profil);
        }
        return profil;
    }

    private MutationRDTO toDtoPourTraitement(Mutation mutation) {
        MutationRDTO dto = mutationMapper.toDto(mutation);
        dto.setProfilDevantTraiter(profilEffectif(mutation));
        return dto;
    }

    Mutation addBordereauTransmission(Mutation mutation,String bordereauName, Utilisateur demandeur, String niveau){

        String pro = mutation.getProfilDevantTraiter();
        if(pro.contains("Chef-division") && !pro.equals("Chef-division-dgpeec"))
            pro = "Chef-division";
        if(pro.startsWith("Chef-service"))
            pro = "Chef-service";

        if(niveau.equals("DEC")){
            switch (pro) {
                case "Chef-etablissement": {
                    mutation.setBtCE(bordereauName);
                    break;
                }
                case "Chef-EFF":
                    mutation.setBtEFF(bordereauName);
                    break;
                case "Chef-cfp":
                    mutation.setBtCFP(bordereauName);
                    break;
                case "Représentant-IEF":
                    mutation.setBtIEF(bordereauName);
                    break;
                case "Representant-IA":
                    mutation.setBtIA(bordereauName);
                    break;

                case "Directeur-DRH":
                    mutation.setBtDRH(bordereauName);
                    break;
                case "Chef-division-dgpeec":
                    mutation.setBtDGPEEC(bordereauName);;
                    break;

                default:
                    mutation.setBtDGPEEC(bordereauName);
                    break;
            }
        }
        else {

            switch (pro){

                case "Chef-bureau":
                    mutation.setBtCB(bordereauName);
                    break;
                case "Chef-division":// le cas de tous les chefs de divisions
                    mutation.setBtCD(bordereauName);
                    break;
                case "Chef-service":
                    mutation.setBtCS(bordereauName);
                    break;
                case "Directeur-DRH":
                    mutation.setBtDRH(bordereauName);
                    break;
                case "Chef-division-dgpeec": //le cas du chef de division GPEEC qui est le validateur final
                    mutation.setBtDGPEEC(bordereauName);
                    break;
                default:
                    mutation.setBtDGPEEC(bordereauName);
                    break;
            }
        }
        return mutation;
    }
    void sendNotify(Mutation mutation, String futureProfileTraitant,String ancienProfileTraitant, String userType){
        Profile profile = iProfilRepository.findProfileByCode(futureProfileTraitant);

        Profile ancienProf = new Profile();
        if(!Objects.equals(ancienProfileTraitant, ""))
            ancienProf = iProfilRepository.findProfileByCode(ancienProfileTraitant);

        Set<Profile> profileSet = new HashSet<>();
        profileSet.add(profile);
        Notification notification = new Notification();
        notification.setObjet("Traitement mutation");
        notification.setMessage("Bonjour, \n une nouvelle demande de mutation(N° "+mutation.getNumeroRef()+")"+" vous a été trasmise. \n Merci de procéder au traitement.");

        List<DeconcentratedLevel>  deconcentratedLevel = new ArrayList<>();
        List<CentralLevel>  centralLevels = new ArrayList<>();

        if(userType.equals("CEN")){
            switch (futureProfileTraitant) {
                case "Chef-bureau":
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
                case "Chef-division":// le cas de tous les chefs de divisions
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
                case "Chef-service":
                    if (mutation.getOrigineDemandeurLog().getDirection() != null) {
                        centralLevels = centralLevelRepository.findByDirection_Code(mutation.getOrigineDemandeurLog().getDirection().getCode());
                    } else if (mutation.getOrigineDemandeurLog().getService() != null) {
                        centralLevels = centralLevelRepository.findByService_Code(mutation.getOrigineDemandeurLog().getService().getCode());
                    }
                    centralLevels = centralLevels.stream().filter(user -> user.getProfils().stream()
                                        .anyMatch(role -> role.getCode() != null && role.getCode().startsWith("Chef-service")))
                                .toList();
                    break;
                case "Directeur-DRH":
                case "Chef-division-dgpeec": //le cas du chef de division GPEEC qui est le validateur final

                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);

                    break;
                default:
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    break;
            }
            ///   centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
            //recupération mail traitant

        }else {

            switch (futureProfileTraitant) {
                case "Chef-etablissement":
                case "Chef-EFF":
                case "Chef-cfp":{
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByEtablissementAndProfilsContains(mutation.getOrigineDemandeurLog().getEtablissement(), profile);
                    break;
                }

                case "Représentant-IEF":
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByIefAndProfilsContains(mutation.getOrigineDemandeurLog().getIef(), profile);
                    break;
                case "Representant-IA":
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByIaAndProfilsContains(mutation.getOrigineDemandeurLog().getIa(), profile);
                    break;

                case "Directeur-DRH":
                case "Chef-division-dgpeec":
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);

                    break;

                default:
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    //   mutation.setBtDGPEEC(bordereauName);
                    break;
            }


        }

        if(!deconcentratedLevel.isEmpty()){
            for (DeconcentratedLevel deconcentratedLevel1 : deconcentratedLevel){
                notification.setIdUser(deconcentratedLevel1.getId());
                businessNotifications.notifyUser(notification);
                Set<String>  emails = mutation.getEmailsTraitant();
                emails.add(deconcentratedLevel.get(0).getEmail());
                mutation.setEmailsTraitant(emails);
                imutationRepository.save(mutation);
            }}
        if(!centralLevels.isEmpty()){
            for (CentralLevel centralLevel : centralLevels){
                notification.setIdUser(centralLevel.getId());
                businessNotifications.notifyUser(notification);
                Set<String>  emails = mutation.getEmailsTraitant();
                emails.add(centralLevel.getEmail());
                mutation.setEmailsTraitant(emails);
                imutationRepository.save(mutation);

            }
        }

    }
}


