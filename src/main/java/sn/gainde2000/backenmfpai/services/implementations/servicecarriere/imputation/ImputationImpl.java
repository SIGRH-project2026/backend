package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.imputation;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.commons.utils.JasperGenerator;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.commons.utils.password.PasswordGenerator;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.QImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Imputation.ImputationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.ImputationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.implementations.files.FileImpl;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Imputation.IImputation;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation.ImputationRequestdto;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationResponseDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.IndicateursImputationOrBulletin;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.QImputationOuBulletin.imputationOuBulletin;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImputationImpl implements IImputation {

    private static final List<String> GLOBAL_ACCESS_PROFILES = List.of(
            "ADMIN-DRH",
            "Admin-General",
            "Directeur-DRH");

    @Value("${url.logo.starterkit}")
    private String urlLogoStarterKit;
    private static final String NOM_ORGANISATION = "MFPAI";
    private final ImputationRepository imputationRepository;
    private final ImputationMapper imputationMapper;

    private final IUtilisateurRepository iUtilisateurRepository;

    private final IUtilisateur iUtilisateur;

    private final CentralLevelRepository centralLevelRepository;

    private final DeconcentratedLevelRepository deconcentratedLevelRepository;

    private final MailService mailService;
    private final FileImpl fileImpl;
    private final JasperGenerator jasperGenerator;

    private boolean hasGlobalAccess(Utilisateur utilisateur) {
        return utilisateur.getProfils().stream()
                .map(Profile::getCode)
                .anyMatch(GLOBAL_ACCESS_PROFILES::contains);
    }

    private Page<ImputationOuBulletin> getGlobalImputations(
            String typeDemande, PageRequest pageRequest) {
        if (StringUtils.isNotBlank(typeDemande)) {
            return imputationRepository.imputationOrBulletinByType(typeDemande, pageRequest);
        }
        return imputationRepository.allImputationOrBulletin(pageRequest);
    }

    private String getHtmlMessage(String text, String logo, String nom_organisation) {

        return """
                <!doctype html>
                  <html lang="fr">
                      <head>
                          <title> Hello world </title>
                          <meta http-equiv="X-UA-Compatible" content="IE=edge">
                          <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                          <meta name="viewport" content="width=device-width, initial-scale=1">
                      </head>
                      <body style="word-spacing:normal;">
                          <p>Bonjour,</p></br>
                          %s </br></br></br>
                          <img alt height="auto" width='auto' src="%s" style="border:0;display:block;outline:none;text-decoration:none;font-size:13px;  display: block; margin-left: auto;margin-right: auto;" /></br></br>
                          <div style="font-family:Roboto, Helvetica, sans-serif;font-size:18px;font-weight:500;line-height:24px;text-align:center;color:blue;">%s</div>
                      </body>
                  </html>
                  """
                .formatted(text, logo, nom_organisation);
    }

    public void sendEmail(MailInfosDTO mailInfosDTO) {
        MailInfosDTO mailInfos = new MailInfosDTO(mailInfosDTO.id(), mailInfosDTO.originalText(),
                mailInfosDTO.subject(),
                getHtmlMessage(mailInfosDTO.originalText(), urlLogoStarterKit, NOM_ORGANISATION),
                mailInfosDTO.destinataire());
        /// System.out.println("#### send mail dem #######");
        mailService.sendASynchronousMail(mailInfos);
        // System.out.println("#### send mail dikk #######");
    }

    public void sendNotification(LoginFormDTO loginFormDTO) {
        String textMessage = "Bonjour, (Prenom nom) votre demande d'imputation budgetaire N : (numéro) a été enrégistré avec succes.";

        MailInfosDTO mailInfosDTO = new MailInfosDTO(null, textMessage,
                "DEMANDE IMPUTATION BUDGETAIRE / BULLETIN DE VISITE", null, loginFormDTO.login());
        // System.out.println("#### send notif dem #######");
        sendEmail(mailInfosDTO);
        // System.out.println("#### send notif dikk #######");

    }

    @Override
    public Page<ImputationOuBulletin> getAllImputationFromDashbaord(int page, int size, String region, String matricule,
            String nom, String prenom, String date, String typeDemande) {
        Utilisateur currentUser = iUtilisateur.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (hasGlobalAccess(currentUser)) {
            return getGlobalImputations(typeDemande, pageRequest);
        }

        if (currentUser.getTypeUser().equals("DEC")) {
            DeconcentratedLevel deconcentratedLevel = (DeconcentratedLevel) iUtilisateur.getCurrentUser();
            switch (currentUser.getProfils().stream().findAny().get().getCode()) {
                case "Representant-IA":
                    if (typeDemande.equals("Bulletin de visite") || typeDemande.equals("Imputation budgétaire")) {
                        return imputationRepository.AllImputationOrBulletinByIaOrCreatedByAndType(
                                deconcentratedLevel.getIa().getCode(), deconcentratedLevel.getId(), // ✅ ses propres
                                                                                                    // imputations
                                typeDemande, pageRequest);
                    } else {
                        return imputationRepository.AllImputationOrBulletinByIaOrCreatedBy(
                                deconcentratedLevel.getIa().getCode(), deconcentratedLevel.getId(), // ✅ ses propres
                                                                                                    // imputations
                                pageRequest);
                    }
                case "Représentant-IEF":
                    if (typeDemande.equals("Bulletin de visite") || typeDemande.equals("Imputation budgétaire")) {
                        return imputationRepository.AllImputationOrBulletinByIefIaOrCreatedByAndType(
                                deconcentratedLevel.getIef().getIa().getCode(), // ✅ remonte à l'IA
                                deconcentratedLevel.getId(), // ✅ ses propres imputations
                                typeDemande, pageRequest);
                    } else {
                        return imputationRepository.AllImputationOrBulletinByIefIaOrCreatedBy(
                                deconcentratedLevel.getIef().getIa().getCode(), deconcentratedLevel.getId(),
                                pageRequest);
                    }
                case "Chef-etablissement":
                    if (typeDemande.equals("Bulletin de visite") || typeDemande.equals("Imputation budgétaire")) {
                        return imputationRepository.AllImputationOrBulletinByEtabAndType(
                                deconcentratedLevel.getEtablissement().getCode(), typeDemande, pageRequest);
                    } else {
                        return imputationRepository.AllImputationOrBulletinByEtablissement(
                                deconcentratedLevel.getEtablissement().getCode(), pageRequest);
                    }
            }
        } else {
            CentralLevel centralLevel = (CentralLevel) iUtilisateur.getCurrentUser();
            switch (currentUser.getProfils().stream().findAny().get().getCode()) {
                case "Chef-division-dgcaa":
                case "Chef-division-dgpeec":
                case "Chef-division-dfc":
                    if (typeDemande.equals("Bulletin de visite") || typeDemande.equals("Imputation budgétaire")) {
                        return imputationRepository.ImputationOrbulletinForDivisionDfc(
                                centralLevel.getDivision().getCode(), typeDemande, pageRequest);
                    } else {
                        return imputationRepository
                                .AllImputaionOrBulletinDivisionDfc(centralLevel.getDivision().getCode(), pageRequest);
                    }
                case "Chef-service":
                    if (typeDemande.equals("Bulletin de visite") || typeDemande.equals("Imputation budgétaire")) {
                        return imputationRepository.ImputationOrbulletinForServiceType(
                                centralLevel.getService().getCode(), typeDemande, pageRequest);
                    } else {
                        return imputationRepository.AllImputaionOrBulletinService(centralLevel.getService().getCode(),
                                pageRequest);
                    }
                case "Agent-bureau-das":
                case "Chef-bureau-das":
                case "Chef-division-das":
                    return getGlobalImputations(typeDemande, pageRequest);
            }

        }
        return null;
    }


    // À ajouter dans ImputationImpl.java
@Override
public Page<ImputationOuBulletin> getMesCreations(int page, int size, String typeDemande) {
    Utilisateur currentUser = iUtilisateur.getCurrentUser();
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    
    log.info("Récupération des créations de l'utilisateur ID: {}", currentUser.getId());
    
    if (StringUtils.isNotBlank(typeDemande)) {
        return imputationRepository.findMesImputationsByType(
            currentUser.getId(), 
            typeDemande, 
            pageRequest
        );
    } else {
        return imputationRepository.findMesImputations(
            currentUser.getId(), 
            pageRequest
        );
    }
}

    @Override
    public Page<ImputationOuBulletin> getAllImputation(int page, int size, String region,
            String matricule, String nom, String prenom, String date, String typeDemande) {

        Utilisateur currentUser = iUtilisateur.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        if (hasGlobalAccess(currentUser)) {
            return getGlobalImputations(typeDemande, pageRequest);
        }

        String profilCode = currentUser.getProfils().stream()
                .findAny()
                .orElseThrow(() -> new EntityNotFoundException("Profil introuvable"))
                .getCode();

        // ============ UTILISATEURS DECENTRALISES ============
        if (currentUser.getTypeUser().equals("DEC")) {
            DeconcentratedLevel dec = (DeconcentratedLevel) currentUser;

            switch (profilCode) {
                case "Representant-IA":
                    if (StringUtils.isNotBlank(typeDemande)) {
                        return imputationRepository
                                .AllImputationOrBulletinByIaOrCreatedByAndType(
                                        dec.getIa().getCode(),
                                        dec.getId(),
                                        typeDemande,
                                        pageRequest);
                    } else {
                        return imputationRepository
                                .AllImputationOrBulletinByIaOrCreatedBy(
                                        dec.getIa().getCode(),
                                        dec.getId(),
                                        pageRequest);
                    }

                case "Représentant-IEF":
                    if (StringUtils.isNotBlank(typeDemande)) {
                        return imputationRepository
                                .AllImputationOrBulletinByIefIaOrCreatedByAndType(
                                        dec.getIef().getIa().getCode(),
                                        dec.getId(),
                                        typeDemande,
                                        pageRequest);
                    } else {
                        return imputationRepository
                                .AllImputationOrBulletinByIefIaOrCreatedBy(
                                        dec.getIef().getIa().getCode(),
                                        dec.getId(),
                                        pageRequest);
                    }

                case "Chef-etablissement":
                    if (StringUtils.isNotBlank(typeDemande)) {
                        return imputationRepository
                                .AllImputationOrBulletinByEtabAndType(
                                        dec.getEtablissement().getCode(),
                                        typeDemande,
                                        pageRequest);
                    } else {
                        return imputationRepository
                                .AllImputationOrBulletinByEtablissement(
                                        dec.getEtablissement().getCode(),
                                        pageRequest);
                    }

                default:
                    throw new EntityNotFoundException("Profil DEC non autorisé : " + profilCode);
            }
        }

        // ============ UTILISATEURS CENTRAUX ============
        else {
            CentralLevel cen = (CentralLevel) currentUser;

            switch (profilCode) {
                case "Chef-service":
                    if (StringUtils.isNotBlank(typeDemande)) {
                        return imputationRepository
                                .ImputationOrbulletinForServiceType(
                                        cen.getService().getCode(),
                                        typeDemande,
                                        pageRequest);
                    } else {
                        return imputationRepository
                                .AllImputaionOrBulletinService(
                                        cen.getService().getCode(),
                                        pageRequest);
                    }

                case "Chef-division-dgcaa":
                case "Chef-division-dgpeec":
                case "Chef-division-dfc":
                    if (StringUtils.isNotBlank(typeDemande)) {
                        return imputationRepository
                                .ImputationOrbulletinForDivisionDfc(
                                        cen.getDivision().getCode(),
                                        typeDemande,
                                        pageRequest);
                    } else {
                        return imputationRepository
                                .AllImputaionOrBulletinDivisionDfc(
                                        cen.getDivision().getCode(),
                                        pageRequest);
                    }

                case "Agent-bureau-das":
                case "Chef-bureau-das":
                case "Chef-division-das":
                case "Assistant-DRH":
                    return getGlobalImputations(typeDemande, pageRequest);

                default:
                    throw new EntityNotFoundException("Profil CEN non autorisé : " + profilCode);
            }
        }
    }
    // @Override
    // public Page<ImputationOuBulletin> getAllImputation(int page, int size, String
    // region, String matricule, String nom, String prenom, String date,String
    // typeDemande) {
    // Utilisateur currentUser = iUtilisateur.getCurrentUser();
    // /*
    // if(currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IA")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IEF")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-BFPA")||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Agent-bureau-das")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-division-das")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-bureau-das"))
    // {*/

    // System.out.println("current user === " + currentUser.getProfils());

    // BooleanBuilder builder = new BooleanBuilder();
    // QImputationOuBulletin qImputationOuBulletin = imputationOuBulletin;
    // builder.and(
    // imputationOuBulletin.isDeleted.isFalse()
    // );
    // builder.and(
    // imputationOuBulletin.utilisateur.region.code.like(currentUser.getRegion().getCode())
    // );
    // if (StringUtils.isNotBlank(nom)) {
    // builder.and(
    // imputationOuBulletin.nomBeneficiere.likeIgnoreCase("%" + nom + "%"));
    // }

    // if (StringUtils.isNotBlank(typeDemande)) {
    // builder.and(
    // imputationOuBulletin.typeDemande.likeIgnoreCase("%" + typeDemande + "%"));
    // }

    // /*if (numeroDemande != 0) {
    // builder.and(
    // imputationOuBulletin.numeroDemande.like("%" + numeroDemande + "%"));
    // }*/

    // if (StringUtils.isNotBlank(date)) {
    // builder.and(
    // imputationOuBulletin.dateImputation.stringValue().likeIgnoreCase("%" + date +
    // "%"));
    // }

    // if (StringUtils.isNotBlank(matricule)) {
    // builder.and(
    // imputationOuBulletin.utilisateur.matricule.likeIgnoreCase("%" + matricule +
    // "%"));
    // }

    // if (StringUtils.isNotBlank(region)) {
    // builder.and(
    // imputationOuBulletin.utilisateur.region.label.likeIgnoreCase("%" + region +
    // "%"));
    // }

    // if (StringUtils.isNotBlank(prenom)) {
    // builder.and(
    // imputationOuBulletin.prenomBeneficiere.likeIgnoreCase("%" + prenom + "%"));
    // }

    // /*
    // if(currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IA")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IEF")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-BFPA"))
    // {
    // builder.and(
    // imputationOuBulletin.utilisateur.typeUser.like("DEC")
    // );

    // }else{
    // if
    // (currentUser.getProfils().stream().findAny().get().getCode().equals("Agent-bureau-dgcaa")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-division-dgcaa")
    // ||
    // currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-bureau-dgcaa"))
    // {
    // builder.and(
    // imputationOuBulletin.utilisateur.typeUser.like("CEN")
    // );
    // }

    // }*/
    // PageRequest pageRequest = PageRequest.of(page, size,
    // Sort.by(Sort.Direction.DESC, "id"));

    // return imputationRepository.findAll(builder, pageRequest);
    // }
    /*
     * else{
     * throw new
     * EntityNotFoundException("Vous avez pas acces à cette fonctionnalité");
     * }
     * 
     * 
     * }
     */

    /*
     * @Override
     * public ImputationOuBulletin createimputation(ImputationRequestdto dto) {
     * Optional<Utilisateur> utilisateurOptional =
     * iUtilisateurRepository.findById(dto.getUtilisateurId());
     * String password = PasswordGenerator.generateRandomString();
     * 
     * if (utilisateurOptional.isPresent()){
     * ImputationOuBulletin imputationOuBulletin = imputationMapper.toEntity(dto);
     * imputationOuBulletin.setUtilisateur(utilisateurOptional.get());
     * imputationOuBulletin.setDateImputation(LocalDate.now());
     * ImputationOuBulletin imputationOuBulletin1 =
     * imputationRepository.save(imputationOuBulletin);
     * if(Objects.nonNull(imputationOuBulletin1)){
     * sendNotification(new
     * LoginFormDTO(utilisateurOptional.get().getEmail(),password));
     * return imputationOuBulletin1;
     * }else
     * throw new EntityNotFoundException("utilisateur introuvable");
     * 
     * }else{
     * throw new EntityNotFoundException("utilisateur introuvable");
     * }
     * }
     */
    @Override
    public ImputationOuBulletin createimputation(ImputationRequestdto dto) {
        Optional<Utilisateur> utilisateurOptional = iUtilisateurRepository.findById(dto.getUtilisateurId());

        if (utilisateurOptional.isPresent()) {
            ImputationOuBulletin imputationOuBulletin = imputationMapper.toEntity(dto);
            imputationOuBulletin.setUtilisateur(utilisateurOptional.get());
            imputationOuBulletin.setDateImputation(LocalDate.now());

            // ✅ Stocker le créateur (l'utilisateur connecté)
            Utilisateur currentUser = iUtilisateur.getCurrentUser();
            imputationOuBulletin.setCreatedBy(currentUser);

            ImputationOuBulletin saved = imputationRepository.save(imputationOuBulletin);
            if (Objects.nonNull(saved)) {
                sendNotification(new LoginFormDTO(
                        utilisateurOptional.get().getEmail(), ""));
                return saved;
            } else {
                throw new EntityNotFoundException("Erreur lors de la sauvegarde");
            }
        } else {
            throw new EntityNotFoundException("Utilisateur introuvable");
        }
    }

    @Override
    public ImputationResponseDto deleteImputation(long id) {
        Optional<ImputationOuBulletin> imputationOuBulletinOptional = imputationRepository.findById(id);
        if (imputationOuBulletinOptional.isPresent()) {
            ImputationOuBulletin imputationOuBulletin = imputationOuBulletinOptional.get();
            imputationOuBulletin.setIsDeleted(true);
            return imputationMapper.toDto(imputationOuBulletin);
        } else {
            throw new EntityNotFoundException("utilisateur introuvable");
        }
    }

    @Override
    public ImputationOuBulletin generateimputation(long id) throws JRException, FileNotFoundException {

        ImputationOuBulletin imputationOuBulletin1 = imputationRepository.findById(id).get();
        byte[] imputationOrBulletinPDF = jasperGenerator.getImputationOrBulletinPDF(imputationOuBulletin1);
        MultipartFile multipartFile = new MockMultipartFile(
                imputationOuBulletin1.getPrenomBeneficiere() + "_" + imputationOuBulletin1.getNomBeneficiere() + "_"
                        + imputationOuBulletin1.getDateImputation() + "_" + imputationOuBulletin1.getId(),
                imputationOrBulletinPDF);
        fileImpl.uploadSingleFile(multipartFile, imputationOuBulletin1.getId(), "imputationOuBulletin");

        return imputationOuBulletin1;
    }

    @Override
    public ImputationResponseDto getOneImputation(long id) {
        System.out.println("test getOne 111 #####################");
        Optional<ImputationOuBulletin> imputationOuBulletinOptional = imputationRepository.findById(id);
        System.out.println("test getOne 222 #####################");

        if (imputationOuBulletinOptional.isPresent()) {
            ImputationOuBulletin imputationOuBulletin = imputationOuBulletinOptional.get();
            return imputationMapper.toDto(imputationOuBulletin);
        } else {
            throw new EntityNotFoundException("utilisateur introuvable");
        }
    }

    @Override
    public ImputationResponseDto recherche(String matricule) {
        Optional<Utilisateur> utilisateurOptional = iUtilisateurRepository.findUtilisateurByMatricule(matricule);
        Utilisateur currentUser = iUtilisateur.getCurrentUser();
        // System.out.println("UserEmail *************************
        // "+currentUser.getEmail());
        if (utilisateurOptional.isPresent()) {
            Utilisateur user = utilisateurOptional.get();
            // La DAS (Agent/Chef-bureau-das, Chef-division-das) gère les imputations et
            // bulletins de visite pour l'ensemble du personnel, central COMME
            // déconcentré (cf. getAllImputation/getAllImputationOrBulletin plus bas, où
            // ces profils ont une vue globale sans filtre de type d'utilisateur) : la
            // recherche par matricule ne doit donc pas se limiter aux agents "CEN",
            // sinon les agents déconcentrés (la grande majorité du personnel) sont
            // introuvables pour la création d'une imputation.
            if (hasGlobalAccess(currentUser) ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IA") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Représentant-IEF") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-BFPA") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Agent-bureau-das") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-division-das") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-bureau-das")) {
                ImputationResponseDto imputationResponseDto = new ImputationResponseDto();
                imputationResponseDto.setUtilisateur(user);
                return imputationResponseDto;
            } else
                throw new EntityNotFoundException("utilisateur introuvable");

        } else {
            throw new EntityNotFoundException("utilisateur introuvable");
        }
    }

    /*
     * les indicateurs
     */
    @Override
    public Response<Object> indicateurImputationOrBulletin(String codeProfile) {
        long imputation = 0;
        long bulletin = 0;
        long all = 0;
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        CentralLevel centralLevel = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (utilisateurConnected.getTypeUser().equals("CEN")) {
            centralLevel = (CentralLevel) utilisateurConnected;

            if (hasGlobalAccess(utilisateurConnected)) {
                imputation = imputationRepository.countImputationOrBulletinByType("Imputation budgétaire");
                bulletin = imputationRepository.countImputationOrBulletinByType("Bulletin de visite");
                all = imputationRepository.countAllImputationOrBulletin();
                return Response.ok()
                        .setMessage("indicateurs imputation et bulletin de visite")
                        .setPayload(new IndicateursImputationOrBulletin(imputation, bulletin, all));
            }

            List<Profile> profiles = new ArrayList<>(centralLevel.getProfils());
            Profile profile = profiles.get(0);
            String pro = profile.getCode();
            switch (profile.getCode()) {
                case "Chef-service":
                    all = imputationRepository
                            .countAllImputaionOrBulletinServiceDfc(centralLevel.getService().getCode());
                    imputation = imputationRepository.countImputationOrbulletinForServiceDfc(
                            centralLevel.getService().getCode(), "Imputation budgétaire");
                    bulletin = imputationRepository.countImputationOrbulletinForServiceDfc(
                            centralLevel.getService().getCode(), "Bulletin de visite");
                    break;
                case "Chef-division-dgpeec":
                case "Chef-division-dfc":
                    all = imputationRepository
                            .countAllImputaionOrBulletinDivisionDfc(centralLevel.getDivision().getCode());
                    imputation = imputationRepository.countImputationOrbulletinForDivisionDfc(
                            centralLevel.getDivision().getCode(), "Imputation budgétaire");
                    bulletin = imputationRepository.countImputationOrbulletinForDivisionDfc(
                            centralLevel.getDivision().getCode(), "Bulletin de visite");
                    break;
                case "Chef-division-das":
                    imputation = imputationRepository.countImputationOrBulletinByType("Imputation budgétaire");
                    bulletin = imputationRepository.countImputationOrBulletinByType("Bulletin de visite");
                    all = imputationRepository.countAllImputationOrBulletin();
            }
        } else {
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            List<Profile> profiles = new ArrayList<>(deconcentratedLevel.getProfils());
            Profile profile = profiles.get(0);
            String pro = profile.getCode();
            switch (profile.getCode()) {
                case "Representant-IA":
                    /*
                     * all =
                     * imputationRepository.countAllImputationOrBulletinByIA(deconcentratedLevel.
                     * getIa().getCode());
                     * imputation = imputationRepository.countAllImputationOrBulletinByIaAndType(
                     * deconcentratedLevel.getIa().getCode(),"Imputation budgétaire");
                     * bulletin = imputationRepository.countAllImputationOrBulletinByIaAndType(
                     * deconcentratedLevel.getIa().getCode(),"Bulletin de visite");
                     * System.out.println("#### Count Representant-IA == "
                     * +all+" imputation "+imputation+" bulletin "+bulletin);
                     * break;
                     */
                    all = imputationRepository.countAllImputationOrBulletinByIaOrCreatedBy(
                            deconcentratedLevel.getIa().getCode(),
                            deconcentratedLevel.getId());
                    imputation = imputationRepository.countAllImputationOrBulletinByIaOrCreatedByAndType(
                            deconcentratedLevel.getIa().getCode(),
                            deconcentratedLevel.getId(), "Imputation budgétaire");
                    bulletin = imputationRepository.countAllImputationOrBulletinByIaOrCreatedByAndType(
                            deconcentratedLevel.getIa().getCode(),
                            deconcentratedLevel.getId(), "Bulletin de visite");
                    break;
                case "Représentant-IEF":
                    /*
                     * all =
                     * imputationRepository.countAllImputationOrBulletinByIef(deconcentratedLevel.
                     * getIef().getCode());
                     * imputation = imputationRepository.countAllImputationOrBulletinByIefAndType(
                     * deconcentratedLevel.getIef().getCode(),"Imputation budgétaire");
                     * bulletin = imputationRepository.countAllImputationOrBulletinByIefAndType(
                     * deconcentratedLevel.getIef().getCode(),"Bulletin de visite");
                     * System.out.println("#### Count Representant-Ief == "
                     * +all+" imputation "+imputation+" bulletin "+bulletin);
                     * break;
                     */
                    all = imputationRepository.countAllImputationOrBulletinByIefIaOrCreatedBy(
                            deconcentratedLevel.getIef().getIa().getCode(),
                            deconcentratedLevel.getId());
                    imputation = imputationRepository.countAllImputationOrBulletinByIefIaOrCreatedByAndType(
                            deconcentratedLevel.getIef().getIa().getCode(),
                            deconcentratedLevel.getId(), "Imputation budgétaire");
                    bulletin = imputationRepository.countAllImputationOrBulletinByIefIaOrCreatedByAndType(
                            deconcentratedLevel.getIef().getIa().getCode(),
                            deconcentratedLevel.getId(), "Bulletin de visite");
                    break;
                case "chef-etablisement":
                    all = imputationRepository.countAllImputationOrBulletinByEtablissement(
                            deconcentratedLevel.getEtablissement().getCode());
                    imputation = imputationRepository.countAllImputationOrBulletinByEtabAndType(
                            deconcentratedLevel.getEtablissement().getCode(), "Imputation budgétaire");
                    bulletin = imputationRepository.countAllImputationOrBulletinByEtabAndType(
                            deconcentratedLevel.getEtablissement().getCode(), "Bulletin de visite");
                    System.out.println("#### Count etablissement == " + all + " imputation " + imputation + " bulletin "
                            + bulletin);
                    break;
            }
        }
        IndicateursImputationOrBulletin indicateursImputationOrBulletin = new IndicateursImputationOrBulletin();
        indicateursImputationOrBulletin.setImputation(imputation);
        indicateursImputationOrBulletin.setBulletin(bulletin);
        indicateursImputationOrBulletin.setAll(all);
        return Response.ok().setMessage("indicateurs imputation et bulletin de visite")
                .setPayload(indicateursImputationOrBulletin);
    }
}
