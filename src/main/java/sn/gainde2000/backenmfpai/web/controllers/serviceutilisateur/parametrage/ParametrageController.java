package sn.gainde2000.backenmfpai.web.controllers.serviceutilisateur.parametrage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Diplomes;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.*;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.DiplomesMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.FonctionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central.BureauMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central.DirectionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central.DivisionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.RegionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.deconnected.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.parametrage.ParametrageService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DiplomeReqDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.RegionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.BureauDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DirectionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DivisionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.FonctionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.DiplomeBaseDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-11:17
 * @project backend_mfpai
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/parametrages/utilisateur")
@Tag(name = "Gestion parametrage Controller", description = "Permet de gérer les parametres utilisateurs")
public class ParametrageController {

    private final ParametrageService parametrageService;
    private final RegionMapper regionMapper;
    private final DirectionMapper directionMapper;
    private final DivisionMapper divisionMapper;
    private final BureauMapper bureauMapper;
    private final IAMapper iaMapper;
    private final IEFMapper iefMapper;
    private final EtablissementMapper etablissementMapper;
    private final SpecialityMapper specialityMapper;
    private final FonctionMapper fonctionMapper;
    private final SpecialityEtablissementMapper specialityEtablissementMapper;

    private final DiplomesMapper diplomeMapper;


    // REGION

