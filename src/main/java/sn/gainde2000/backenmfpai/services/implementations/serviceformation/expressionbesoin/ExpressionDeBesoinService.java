package sn.gainde2000.backenmfpai.services.implementations.serviceformation.expressionbesoin;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutCampagneEnum;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutExpressionDeBesoinEnum;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin.CampagneMapper;
import sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin.ExpressionDeBesoinMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin.IExpressionDeBesoin;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AuthorizedDemandeStageDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.CampagneResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.UtilisateurResponseDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ExpressionDeBesoinService implements IExpressionDeBesoin {
    private final BusinessNotificationService businessNotifications;

    private final ExpressionDeBesoinRepository expressionDeBesoinRepository;
    private final CampagneRepository campagneRepository;
    private final ExpressionDeBesoinMapper expressionDeBesoinMapper;
    private final TraitementCampagneRepository traitementCampagneRepository;
    private final IUtilisateur iUtilisateur;
    private final StatutExpressionDeBesoinRepository statutExpressionDeBesoinRepository;
    private final TraitementExpressionDeBesoinRepository traitementExpressionDeBesoinRepository;
    private final CampagneMapper campagneMapper;
    private final MailService mailService;
    private final CentralLevelRepository centralLevelRepository;
    private final IUtilisateurRepository iUtilisateurRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;

    /**
     * creation expression de besoin
     * @param expressionDeBesoinDTO
     * @param idCampagne : identifient de la campagne
     * @return
     */
    @Override
    @Transactional
    public Response<Object> saveExpressionDeBesoin(ExpressionDeBesoinDTO expressionDeBesoinDTO, long idCampagne, HttpServletRequest request) {
//        businessNotifications.sendMail(new MailInfosDTO(null, "Bonjour \nUne epxressionde besoin vous a été soumise.\nMerci de procéder au traitement.", "Expression de besoin", null, "chefdivision@yopmail.com"));

        Optional<Campagne> optionalCampagne = campagneRepository.findByIdAndDeletedFalse(idCampagne);
        if (optionalCampagne.isEmpty())
            return Response.ok().setMessage("Campagne inexistante.");

        TraitementCampagne traitementCampagne = traitementCampagneRepository.findTraitementCampagneByActivatedTrueAndCampagne_Id(optionalCampagne.get().getId()).get(0);
        if (traitementCampagne.getStatutCampagne().getCode().equals(StatutCampagneEnum.ENDED_CAMPAGNE.name()))
            return Response.ok().setMessage("Impossible de soumettre une expression de besion sur une campagne déjà cloturée.");


        try {
            ExpressionDeBesoin expressionDeBesoin = expressionDeBesoinMapper.map(expressionDeBesoinDTO);
            expressionDeBesoin.setStatutExpression(StatutExpressionDeBesoinEnum.NON_TRAITER);
            expressionDeBesoin.setCampagne(optionalCampagne.get());

            expressionDeBesoin.setUtilisateur(iUtilisateur.getCurrentUser());
            expressionDeBesoin.setDate(LocalDate.now());
            ExpressionDeBesoin expressionDeBesoinSaved = expressionDeBesoinRepository.save(expressionDeBesoin);
            expressionDeBesoinSaved.setReference(formatNumber(expressionDeBesoinSaved.getId())+"EB"+LocalDate.now().getYear());
            expressionDeBesoinRepository.save(expressionDeBesoinSaved);
            TraitementExpressionDeBesoin traitementExpressionDeBesoin = new TraitementExpressionDeBesoin();
            traitementExpressionDeBesoin.setExpressionDeBesoin(expressionDeBesoinSaved);
            traitementExpressionDeBesoin.setActivated(true);

            traitementExpressionDeBesoin.setStatutExpressionDeBesoin(statutExpressionDeBesoinRepository.findByCode(StatutExpressionDeBesoinEnum.NON_TRAITER.name()).get());
            traitementExpressionDeBesoinRepository.save(traitementExpressionDeBesoin);
            businessNotifications.notify(expressionDeBesoin.getUtilisateur(), "Création de votre expression de besoin",
                    "Votre expression de besoin " + expressionDeBesoinSaved.getReference() + " a été enregistrée.");
        }catch (Exception e){
            throw new IllegalStateException("Échec de création de l’expression de besoin", e);
        }


        // Chef de division, Chef de Bureau,Agent Bureau : ENVOIE MAIL
        List<String> profileTraitants = new ArrayList<>(Arrays.asList("Chef-division","Chef-bureau","Agent-bureau"));

        List<Utilisateur> utilisateurs = iUtilisateurRepository.findByProfileCodes(profileTraitants);
        for (Utilisateur centralLevel: utilisateurs
             ) {
            List<String> profils = new ArrayList<>();
            profils = centralLevel.getProfils().stream().map(profile -> {
                return profile.getCode();
            }).toList();

            if (containsOneRole(profils,profileTraitants)){
                businessNotifications.notify(centralLevel, "Expression de besoin", "Bonjour "+centralLevel.getPrenom()+" "+centralLevel.getNom()+"\nUne epxressionde besoin vous a été soumise.\nMerci de procéder au traitement.");
            }
        }
        return Response.ok().setMessage("Expression de besoin créer avec succès.");
    }

    /**
     * formater nombre unique
     * @param number
     * @return
     */
    public String formatNumber(long number) {
        return String.format("%0" + 4 + "d", number);
    }

    /**
     * verifier si une liste de role contient un element d'une autre liste
     * @param mainList
     * @param elementsToCheck
     * @return
     */
    private static boolean containsOneRole(List<String> mainList, List<String> elementsToCheck) {
        for (String element : elementsToCheck) {
            if (mainList.contains(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get expression de besion
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Response<Object> getExpressionDeBesoin(long id) {
        Optional<ExpressionDeBesoin> optionalExpressionDeBesoin = expressionDeBesoinRepository.findByIdAndDeletedFalse(id);
        if (optionalExpressionDeBesoin.isEmpty())
            return Response.ok().setMessage("Expression de besoins inexistante.");
        ExpressionDeBesoinResponseDTO expressionDeBesoinResponseDTO = expressionDeBesoinMapper.mapToExpressionDeBesoinResponseDTO(optionalExpressionDeBesoin.get());
        UtilisateurResponseDTO utilisateurResponseDTO = new UtilisateurResponseDTO();
        CentralLevel central = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (iUtilisateur.getCurrentUser().getTypeUser().equals("CEN")){
            central = centralLevelRepository.findByEmail(optionalExpressionDeBesoin.get().getUtilisateur().getEmail()).get();
            utilisateurResponseDTO.setProfils(central.getProfils());
            utilisateurResponseDTO.setId(central.getId());
            utilisateurResponseDTO.setMatricule(central.getMatricule());
            utilisateurResponseDTO.setNom(central.getNom());
            utilisateurResponseDTO.setPrenom(central.getPrenom());
            utilisateurResponseDTO.setEmail(central.getEmail());
            utilisateurResponseDTO.setTelephone(central.getTelephone());
            utilisateurResponseDTO.setSexe(central.getSexe());
            utilisateurResponseDTO.setAdresse(central.getAdresse());
            utilisateurResponseDTO.setMatricule(central.getMatricule());
            utilisateurResponseDTO.setFonction(central.getFonction());
            //utilisateurResponseDTO.setService(central.getService());
            utilisateurResponseDTO.setDirection(central.getDirection());
        }else {
            deconcentratedLevel = deconcentratedLevelRepository.findByMatricule(optionalExpressionDeBesoin.get().getUtilisateur().getMatricule()).get();
            utilisateurResponseDTO.setProfils(deconcentratedLevel.getProfils());
            utilisateurResponseDTO.setId(deconcentratedLevel.getId());
            utilisateurResponseDTO.setMatricule(deconcentratedLevel.getMatricule());
            utilisateurResponseDTO.setNom(deconcentratedLevel.getNom());
            utilisateurResponseDTO.setPrenom(deconcentratedLevel.getPrenom());
            utilisateurResponseDTO.setEmail(deconcentratedLevel.getEmail());
            utilisateurResponseDTO.setTelephone(deconcentratedLevel.getTelephone());
            utilisateurResponseDTO.setSexe(deconcentratedLevel.getSexe());
            utilisateurResponseDTO.setAdresse(deconcentratedLevel.getAdresse());
            utilisateurResponseDTO.setMatricule(deconcentratedLevel.getMatricule());
            utilisateurResponseDTO.setFonction(deconcentratedLevel.getFonction());
        }


        expressionDeBesoinResponseDTO.setUtilisateurResponseDTO(utilisateurResponseDTO);
        CampagneResponseDTO campagneResponseDTO = campagneMapper.mapToCampagneResponseDTO(optionalExpressionDeBesoin.get().getCampagne());
        campagneResponseDTO.setStatut(StatutCampagneEnum.valueOf(traitementCampagneRepository.findTraitementCampagneByCampagne_IdAndActivatedTrue(campagneResponseDTO.getId()).get(0).getStatutCampagne().getCode()));
        expressionDeBesoinResponseDTO.setCampagneResponseDTO(campagneResponseDTO);
        expressionDeBesoinResponseDTO.setThemeProvisoire(traitementExpressionDeBesoinRepository.findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(id).get(0).getThemeProvisoire());
        return Response.ok().setPayload(expressionDeBesoinResponseDTO).setMessage("Récupèration expression de besoins.");
    }

    /**
     * traiter expression de besoin
     * @param ids
     * @param request
     * @param themeProvisoire
     * @return
     */
    @Override
    @Transactional
    public Response<Object> traiterExpressionDeBesoins(String ids,HttpServletRequest request, String themeProvisoire) {

        String[] ids_ = ids.split(",");

        List<Long> longList = Arrays.stream(ids_).map(Long::parseLong).toList();
        System.out.println(longList.size());
        List<ExpressionDeBesoin> expressionDeBesoins = expressionDeBesoinRepository.findAllById(longList);
        for (ExpressionDeBesoin expressionDeBesoin: expressionDeBesoins
             ) {
            traitementExpressionDeBesoinRepository.findTraitementExpressionDeBesoinByExpressionDeBesoin_Id(expressionDeBesoin.getId()).stream().forEach(traitementExpressionDeBesoin -> {
                traitementExpressionDeBesoin.setActivated(false);
                traitementExpressionDeBesoinRepository.save(traitementExpressionDeBesoin);
            });
            expressionDeBesoin.setStatutExpression(StatutExpressionDeBesoinEnum.TRAITER);
            ExpressionDeBesoin expressionDeBesoinSaved = expressionDeBesoinRepository.save(expressionDeBesoin);
            TraitementExpressionDeBesoin traitementExpressionDeBesoin = new TraitementExpressionDeBesoin();
            traitementExpressionDeBesoin.setExpressionDeBesoin(expressionDeBesoinSaved);
            traitementExpressionDeBesoin.setUtilisateur(iUtilisateur.getCurrentUser());


            traitementExpressionDeBesoin.setActivated(true);
            traitementExpressionDeBesoin.setThemeProvisoire(themeProvisoire);

            traitementExpressionDeBesoin.setStatutExpressionDeBesoin(statutExpressionDeBesoinRepository.findByCode(StatutExpressionDeBesoinEnum.NON_TRAITER.name()).get());
            traitementExpressionDeBesoinRepository.save(traitementExpressionDeBesoin);

            // envoie mail au demandeur
            String emailDemandeur = expressionDeBesoin.getUtilisateur().getEmail();
            Utilisateur utilisateur = expressionDeBesoin.getUtilisateur();
            businessNotifications.notify(utilisateur, "Traitement expression de besoin", "Bonjour "+utilisateur.getPrenom()+" "+utilisateur.getNom()+"\nVotre  demande d'expressions des besoins a éte traité avec succès");

        }
        if (ids_.length > 1)
            return Response.ok().setMessage("Expressions de besoin traitées avec succès");
        return Response.ok().setMessage("Expression de besoin traitée avec succès");
    }

    /**
     * modifier expression de besoin
     * @param id
     * @param expressionDeBesoinDTO
     * @return
     */
    @Override
    @Transactional
    public Response<Object> editExpressionDeBesoins(long id, ExpressionDeBesoinDTO expressionDeBesoinDTO) {
        Optional<ExpressionDeBesoin> optionalExpressionDeBesoin = expressionDeBesoinRepository.findByIdAndDeletedFalse(id);
        if (optionalExpressionDeBesoin.isEmpty())
            return Response.ok().setMessage("Expression de besoins inexistante.");
        ExpressionDeBesoin expressionDeBesoin = optionalExpressionDeBesoin.get();
        expressionDeBesoin.setBesoin(expressionDeBesoinDTO.getBesoin());
        expressionDeBesoin.setMotif(expressionDeBesoinDTO.getMotif());
        expressionDeBesoinRepository.save(expressionDeBesoin);
        return Response.ok().setMessage("Expressions de besoin modifiée avec succès.");
    }
}
