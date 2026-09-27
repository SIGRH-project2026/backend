package sn.gainde2000.backenmfpai.web.controllers.serviceformation.expressionbesoin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin.IStatutCampagne;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.StatutCampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/statutCampagne")
@Tag(name = "GestionStatutCampagne", description = "Permet de gérer les statut des campagnes")
public class StatutCampagneController {
    private final IStatutCampagne iStatutCampagne;

    @Operation(description = "Création Statut campagne")
    @PostMapping("/add")
    public ResponseEntity<Response<Object>> saveStatutCampagne(@RequestBody StatutCampagneRequestDTO campagneRequestDTO){
        return ResponseEntity.ok().body(iStatutCampagne.saveStatutCampagne(campagneRequestDTO));
    }


    @Operation(description = "Recupèration Statut campagne")
    @GetMapping("/get/{code}")
    public ResponseEntity<Response<Object>> getStatutCampagne(@PathVariable String code){
        return ResponseEntity.ok().body(iStatutCampagne.findByCode(code));
    }


    @Operation(description = "Recupèration Statut campagne")
    @GetMapping("/all")
    public ResponseEntity<Response<Object>> getAllStatutCampagne(){
        return ResponseEntity.ok().body(iStatutCampagne.getAllStatutCampagne());
    }

}