    @Operation(summary = "Endpoint pour ajouter une nouvelle région")
    @PostMapping("/region/add")
    public ResponseEntity<MFPAIResponse> saveRegion(@Valid @RequestBody RegionDTO dto) {
        Region region = parametrageService.addRegion(dto);
        MFPAIResponse response = MFPAIResponse.success(regionMapper.toDto(region));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour une région")
    @PutMapping("/region/update/{id}")
    public ResponseEntity<MFPAIResponse> updateRegion(@PathVariable(value = "id") Long id,
                                                     @Valid @RequestBody RegionDTO dto) {
        Region region = parametrageService.updateRegion(id, dto);
        MFPAIResponse response = MFPAIResponse.success(regionMapper.toDto(region));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer une région")
    @GetMapping("/region/{id}")
    public ResponseEntity<MFPAIResponse> getRegion(@PathVariable("id") Long id) {
        Region region = parametrageService.getRegion(id);
        MFPAIResponse response = MFPAIResponse.success(regionMapper.toDto(region));
        return ResponseEntity.ok().body(response);
    }



    @GetMapping(path = "/region/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des régions")
    public Response<Object> listPageRegion(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "10") int size,
                                     @RequestParam(name = "filter", defaultValue = "") String filter

    )
    {


        return parametrageService.getPageRegion (page, size, filter);
    }


    //  DIRECTION

    @Operation(summary = "Endpoint pour ajouter une nouvelle direction")
    @PostMapping("/direction/add")
    public ResponseEntity<MFPAIResponse> saveDirection(@Valid @RequestBody DirectionDTO dto) {
        Direction direction = parametrageService.addDirection(dto);
        MFPAIResponse response = MFPAIResponse.success(directionMapper.toDto(direction));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour une direction")
    @PutMapping("/direction/update/{id}")
    public ResponseEntity<MFPAIResponse> update(@PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody DirectionDTO dto) {
        Direction direction = parametrageService.updateDirection(id, dto);
        MFPAIResponse response = MFPAIResponse.success(directionMapper.toDto(direction));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer une direction")
    @GetMapping("/direction/{id}")
    public ResponseEntity<MFPAIResponse> getDirection(@PathVariable("id") Long id) {
        Direction direction = parametrageService.getDirection(id);
        MFPAIResponse response = MFPAIResponse.success(directionMapper.toDto(direction));
        return ResponseEntity.ok().body(response);
    }



    @GetMapping(path = "/direction/list-pages")
    @Operation(description = "Endpoint de recuperation liste des  directions avec des filtres avancés")
    public Response<Object> listPageDirection(@RequestParam(name = "page", defaultValue = "0") int page,
                                     @RequestParam(name = "size", defaultValue = "10") int size,
                                     @RequestParam(name = "filter", defaultValue = "") String filter,
                                     @RequestParam(name = "statut", defaultValue = "")  Boolean  statut


    )
    {


        return parametrageService.getPageDirection (page, size, filter, statut);
    }


    @PutMapping("/direction/change-status/{id}")
    public ResponseEntity<?> changeStatut(@PathVariable("id") Long id) {
        Direction direction = parametrageService.activateDirection(id);
        MFPAIResponse response = MFPAIResponse.success(direction);
        return ResponseEntity.ok().body(response);
    }


    // Division

    @Operation(summary = "Endpoint pour ajouter une nouvelle division")
    @PostMapping("/division/add")
    public ResponseEntity<MFPAIResponse> saveDivision(@Valid @RequestBody DivisionDTO dto) {
        Division division = parametrageService.addDivision(dto);
        MFPAIResponse response = MFPAIResponse.success(divisionMapper.toDto(division));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour une division")
    @PutMapping("/division/update/{id}")
    public ResponseEntity<MFPAIResponse> updateDivision(@PathVariable(value = "id") Long id,
                                                @Valid @RequestBody DivisionDTO dto) {
        Division division = parametrageService.updateDivision(id, dto);
        MFPAIResponse response = MFPAIResponse.success(divisionMapper.toDto(division));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer une division")
    @GetMapping("/division/{id}")
    public ResponseEntity<MFPAIResponse> getDivision(@PathVariable("id") Long id) {
        Division division = parametrageService.getDivision(id);
        MFPAIResponse response = MFPAIResponse.success(divisionMapper.toDto(division));
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/division/change-status/{id}")
    public ResponseEntity<?> changeStatutDivision(@PathVariable("id") Long id) {
        Division division = parametrageService.activateDivision(id);
        MFPAIResponse response = MFPAIResponse.success(division);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/division/list-pages")
    @Operation(description = "Endpoint de recuperation liste des  divisions avec des filtres avancés")
    public Response<Object> listPageDivision(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "10") int size,
                                              @RequestParam(name = "filter", defaultValue = "") String filter,
                                             @RequestParam(name = "statut", defaultValue = "")  Boolean  statut

    )
    {


        return parametrageService.getPageDivision (page, size, filter, statut);
    }


    // Bureau

    @Operation(summary = "Endpoint pour ajouter une nouvelle Bureau")
    @PostMapping("/bureau/add")
    public ResponseEntity<MFPAIResponse> saveBureau(@Valid @RequestBody BureauDTO dto) {
        Bureau bureau = parametrageService.addBureau(dto);
        MFPAIResponse response = MFPAIResponse.success(bureauMapper.toDto(bureau));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour une Bureau")
    @PutMapping("/bureau/update/{id}")
    public ResponseEntity<MFPAIResponse> updateBureau(@PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody BureauDTO dto) {
        Bureau bureau = parametrageService.updateBureau(id, dto);
        MFPAIResponse response = MFPAIResponse.success(bureauMapper.toDto(bureau));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer une Bureau")
    @GetMapping("/bureau/{id}")
    public ResponseEntity<MFPAIResponse> getBureau(@PathVariable("id") Long id) {
        Bureau bureau = parametrageService.getBureau(id);
        MFPAIResponse response = MFPAIResponse.success(bureauMapper.toDto(bureau));
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/bureau/change-status/{id}")
    public ResponseEntity<?> changeStatutBureau(@PathVariable("id") Long id) {
        Bureau bureau = parametrageService.activateBureau(id);
        MFPAIResponse response = MFPAIResponse.success(bureau);
        return ResponseEntity.ok().body(response);
    }




    @GetMapping(path = "/bureau/list-pages")
    @Operation(description = "Endpoint de recuperation liste des  Bureau avec des filtres avancés")
    public Response<Object> listPageBureau(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                             @RequestParam(name = "filter", defaultValue = "") String filter,
                                             @RequestParam(name = "statut", defaultValue = "")  Boolean  statut

    )
    {


        return parametrageService.getPageBureau (page, size, filter, statut);
    }


    // IA
    @Operation(summary = "Endpoint pour ajouter une nouvelle ia")
    @PostMapping("/ia/add")
    public ResponseEntity<MFPAIResponse> saveIA(@Valid @RequestBody IADTO dto) {
        IA ia = parametrageService.addIA(dto);
        MFPAIResponse response = MFPAIResponse.success(iaMapper.toDto(ia));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour un ia")
    @PutMapping("/ia/update/{id}")
    public ResponseEntity<MFPAIResponse> updateIA(@PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody IADTO dto) {
        IA ia = parametrageService.updateIA(id, dto);
        MFPAIResponse response = MFPAIResponse.success(iaMapper.toDto(ia));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un ia")
    @GetMapping("/ia/{id}")
    public ResponseEntity<MFPAIResponse> getIA(@PathVariable("id") Long id) {
        IA ia = parametrageService.getIA(id);
        MFPAIResponse response = MFPAIResponse.success(iaMapper.toDto(ia));
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/ia/change-status/{id}")
    public ResponseEntity<?> changeStatutIA(@PathVariable("id") Long id) {
        IA ia    = parametrageService.activateIA(id);
        MFPAIResponse response = MFPAIResponse.success(ia);
        return ResponseEntity.ok().body(response);
    }




    @GetMapping(path = "/ia/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des ias")
    public Response<Object> listPageIA(
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "10") int size,
                        @RequestParam(name = "filter", defaultValue = "") String filter,
                        @RequestParam(name = "statut", defaultValue = "")  Boolean  statut


    )
    {


        return parametrageService.getPageIA(page, size, filter, statut);
    }


    // IEF
    @Operation(summary = "Endpoint pour ajouter une nouvelle ief")
    @PostMapping("/ief/add")
    public ResponseEntity<MFPAIResponse> saveIEF(@Valid @RequestBody IEFDTO dto) {
        IEF ief = parametrageService.addIEF(dto);
        MFPAIResponse response = MFPAIResponse.success(iefMapper.toDto(ief));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour un ief")
    @PutMapping("/ief/update/{id}")
    public ResponseEntity<MFPAIResponse> updateIEF(@PathVariable(value = "id") Long id,
                                                  @Valid @RequestBody IEFDTO dto) {
        IEF ief = parametrageService.updateIEF(id, dto);
        MFPAIResponse response = MFPAIResponse.success(iefMapper.toDto(ief));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un ief")
    @GetMapping("/ief/{id}")
    public ResponseEntity<MFPAIResponse> getIEF(@PathVariable("id") Long id) {
        IEF ief = parametrageService.getIEF(id);
        MFPAIResponse response = MFPAIResponse.success(iefMapper.toDto(ief));
        return ResponseEntity.ok().body(response);
    }



    @PutMapping("/ief/change-status/{id}")
    public ResponseEntity<?> changeStatutIEF(@PathVariable("id") Long id) {
        IEF ief    = parametrageService.activateIEF(id);
        MFPAIResponse response = MFPAIResponse.success(ief);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/ief/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des iefs")
    public Response<Object> listPageIEF(@RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                       @RequestParam(name = "filter", defaultValue = "") String filter,
                                        @RequestParam(name = "statut", defaultValue = "")  Boolean  statut

    )
    {


        return parametrageService.getPageIEF(page, size, filter, statut);
    }




    // Etablissement
    @Operation(summary = "Endpoint pour ajouter une nouvelle Etablissement")
    @PostMapping("/etablissement/add")
    public ResponseEntity<MFPAIResponse> save(@Valid @RequestBody EtablissementDTO dto) {
        Etablissement etablissement = parametrageService.addEtablissement(dto);
        MFPAIResponse response = MFPAIResponse.success(etablissementMapper.toDto(etablissement));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour un etablissement")
    @PutMapping("/etablissement/update/{id}")
    public ResponseEntity<MFPAIResponse> updateEtablissement(@PathVariable(value = "id") Long id,
                                                   @Valid @RequestBody EtablissementDTO dto) {
        Etablissement etablissement = parametrageService.updateEtablissement(id, dto);
        MFPAIResponse response = MFPAIResponse.success(etablissementMapper.toDto(etablissement));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un etablissement")
    @GetMapping("/etablissement/{id}")
    public ResponseEntity<MFPAIResponse> getEtablissement(@PathVariable("id") Long id) {
        Etablissement etablissement = parametrageService.getEtablissement(id);
        MFPAIResponse response = MFPAIResponse.success(etablissementMapper.toDto(etablissement));
        return ResponseEntity.ok().body(response);
    }



    @PutMapping("/etablissement/change-status/{id}")
    public ResponseEntity<?> changeStatutEtablissement(@PathVariable("id") Long id) {
        Etablissement etablissement    = parametrageService.activateEtablissement(id);
        MFPAIResponse response = MFPAIResponse.success(etablissement);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/etablissement/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des etablissements")
    public Response<Object> listPageEtablissement(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                        @RequestParam(name = "filter", defaultValue = "") String filter,
                                      @RequestParam(name = "statut", defaultValue = "")  Boolean  statut

    )
    {

        return parametrageService.getPageEtablissement(page, size, filter, statut);
    }


    // Speciality
    @Operation(summary = "Endpoint pour ajouter une nouvelle Speciality")
    @PostMapping("/speciality/add")
    public ResponseEntity<MFPAIResponse> saveSpeciality(@Valid @RequestBody SpecialityDTO dto) {
        Speciality speciality = parametrageService.addSpeciality(dto);
        MFPAIResponse response = MFPAIResponse.success(specialityMapper.toDto(speciality));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour un Speciality")
    @PutMapping("/speciality/update/{id}")
    public ResponseEntity<MFPAIResponse> updateSpeciality(@PathVariable(value = "id") Long id,
                                                             @Valid @RequestBody SpecialityDTO dto) {
        Speciality speciality = parametrageService.updateSpeciality(id, dto);
        MFPAIResponse response = MFPAIResponse.success(specialityMapper.toDto(speciality));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un Speciality")
    @GetMapping("/speciality/{id}")
    public ResponseEntity<MFPAIResponse> getSpeciality(@PathVariable("id") Long id) {
        Speciality speciality = parametrageService.getSpeciality(id);
        MFPAIResponse response = MFPAIResponse.success(specialityMapper.toDto(speciality));
        return ResponseEntity.ok().body(response);
    }



    @PutMapping("/speciality/change-status/{id}")
    public ResponseEntity<?> changeSpeciality(@PathVariable("id") Long id) {
        Speciality speciality    = parametrageService.activateSpeciality(id);
        MFPAIResponse response = MFPAIResponse.success(speciality);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/speciality/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des Speciality")
    public Response<Object> listPageSpeciality(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                                  @RequestParam(name = "filter", defaultValue = "") String filter,
                                                  @RequestParam(name = "statut", defaultValue = "")  Boolean  statut)
    {

        return parametrageService.getPageSpeciality(page, size, filter, statut);
    }


    // Fonction
    @Operation(summary = "Endpoint pour ajouter une nouvelle Speciality")
    @PostMapping("/fonction/add")
    public ResponseEntity<MFPAIResponse> saveFonction(@Valid @RequestBody FonctionDTO dto) {
        Fonction fonction = parametrageService.addFonction(dto);
        MFPAIResponse response = MFPAIResponse.success(fonctionMapper.toDto(fonction));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour un Speciality")
    @PutMapping("/fonction/update/{id}")
    public ResponseEntity<MFPAIResponse> updateFonction(@PathVariable(value = "id") Long id,
                                                          @Valid @RequestBody FonctionDTO dto) {
        Fonction fonction = parametrageService.updateFonction(id, dto);
        MFPAIResponse response = MFPAIResponse.success(fonctionMapper.toDto(fonction));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un Speciality")
    @GetMapping("/fonction/{id}")
    public ResponseEntity<MFPAIResponse> getFonction(@PathVariable("id") Long id) {
        Fonction fonction = parametrageService.getFonction(id);
        MFPAIResponse response = MFPAIResponse.success(fonctionMapper.toDto(fonction));
        return ResponseEntity.ok().body(response);
    }



    @PutMapping("/fonction/change-status/{id}")
    public ResponseEntity<?> changeFonction(@PathVariable("id") Long id) {
        Fonction fonction    = parametrageService.activateFonction(id);
        MFPAIResponse response = MFPAIResponse.success(fonction);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/fonction/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des Speciality")
    public Response<Object> listPageFonction(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                               @RequestParam(name = "filter", defaultValue = "") String filter,
                                               @RequestParam(name = "statut", defaultValue = "")  Boolean  statut)
    {

        return parametrageService.getPageFonction(page, size, filter, statut);
    }


    // SpecialityEtablissement
    @Operation(summary = "Endpoint pour ajouter une nouvelle SpecialityEtablissement")
    @PostMapping("/speciality-etablissement/add")
    public ResponseEntity<MFPAIResponse> saveSpecialityEtablissement(@Valid @RequestBody SpecialityEtablissementDTO dto) {
        SpecialityEtablissement specialityEtablissement = parametrageService.addSpecialityEtablissement(dto);
        MFPAIResponse response = MFPAIResponse.success(specialityEtablissementMapper.toDto(specialityEtablissement));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour mettre à jour un Speciality")
    @PutMapping("/speciality-etablissement/update/{id}")
    public ResponseEntity<MFPAIResponse> updateSpecialityEtablissement(@PathVariable(value = "id") Long id,
                                                        @Valid @RequestBody SpecialityEtablissementDTO dto) {
        SpecialityEtablissement specialityEtablissement = parametrageService.updateSpecialityEtablissement(id, dto);
        MFPAIResponse response = MFPAIResponse.success(specialityEtablissementMapper.toDto(specialityEtablissement));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un SpecialityEtablissement")
    @GetMapping("/speciality-etablissement/{id}")
    public ResponseEntity<MFPAIResponse> getSpecialityEtablissement(@PathVariable("id") Long id) {
        SpecialityEtablissement specialityEtablissement = parametrageService.getSpecialityEtablissement(id);
        MFPAIResponse response = MFPAIResponse.success(specialityEtablissementMapper.toDto(specialityEtablissement));
        return ResponseEntity.ok().body(response);
    }



    @PutMapping("/speciality-etablissement/change-status/{id}")
    public ResponseEntity<?> changeSpecialityEtablissement(@PathVariable("id") Long id) {
        SpecialityEtablissement specialityEtablissement    = parametrageService.activateSpecialityEtablissement(id);
        MFPAIResponse response = MFPAIResponse.success(specialityEtablissement);
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/speciality-etablissement/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des SpecialityEtablissement")
    public Response<Object> listPageSpecialityEtablissement(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                             @RequestParam(name = "filter", defaultValue = "") String filter,
                                             @RequestParam(name = "statut", defaultValue = "")  Boolean  statut)
    {

        return parametrageService.getPageSpecialityEtablissement(page, size, filter, statut);
    }




    // Speciality
    @Operation(summary = "Endpoint pour ajouter une nouvelle Diplome")
    @PostMapping("/diplome/add")
    public ResponseEntity<MFPAIResponse> saveDiplome(@Valid @RequestBody DiplomeReqDTO dto) {
        Diplomes diplomes = parametrageService.addDiplome(dto);
        MFPAIResponse response = MFPAIResponse.success(diplomeMapper.toDto(diplomes));
        return ResponseEntity.ok().body(response);
    }


    @GetMapping(path = "/diplome/list-pages")
    @Operation(description = "Endpoint de recuperation de la liste des Speciality")
    public Response<Object> listPageDiplomes(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                             @RequestParam(name = "filter", defaultValue = "") String filter,
                                             @RequestParam(name = "statut", defaultValue = "")  Boolean  statut,
                                             @RequestParam(name = "type-diplome", defaultValue = "")  String  typeDiplome)
    {

        return parametrageService.getPageDiplome(page, size, filter, statut,  typeDiplome);

    }

    @GetMapping("diplome/all")
    public ResponseEntity<Page<DiplomeBaseDTO>> getDiplomes(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String label,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<DiplomeBaseDTO> diplomePage = parametrageService.getDiplomesPage(code, label, page, size);
        return ResponseEntity.ok(diplomePage);
    }

}
