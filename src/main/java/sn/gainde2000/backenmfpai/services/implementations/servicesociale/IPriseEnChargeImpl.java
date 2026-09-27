package sn.gainde2000.backenmfpai.services.implementations.servicesociale;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.querydsl.core.BooleanBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sn.gainde2000.backenmfpai.commons.utils.helpers.Utilitaire;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.QActe;
import sn.gainde2000.backenmfpai.entities.servicesociale.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.Files.FileMapper;
import sn.gainde2000.backenmfpai.mappers.servicesociale.PriseEnChargeMapper;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.servicesociale.PriseEnChargeRepository;
import sn.gainde2000.backenmfpai.repositories.servicesociale.StatutPriseEnchargeRepository;
import sn.gainde2000.backenmfpai.repositories.servicesociale.TraitementPriseEnChargeRepository;
import sn.gainde2000.backenmfpai.repositories.servicesociale.TypeDemandeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicesociale.IPriseEnChargeService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicesociale.PriseEnChargeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.IndicateursImputationOrBulletin;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicesociale.IndicateursPec;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicesociale.PriseEnChargeResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes.IActeImpl.stringToLocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class IPriseEnChargeImpl implements IPriseEnChargeService {

    private final PriseEnChargeRepository priseEnChargeRepository;
    private final TraitementPriseEnChargeRepository traitementPriseEnChargeRepository;
    private final IUtilisateurRepository iUtilisateurRepository;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final MailService mailService;
    private final IUtilisateur iUtilisateur;
    private final StatutPriseEnchargeRepository statutPriseEnchargeRepository;
    private final PriseEnChargeMapper priseEnChargeMapper;
    private final TypeDemandeRepository typeDemandeRepository;


    @Override
    public Response<Object> createPEC(PriseEnChargeDTO priseEnChargeDTO) {
       // try {
            log.info("------------------------INIT CREATION PRISE EN CHARGE-----------------------");
            PriseEnCharge priseEnCharge = priseEnChargeMapper.toEntity(priseEnChargeDTO);
            CentralLevel centralLevel = new CentralLevel();
            DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
            log.info(priseEnChargeDTO.toString());
            log.info("On est là1");
            Utilisateur utilisateur = iUtilisateurRepository.findById(priseEnChargeDTO.getIdUtilisateur()).orElseThrow();
            TypeDemande typeDemande=typeDemandeRepository.findTypeDemandeByCode(priseEnChargeDTO.getCodeTypeDemande()).orElseThrow();
            System.out.println(typeDemande.toString());
            priseEnCharge.setUtilisateur(utilisateur);
           if(utilisateur.getTypeUser().equalsIgnoreCase("CEN"))
           {
             centralLevel = (CentralLevel) utilisateur;
             priseEnCharge.setDivision(centralLevel.getDivision());
             priseEnCharge.setDirection(centralLevel.getDirection());
             priseEnCharge.setService(centralLevel.getService());
             priseEnCharge.setBureau(centralLevel.getBureau());
          }
          if(utilisateur.getTypeUser().equalsIgnoreCase("DEC"))
          {
            deconcentratedLevel = (DeconcentratedLevel) utilisateur;
            priseEnCharge.setEtablissement(deconcentratedLevel.getEtablissement());
            priseEnCharge.setIef(deconcentratedLevel.getIef());
            priseEnCharge.setIa(deconcentratedLevel.getIa());
            priseEnCharge.setRegion(deconcentratedLevel.getRegion());
          }
            priseEnCharge.setStatutPriseEnCharge(new StatutPriseEnCharge(200L, "soumise", "soumise"));
            priseEnCharge.setNumeroDemande(Utilitaire.genererNumero());
            priseEnCharge.setDateDemande(LocalDate.now());
            priseEnCharge.setTypeDemandePeec(typeDemande);

            PriseEnCharge savedPriseEnCharge = priseEnChargeRepository.save(priseEnCharge);
        // envoie mail au demandeur
           String emailDemandeur = priseEnCharge.getUtilisateur().getEmail();
            mailService.sendMail(new MailInfosDTO(null,
                "Bonjour "+utilisateur.getPrenom()+" " +
                        ""+utilisateur.getNom()+"\nVotre demande prise en charge N°"+ priseEnCharge.getNumeroDemande()+"  a été enregitrée avec succès",
                "Création Demande de Prise en Charge",
                null, utilisateur.getEmail()));



            return Response.ok()
                    .setPayload(priseEnChargeMapper.toDto(savedPriseEnCharge))
                    .setMessage("Création Prise en Charge avec Succés");
        /*} catch (Exception e) {
            log.error("------------------Erreur lors de l'operation ------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur s'est produite lors de l'operation");
        }*/
    }

    @Override
    public Response<Object> ListPECFiltreAvances(int page, int size, long typeUserId, String numero,String matricule, String nom, String prenom, String region, String ia, String date, String objet, String statut,String type) {
        try {
            log.info("+++++++++++++++++++++++INIT LISTE PRISE EN CHARGE+++++++++++++++++++++++++++");
            BooleanBuilder builder = new BooleanBuilder();
            Page<PriseEnChargeResponseDTO> priseEnChargePage;
            Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
            CentralLevel centralLevel = new CentralLevel();
            DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
            List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
            Profile p = profiles.get(0);
            if (utilisateurConnected.getTypeUser().equalsIgnoreCase("CEN")) {
                centralLevel = (CentralLevel) utilisateurConnected;
            } if (utilisateurConnected.getTypeUser().equalsIgnoreCase("DEC")){
                deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            }


            if (typeUserId != 0) {
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.id.eq(typeUserId));
            }
            else {
                builder.and((QPriseEnCharge.priseEnCharge.utilisateur.id.notIn(utilisateurConnected.getId())));
                if (p.getCode().contains("Chef-division") && !p.getCode().equalsIgnoreCase("Chef-division-dgcaa")) {
                    System.out.println("CHEF");
                    builder.and(QPriseEnCharge.priseEnCharge.division.code.eq(centralLevel.getDivision().getCode()));
                }
                if (p.getCode().contains("Directeur") && !p.getCode().equalsIgnoreCase("Directeur-DRH") )  {
                    builder.and(QPriseEnCharge.priseEnCharge.direction.code.eq(centralLevel.getDirection().getCode()));
                }
                if (p.getLabel().equalsIgnoreCase("Chef-service")) {
                    System.out.println("CHEF");
                    builder.and(QPriseEnCharge.priseEnCharge.direction.code.eq(centralLevel.getDirection().getCode()));
                    if(centralLevel.getService()!=null)
                        builder.and(QPriseEnCharge.priseEnCharge.service.code.eq(centralLevel.getService().getCode()));

                }

                if (p.getCode().equalsIgnoreCase("Chef-etablissement")) {
                    System.out.println("Here");
                    builder.and(QPriseEnCharge.priseEnCharge.etablissement.code.eq(deconcentratedLevel.getEtablissement().getCode()));

                }
                if (p.getCode().equalsIgnoreCase("Chef-cfp")) {
                    builder.and(QPriseEnCharge.priseEnCharge.etablissement.typeEtablissement.code.eq(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode())
                            .and(QPriseEnCharge.priseEnCharge.etablissement.typeEtablissement.code.equalsIgnoreCase("cfp")));

                }

                if (p.getCode().equalsIgnoreCase("Chef-EFF")) {
                    builder.and(QPriseEnCharge.priseEnCharge.etablissement.typeEtablissement.code.eq(deconcentratedLevel.getEtablissement().getTypeEtablissement().getCode())
                            .and(QPriseEnCharge.priseEnCharge.etablissement.typeEtablissement.code.equalsIgnoreCase("eff")));

                }

                if (p.getCode().equalsIgnoreCase("Representant-IA")) {

                    builder.and(QPriseEnCharge.priseEnCharge.ia.code.equalsIgnoreCase(deconcentratedLevel.getIa().getCode()));

                }
                if (p.getCode().equalsIgnoreCase("Représentant-IEF")) {
                    builder.and(QPriseEnCharge.priseEnCharge.ief.code.equalsIgnoreCase(deconcentratedLevel.getIef().getCode()));

                }
            }
            if (StringUtils.isNotBlank(numero)) {
                builder.and(QPriseEnCharge.priseEnCharge.numeroDemande.containsIgnoreCase(numero));
            }

            if (StringUtils.isNotBlank(region)) {
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.region.code.containsIgnoreCase(region));
            }
            if (StringUtils.isNotBlank(nom)) {
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.nom.containsIgnoreCase(nom));
            }
            if (StringUtils.isNotBlank(prenom)) {
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.prenom.containsIgnoreCase(prenom));
            }

            /*if (StringUtils.isNotBlank(ia)) {
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.typeUser.containsIgnoreCase("DEC"));
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.ia.containsIgnoreCase("DEC"));

                {
                }

            }*/
            if (StringUtils.isNotBlank(matricule)) {
                builder.and(QPriseEnCharge.priseEnCharge.utilisateur.matricule.containsIgnoreCase(matricule));
            }

            if (StringUtils.isNotBlank(date)) {
                builder.and(QPriseEnCharge.priseEnCharge.dateDemande.eq(stringToLocalDate(date, "yyyy-MM-dd")));
            }

            if (StringUtils.isNotBlank(objet)) {
                builder.and(QPriseEnCharge.priseEnCharge.objetDemande.containsIgnoreCase(objet));
            }

            if (StringUtils.isNotBlank(statut)) {
                builder.and(QPriseEnCharge.priseEnCharge.statutPriseEnCharge.libelle.containsIgnoreCase(statut));
            }

            if (StringUtils.isNotBlank(type)) {
                builder.and(QPriseEnCharge.priseEnCharge.typeDemandePeec.libelle.containsIgnoreCase(type));
            }

            priseEnChargePage = Objects.nonNull(builder.getValue()) ?
                    priseEnChargeRepository.findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                            .map(priseEnCharge -> priseEnChargeMapper.toDto(priseEnCharge))
                    :
                    priseEnChargeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                            .map(priseEnCharge -> priseEnChargeMapper.toDto(priseEnCharge))
            ;
            //System.out.println(priseEnChargePage.stream().toList());
            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(priseEnChargePage.getSize())
                    .totalPages(priseEnChargePage.getTotalPages())
                    .totalElements(priseEnChargePage.getTotalElements())
                    .number(priseEnChargePage.getNumber())
                    .build();
            return Response.ok()
                    .setPayload(priseEnChargePage.getContent())
                    .setMetadata(pageMetadata)
                    .setMessage("Liste des Demandes de Prise en Charge");
        } catch (Exception e) {
            log.error("------------------Erreur lors de l'operation ------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Erreur sur la liste des Demandes de Prise en Charge avec filtres avancés ");
        }
    }

    @Override
    public Response<Object> getPECById(long id) {
        log.info("+++++++++++++++++++++++INIT GETONE PEC+++++++++++++++++++++++++++");
        try {
            List<File> files = new ArrayList<>();
            PriseEnCharge priseEnCharge = priseEnChargeRepository.findPriseEnChargeById(id).orElseThrow();
            files = fileRepository.getAllByIdAppartenance(id);
            log.info(files.toString());
            PriseEnCharge savedPriseEnCharge = priseEnChargeRepository.save(priseEnCharge);
            List<File> savedFiles = savedPriseEnCharge.getPieceJointes();
            PriseEnChargeResponseDTO priseEnChargeResponseDTO = priseEnChargeMapper.toDto(savedPriseEnCharge);
            List<FileRspDTO> fileRspDTOS = fileMapper.toDtoList(savedFiles);
            priseEnChargeResponseDTO.setPieceJointes(fileRspDTOS);
            return Response.ok()
                    .setPayload(priseEnChargeResponseDTO)
                    .setMessage("Récupération PEC avec Succés");
        } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la recherche de la Demande de Prise en Charge------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la recherche de la Demande de Prise en Charge");
        }
    }

    @Override
    public Response<Object> traiterPEC(long id, long idResponsableTraitement, String traitement,String motifModif,String motifRejet) {
       // try {
            PriseEnCharge priseEnCharge = priseEnChargeRepository.findPriseEnChargeById(id).orElseThrow();
            TraitementPriseEnCharge traitementPriseEnCharge = new TraitementPriseEnCharge();
            Utilisateur agentResp = iUtilisateurRepository.findById(idResponsableTraitement).orElseThrow();
            traitementPriseEnCharge.setDernierEtatPec(priseEnCharge.getStatutPriseEnCharge().getLibelle());
            StatutPriseEnCharge statutPriseEnCharge = new StatutPriseEnCharge();
            traitementPriseEnCharge.setDernierEtatPec(priseEnCharge.getStatutPriseEnCharge().getLibelle());
            System.out.println("ja suis là 1 ");
            switch (traitement) {
                    case "modifier": {
                    System.out.println(" Modif "+ motifModif);
                    statutPriseEnCharge = statutPriseEnchargeRepository.findByCode("AMODIFIER").orElseThrow();
                    priseEnCharge.setMotifModification(motifModif);
                    // envoie mail au demandeur
                        String emailDemandeur = priseEnCharge.getUtilisateur().getEmail();
                        mailService.sendMail(new MailInfosDTO(null,
                                "Bonjour "+priseEnCharge.getUtilisateur().getPrenom()+" " +
                                        ""+priseEnCharge.getUtilisateur().getNom()+"\nVotre demande prise en charge N°"+ priseEnCharge.getNumeroDemande()+"  vous est renvoyé pour modification",
                                "Modification Demande de Prise en Charge",
                                null, priseEnCharge.getUtilisateur().getEmail()));
                        break;
                }
                case "rejeter": {
                    System.out.println("Rejet "+motifRejet);
                    statutPriseEnCharge = statutPriseEnchargeRepository.findByCode("REJETEE").orElseThrow();
                    priseEnCharge.setMotifRejetDemande(motifRejet);
                    break;
                }
                case "valider": {
                    statutPriseEnCharge = statutPriseEnchargeRepository.findByCode("VALIDEE").orElseThrow();
                    break;
                }
                default:
            }
            priseEnCharge.setStatutPriseEnCharge((statutPriseEnCharge));
            System.out.println("ja suis là 2 "+ priseEnCharge.getMotifModification());
           // System.out.println("ja suis là 2 "+ statutPriseEnCharge.toString());
            traitementPriseEnCharge.setDernierEtatPec(priseEnCharge.getStatutPriseEnCharge().getLibelle());
            traitementPriseEnCharge.setTraitant(agentResp);
            traitementPriseEnCharge.setTraitement(statutPriseEnCharge.getLibelle());
            TraitementPriseEnCharge saved=traitementPriseEnChargeRepository.save(traitementPriseEnCharge);
          //  System.out.println("j'ai fait le traitement");
            return Response.ok()
                    .setPayload(priseEnChargeMapper.toDto(priseEnChargeRepository.save(priseEnCharge)))
                    .setMessage("Traitement prise en charge effetuee avec succes");
      /*  } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors du traitement de la Demande de Prise en Charge------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors du traitement de Demande de Prise en Charge");
        }*/
    }

    @Override
    public Response<Object> updatePEC(long id, PriseEnChargeDTO priseEnChargeDTO) {
        try {
           // log.info("------------------------INIT MODIFICATION PRISE EN CHARGE-----------------------");
            PriseEnCharge priseEnCharge = priseEnChargeRepository.findPriseEnChargeById(id).orElseThrow();
           // log.info("11111111111111"+priseEnCharge.toString());
            long idPec=priseEnCharge.getId();
            String num= priseEnCharge.getNumeroDemande();
            Utilisateur utilisateur= priseEnCharge.getUtilisateur();
            LocalDate dateDemande=priseEnCharge.getDateDemande();
            List<File> savedFiles = priseEnCharge.getPieceJointes();
            StatutPriseEnCharge statutPriseEnCharge=priseEnCharge.getStatutPriseEnCharge();
            priseEnCharge=priseEnChargeMapper.toEntity(priseEnChargeDTO);
           // log.info("222222222222222222"+priseEnCharge.toString());
            priseEnCharge.setId(idPec);
            priseEnCharge.setDateDemande(dateDemande);
            priseEnCharge.setNumeroDemande(num);
            priseEnCharge.setLastModified(LocalDate.now());

            TypeDemande typeDemande=typeDemandeRepository.findTypeDemandeByCode(priseEnChargeDTO.getCodeTypeDemande()).orElseThrow();
            priseEnCharge.setTypeDemandePeec(typeDemande);

            statutPriseEnCharge=statutPriseEnchargeRepository.findStatutPriseEnChargeByCode("SOUMISE").orElseThrow();

          //  statutPriseEnCharge.setCode();
            priseEnCharge.setStatutPriseEnCharge(statutPriseEnCharge);
            priseEnCharge.setUtilisateur(utilisateur);
            priseEnCharge.setStatutPriseEnCharge(statutPriseEnCharge);
            priseEnCharge.setPieceJointes(savedFiles);
           // log.info("33333333333333333333"+priseEnCharge.toString());

            return Response.ok()
                        .setPayload(priseEnChargeMapper.toDto(priseEnChargeRepository.save(priseEnCharge)))
                        .setMessage("Modifiaction prise en charge effetuee avec succes");
        } catch(Exception e) {
        log.error("------------------Une erreur est survenue lors de la mise à jour de la Demande de Prise en Charge------------------------ : {}", e.getMessage());
        return Response
                .exception()
                .setMessage("Une erreur est survenue lors de la mise à jour de la Demande de Prise en Charge");
        }
    }
    @Override
    public Response<Object> deletePEC(long id) {
        try {
            PriseEnCharge priseEnCharge = priseEnChargeRepository.findPriseEnChargeById(id).orElseThrow();
            if (priseEnCharge.getPieceJointes()!=null)
                for (File f: priseEnCharge.getPieceJointes()
                     ) {
                    fileRepository.delete(f);
                }
            priseEnChargeRepository.deleteById(id);
            return Response.ok()
                    .setMessage("Suppression Prise en Charge effetuee avec succes");

        } catch (Exception e) {
            log.error("------------------Une erreur est survenue lors de la suppression de la Demande de Prise en Charge------------------------ : {}", e.getMessage());
            return Response
                    .exception()
                    .setMessage("Une erreur est survenue lors de la suppression de la Demande de Prise en Charge");
        }
    }

    @Override
    public Response<Object> getPecStatistiques(String codeProfile) {
        long validPec = 0;
        long rejectPec = 0;
        long allPec = 0;
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
                    validPec = priseEnChargeRepository.countValidRejectPecBureau(centralLevel.getBureau().getCode(), "VALIDEE");
                    rejectPec = priseEnChargeRepository.countValidRejectPecBureau(centralLevel.getBureau().getCode(), "REJETEE");
                    allPec = priseEnChargeRepository.countAllPecBureau(centralLevel.getBureau().getCode());
                }
                break;
                case "Chef-division": {
                    System.out.println("Division");
                    validPec = priseEnChargeRepository.countValidRejectPecDivision(centralLevel.getDivision().getCode(), "VALIDEE");
                    rejectPec = priseEnChargeRepository.countValidRejectPecDivision(centralLevel.getDivision().getCode(), "REJETEE");
                    allPec = priseEnChargeRepository.countTotalPecDivision(centralLevel.getDivision().getCode());
                    break;
                }
                case "Chef-service": {
                        System.out.println("Service");

                        validPec = priseEnChargeRepository.countValidRejectPecService(centralLevel.getService().getCode(), "VALIDEE");
                        rejectPec = priseEnChargeRepository.countValidRejectPecService(centralLevel.getService().getCode(), "REJETEE");
                        allPec = priseEnChargeRepository.countTotalPecService(centralLevel.getService().getCode());
                        break;

                }

                default: {
                    System.out.println("Chefs");
                    validPec = priseEnChargeRepository.countValidReject( "VALIDEE");
                    rejectPec = priseEnChargeRepository.countValidReject( "REJETEE");
                    allPec = priseEnChargeRepository.countTotalPec();
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
                    validPec = priseEnChargeRepository.countValidRejectPecEtab(deconcentratedLevel.getEtablissement().getCode(), "VALIDEE");
                    rejectPec = priseEnChargeRepository.countValidRejectPecEtab(deconcentratedLevel.getEtablissement().getCode(), "REJETEE");
                    allPec = priseEnChargeRepository.countAllPecEtab(deconcentratedLevel.getEtablissement().getCode());
                    break;
                }
                case "Représentant-IEF": {
                    System.out.println("IEF");

                    validPec = priseEnChargeRepository.countValidRejectPecIef(deconcentratedLevel.getIef().getCode(), "VALIDEE");
                    rejectPec = priseEnChargeRepository.countValidRejectPecIef(deconcentratedLevel.getIef().getCode(), "REJETEE");
                    allPec = priseEnChargeRepository.countAllPecIef(deconcentratedLevel.getIef().getCode());
                    break;
                }
                case "Representant-IA": {
                    System.out.println("Ia");
                    validPec = priseEnChargeRepository.countValidRejectPecIa(deconcentratedLevel.getIa().getCode(), "VALIDEE");
                    rejectPec = priseEnChargeRepository.countValidRejectPecIa(deconcentratedLevel.getIa().getCode(), "REJETEE");
                    allPec = priseEnChargeRepository.countAllPecIa(deconcentratedLevel.getIa().getCode());
                    break;
                }

                default:


                    break;
            }
        }
        IndicateursPec indicateursPec = new IndicateursPec();
        indicateursPec.setValidPec(validPec);
        indicateursPec.setRejectedPec(rejectPec);
        indicateursPec.setAllPec(allPec);
        return Response.ok().setMessage("Statistiques Prises en Charge").setPayload(indicateursPec);
    }
}
