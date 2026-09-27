package sn.gainde2000.backenmfpai.web.controllers.serviceutilisateur;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.UTILISATEUR_RECEIVE_EMAIL;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nTranslate;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.UserManager;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.UserManagerMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.central.CentralLevelMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.deconcentred.DeconcentratedLevelMapper;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.BureauRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DirectionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DivisionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.ServiceRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.ReferenceService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UserManagerRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.CentralLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DeconcentratedLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.DuplicateMatriculeReportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.UserImportResultDTO;

/**
 * @author G2k R&D
 */

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/utilisateur")
@Tag(name = "Gestion Utilisateur Controller", description = "Permet de gérer le profil de l'utilisateur connecte")
public class UtilisateurController {

    private final IUtilisateur iUtilisateur;
    private final DeconcentratedLevelMapper deconcentratedLevelMapper;
    private final CentralLevelMapper centralLevelMapper;
    private final UserManagerMapper userManagerMapper;
    private final I18nTranslate i18nTranslat;
    private final ReferenceService referenceService;
    private final DirectionRepository directionRepository;
    private final ServiceRepository serviceRepository;
    private final DivisionRepository divisionRepository;
    private final BureauRepository bureauRepository;

    private static final Logger log = LoggerFactory.getLogger(UtilisateurController.class);

    // @GetMapping("/prioritaires")
    // public ResponseEntity<MFPAIResponse> getPrioritaires(@RequestParam("n") int
    // n) {
    // List<DeconcentratedLevel> prioritaires = iUtilisateur.getPrioritaires(n);
    // MFPAIResponse response =
    // MFPAIResponse.success(deconcentratedLevelMapper.toDtoList(prioritaires));
    // return ResponseEntity.ok(response);
    // }


    @Operation(summary = "Endpoint pour ajouter un utilisateur de niveau déconnecté")
    @PostMapping("/switch-user/{id}")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> switchUserType(@PathVariable("id") Long id) {
        Utilisateur user = iUtilisateur.switchUserType(id);
        MFPAIResponse response = MFPAIResponse.success(user);
        return ResponseEntity.ok().body(response);
    }




