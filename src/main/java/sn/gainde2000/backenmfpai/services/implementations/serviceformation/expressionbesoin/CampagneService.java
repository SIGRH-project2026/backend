package sn.gainde2000.backenmfpai.services.implementations.serviceformation.expressionbesoin;

import com.querydsl.core.BooleanBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutCampagneEnum;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutExpressionDeBesoinEnum;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin.CampagneMapper;
import sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin.ExpressionDeBesoinMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin.ICampagne;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.CampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.CampagneResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.UtilisateurResponseDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * On donne les date de debut et de fin d'une campagne
 * lorsqu'une campagne est demarrer c'est plus possible de
 * modifier sa date de debut
 * le clic sur demarrer campagne recupere automatiquement la date de debut de la
 * campagne
 * le clic sur cloturee campagne renseigne automatiquement la date de fin de la
 * campagne
 */


@Service
@RequiredArgsConstructor
public class CampagneService implements ICampagne {
    private final CampagneRepository campagneRepository;
    private final CampagneMapper campagneMapper;
    private final CentralLevelRepository centralLevelRepository;
    private final StatutCampagneService statutCampagneService;
    private final TraitementCampagneRepository traitementCampagneRepository;
    private final ExpressionDeBesoinRepository expressionDeBesoinRepository;
    private final ExpressionDeBesoinMapper expressionDeBesoinMapper;
    private final IUtilisateur iUtilisateur;
    private final StatutCampagneRepository statutCampagneRepository;
    private final TraitementExpressionDeBesoinRepository traitementExpressionDeBesoinRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;

