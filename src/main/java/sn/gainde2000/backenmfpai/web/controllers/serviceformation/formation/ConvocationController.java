package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Convocation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IConvocationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ConvocationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/convocations")
public class ConvocationController {

    private final IConvocationService convocationService;

    public ConvocationController(IConvocationService convocationService) {
        this.convocationService = convocationService;
    }

    @Operation(summary = "Endpoint pour ajouter une nouvelle convocation")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MFPAIResponse> addConvocation(
            @RequestPart(name = "tdr") MultipartFile tdr,
            @RequestParam(name = "formationId") Long formationId,
            @RequestParam(name = "nom") String nom) {

        ConvocationDTO convocationDTO = new ConvocationDTO();

        Convocation convocationEntity = convocationService.createConvocation(tdr, nom,
                formationId);

        MFPAIResponse response = MFPAIResponse.success(convocationEntity);
        return ResponseEntity.ok().body(response);
    }

    // @GetMapping("/{formationId}")
    // public ResponseEntity<MFPAIResponse>
    // getConvocationByFormationId(@PathVariable Long formationId) {
    // Convocation convocation =
    // convocationService.getConvocationByFormationId(formationId);
    // if (convocation != null) {
    // MFPAIResponse response = MFPAIResponse.success(convocation);
    // return ResponseEntity.ok().body(response);
    // } else {
    // // Retournez une réponse avec un statut 404 Not Found si la convocation n'est
    // // pas trouvée
    // return ResponseEntity.notFound().build();
    // }
    // }

    @GetMapping("/{formationId}")
    public ResponseEntity<MFPAIResponse> getConvocationByFormationId(@PathVariable Long formationId) {
        List<Convocation> convocations = convocationService.getConvocationByFormationId(formationId);
        if (!convocations.isEmpty()) {
            MFPAIResponse response = MFPAIResponse.success(convocations);
            return ResponseEntity.ok().body(response);
        } else {
            // Retournez une réponse avec un statut 404 Not Found si aucune convocation
            // n'est trouvée
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deleteConvocation(@PathVariable Long id) {
        convocationService.deleteConvocation(id);
        return ResponseEntity.ok(MFPAIResponse.success("Convocation supprimée avec succès"));
    }

}