    @Operation(summary = "Endpoint pour ajouter un utilisateur de niveau déconnecté")
    @PostMapping("/deconected/add")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> saveUtilisateurDec(@Valid @RequestBody DeconcentratedLevelDTO dto) {
        DeconcentratedLevel user = iUtilisateur.saveUtilisateurDL(dto);
        MFPAIResponse response = MFPAIResponse.success(deconcentratedLevelMapper.toDto(user));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour importer en masse des utilisateurs de niveau déconcentré (Excel/CSV)")
    @PostMapping(value = "/deconected/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> importUtilisateursDec(@RequestParam("file") MultipartFile file) {
        UserImportResultDTO result = iUtilisateur.importUtilisateursDL(file);
        MFPAIResponse response = MFPAIResponse.success(result)
                .message(result.getImported() + " utilisateur(s) importé(s), " + result.getFailed() + " en erreur.");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour détecter les matricules en doublon (niveau déconcentré) sans les supprimer")
    @GetMapping("/deconected/duplicates")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> findDuplicateUtilisateursDec() {
        DuplicateMatriculeReportDTO report = iUtilisateur.findDuplicateMatriculesDL();
        MFPAIResponse response = MFPAIResponse.success(report);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour supprimer les doublons de matricule (niveau déconcentré), conserve le plus ancien")
    @DeleteMapping("/deconected/duplicates")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> removeDuplicateUtilisateursDec() {
        DuplicateMatriculeReportDTO report = iUtilisateur.removeDuplicateMatriculesDL();
        MFPAIResponse response = MFPAIResponse.success(report)
                .message(report.getEnregistrementsSupprimes() + " doublon(s) supprimé(s).");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour mettre à jour  un utilisateur de niveau déconnecté")
    @PutMapping("/deconected/update/{id}")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> saveUtilisateurDec(@PathVariable(value = "id") Long id,
            @Valid @RequestBody DeconcentratedLevelDTO dto) {
        DeconcentratedLevel user = iUtilisateur.updateUtilisateurDL(id, dto);
        MFPAIResponse response = MFPAIResponse.success(deconcentratedLevelMapper.toDto(user));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/deconected/list/{code}")
    public ResponseEntity<MFPAIResponse> listUserFromEtablissementCode(@PathVariable(name = "code") String code) {
        List<DeconcentratedLevel> deconcentratedLevels = referenceService.listUserFromEtablissementCode(code);

        MFPAIResponse response = MFPAIResponse.success(deconcentratedLevels);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour récupérer la liste des utilisateurs de niveau déconcentré en page")
    @GetMapping("/deconected/list")
    public ResponseEntity<MFPAIResponse> getUserDecoListPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortByDescending) {
        Page<DeconcentratedLevel> userPage = iUtilisateur.getPageDecocentred(page, size, sortBy, sortByDescending);
        MFPAIResponse response = MFPAIResponse.success(userPage);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour récupérer un utilisateur de niveau déconnecté")
    @GetMapping("/deconected/{id}")
    public ResponseEntity<MFPAIResponse> getUserDeconectedId(@PathVariable("id") Long id) {
        DeconcentratedLevel user = iUtilisateur.getUserDeconected(id);
        MFPAIResponse response = MFPAIResponse.success(deconcentratedLevelMapper.toDto(user));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour ajouter un utilisateur de niveau connecté")
    @PostMapping("/central/add")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> saveUtilisateurCen(@Valid @RequestBody CentralLevelDTO dto) {
        CentralLevel user = iUtilisateur.saveUtilisateurCL(dto);
        MFPAIResponse response = MFPAIResponse.success(centralLevelMapper.toDto(user));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour importer en masse des utilisateurs de niveau central (Excel/CSV)")
    @PostMapping(value = "/central/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> importUtilisateursCen(@RequestParam("file") MultipartFile file) {
        UserImportResultDTO result = iUtilisateur.importUtilisateursCL(file);
        MFPAIResponse response = MFPAIResponse.success(result)
                .message(result.getImported() + " utilisateur(s) importé(s), " + result.getFailed() + " en erreur.");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour mettre à jour  un utilisateur de niveau connecté")
    @PutMapping("/central/update/{id}")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> updateUtilisateurCen(@PathVariable("id") Long id,
            @Valid @RequestBody CentralLevelDTO dto) {

        CentralLevel user = iUtilisateur.updateUtilisateurCL(id, dto);
        MFPAIResponse response = MFPAIResponse.success(centralLevelMapper.toDto(user));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour récupérer la liste des utilisateurs de niveau central en page")
    @GetMapping("/central/list")
    public ResponseEntity<MFPAIResponse> getUserCentralListPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortByDescending) {
        Page<CentralLevel> userPage = iUtilisateur.getPageCentral(page, size, sortBy, sortByDescending);

        MFPAIResponse response = MFPAIResponse.success(userPage);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour lister les  utilisateur de niveau deconected avec filtre")
    @GetMapping(path = "/deconected/listPage")
    public Response<Object> getListDeconectedLevelPage(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter) {
        return iUtilisateur.getPageDeconectedWithFilter(page, size, filter);
    }

    @Operation(summary = "Endpoint pour lister les  utilisateur de niveau deconected avec filtre")
    @GetMapping(path = "/deconected/listAdvandedPage")
    public Response<Object> getListDeconectedLevelPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter,
            @RequestParam(value = "profile", defaultValue = "") String profile,
            @RequestParam(value = "matricule", defaultValue = "") String matricule,
            @RequestParam(value = "prenom", defaultValue = "") String prenom,
            @RequestParam(value = "nom", defaultValue = "") String nom,
            @RequestParam(value = "region", defaultValue = "") String region,
            @RequestParam(value = "ia", defaultValue = "") String ia,
            @RequestParam(value = "ief", defaultValue = "") String ief,
            @RequestParam(value = "typeSystemeEnseignement", defaultValue = "") String typeSystemeEnseignement,
            @RequestParam(value = "etablissement", defaultValue = "") String etablissement) {
        return iUtilisateur.getPageDeconectedWithFilterAdvanced(page, size, filter, profile, matricule, prenom, nom,
                region, ia, ief, typeSystemeEnseignement, etablissement);
    }

    @Operation(summary = "Endpoint pour lister les  utilisateur de niveau central avec filtre")
    @GetMapping(path = "/central/listAvancedPage")
    public Response<Object> getListCentralLevelAvancedPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter,
            @RequestParam(value = "profile", defaultValue = "") String profile,
            @RequestParam(value = "matricule", defaultValue = "") String matricule,
            @RequestParam(value = "prenom", defaultValue = "") String prenom,
            @RequestParam(value = "nom", defaultValue = "") String nom,
            @RequestParam(value = "direction", defaultValue = "") String direction
    ) {

        return iUtilisateur.getPageCentralWithFilterAdvanced(page, size, filter, profile, matricule, prenom, nom, direction);
    }


    @Operation(summary = "Endpoint pour lister les  utilisateur de niveau déconnecté avec filtre")
    @GetMapping(path = "/central/listCenPage")
    public Response<Object> getListCentralLevelPage(
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "region", defaultValue = "") String region,
                                                           @RequestParam(value = "direction", defaultValue = "") String direction,
                                                           @RequestParam(value = "division", defaultValue = "")String division,
                                                           @RequestParam(value = "bureau", defaultValue = "") String bureau,
                                                           @RequestParam(value = "specialite", defaultValue = "") String specialite,
                                                           @RequestParam(value = "corps", defaultValue = "")String corps,
                                                           @RequestParam(value = "grade", defaultValue = "")String grade,
                                                           @RequestParam(value = "matricule", defaultValue = "")String matricule,
                                                           @RequestParam(value = "prenom", defaultValue = "") String prenom,
                                                           @RequestParam(value = "nom", defaultValue = "")String nom,
                                                           @RequestParam(value = "dateNaissance", defaultValue = "")String dateNaissance,
                                                           @RequestParam(value = "cni", defaultValue = "")String cni,
                                                           @RequestParam(value = "telephone", defaultValue = "")String telephone,
                                                           @RequestParam(value = "email", defaultValue = "")String email) {


        return iUtilisateur.getPageCentralAdvanced(page, size, region, direction, division, bureau, specialite, corps,
                grade, matricule, prenom, nom, dateNaissance, cni, telephone, email);
    }



    @Operation(summary = "Endpoint pour lister les  utilisateur de niveau deconcentré avec filtre")
    @GetMapping(path = "/deconected/listDecoPage")
    public Response<Object> getListDecoLevelPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "region", defaultValue = "") String region,
            @RequestParam(value = "ia", defaultValue = "") String ia,
            @RequestParam(value = "ief", defaultValue = "")String ief,
            @RequestParam(value = "etablissement", defaultValue = "") String etablissement,
            @RequestParam(value = "typeSystemeEnseignement", defaultValue = "") String typeSystemeEnseignement,
            @RequestParam(value = "specialite", defaultValue = "") String specialite,
            @RequestParam(value = "corps", defaultValue = "")String corps,
            @RequestParam(value = "grade", defaultValue = "")String grade,
            @RequestParam(value = "matricule", defaultValue = "")String matricule,
            @RequestParam(value = "prenom", defaultValue = "") String prenom,
            @RequestParam(value = "nom", defaultValue = "")String nom,
            @RequestParam(value = "dateNaissance", defaultValue = "")String dateNaissance,
            @RequestParam(value = "cni", defaultValue = "")String cni,
            @RequestParam(value = "telephone", defaultValue = "")String telephone,
            @RequestParam(value = "email", defaultValue = "")String email) {

        return iUtilisateur.getPageDecoAdvanced(page, size, region, ia , ief, etablissement, typeSystemeEnseignement, specialite,
                corps, grade, matricule, prenom, nom, dateNaissance, cni, telephone, email);
    }
    @Operation(summary = "Endpoint pour lister le personnel d'un établissement")
    @GetMapping(path = "/personnels/")
    public Response<Object> getPersonnels(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "region", defaultValue = "") String region,
            @RequestParam(value = "structure", defaultValue = "") String structure,
            @RequestParam(value = "ia", defaultValue = "") String ia,
            @RequestParam(value = "ief", defaultValue = "")String ief,
            @RequestParam(value = "etablissement", defaultValue = "") String etablissement)
             {
        return iUtilisateur.getPersonnels(page, size, region, structure, ia , ief, etablissement);
    }

    /*
     * @Operation(summary =
     * "Endpoint pour lister les  utilisateur de niveau deconected avec filtre")
     * 
     * @GetMapping(path = "/deconected/listPage")
     * public Response<Object> getListDeconectedLevelPage(@RequestParam(value =
     * "page", defaultValue = "0") int page,
     * 
     * @RequestParam(value = "size", defaultValue = "10") int size,
     * 
     * @RequestParam(value = "filter", defaultValue = "") String filter) {
     * return iUtilisateur.getPageCentralWithFilter(page, size, filter);
     * }
     */
    @Operation(summary = "Endpoint pour lister les  utilisateur de niveau central avec filtre")
    @GetMapping(path = "/central/listPage")
    public Response<Object> getListCentralLevelPage(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "filter", defaultValue = "") String filter) {
        return iUtilisateur.getPageCentralWithFilter(page, size, filter);
    }

    @Operation(summary = "Endpoint pour récupérer un  utilisateur de niveau central")
    @GetMapping("/central/{id}")
    public ResponseEntity<MFPAIResponse> getUserCentralId(@PathVariable("id") Long id) {
        CentralLevel user = iUtilisateur.getUserCentral(id);
        MFPAIResponse response = MFPAIResponse.success(centralLevelMapper.toDto(user));
        return ResponseEntity.ok().body(response);
    }

   /* @Operation(summary = "Endpoint pour récupérer la liste des utilisateurs en page")
    @GetMapping("/listUser")
    public ResponseEntity<MFPAIResponse> getUserListPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortByDescending) {
        Page<Utilisateur> userPage = iUtilisateur.getPageUsers(page, size, sortBy, sortByDescending);
        MFPAIResponse response = MFPAIResponse.success(userPage);
        return ResponseEntity.ok().body(response);
    }

    */

    @Operation(summary = "Endpoint pour récupérer l'utilisateur")

    @GetMapping("/{id}")
    public ResponseEntity<MFPAIResponse> getUserById(@PathVariable("id") Long id) {
        Utilisateur user = iUtilisateur.getUserId(id);
        MFPAIResponse response = MFPAIResponse.success(user);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Ajouter des utilisateurs admin")
    @PostMapping("/manager-user")
    public ResponseEntity<MFPAIResponse> saveUtilisateur(@Valid @RequestBody UserManagerRequestDTO dto) {
        UserManager userManager = iUtilisateur.saveUtilisateurManager(dto, false);
        MFPAIResponse response = MFPAIResponse.success(userManagerMapper.toDto(userManager))
                .message(i18nTranslat.toTranslate(UTILISATEUR_RECEIVE_EMAIL));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatut(@PathVariable("id") Long id) {
        Utilisateur utilisateur = iUtilisateur.changeStatut(id);
        MFPAIResponse response = MFPAIResponse.success(utilisateur);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/by-direction/{code}")
    public ResponseEntity<List<CentralLevel>> getUsersByDirection(@PathVariable(name = "code") String code) {
        List<CentralLevel> users = iUtilisateur.getUsersByDirection(code);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile/{code}")
    public ResponseEntity<List<Utilisateur>> getUsersProfiles(@PathVariable(name = "code") String code) {
        List<Utilisateur> users = iUtilisateur.listUtilisateurByCodeProfile(code);
        return ResponseEntity.ok(users);
    }

    @GetMapping("getAll")
    public ResponseEntity<Long> getAllUsers() {
        long usersCount = iUtilisateur.getAllUsersCount();
        return ResponseEntity.ok(usersCount);
    }

    /* COK */
    @GetMapping("/prioritaires")
    public ResponseEntity<MFPAIResponse> getPrioritaires(@RequestParam("n") int n) {
        List<DeconcentratedLevel> prioritaires = iUtilisateur.getPrioritaires(n);
        MFPAIResponse response = MFPAIResponse.success(deconcentratedLevelMapper.toDtoList(prioritaires));
        return ResponseEntity.ok(response);
    }

  /*  @GetMapping("/prioritaire")
    public ResponseEntity<MFPAIResponse> isCurrentUserInTopN(@RequestParam("n") int n) {
        boolean isInTopN = iUtilisateur.getPrioritaire(n, );
        MFPAIResponse response = MFPAIResponse.success(isInTopN);
        return ResponseEntity.ok(response);
    }
*/

    @Operation(summary = "Endpoint pour récupérer l'utilisateur")

    @GetMapping("/filter-user")
    public ResponseEntity<MFPAIResponse> getUserSearch(
            @RequestParam(value = "filter", defaultValue = "") String filter) {
        Utilisateur user = iUtilisateur.searchUser(filter);
        MFPAIResponse response = MFPAIResponse.success(user);
        return ResponseEntity.ok().body(response);
    }

    //  AJOUTEZ CES NOUVEAUX ENDPOINTS 
    
    /**
     * Endpoint pour rechercher le personnel du niveau central
     * GET /api/utilisateur/personnel/niveau-central
     */
    @GetMapping("/personnel/niveau-central")
    public Response<Object> getPersonnelsNiveauCentral(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String division,
            @RequestParam(required = false) String bureau) {
        
        log.info("Appel getPersonnelsNiveauCentral - page: {}, size: {}, direction: {}, service: {}, division: {}, bureau: {}", 
                 page, size, direction, service, division, bureau);
        
        return iUtilisateur.getPersonnelsNiveauCentral(page, size, direction, service, division, bureau);
    }
    
    /**
     * Endpoint pour récupérer toutes les directions
     * GET /api/utilisateur/references/directions
     */
    @GetMapping("/references/directions")
    public Response<List<Direction>> getAllDirections() {
        return iUtilisateur.getAllDirections();
    }
    
    /**
     * Endpoint pour récupérer les services d'une direction
     * GET /api/utilisateur/references/services/{directionCode}
     */
    @GetMapping("/references/services/{directionCode}")
    public Response<List<Services>> getServicesByDirection(@PathVariable String directionCode) {
        return iUtilisateur.getServicesByDirection(directionCode);
    }
    
    /**
     * Endpoint pour récupérer les divisions d'une direction
     * GET /api/utilisateur/references/divisions/{directionCode}
     */
    @GetMapping("/references/divisions/{directionCode}")
    public Response<List<Division>> getDivisionsByDirection(@PathVariable String directionCode) {
        return iUtilisateur.getDivisionsByDirection(directionCode);
    }
    
    /**
     * Endpoint pour récupérer les bureaux d'une division
     * GET /api/utilisateur/references/bureaus/{divisionCode}
     */
    @GetMapping("/references/bureaus/{divisionCode}")
    public Response<List<Bureau>> getBureausByDivision(@PathVariable String divisionCode) {
        return iUtilisateur.getBureausByDivision(divisionCode);
    }

}