    /**
     * CREATION CAMPAGNE
     *
     * @param campagneRequestDTO
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Response<Object> saveCampagne(CampagneRequestDTO campagneRequestDTO, HttpServletRequest request) {
        // verifier si le nom est vide
        if (campagneRequestDTO.getNom().isEmpty())
            return Response.exception().setMessage("Veuillez renseigner le nom de la campagne");
        // verifier si la date de debut est vide
        if (campagneRequestDTO.getDateDebut() == null)
            return Response.exception().setMessage("Veuillez renseigner la date de début de la campagne");
        // verifier si la date de fin est vide
        if (campagneRequestDTO.getDateFin() == null)
            return Response.exception().setMessage("Veuillez renseigner la date de fin de la campagne");
        // verifier si la date de debut est anterieur a la date final de la campagne
        boolean isBefore_1 = campagneRequestDTO.getDateDebut().isBefore(campagneRequestDTO.getDateFin());
        if (!isBefore_1)
            return Response.exception().setMessage("La date de début de la campagne invalide");
        // mappage en campagne
        Campagne campagne = campagneMapper.map(campagneRequestDTO);
        campagne.setStatut(StatutCampagneEnum.NEW_CAMPAGNE.name());

        campagne.setCentralLevel(centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).orElse(null));
        Campagne campagne_ = campagneRepository.save(campagne);
        saveTraitement(campagne_, StatutCampagneEnum.NEW_CAMPAGNE);
        return Response.ok().setMessage("Campagne créer avec succès.").setPayload(campagneMapper.mapToCampagneResponseDTO(campagne_));
    }

    /**
     * MODIFICATION CAMPAGNE
     * 
     * @param campagneRequestDTO
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<Object> editCampagne(CampagneRequestDTO campagneRequestDTO, long id) {

        boolean isBefore = campagneRequestDTO.getDateDebut().isBefore(campagneRequestDTO.getDateFin());
        if (!isBefore)
            return Response.exception().setMessage("La date de début de la campagne est invalide");
        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(id);
        if (optionalCampagne.isEmpty())
            return Response.exception().setMessage("Campagne inexistante");
        Campagne campagne = optionalCampagne.get();

//        if (campagneRequestDTO.getIsFilePresent().equals("oui"))
//            campagne.getPieceJoint().clear();


        campagne.setNom(campagneRequestDTO.getNom());
        campagne.setDateDebut(campagneRequestDTO.getDateDebut());
        campagne.setDateFin(campagneRequestDTO.getDateFin());
        campagneRepository.save(campagne);

        return Response.ok().setMessage("Campagne modifiée avec succès.").setPayload(campagneMapper.mapToCampagneResponseDTO(campagne));
    }

    /**
     * supprimer une campagne
     * 
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<Object> deleteCampagne(long id) {
        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(id);
        if (optionalCampagne.isEmpty())
            return Response.exception().setMessage("Campagne inexistante");
        Campagne campagne = optionalCampagne.get();
        campagne.setDeleted(true);
        campagneRepository.save(campagne);
        return Response.ok().setMessage("Campagne supprimée avec succès.");
    }

    public static LocalDate stringToLocalDate(String dateString, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * recuperation des campagne
     * @param page
     * @param size
     * @param filter
     * @param nom
     * @param dateDebut
     * @param dateFin
     * @return
     */
    @Override
    @Transactional
    public Response<Object> getAllCampagne(int page, int size, String filter, String nom, String dateDebut,
            String dateFin) {
        Page<CampagneResponseDTO> campagneResponseDTOS;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(
                QCampagne.campagne.deleted.isFalse());
        if (StringUtils.isNotBlank(nom)) {
            builder.and(
                    QCampagne.campagne.nom.likeIgnoreCase("%" + nom + "%"));
        }
        if (StringUtils.isNotBlank(dateDebut)) {
            builder.and(
                    QCampagne.campagne.dateDebut.after(stringToLocalDate(dateDebut, "yyyy-MM-dd")));
        }

        if (StringUtils.isNotBlank(dateFin)) {
            builder.and(
                    QCampagne.campagne.dateDebut.before(stringToLocalDate(dateFin, "yyyy-MM-dd")));
        }

        campagneResponseDTOS = Objects.nonNull(builder.getValue()) ? campagneRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(campagne -> {
                    TraitementCampagne traitementCampagne = traitementCampagneRepository
                            .findTraitementCampagneByActivatedTrueAndCampagne_Id(campagne.getId()).get(0);
                    CampagneResponseDTO campagneResponseDTO = campagneMapper.mapToCampagneResponseDTO(campagne);
                    campagneResponseDTO.setNumberOfExpressionDeBesoin(
                            expressionDeBesoinRepository.findByCampagne_IdAndDeletedFalse(campagne.getId()).size());
                    campagneResponseDTO.setStatut(StatutCampagneEnum.valueOf(campagne.getStatut()));
                    switch (campagneResponseDTO.getStatut().toString()) {
                        case "NEW_CAMPAGNE":
                            campagneResponseDTO.setCardBackground("new-campaign");
                            break;
                        case "STARTED_CAMPAGNE":
                            campagneResponseDTO.setCardBackground("progress-campaign");
                            break;
                        case "ENDED_CAMPAGNE":
                            campagneResponseDTO.setCardBackground("closed-campaign");
                            break;
                        default:
                            break;
                    }
                    return campagneResponseDTO;
                })
                : campagneRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(campagne -> {
                            TraitementCampagne traitementCampagne = traitementCampagneRepository
                                    .findTraitementCampagneByActivatedTrueAndCampagne_Id(campagne.getId()).get(0);
                            CampagneResponseDTO campagneResponseDTO = campagneMapper.mapToCampagneResponseDTO(campagne);
                            campagneResponseDTO.setNumberOfExpressionDeBesoin(expressionDeBesoinRepository
                                    .findByCampagne_IdAndDeletedFalse(campagne.getId()).size());
                            campagneResponseDTO.setStatut(
                                    StatutCampagneEnum.valueOf(traitementCampagne.getStatutCampagne().getCode()));
                            switch (campagneResponseDTO.getStatut().toString()) {
                                case "NEW_CAMPAGNE":
                                    campagneResponseDTO.setCardBackground("new-campaign");
                                    break;
                                case "STARTED_CAMPAGNE":
                                    campagneResponseDTO.setCardBackground("progress-campaign");
                                    break;
                                case "ENDED_CAMPAGNE":
                                    campagneResponseDTO.setCardBackground("closed-campaign");
                                    break;
                                default:
                                    break;
                            }
                            return campagneResponseDTO;
                        });
        List<CampagneResponseDTO> campagneResponseDTOS2;
        Page<CampagneResponseDTO> campagneResponseDTOS_;
        if (StringUtils.isNotBlank(filter)) {
            campagneResponseDTOS_ = getValueFormPage(campagneResponseDTOS, filter);
            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(campagneResponseDTOS_.getSize())
                    .number(campagneResponseDTOS_.getNumber())
                    .totalElements(campagneResponseDTOS_.getTotalElements())
                    .totalPages(campagneResponseDTOS_.getTotalPages())
                    .build();
            return Response.ok().setPayload(campagneResponseDTOS_.getContent()).setMetadata(pageMetadata)
                    .setMessage("Liste des campagnes");
        } else {
            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(campagneResponseDTOS.getSize())
                    .number(campagneResponseDTOS.getNumber())
                    .totalElements(campagneResponseDTOS.getTotalElements())
                    .totalPages(campagneResponseDTOS.getTotalPages())
                    .build();
            return Response.ok().setPayload(campagneResponseDTOS.getContent()).setMetadata(pageMetadata)
                    .setMessage("Liste des campagnes");
        }

    }

