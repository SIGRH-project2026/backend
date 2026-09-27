package sn.gainde2000.backenmfpai.web.controllers.serviceformation.courrier;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.courrier.ICourrier;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.courrier.CourrierRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/courrier")
@Tag(name = "GestionCourrierController", description = "Permet de gérer les courriers")
public class CourrierController {
    private final ICourrier iCourrier;


    @Operation(description = "Création Courrier")
    @PostMapping("/add")
//    @PreAuthorize("hasAnyAuthority('Chef-bureau')")
    public ResponseEntity<Response<Object>> saveCourrier(@RequestBody CourrierRequest courrierRequest) {
        return ResponseEntity.ok().body(iCourrier.saveCourrier(courrierRequest));
    }


    @Operation(description = "Création Courrier")
    @GetMapping("/verifyIfUserCanCreateCourrier")
//    @PreAuthorize("hasAnyAuthority('Chef-bureau')")
    public ResponseEntity<Response<Object>> verifyIfUserCanCreateCourrier() {
        return ResponseEntity.ok().body(iCourrier.verifyIfUserCanCreateCourrier());
    }

    @Operation(description = "Traiter Courrier")
    @GetMapping("/traiter-courrier/{id}")
    public ResponseEntity<Response<Object>> traiterCourrier(@PathVariable Long id){
        return ResponseEntity.ok().body(iCourrier.traiterCourrier(id));
    }




    @Operation(description = "Listes Courriers")
    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau','Agent-bureau-dgcaa','Chef-division-dgcaa','Chef-division-dfc','Chef-bureau-dgcaa','Chef-bureau-dfc','Agent-buerau-dfc')")
    public ResponseEntity<Response<Object>> getAllCourrier(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "reference", defaultValue = "") String reference,
            @RequestParam(name = "typeDemande", defaultValue = "") String typeDemande,
            @RequestParam(name = "direction", defaultValue = "") String direction,
            @RequestParam(name = "division", defaultValue = "") String division,
            @RequestParam(name = "typeCourrier", defaultValue = "") String typeCourrier,
            @RequestParam(name = "statut", defaultValue = "") String statut
    ) {
        return ResponseEntity.ok().body(iCourrier.getAllCourrier(page, size, filter,reference, typeDemande, direction, division,typeCourrier, statut));
    }

    @Operation(description = "Listes types demande de courrier Courrier")
    @GetMapping("/list-type-demande-courrier-by-divison-and-nom-type-courrier/{divisionCode}/{nomTypeCourrier}")
    public ResponseEntity<Response<Object>> listTypeDemandeCourrierByDivisionAndNomTypeCourrier(@PathVariable String divisionCode, @PathVariable String nomTypeCourrier){
        System.out.println("DivisionCode: " + divisionCode);
        System.out.println("NomTypeCourrier: " + nomTypeCourrier);
        return ResponseEntity.ok().body(iCourrier.listTypeDemandeCourrierByDivisionAndNomTypeCourrier(divisionCode,nomTypeCourrier));

    }

    /**
     * list des types de demande de courriers
     * @return
     */
    @Operation(description = "Listes types demande de courrier Courrier")
    @GetMapping("/list-type-demande-courrier")
    public ResponseEntity<Response<Object>> listTypeDemandeCourrier(){
        return ResponseEntity.ok().body(iCourrier.listTypeDemandeCourrier());

    }

    @Operation(description = "Nombre de courrier traites et non traites")
    @GetMapping("/nombre-courrier-traiter-nontraiter")
    public ResponseEntity<Response<Object>> nombreDeCourrierTraiterEtNomTraiter(){
        return ResponseEntity.ok().body(iCourrier.nombreDeCourrierTraiterEtNomTraiter());
    }

}
