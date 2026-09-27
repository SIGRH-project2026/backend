package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PvExamen;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IPvExamenService;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/pvexamens")
public class PvExamenController {
    private final IPvExamenService pvExamenService;

    public PvExamenController(IPvExamenService pvExamenService) {
        this.pvExamenService = pvExamenService;
    }

    @Operation(summary = "Endpoint pour ajouter un pv d'examen")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MFPAIResponse> addPvExamen(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam(name = "formationId") Long formationId) {

        PvExamen pvExamenEntity = pvExamenService.createPvExamen(file,
                formationId);

        MFPAIResponse response = MFPAIResponse.success(pvExamenEntity);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{formationId}")
    public ResponseEntity<MFPAIResponse> getPvExamenByFormationId(@PathVariable Long formationId) {
        List<PvExamen> pvExamens = pvExamenService.getPvExamenByFormationId(formationId);
        if (!pvExamens.isEmpty()) {
            MFPAIResponse response = MFPAIResponse.success(pvExamens);
            return ResponseEntity.ok().body(response);
        } else {
            // Retournez une réponse avec un statut 404 Not Found si aucun pv
            // n'est trouvée
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deletePvExamen(@PathVariable Long id) {
        pvExamenService.deletePvExamen(id);
        return ResponseEntity.ok(MFPAIResponse.success("PV supprimée avec succès"));
    }

}