    Page<CampagneResponseDTO> getValueFormPage(Page<CampagneResponseDTO> campagneResponseDTOS, String filter) {
        List<CampagneResponseDTO> campagneResponseDTOS1 = campagneResponseDTOS.getContent();
        List<CampagneResponseDTO> campagneResponseDTOS2;
        campagneResponseDTOS2 = campagneResponseDTOS1.stream()
                .filter(campagne_ -> campagne_.getDateDebut().getYear() == Integer.parseInt(filter))
                .collect(Collectors.toList());
        return new PageImpl<>(campagneResponseDTOS2);
    }

    public static <T> Page<T> listToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    /**
     * demarrer une campagne
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Response<Object> startCampagne(long id, HttpServletRequest request) {
        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(id);
        if (optionalCampagne.isEmpty())
            return Response.ok().setMessage("Campagne inexistante");
        if (!campagneRepository.findCampagneByStatutAndDeletedFalse("STARTED_CAMPAGNE").isEmpty())
            return Response.exception().setMessage("Il y a déja une campagne en cours.");
        TraitementCampagne traitementCampagne = traitementCampagneRepository
                .findTraitementCampagneByCampagne_IdAndActivatedTrue(optionalCampagne.get().getId()).get(0);
        if (traitementCampagne.getStatutCampagne().getCode().equals(StatutCampagneEnum.STARTED_CAMPAGNE.name()))
            return Response.ok().setMessage("Campagne déjà démarrée.");
        Campagne campagne = optionalCampagne.get();
        campagne.setDateDebut(LocalDate.now());
        campagne.setStatut(StatutCampagneEnum.STARTED_CAMPAGNE.name());
        Campagne campagne_ = campagneRepository.save(campagne);
        saveTraitement(campagne_, StatutCampagneEnum.STARTED_CAMPAGNE);
        return Response.ok().setMessage("Campagne demarrer avec succès.");
    }

    /**
     * enregistrer traitement campagne
     * @param campagne
     * @param statutCampagneEnum
     */
    private void saveTraitement(Campagne campagne, StatutCampagneEnum statutCampagneEnum) {
        // mettre les statut des campagne a false
        List<TraitementCampagne> traitementCampagnes = traitementCampagneRepository
                .findTraitementCampagneByCampagne_Id(campagne.getId());
        for (TraitementCampagne traitementCampagne : traitementCampagnes) {
            traitementCampagne.setActivated(false);
            traitementCampagneRepository.save(traitementCampagne);
        }
        // creer un nouveau traitement de campagne
        TraitementCampagne traitementCampagne = new TraitementCampagne();
        traitementCampagne.setCampagne(campagne);
        traitementCampagne.setUtilisateur(iUtilisateur.getCurrentUser());
        traitementCampagne.setActivated(true);
        if (statutCampagneRepository.findStatutCampagneByCode(statutCampagneEnum.name()).isPresent())
            traitementCampagne.setStatutCampagne(
                    statutCampagneRepository.findStatutCampagneByCode(statutCampagneEnum.name()).get());
        traitementCampagneRepository.save(traitementCampagne);
    }

