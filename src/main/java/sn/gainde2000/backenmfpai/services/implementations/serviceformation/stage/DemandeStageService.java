package sn.gainde2000.backenmfpai.services.implementations.serviceformation.stage;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.mappers.serviceformation.stage.DemandeStageMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IDemandeStage;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IDisciplineStage;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AuthorizedDemandeStageDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DemandeStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.ImputationRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.stage.AttestationStageResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.stage.DemandeStageResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.stage.RapportStageResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandeStageService implements IDemandeStage {
    private final DemandeStageMapper demandeStageMapper;
    private final DemandeStageRepository demandeStageRepository;
    private final DirectionRepository directionRepository;
    private final DivisionRepository divisionRepository;
    private final BureauRepository bureauRepository;
    private final ServiceRepository serviceRepository;
    private final IUtilisateur iUtilisateur;
    private final DisciplineRepository disciplineRepository;
    private final NiveauScolaireRepository niveauScolaireRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final StatutDemandeRepository statutDemandeRepository;
    private final TraitementDemandeStageRepository traitementDemandeStageRepository;
    private final IDisciplineStage iDisciplineStage;
    private final AvisDemandeRepository avisDemandeRepository;
    private final RapportStageRepository rapportStageRepository;
    private final AttestationStageRepository attestationStageRepository;
    private final MailService mailService;


    /**
     * enregistrement demande de stage
     * @param demandeStageRequest
     * @return
     */
    @Override
    @Transactional
    public Response<Object> saveDemandeStage(DemandeStageRequest demandeStageRequest) {
        if (demandeStageRequest.getDateDebut() != null && demandeStageRequest.getDateFin() != null){
            if (demandeStageRequest.getDateDebut().isAfter(demandeStageRequest.getDateFin()))
                return Response.exception().setMessage("Date de début doit être antérieure à la date de fin.");
        }
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());
        if (optionalCentralLevel.isEmpty()){
            return Response.exception().setMessage("Vous n'avez pas le privilège d'enregistrer une demande de stage");
        }
        CentralLevel centralLevel = optionalCentralLevel.get();
        // la division métier (DFC) crée les demandes de stage ;
        // l'ADMIN-DRH et le Directeur-DRH peuvent créer en secours lorsque cette division n'est pas disponible
        boolean isDfc = centralLevel.getDivision() != null && "DFC".equals(centralLevel.getDivision().getCode());
        boolean isAdminOrDirecteur = centralLevel.getProfils().stream()
                .anyMatch(profile -> "ADMIN-DRH".equals(profile.getCode()) || "Directeur-DRH".equals(profile.getCode()));
        if (!isDfc && !isAdminOrDirecteur){
            return Response.exception().setMessage("Vous n'avez pas le privilège d'enregistrer une demande de stage");
        }
        Optional<Direction> optionalDirection = directionRepository.findByCode(demandeStageRequest.getDirectionCode());
        Optional<Division> optionalDivision = divisionRepository.findByCode(demandeStageRequest.getDivisionCode());
        Optional<Bureau> optionalBureau = bureauRepository.findByCode(demandeStageRequest.getBureauCode());
        Optional<Services> optionalService = serviceRepository.findByCode(demandeStageRequest.getServiceCode());
        Optional<NiveauScolaire> optionalNiveauScolaire = niveauScolaireRepository.findByCode(demandeStageRequest.getCodeNiveauScolaire());
        DisciplineStage disciplineStage = new DisciplineStage();
        if (optionalNiveauScolaire.isEmpty()){
            return Response.exception().setMessage("Niveau Scolaire inéxistant");
        }
        DemandeStage demandeStage = new DemandeStage();

        if (demandeStageRequest.getDirectionCode() != null ){
            if (optionalDirection.isEmpty() && !demandeStageRequest.getDirectionCode().isEmpty())
                return Response.exception().setMessage("Direction inéxistante");
        }

        if (demandeStageRequest.getDivisionCode() != null){
            if (optionalDivision.isEmpty() && !demandeStageRequest.getDivisionCode().isEmpty())
                return Response.exception().setMessage("Division inéxistante");
        }

        if (demandeStageRequest.getBureauCode() != null){
            if (optionalBureau.isEmpty() && !demandeStageRequest.getBureauCode().isEmpty())
                return Response.exception().setMessage("Bureau inéxistant");
        }

        if (demandeStageRequest.getServiceCode() != null){
            if (optionalService.isEmpty() && !demandeStageRequest.getServiceCode().isEmpty())
                return Response.exception().setMessage("Service inéxistant");
        }


        demandeStage.setPrenomDemandeur(demandeStageRequest.getPrenomDemandeur());
        demandeStage.setNomDemandeur(demandeStageRequest.getNomDemandeur().toLowerCase());
        demandeStage.setAdresse(demandeStageRequest.getAdresse());

        if (demandeStageRequest.getDirectionCode() != null && !demandeStageRequest.getDirectionCode().isEmpty())
            demandeStage.setDirection(optionalDirection.get());

        if (demandeStageRequest.getDivisionCode() != null && !demandeStageRequest.getDivisionCode().isEmpty())
            demandeStage.setDivision(optionalDivision.get());

        if (demandeStageRequest.getBureauCode() != null &&  !demandeStageRequest.getBureauCode().isEmpty())
            demandeStage.setBureau(optionalBureau.get());

        if (demandeStageRequest.getServiceCode() != null && !demandeStageRequest.getServiceCode().isEmpty())
            demandeStage.setService(optionalService.get());

        demandeStage.setCommentaire(demandeStageRequest.getCommentaire());
        demandeStage.setMail(demandeStageRequest.getMail());
        demandeStage.setDateDebut(demandeStageRequest.getDateDebut());
        demandeStage.setDateNaissance(demandeStageRequest.getDateNaissance());
        demandeStage.setDateFin(demandeStageRequest.getDateFin());
        demandeStage.setTel("+221"+demandeStageRequest.getTel());
        demandeStage.setLieuDeNaissance(demandeStageRequest.getLieuDeNaissance());
        demandeStage.setObjet(demandeStageRequest.getObjet());
        demandeStage.setNiveauScolaire(optionalNiveauScolaire.get());
        demandeStage.setDisciplineStage(demandeStageRequest.getDisciplineStage());
        demandeStage.setStatutDemandeStage(statutDemandeRepository.findByCode("ENREGISTRER").get());

        demandeStage.setCentralLevel(centralLevel);
        DemandeStage saveDemandeStage =  demandeStageRepository.save(demandeStage);
        saveDemandeStage.setNumero(formatNumber(saveDemandeStage.getId()));
        saveDemandeStage =  demandeStageRepository.save(demandeStage);
        // traitement
        TraitementDemandeStage traitementDemandeStage = new TraitementDemandeStage();
        traitementDemandeStage.setDemandeStage(saveDemandeStage);
        traitementDemandeStage.setCentralLevel(centralLevel);
        traitementDemandeStage.setActivated(true);
        traitementDemandeStage.setStatutDemandeStage(statutDemandeRepository.findByCode("ENREGISTRER").get());
        traitementDemandeStageRepository.save(traitementDemandeStage);

        DemandeStageResponse demandeStageResponse = demandeStageMapper.mapToDemandeStageResponse(saveDemandeStage);

        return Response.ok().setPayload(demandeStageResponse).setMessage("Demande de stage enregistrée avec succès.");
    }


    /**
     * autoriser demande de stage
     * @param authorizedDemandeStageDTO
     * @retur
     */
    @Override
    @Transactional
    public Response<Object> autoriserDemande(long id) {
        Optional<DemandeStage> optionalDemandeStage = demandeStageRepository.findById(id);
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande stage introuvable.");
        optionalDemandeStage.get().setStatutDemandeStage(statutDemandeRepository.findByCode("AUTORISER").get());
        // traitement
        TraitementDemandeStage traitementDemandeStage = new TraitementDemandeStage();
        traitementDemandeStage.setDemandeStage(optionalDemandeStage.get());
        TraitementDemandeStage traitementDemandeStageThatIsTrue = traitementDemandeStageRepository.findByDemandeStage_IdAndActivatedTrue(id).get(0);
        traitementDemandeStageThatIsTrue.setActivated(false);
        traitementDemandeStageRepository.save(traitementDemandeStageThatIsTrue);
        CentralLevel centralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).orElse(null);
        traitementDemandeStage.setCentralLevel(centralLevel);
        traitementDemandeStage.setActivated(true);
        traitementDemandeStage.setStatutDemandeStage(statutDemandeRepository.findByCode("AUTORISER").get());
        traitementDemandeStageRepository.save(traitementDemandeStage);
        demandeStageRepository.save(optionalDemandeStage.get());
        return Response.ok().setMessage("Demande de stage autorisée.");
    }

    /**
     * formater numero
     * @param number
     * @return
     */
    public String formatNumber(long number) {
        return String.format("%0" + 5 + "d", number);
    }

    /**
     * de pas autoriser demande de stage
     * @param id
     * @param avis
     * @return
     */
    @Override
    @Transactional
    public Response<Object> nePasAutoriserDemande(long id, String avis) {


        Optional<DemandeStage> optionalDemandeStage = demandeStageRepository.findById(id);
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande stage introuvable.");
        optionalDemandeStage.get().setStatutDemandeStage(statutDemandeRepository.findByCode("NONAUTORISER").get());
        CentralLevel centralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).orElse(null);
        AvisDemandeStage avisDemaneStage = new AvisDemandeStage();
        avisDemaneStage.setCentralLevel(centralLevel);
        avisDemaneStage.setDemandeStage(optionalDemandeStage.get());
        avisDemaneStage.setAvis(avis);
        avisDemandeRepository.save(avisDemaneStage);
        // traitement
        TraitementDemandeStage traitementDemandeStage = new TraitementDemandeStage();
        traitementDemandeStage.setDemandeStage(optionalDemandeStage.get());

        TraitementDemandeStage traitementDemandeStageThatIsTrue = traitementDemandeStageRepository.findByDemandeStage_IdAndActivatedTrue(id).get(0);
        traitementDemandeStageThatIsTrue.setActivated(false);
        traitementDemandeStageRepository.save(traitementDemandeStageThatIsTrue);


        traitementDemandeStage.setCentralLevel(centralLevel);
        traitementDemandeStage.setActivated(true);
        traitementDemandeStage.setStatutDemandeStage(statutDemandeRepository.findByCode("NONAUTORISER").get());
        traitementDemandeStageRepository.save(traitementDemandeStage);
        demandeStageRepository.save(optionalDemandeStage.get());
        return Response.ok().setMessage("Demande de stage non autorisée.");
    }

    /**
     * recuperation des demandes de stage
     * @param page
     * @param size
     * @param filter
     * @param numero
     * @param prenomDemandeur
     * @param nomDemandeur
     * @param discipline
     * @param dateDebut
     * @param dateFin
     * @param statut
     * @return
     */

    @Override
    @Transactional
    public Response<Object> getAllDemandeStage(int page, int size, String filter, String numero, String prenomDemandeur, String nomDemandeur, String discipline, String dateDebut, String dateFin, String statut) {

        Page<DemandeStageResponse> demandeStageResponses;
        BooleanBuilder builder = new BooleanBuilder();
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());

        builder.and(
                QDemandeStage.demandeStage.deleted.isFalse()
        );

        // si l'utilisateur connecte n'est pas de la division DFC
        // on affiche seulement la liste des demande de stage qui sont dans sa propre division
        // un utilisateur sans CentralLevel (ex: ADMIN-DRH) voit l'ensemble des demandes, comme la division DFC

        if(optionalCentralLevel.isPresent() && Objects.nonNull(optionalCentralLevel.get().getDivision())) {
           if (!optionalCentralLevel.get().getDivision().getCode().equals("DFC"))
               builder.and(
                       QDemandeStage.demandeStage.division.code.eq(optionalCentralLevel.get().getDivision().getCode())
               );
       }


        if(StringUtils.isNotBlank(filter)){
            builder.andAnyOf(
                    QDemandeStage.demandeStage.numero.likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.prenomDemandeur.likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.nomDemandeur.likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.mail.likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.disciplineStage.likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.dateDebut.stringValue().likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.dateFin.stringValue().likeIgnoreCase("%" + filter + "%"),
                    QDemandeStage.demandeStage.statutDemandeStage.code.likeIgnoreCase("%" + filter + "%")

            );
        }

        if(StringUtils.isNotBlank(numero)){
            builder.and(
                    QDemandeStage.demandeStage.numero.likeIgnoreCase("%" + numero + "%")
            );
        }
        if(StringUtils.isNotBlank(prenomDemandeur)){
            builder.and(
                    QDemandeStage.demandeStage.prenomDemandeur.likeIgnoreCase("%" + prenomDemandeur + "%")
            );
        }
        if(StringUtils.isNotBlank(nomDemandeur)){
            builder.and(
                    QDemandeStage.demandeStage.nomDemandeur.likeIgnoreCase("%" + nomDemandeur + "%")
            );
        }
        if(StringUtils.isNotBlank(discipline)){
            builder.and(
                    QDemandeStage.demandeStage.disciplineStage.eq(discipline )
            );
        }
        if(StringUtils.isNotBlank(dateDebut)){
            builder.and(
                    QDemandeStage.demandeStage.dateDebut.stringValue().likeIgnoreCase("%" + dateDebut + "%")
            );
        }

        if(StringUtils.isNotBlank(dateFin)){
            builder.and(
                    QDemandeStage.demandeStage.dateFin.stringValue().likeIgnoreCase("%" + dateFin + "%")
            );
        }

        if(StringUtils.isNotBlank(statut)){
            builder.and(
                    QDemandeStage.demandeStage.statutDemandeStage.code.eq(statut)
            );
        }

        demandeStageResponses =  Objects.nonNull(builder.getValue()) ?
                demandeStageRepository.findAll(builder.getValue(), PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "id"))).map(demandeStage -> {
                    DemandeStageResponse demandeStageResponse = demandeStageMapper.mapToDemandeStageResponse(demandeStage);
                    if (demandeStageResponse.isHaveRapport()){
                        RapportStage rapportStage = rapportStageRepository.findByDemandeStage_Id(demandeStageResponse.getId()).get();
                        RapportStageResponse rapportStageResponse = new RapportStageResponse();
                        rapportStageResponse.setId(rapportStage.getId());
                        rapportStageResponse.setPiecesJoint(rapportStage.getPiecesJoint());
                        demandeStageResponse.setRapportStageResponse(rapportStageResponse);
                    }
                    if (demandeStageResponse.isHaveAttestation()){
                        AttestationStage attestationStage  = attestationStageRepository.findAttestationStageByDemandeStage_Id(demandeStageResponse.getId()).get();
                        AttestationStageResponse attestationStageResponse = new AttestationStageResponse();
                        attestationStageResponse.setPiecesJoint(attestationStage.getPiecesJoint());
                        attestationStageResponse.setId(attestationStage.getId());
                        demandeStageResponse.setAttestationStageResponse(attestationStageResponse);
                    }
                    return demandeStageResponse;
                })
                :
                demandeStageRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "id"))).map(demandeStage -> {
                    DemandeStageResponse demandeStageResponse = demandeStageMapper.mapToDemandeStageResponse(demandeStage);
                    if (demandeStageResponse.isHaveRapport()){
                        RapportStageResponse rapportStageResponse = new RapportStageResponse();
                        rapportStageResponse.setId(rapportStageRepository.findByDemandeStage_Id(demandeStageResponse.getId()).get().getId());
                        rapportStageResponse.setPiecesJoint(rapportStageRepository.findByDemandeStage_Id(demandeStageResponse.getId()).get().getPiecesJoint());
                        demandeStageResponse.setRapportStageResponse(rapportStageResponse);
                    }
                    if (demandeStageResponse.isHaveAttestation()){
                        AttestationStageResponse attestationStageResponse = new AttestationStageResponse();
                        attestationStageResponse.setId(attestationStageRepository.findAttestationStageByDemandeStage_Id(demandeStageResponse.getId()).get().getId());
                        attestationStageResponse.setPiecesJoint(attestationStageRepository.findAttestationStageByDemandeStage_Id(demandeStageResponse.getId()).get().getPiecesJoint());
                        demandeStageResponse.setAttestationStageResponse(attestationStageResponse);
                    }
                    return demandeStageResponse;
                });


        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(demandeStageResponses.getSize())
                .number(demandeStageResponses.getNumber())
                .totalElements(demandeStageResponses.getTotalElements())
                .totalPages(demandeStageResponses.getTotalPages())
                .build();
        return Response.ok().setPayload(demandeStageResponses.getContent()).setMetadata(pageMetadata).setMessage("Liste des Demandes de stage");
    }


    /**
     * modification demande de stage
     * @param id
     * @param demandeStageRequest
     * @return
     */
    @Override
    @Transactional
    public Response<Object> editDemandeStage(long id, DemandeStageRequest demandeStageRequest) {
        if (demandeStageRequest.getDateDebut() != null && demandeStageRequest.getDateFin() != null){
            if (demandeStageRequest.getDateDebut().isAfter(demandeStageRequest.getDateFin()))
                return Response.exception().setMessage("Date de début doit être antérieure à la date de fin.");
        }

        Optional<DemandeStage>  optionalDemandeStage = demandeStageRepository.findById(id);
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande stage inéxistante");
        Optional<Direction> optionalDirection = directionRepository.findByCode(demandeStageRequest.getDirectionCode());
        Optional<Division> optionalDivision = divisionRepository.findByCode(demandeStageRequest.getDivisionCode());
        Optional<Bureau> optionalBureau = bureauRepository.findByCode(demandeStageRequest.getBureauCode());
        Optional<Services> optionalService = serviceRepository.findByCode(demandeStageRequest.getServiceCode());
        Optional<NiveauScolaire> optionalNiveauScolaire = niveauScolaireRepository.findByCode(demandeStageRequest.getCodeNiveauScolaire());
        DisciplineStage disciplineStage = new DisciplineStage();

        if (optionalNiveauScolaire.isEmpty()){
            return Response.exception().setMessage("Niveau Scolaire inéxistant");
        }
        DemandeStage demandeStage = optionalDemandeStage.get();
        if (demandeStageRequest.getDirectionCode().equals(""))
            demandeStage.setDirection(null);
        if (demandeStageRequest.getDivisionCode().equals(""))
            demandeStage.setDivision(null);
        if (demandeStageRequest.getBureauCode().equals(""))
            demandeStage.setBureau(null);

        if (demandeStageRequest.getDirectionCode() != null ){
            if (optionalDirection.isEmpty()  && !demandeStageRequest.getDirectionCode().isEmpty())
                return Response.exception().setMessage("Direction inéxistante");
        }

        if (demandeStageRequest.getDivisionCode() != null){
            if (optionalDivision.isEmpty() && !demandeStageRequest.getDivisionCode().isEmpty())
                return Response.exception().setMessage("Division inéxistante");
        }

        if (demandeStageRequest.getBureauCode() != null){
            if (optionalBureau.isEmpty() && !demandeStageRequest.getBureauCode().isEmpty())
                return Response.exception().setMessage("Bureau inéxistant");
        }

        if (demandeStageRequest.getServiceCode() != null){
            if (optionalService.isEmpty() && !demandeStageRequest.getServiceCode().isEmpty())
                return Response.exception().setMessage("Service inéxistant");
        }

        if (demandeStageRequest.getDirectionCode() != null && !demandeStageRequest.getDirectionCode().isEmpty()){
            demandeStage.setDirection(optionalDirection.get());

        }

        if (demandeStageRequest.getDivisionCode() != null && !demandeStageRequest.getDivisionCode().isEmpty())
            demandeStage.setDivision(optionalDivision.get());

        if (demandeStageRequest.getBureauCode() != null &&  !demandeStageRequest.getBureauCode().isEmpty())
            demandeStage.setBureau(optionalBureau.get());

        if (demandeStageRequest.getServiceCode() != null && !demandeStageRequest.getServiceCode().isEmpty())
            demandeStage.setService(optionalService.get());

        demandeStage.setPrenomDemandeur(demandeStageRequest.getPrenomDemandeur());
        demandeStage.setNomDemandeur(demandeStageRequest.getNomDemandeur());
        demandeStage.setAdresse(demandeStageRequest.getAdresse());
        demandeStage.setCommentaire(demandeStageRequest.getCommentaire());
        demandeStage.setMail(demandeStageRequest.getMail());
        demandeStage.setDateDebut(demandeStageRequest.getDateDebut());
        demandeStage.setDateNaissance(demandeStageRequest.getDateNaissance());
        demandeStage.setDateFin(demandeStageRequest.getDateFin());
        demandeStage.setTel(demandeStageRequest.getTel());
        demandeStage.setLieuDeNaissance(demandeStageRequest.getLieuDeNaissance());
        demandeStage.setObjet(demandeStageRequest.getObjet());
        demandeStage.setNiveauScolaire(optionalNiveauScolaire.get());
        demandeStage.setDisciplineStage(demandeStageRequest.getDisciplineStage());

        demandeStageRepository.save(demandeStage);
        return Response.ok().setMessage("Demande modifiée avec succès.");
    }

    /**
     * detail d'une demande de stage
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<Object> detailDemandeStage(long id) {
        Optional<DemandeStage> optionalDemandeStage = demandeStageRepository.findById(id);
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande stage introuvable.");


        DemandeStageResponse demandeStageResponse = demandeStageMapper.mapToDemandeStageResponse(optionalDemandeStage.get());
        if (demandeStageResponse.isHaveRapport()){
            RapportStageResponse rapportStageResponse = new RapportStageResponse();
            rapportStageResponse.setId(rapportStageRepository.findByDemandeStage_Id(demandeStageResponse.getId()).get().getId());
            rapportStageResponse.setPiecesJoint(rapportStageRepository.findByDemandeStage_Id(demandeStageResponse.getId()).get().getPiecesJoint());
            demandeStageResponse.setRapportStageResponse(rapportStageResponse);
        }
        if (demandeStageResponse.isHaveAttestation()){
            AttestationStageResponse attestationStageResponse = new AttestationStageResponse();
            attestationStageResponse.setId(attestationStageRepository.findAttestationStageByDemandeStage_Id(demandeStageResponse.getId()).get().getId());
            attestationStageResponse.setPiecesJoint(attestationStageRepository.findAttestationStageByDemandeStage_Id(demandeStageResponse.getId()).get().getPiecesJoint());
            demandeStageResponse.setAttestationStageResponse(attestationStageResponse);
        }

        return Response.ok().setPayload(demandeStageResponse).setMessage("Recupèration demande stage");
    }

    /**
     * imputer une demande de stage
     * @param id
     * @param imputationRequest
     * @return
     */
    @Override
    @Transactional
    public Response<Object> imputationDemandeStage(long id, ImputationRequest imputationRequest) {
        Optional<DemandeStage> optionalDemandeStage = demandeStageRepository.findById(id);
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande stage introuvable.");

        DemandeStage demandeStage = optionalDemandeStage.get();

        Optional<Direction> optionalDirection = directionRepository.findByCode(imputationRequest.getDirectionCode());
        Optional<Division> optionalDivision = divisionRepository.findByCode(imputationRequest.getDivisionCode());
        Optional<Bureau> optionalBureau = bureauRepository.findByCode(imputationRequest.getBureauCode());
        Optional<Services> optionalService = serviceRepository.findByCode(imputationRequest.getServiceCode());


        if (imputationRequest.getDirectionCode() != null){
            if (optionalDirection.isEmpty())
                return Response.exception().setMessage("Direction inéxistante");
        }

        if (imputationRequest.getDivisionCode() != null){
            if (optionalDivision.isEmpty() && !imputationRequest.getDivisionCode().isEmpty())
                return Response.exception().setMessage("Division inéxistante");
        }

        if (imputationRequest.getBureauCode() != null){
            if (optionalBureau.isEmpty() && !imputationRequest.getBureauCode().isEmpty())
                return Response.exception().setMessage("Bureau inéxistant");
        }

        if (imputationRequest.getServiceCode() != null){
            if (optionalService.isEmpty() && !imputationRequest.getServiceCode().isEmpty())
                return Response.exception().setMessage("Service inéxistant");
        }

        if (imputationRequest.getDirectionCode() != null && !imputationRequest.getDirectionCode().isEmpty())
            demandeStage.setDirection(optionalDirection.get());

        if (imputationRequest.getDivisionCode() != null && !imputationRequest.getDivisionCode().isEmpty())
            demandeStage.setDivision(optionalDivision.get());

        if (imputationRequest.getBureauCode() != null &&  !imputationRequest.getBureauCode().isEmpty())
            demandeStage.setBureau(optionalBureau.get());

        if (imputationRequest.getServiceCode() != null && !imputationRequest.getServiceCode().isEmpty())
            demandeStage.setService(optionalService.get());
        demandeStageRepository.save(demandeStage);

        // envoie mails a tous les chef de division
        List<CentralLevel> centralLevels  = centralLevelRepository.findByDirection_Code(optionalDirection.get().getCode());
        for (CentralLevel centralLevel: centralLevels
             ) {
            // envoie au chef de division de la direction un mail
            if (centralLevel.getProfils().contains("Chef-division"))
                mailService.sendMail(new MailInfosDTO(null, "Bonjour "+centralLevel.getPrenom()+" "+centralLevel.getNom()+"\nune demande de stage (N°"+demandeStage.getNumero()+") vous est envoyée pour traitement.", "Imputation demande de stage", null, centralLevel.getEmail()));

        }
        return Response.ok().setMessage("Demande de stage imputer");
    }

    /**
     * verifier si l'utilisateur connecter a un bureau de la DFC
     * @return
     */
    @Override
    @Transactional
    public Response<Object> isCurrentUserInDFRBureau() {
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());
        if (optionalCentralLevel.isEmpty())
            return Response.ok().setPayload(false);
        CentralLevel centralLevel = optionalCentralLevel.get();
        // ['Chef-bureau-dfc', 'Chef-division-dfc','Agent-bureau-dfc']
        if (!centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-bureau-dfc")).toList().isEmpty() || !centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-division-dfc")).toList().isEmpty() || !centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Agent-bureau-dfc")).toList().isEmpty()){
            return Response.ok().setPayload(true);
        }
        return Response.ok().setPayload(false);
    }


    /**
     * autoriser demande de stage
     * @param authorizedDemandeStageDTO
     * @return
     */
    @Override
    public Response<Object> authorisationStage(AuthorizedDemandeStageDTO authorizedDemandeStageDTO) {
        Optional<DemandeStage> optionalDemandeStage = demandeStageRepository.findById(authorizedDemandeStageDTO.getId());
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande stage introuvable.");
        if (authorizedDemandeStageDTO.getDateDebut().isAfter(authorizedDemandeStageDTO.getDateFin()))
            return Response.exception().setMessage("Date invalide !");
        DemandeStage demandeStage = optionalDemandeStage.get();
        demandeStage.setDateFin(authorizedDemandeStageDTO.getDateFin());
        demandeStage.setDateDebut(authorizedDemandeStageDTO.getDateDebut());
        demandeStageRepository.save(demandeStage);
        return Response.ok().setMessage("Autorisation de stage enregistée.");
    }

    @Override
    public Response<Object> nombreDemandeStageAutoriserNonAutoriserEnregistrer() {
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());

        int demandeStageEnregistrer = 0;
        int demandeStageAutoriser = 0;
        int demandeStageNonAutoriser = 0;

        // un utilisateur sans division rattachée (ex: ADMIN-DRH, dont le CentralLevel a division_id NULL)
        // voit les totaux globaux, au même titre que la division DFC
        String divisionCode = optionalCentralLevel
                .map(CentralLevel::getDivision)
                .map(Division::getCode)
                .orElse(null);

        if (divisionCode == null || divisionCode.equals("DFC")){

                 demandeStageEnregistrer = demandeStageRepository.findDemandeStageByStatutDemandeStage(statutDemandeRepository.findByCode("ENREGISTRER").get()).size();
                 demandeStageAutoriser = demandeStageRepository.findDemandeStageByStatutDemandeStage(statutDemandeRepository.findByCode("AUTORISER").get()).size();
                 demandeStageNonAutoriser = demandeStageRepository.findDemandeStageByStatutDemandeStage(statutDemandeRepository.findByCode("NONAUTORISER").get()).size();

        }else{

             demandeStageEnregistrer = demandeStageRepository.findDemandeStageByStatutDemandeStageAndDivision_Code(statutDemandeRepository.findByCode("ENREGISTRER").get(), divisionCode).size();
             demandeStageAutoriser = demandeStageRepository.findDemandeStageByStatutDemandeStageAndDivision_Code(statutDemandeRepository.findByCode("AUTORISER").get(), divisionCode).size();
             demandeStageNonAutoriser = demandeStageRepository.findDemandeStageByStatutDemandeStageAndDivision_Code(statutDemandeRepository.findByCode("NONAUTORISER").get(), divisionCode).size();

        }

        NombreDemandeStageAutoriserNonAutoriserEnregistrer nombreDemandeStageAutoriserNonAutoriserEnregistrer = new NombreDemandeStageAutoriserNonAutoriserEnregistrer();;
        nombreDemandeStageAutoriserNonAutoriserEnregistrer.setNombreDemandeStageEnregistrer(demandeStageEnregistrer);
        nombreDemandeStageAutoriserNonAutoriserEnregistrer.setNombreDemandeStageAutoriser(demandeStageAutoriser);
        nombreDemandeStageAutoriserNonAutoriserEnregistrer.setNombreDemandeStageNonAutoriser(demandeStageNonAutoriser);
        return Response.ok().setPayload(nombreDemandeStageAutoriserNonAutoriserEnregistrer).setMessage("nombre de demande stage selon le statut");
    }
}
