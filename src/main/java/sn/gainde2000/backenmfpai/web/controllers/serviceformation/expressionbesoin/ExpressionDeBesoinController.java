package sn.gainde2000.backenmfpai.web.controllers.serviceformation.expressionbesoin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin.IExpressionDeBesoin;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinDTO;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/expressionDeBesoin")
@Tag(name = "GestionExpressionDeBesoinController", description = "Permet de gérer les expressions de besoin")
public class ExpressionDeBesoinController {
    private final IExpressionDeBesoin iExpressionDeBesoin;

    @Operation(description = "Création Expression de Besoin")
    @PostMapping("/soumettreExpressionDeBesoin/{idCampagne}")
   // @PreAuthorize("hasAnyAuthority('Chef-division','Directeur-DRH','Chef-service','Chef-EFF','Chef-etablissement','Representant-IA','Representant-IEF')")
    public ResponseEntity<Response<Object>> saveExpressionDeBesoin(@RequestBody ExpressionDeBesoinDTO expressionDeBesoinDTO, @PathVariable long idCampagne, HttpServletRequest request){
        return ResponseEntity.ok().body(iExpressionDeBesoin.saveExpressionDeBesoin(expressionDeBesoinDTO,idCampagne, request));
    }

    @Operation(description = "Recupération Expression de Besoin")
    @GetMapping("/get/{id}")
 //   @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> getExpressionDeBesoin(@PathVariable long id){
        return ResponseEntity.ok().body(iExpressionDeBesoin.getExpressionDeBesoin(id));
    }



    @Operation(description = "Traiter une Expression de Besoin")
    @GetMapping("/traiterExpressionDeBesoins/{ids}/{themeProvisoire}")
   // @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> traiterExpressionDeBesoins(@PathVariable String ids,HttpServletRequest request, @PathVariable String themeProvisoire){
        return ResponseEntity.ok().body(iExpressionDeBesoin.traiterExpressionDeBesoins(ids,request, themeProvisoire));
    }

// @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    @Operation(description = "Modifier une Expression de Besoin")
    @PutMapping("/editExpressionDeBesoins/{id}")
    public ResponseEntity<Response<Object>> editExpressionDeBesoins(@PathVariable long id, @RequestBody ExpressionDeBesoinDTO expressionDeBesoinDTO){
        return ResponseEntity.ok().body(iExpressionDeBesoin.editExpressionDeBesoins(id, expressionDeBesoinDTO));
    }


//    @Operation(description = "Enregistrement autorisation stage")
//    @PostMapping("/enregistrer-autorisation-stage")
//    public ResponseEntity<Response<Object>> enregisterAutorisationStage(@RequestBody AuthorizedDemandeStageDTO authorizedDemandeStageDTO){
//        return ResponseEntity.ok().body(iExpressionDeBesoin.enregisterAutorisationStage(authorizedDemandeStageDTO));
//    }

}