    /**
     * stopper une campagne
     *
     * @param id
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Response<Object> stopCampagne(long id, HttpServletRequest request) {
        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(id);
        if (optionalCampagne.isEmpty())
            return Response.ok().setMessage("Campagne inexistante");
        TraitementCampagne traitementCampagne = traitementCampagneRepository
                .findTraitementCampagneByCampagne_IdAndActivatedTrue(optionalCampagne.get().getId()).get(0);
        if (traitementCampagne.getStatutCampagne().getCode().equals(StatutCampagneEnum.NEW_CAMPAGNE.name()))
            return Response.ok().setMessage("Campagne non démarrée.");
        Campagne campagne = optionalCampagne.get();
        campagne.setDateFin(LocalDate.now());
        campagne.setStatut(StatutCampagneEnum.ENDED_CAMPAGNE.name());
        Campagne campagne_ = campagneRepository.save(campagne);
        saveTraitement(campagne_, StatutCampagneEnum.ENDED_CAMPAGNE);
        return Response.ok().setMessage("Campagne clôturée avec succès.");
    }

    /**
     * get campagne by id
     * 
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<Object> getCampagne(long id) {
        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(id);
        if (optionalCampagne.isEmpty())
            return Response.ok().setMessage("Campagne inexistante");
        CampagneResponseDTO campagneResponseDTO = campagneMapper.mapToCampagneResponseDTO(optionalCampagne.get());

        List<String> profiles = new ArrayList<>();
        for (Profile role : iUtilisateur.getCurrentUser().getProfils()) {
            profiles.add(role.getCode());
        }

        UtilisateurResponseDTO utilisateurResponseDTO = new UtilisateurResponseDTO();
        utilisateurResponseDTO.setMatricule(iUtilisateur.getCurrentUser().getMatricule());
        utilisateurResponseDTO.setPrenom(iUtilisateur.getCurrentUser().getPrenom());
        utilisateurResponseDTO.setNom(iUtilisateur.getCurrentUser().getNom());
        if (containsOneRole(profiles,
                new ArrayList<>(Arrays.asList("Directeur-DRH", "Chef-division-dfc","Chef-division", "Chef-service")))) {
            utilisateurResponseDTO.setService(
                    centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).get().getService());
            utilisateurResponseDTO.setFonction(
                    centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).get().getFonction());
            utilisateurResponseDTO.setDirection(
                    centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).get().getDirection());
        }

        campagneResponseDTO.setUtilisateurResponseDTO(utilisateurResponseDTO);
        campagneResponseDTO.setStatut(StatutCampagneEnum.valueOf(traitementCampagneRepository
                .findTraitementCampagneByCampagne_IdAndActivatedTrue(optionalCampagne.get().getId()).get(0)
                .getStatutCampagne().getCode()));
        return Response.ok().setMessage("Recupèration Campagne").setPayload(campagneResponseDTO);
    }

    /**
     * recuperation expression de besoin d'une campagne
     * @param page
     * @param size
     * @param filter
     * @param statut
     * @param besoin
     * @param prenomDemandeur
     * @param nomDemandeur
     * @param date
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<Object> getExpressionDeBesoinByCampagne(int page, int size, String filter, String statut,
            String besoin, String prenomDemandeur, String nomDemandeur, String date, long id) {
        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(id);
        if (optionalCampagne.isEmpty())
            return Response.ok().setMessage("Campagne inexistante");
        Page<ExpressionDeBesoinResponseDTO> expressionDeBesoinResponseDTOS;
        BooleanBuilder builder = new BooleanBuilder();

        // verifier si l'utilisateur a le profile
        // - Chef-division-dfc ou Directeur-DRH
        if (!isProfilOk()){
            builder.and(
                    QExpressionDeBesoin.expressionDeBesoin.utilisateur.email.eq(iUtilisateur.getCurrentUser().getEmail())
            );
        }

        builder.and(
                QExpressionDeBesoin.expressionDeBesoin.deleted.isFalse());

        builder.and(
                QExpressionDeBesoin.expressionDeBesoin.campagne.eq(optionalCampagne.get()));
        if (StringUtils.isNotBlank(statut)) {
            builder.and(
                    QExpressionDeBesoin.expressionDeBesoin.statutExpression
                            .eq(StatutExpressionDeBesoinEnum.valueOf(statut)));
        }
        if (StringUtils.isNotBlank(prenomDemandeur)) {
            builder.and(
                    QExpressionDeBesoin.expressionDeBesoin.utilisateur.prenom.containsIgnoreCase(prenomDemandeur));
        }
        if (StringUtils.isNotBlank(nomDemandeur)) {
            builder.and(
                    QExpressionDeBesoin.expressionDeBesoin.utilisateur.nom.containsIgnoreCase(nomDemandeur));
        }
        if (StringUtils.isNotBlank(besoin)) {
            builder.and(
                    QExpressionDeBesoin.expressionDeBesoin.besoin.containsIgnoreCase(besoin));
        }
        if (StringUtils.isNotBlank(date)) {
            builder.and(
                    QExpressionDeBesoin.expressionDeBesoin.date.eq(stringToLocalDate(date, "yyyy-MM-dd")));
        }
        if (StringUtils.isNotBlank(filter)) {
            builder.andAnyOf(
                    QExpressionDeBesoin.expressionDeBesoin.reference.containsIgnoreCase(filter),
                    QExpressionDeBesoin.expressionDeBesoin.utilisateur.nom.containsIgnoreCase(filter),
                    QExpressionDeBesoin.expressionDeBesoin.utilisateur.prenom.containsIgnoreCase(filter));
        }

        expressionDeBesoinResponseDTOS = expressionDeBesoinRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(expressionDeBesoin -> {
                    ExpressionDeBesoinResponseDTO expressionDeBesoinResponseDTO = expressionDeBesoinMapper
                            .mapToExpressionDeBesoinResponseDTO(expressionDeBesoin);
                    expressionDeBesoinResponseDTO.setStatut(String.valueOf(expressionDeBesoin.getStatutExpression()));
                    List<String> roles = new ArrayList<>();
                    roles.add("Directeur-DRH");
                    roles.add("Chef-division-dfc");
                    roles.add("Chef-service");
                    List<String> profiles = new ArrayList<>();
                    for (Profile role : expressionDeBesoin.getUtilisateur().getProfils()) {
                        profiles.add(role.getCode());
                    }
                    UtilisateurResponseDTO utilisateurResponseDTO = new UtilisateurResponseDTO();
                    utilisateurResponseDTO.setProfils(expressionDeBesoin.getUtilisateur().getProfils());
                    utilisateurResponseDTO.setId(expressionDeBesoin.getUtilisateur().getId());
                    utilisateurResponseDTO.setMatricule(expressionDeBesoin.getUtilisateur().getMatricule());
                    utilisateurResponseDTO.setNom(expressionDeBesoin.getUtilisateur().getNom());
                    utilisateurResponseDTO.setPrenom(expressionDeBesoin.getUtilisateur().getPrenom());
                    utilisateurResponseDTO.setEmail(expressionDeBesoin.getUtilisateur().getEmail());
                    utilisateurResponseDTO.setTelephone(expressionDeBesoin.getUtilisateur().getTelephone());
                    utilisateurResponseDTO.setSexe(expressionDeBesoin.getUtilisateur().getSexe());
                    utilisateurResponseDTO.setAdresse(expressionDeBesoin.getUtilisateur().getAdresse());

                    if (containsOneRole(profiles, roles)) {
                        utilisateurResponseDTO.setService(centralLevelRepository
                                .findByEmail(expressionDeBesoin.getUtilisateur().getEmail()).get().getService());
                    }
                    expressionDeBesoinResponseDTO.setUtilisateurResponseDTO(utilisateurResponseDTO);
                    return expressionDeBesoinResponseDTO;
                });
        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(expressionDeBesoinResponseDTOS.getSize())
                .number(expressionDeBesoinResponseDTOS.getNumber())
                .totalElements(expressionDeBesoinResponseDTOS.getTotalElements())
                .totalPages(expressionDeBesoinResponseDTOS.getTotalPages())
                .build();
        return Response.ok().setPayload(expressionDeBesoinResponseDTOS.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des expressions de besoin de la campagne " + optionalCampagne.get().getNom());
    }

    private static boolean containsOneRole(List<String> mainList, List<String> elementsToCheck) {
        for (String element : elementsToCheck) {
            if (mainList.contains(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Response<Object> searchCampagne(String nom, LocalDate dateDebut, LocalDate dateFin) {
        return null;
    }

    @Override
    public Response<Object> getExpressionDeBesoinByCampagneForExport(long id) {
        System.out.println(expressionDeBesoinRepository.findByCampagne_IdAndDeletedFalse(id));
        return Response.ok().setPayload(expressionDeBesoinRepository.findByCampagne_IdAndDeletedFalse(id)).setMessage("Exportation Expression de besoin");
    }


    boolean isProfilInList(String profilCode){
        return iUtilisateur.getCurrentUser().getProfils().stream().filter(profile -> profile.getCode().equals(profilCode)).toList().size() == 1;
    }

    boolean isProfilOk(){
        return isProfilInList("Chef-division-dfc") || isProfilInList("Directeur-DRH");
    }

//    @Scheduled(cron = "0 0 0 * * *") // execution chaque jour
    public void executeTask() {
        List<Campagne> campagnes = campagneRepository.findCampagneByStatutAndDeletedFalse(StatutCampagneEnum.STARTED_CAMPAGNE.name());
        for (Campagne campagne : campagnes) {
            if (campagne.getDateFin().isEqual(LocalDate.now())) {
                campagne.setStatut(StatutCampagneEnum.ENDED_CAMPAGNE.name());
                saveTraitement(campagne, StatutCampagneEnum.ENDED_CAMPAGNE);
                // mettre les statut des campagne a false
                List<TraitementCampagne> traitementCampagnes = traitementCampagneRepository
                        .findTraitementCampagneByCampagne_Id(campagne.getId());
                for (TraitementCampagne traitementCampagne : traitementCampagnes) {
                    traitementCampagne.setActivated(false);
                    traitementCampagneRepository.save(traitementCampagne);
                }
                // creer un nouveau traitement de campagne
                TraitementCampagne traitementCampagne = new TraitementCampagne();
                traitementCampagne.setCampagne(campagne);
                traitementCampagne.setUtilisateur(null);
                traitementCampagne.setActivated(true);
                traitementCampagne.setStatutCampagne(statutCampagneRepository.findStatutCampagneByCode(StatutCampagneEnum.ENDED_CAMPAGNE.name()).get());
                traitementCampagneRepository.save(traitementCampagne);
                campagneRepository.save(campagne);
            }
        }
    }
}
