package sn.gainde2000.backenmfpai.services.implementations.serviceformation.courrier;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.mappers.serviceformation.courrier.CourrierMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.courrier.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.courrier.ICourrier;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.courrier.CourrierRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.courrier.CourrierResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourrierService implements ICourrier {
    private final CourrierRepository courrierRepository;
    private final DirectionRepository directionRepository;
    private final DivisionRepository divisionRepository;
    private final BureauRepository bureauRepository;
    private final ServiceRepository serviceRepository;
    private final TypeDemandeCourrierRepository typeDemandeCourrierRepository;
    private final IUtilisateur iUtilisateur;
    private final CentralLevelRepository centralLevelRepository;
    private final CourrierMapper courrierMapper;
    private final MailService mailService;
    private final StatutCourrierRepository statutCourrierRepository;
    private final TraitementCourrierRepository traitementCourrierRepository;

    /**
     * enregistrer courrier
     * @param courrierRequest
     * @return
     */
    @Override
    public Response<Object> saveCourrier(CourrierRequest courrierRequest) {

        System.out.println("kkkkk");
        System.out.println(courrierRequest.toString());
        // recuperation divison, service, direction, bureau via code
        Optional<Direction> optionalDirection = directionRepository.findByCode(courrierRequest.getDirectionCode());
        Optional<Division> optionalDivision = divisionRepository.findByCode(courrierRequest.getDivisionCode());
        Optional<TypeDemandeCourrier> optionalTypeDemandeCourrier = null;
        if (!courrierRequest.getCodeDemandeCourrier().equals("other")) {
            optionalTypeDemandeCourrier = typeDemandeCourrierRepository.findByCode(courrierRequest.getCodeDemandeCourrier());
        }
        Optional<Courrier> optionalCourrier = courrierRepository.findCourrierByReference(courrierRequest.getReference());

        // on verifie si la reference existe deja
        if (optionalCourrier.isPresent())
            return Response.ok().setMessage("Référence existe déjà.");

        // on verifie si le code de la direction existe
        if (optionalDirection.isEmpty())
            return Response.ok().setMessage("Direction inéxistante");
        if (courrierRequest.getDirectionCode() != null) {
            if (optionalDirection.isEmpty())
                return Response.exception().setMessage("Direction inéxistante");
        }

        // on verifie si la division est differente de null
        if (courrierRequest.getDivisionCode() != null) {
            if (optionalDivision.isEmpty() && !courrierRequest.getDivisionCode().isEmpty())
                return Response.exception().setMessage("Division inéxistante");
        }


        // initialisation courrier
        Courrier courrier = new Courrier();

        courrier.setDirection(optionalDirection.get());
        courrier.setReference(courrierRequest.getReference());
        if (!courrierRequest.getCodeDemandeCourrier().equals("other")) {
            courrier.setTypeDemande(optionalTypeDemandeCourrier.get());

        }else{
            courrier.setNomTypeCourrier(courrierRequest.getTypeCourrier());
            courrier.setNomTypeCourrier(courrierRequest.getTypeCourrier());
        }

        courrier.setStatut("NONTRAITER");
        courrier.setTypeCourrier(courrierRequest.getTypeCourrier());
        if (courrierRequest.getCodeDemandeCourrier().equals("other")) {
            courrier.setOtherField(courrierRequest.getOtherField());
        }


        // onverifie si la division, bureau, service ont ete choisi
        if (courrierRequest.getDivisionCode() != null && !courrierRequest.getDivisionCode().isEmpty())
            // si oui on l'affection a courrier
            courrier.setDivision(optionalDivision.get());


        courrier.setCentralLevel(centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).orElse(null));
        TraitementCourrier traitementCourrier = new TraitementCourrier();

        Courrier savedCourrier = courrierRepository.save(courrier);
        traitementCourrier.setCourrier(savedCourrier);
        traitementCourrier.setStatutCourrier(statutCourrierRepository.findByCode("NONTRAITER").get());
        traitementCourrier.setActivated(true);
        traitementCourrier.setUtilisateur(iUtilisateur.getCurrentUser());
        traitementCourrierRepository.save(traitementCourrier);
        List<CentralLevel> centralLevels = centralLevelRepository.findByDivision_Code("DFC");
        if (courrierRequest.getCodeDemandeCourrier().equals("STAGE")) {
            for (CentralLevel centralLevel : centralLevels
            ) {
                if (centralLevel.getProfils().contains("Chef-division-dfc") || centralLevel.getProfils().contains("Chef-bureau-dfc") || centralLevel.getProfils().contains("Agent-bureau-dfc"))
                    mailService.sendMail(new MailInfosDTO(null, "Bonjour " + centralLevel.getPrenom() + " " + centralLevel.getNom() + "\n,Le courier " + courrierRequest.getReference() + " vous a été imputé", "Nouveau Courrier", null, centralLevel.getEmail()));

            }
        }
        return Response.ok().setMessage("Courrier Enregistrer avec succès.");
    }

    /**
     * liste des courriers
     * @param page
     * @param size
     * @param filter
     * @param reference
     * @param typeDemande
     * @param direction
     * @param division
     * @param typeCourrier
     * @return
     */
    @Override
    public Response<Object> getAllCourrier(int page, int size, String filter,String reference, String typeDemande, String direction, String division, String typeCourrier, String statut) {


        Page<CourrierResponse> courrierResponses;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(
                QCourrier.courrier.deleted.isFalse()
        );
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());

        // un utilisateur sans CentralLevel (ex: ADMIN-DRH) voit les courriers de toutes les divisions
        if(optionalCentralLevel.isPresent() && Objects.nonNull(optionalCentralLevel.get().getDivision())){
            builder.and(
                    QCourrier.courrier.division.code.eq(optionalCentralLevel.get().getDivision().getCode())
            );
        }



        if(StringUtils.isNotBlank(filter)){
            builder.andAnyOf(
                    QCourrier.courrier.reference.likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.typeDemande.code.likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.direction.code.likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.division.code.likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.direction.code.likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.typeDemande.libelle.likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.typeCourrier.stringValue().likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.createdAt.stringValue().likeIgnoreCase("%" + filter + "%"),
                    QCourrier.courrier.statut.likeIgnoreCase("%" + filter + "%")
            );
        }
        if(StringUtils.isNotBlank(reference)){
            builder.and(
                    QCourrier.courrier.reference.likeIgnoreCase("%" + reference + "%")
            );
        }

        if(StringUtils.isNotBlank(statut)){
            builder.and(
                    QCourrier.courrier.statut.eq(statut)
            );
        }

        if(StringUtils.isNotBlank(typeDemande)){
            builder.and(
                    QCourrier.courrier.typeDemande.code.likeIgnoreCase("%" + typeDemande + "%")
            );
        }
        if(StringUtils.isNotBlank(typeCourrier)){
            builder.and(
                    QCourrier.courrier.typeCourrier.stringValue().likeIgnoreCase("%" + typeCourrier + "%")
            );
        }
        if(StringUtils.isNotBlank(direction)){
            builder.and(
                    QCourrier.courrier.direction.code.likeIgnoreCase("%" + direction + "%")
            );
        }
        if(StringUtils.isNotBlank(division)){
            builder.and(
                    QCourrier.courrier.division.code.likeIgnoreCase("%" + division + "%")
            );
        }

        courrierResponses = Objects.nonNull(builder.getValue()) ?
                courrierRepository.findAll(builder.getValue(), PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "id"))).map(courrierMapper::mapToCourrierResponse)
                :
                courrierRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "id"))).map(courrierMapper::mapToCourrierResponse);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(courrierResponses.getSize())
                .number(courrierResponses.getNumber())
                .totalElements(courrierResponses.getTotalElements())
                .totalPages(courrierResponses.getTotalPages())
                .build();
        return Response.ok().setPayload(courrierResponses.getContent()).setMetadata(pageMetadata).setMessage("Liste des Courries");

    }

    /**
     * verifier si l'utilisateur peut creer un courrier
     * @return
     */
    @Override
    @Transactional
    public Response<Object> verifyIfUserCanCreateCourrier() {

        Optional<CentralLevel> centralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());
        if (centralLevel.isEmpty())
            return Response.ok().setPayload(false);
        if (!centralLevel.get().getProfils().stream().filter(profile -> profile.getCode().equals("Chef-bureau-buco")).toList().isEmpty())
            return Response.ok().setPayload(true);
        return Response.ok().setPayload(false);
    }

    /**
     * traiter courrier
     * @param id
     * @return
     */
    @Override
    public Response<Object> traiterCourrier(Long id) {
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());

        Optional<Courrier> optionalCourrier = courrierRepository.findById(id);
        if (optionalCourrier.isEmpty())
            return Response.exception().setMessage("Courrier inéxistant.");

        if (optionalCentralLevel.isEmpty()){
            if (!isAdminDrh())
                return Response.exception().setMessage("Vous n'êtes pas autorisé à effectuer cette action");
        } else if (!canTraiterCourrier(optionalCentralLevel.get(),optionalCourrier.get()))
            return Response.exception().setMessage("Vous n'êtes pas autorisé à effectuer cette action");
        optionalCourrier.get().setStatut("TRAITER");
        courrierRepository.save(optionalCourrier.get());
        List<TraitementCourrier> traitementCourriers = traitementCourrierRepository.findAll();
        traitementCourriers.forEach(traitementCourrier -> {
            traitementCourrier.setActivated(false);
            traitementCourrierRepository.save(traitementCourrier);
        });



        TraitementCourrier newTraitementCourrier = new TraitementCourrier();
        newTraitementCourrier.setCourrier(optionalCourrier.get());
        newTraitementCourrier.setStatutCourrier(statutCourrierRepository.findByCode("TRAITER").get());
        newTraitementCourrier.setActivated(true);
        newTraitementCourrier.setUtilisateur(iUtilisateur.getCurrentUser());
        traitementCourrierRepository.save(newTraitementCourrier);

        return Response.ok().setMessage("Courrier Traité.");
    }

    /**
     * liste des type de demande de courrier via division et type courrier
     * @param divisionCode
     * @param nomTypeCourrier
     * @return
     */
    @Override
    @Transactional
    public Response<Object> listTypeDemandeCourrierByDivisionAndNomTypeCourrier(String divisionCode, String nomTypeCourrier) {
        System.out.println("uuuuuuuu");
//        System.out.println(typeDemandeCourrierRepository.findByNomTypeCourrierAndDivision(NomTypeCourrier.valueOf(nomTypeCourrier), divisionCode).size());
//        return Response.ok().setMessage("Liste type courrier").setPayload(typeDemandeCourrierRepository.findTypeDemandeCourrierByNomTypeCourrierAndDivision_Code(NomTypeCourrier.valueOf(nomTypeCourrier), divisionCode));
//        System.out.println(NomTypeCourrier.valueOf(nomTypeCourrier));
        return Response.ok().setMessage("Liste type courrier").setPayload(typeDemandeCourrierRepository.findTypeDemandeCourrierByNomTypeCourrierAndDivision_Code(NomTypeCourrier.valueOf(nomTypeCourrier), divisionCode));
    }

    /**
     * la liste des types de courriers
     * @return
     */
    @Override
    @Transactional
    public Response<Object> listTypeDemandeCourrier() {
        return Response.ok().setMessage("Liste type courrier").setPayload(typeDemandeCourrierRepository.findAll());
    }

    @Override
    public Response<Object> nombreDeCourrierTraiterEtNomTraiter() {
        Optional<CentralLevel> optionalCentralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail());

        NombreCourrierTaiterNonTraiter nombreCourrierTaiterNonTraiter = new NombreCourrierTaiterNonTraiter();
        // un utilisateur sans division rattachée (ex: ADMIN-DRH / Directeur-DRH, dont le CentralLevel a division_id NULL)
        // voit les totaux globaux, toutes divisions confondues
        String divisionCode = optionalCentralLevel
                .map(CentralLevel::getDivision)
                .map(Division::getCode)
                .orElse(null);
        if (divisionCode == null){
            nombreCourrierTaiterNonTraiter.setNombreCourrierTraiter(courrierRepository.findCourrierByStatut("TRAITER").size());
            nombreCourrierTaiterNonTraiter.setNombreCourrierNonTraiter(courrierRepository.findCourrierByStatut("NONTRAITER").size());
        } else {
            nombreCourrierTaiterNonTraiter.setNombreCourrierTraiter(courrierRepository.findCourrierByStatutAndDivision_Code("TRAITER", divisionCode).size());
            nombreCourrierTaiterNonTraiter.setNombreCourrierNonTraiter(courrierRepository.findCourrierByStatutAndDivision_Code("NONTRAITER", divisionCode).size());
        }
        return Response.ok().setPayload(nombreCourrierTaiterNonTraiter).setMessage("Nombre de courrier Traites et nom traites");
    }


    /**
     * traiter courrier
     * @param centralLevel
     * @param courrier
     * @return
     */
    /**
     * verifie si l'utilisateur connecté a le profil ADMIN-DRH ou Directeur-DRH (vue/gestion globale)
     * @return
     */
    boolean isAdminDrh(){
        return iUtilisateur.getCurrentUser().getProfils().stream()
                .anyMatch(profile -> "ADMIN-DRH".equals(profile.getCode()) || "Directeur-DRH".equals(profile.getCode()));
    }

    boolean canTraiterCourrier(CentralLevel centralLevel, Courrier courrier){
        // ADMIN-DRH et Directeur-DRH peuvent traiter n'importe quel courrier (vue globale)
        if (centralLevel.getProfils().stream().anyMatch(profile -> "ADMIN-DRH".equals(profile.getCode()) || "Directeur-DRH".equals(profile.getCode())))
            return true;
        if (
                centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-division-das")).toList().size() >= 1 ||
                        centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-division")).toList().size() >= 1 ||
                        centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-division-dfc")).toList().size() >= 1 ||
                        centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Assistant-DRH")).toList().size() >= 1 ||
                centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-division-dgpeec")).toList().size() >= 1 || centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Chef-division-dgcaa")).toList().size() >= 1
        ){
            if (centralLevel.getProfils().stream().filter(profile -> profile.getCode().equals("Assistant-DRH")).toList().size() >= 1)
                return true;
            if (centralLevel.getDivision().getCode().equals(courrier.getDivision().getCode()))
                return true;
        }

        return false;
    }
}
