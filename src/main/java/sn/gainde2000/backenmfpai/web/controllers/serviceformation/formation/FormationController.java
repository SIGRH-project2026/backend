package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IFormationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.FormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TrainingStatusCountDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequestMapping("/api/formations")
public class FormationController {

    private final ObjectMapper objectMapper;

    @Autowired
    private final IFormationService formationService;

    public FormationController(IFormationService formationService, ObjectMapper objectMapper) {
        this.formationService = formationService;
        this.objectMapper = objectMapper;
    }

    // @PostMapping("/add")
    // public ResponseEntity<FormationDTO> createFormation(@RequestBody FormationDTO
    // formationDTO) {
    // FormationDTO createdFormation =
    // formationService.createFormation(formationDTO);
    // return ResponseEntity.status(HttpStatus.CREATED).body(createdFormation);
    // }

    @Operation(summary = "Endpoint pour ajouter une nouvelle formation")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MFPAIResponse> createFormation(
            @RequestPart(name = "cahierCharge", required = false) MultipartFile cahierCharge,
            @RequestPart(name = "formation") String request) throws JsonProcessingException {

        // FormationDTO formationDTO = new FormationDTO();

        FormationDTO formationDTO = objectMapper.readValue(request, FormationDTO.class);
        Formation tokens = formationService.createFormation(cahierCharge, formationDTO);

        MFPAIResponse response = MFPAIResponse.success(tokens);
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<FormationDTO> getFormationById(@PathVariable Long id) {
        FormationDTO formation = formationService.getFormationById(id);
        return ResponseEntity.ok(formation);
    }

    @GetMapping
    public ResponseEntity<List<FormationDTO>> getAllFormations() {
        List<FormationDTO> formations = formationService.getAllFormations();
        return ResponseEntity.ok(formations);
    }

    @GetMapping("/reference/{reference}")
    public ResponseEntity<FormationDTO> getFormationByReference(@PathVariable String reference) {
        FormationDTO formation = formationService.getFormationByReference(reference);
        return ResponseEntity.ok(formation);
    }

    // Endpoint pour supprimer une formation
    @DeleteMapping("/{formationId}")
    public ResponseEntity<MFPAIResponse> deleteFormation(@PathVariable Long formationId) {
        formationService.deleteFormation(formationId);
        return ResponseEntity.ok(MFPAIResponse.success("Formation supprimée avec succès"));
    }

    // Endpoint pour modifier une formation
    @PutMapping("/{formationId}")
    public ResponseEntity<FormationDTO> updateFormation(@PathVariable Long formationId,
            @RequestBody FormationDTO formationDTO) {
        FormationDTO updatedFormation = formationService.updateFormation(formationId, formationDTO);
        return ResponseEntity.ok(updatedFormation);
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<FormationDTO> updateFormationStatus(
            @PathVariable Long id,
            @RequestParam("newStatutFormationCode") String newStatutFormationCode) {
        FormationDTO updatedFormation = formationService.updateFormationStatus(id, newStatutFormationCode);
        return ResponseEntity.ok(updatedFormation);
    }

    /* by baba dieme */

    @GetMapping("/allformationcontinueordiplomante/{type}")
    public ResponseEntity<List<FormationDTO>> getAllFormationsContinue(@PathVariable("type") String type) {
        List<FormationDTO> formations = formationService.getAllFormationsContinue(type);
        return ResponseEntity.ok(formations);
    }



    /* fin */

    // @GetMapping("/status-counts")
    // public ResponseEntity<TrainingStatusCountDTO> getTrainingStatusCounts() {
    // return ResponseEntity.ok(formationService.getTrainingStatusCounts());
    // }

    @GetMapping("/status-counts")
    public ResponseEntity<TrainingStatusCountDTO> getTrainingStatusCounts() {

        TrainingStatusCountDTO statusCounts = formationService.getTrainingStatusCounts();
        return ResponseEntity.ok(statusCounts);
    }




    @GetMapping(path = "/pages/formations")
    @Operation(description = "Endpoint ")
    public Response<Object> listPageSpecialityEtablissement(@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                                            @RequestParam(name = "filter", defaultValue = "") String filter)
    {

        return formationService.formationByUser(page, size, filter);
    }


    @GetMapping("/allFormationByType/{type}")
    public Response<Object> getAllFormationsByTypeFormation(@PathVariable("type") String type,
                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "10") int size) {

        return  formationService.getAllFormationsByTypeFormation(type, page, size);
    }


}