package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participant;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IRapportService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.RapportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/rapports")
public class RapportController {

    private final IRapportService rapportService;
    private final ObjectMapper objectMapper;

    public RapportController(IRapportService rapportService, ObjectMapper objectMapper) {
        this.rapportService = rapportService;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Endpoint pour ajouter un nouveau rapport")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<Rapport> addRapport(@RequestPart(name = "pieces") MultipartFile[] files,
            @RequestPart(name = "pv", required = false) MultipartFile[] pv,
            @RequestParam("rapportDTO") String request) throws JsonMappingException, JsonProcessingException {

        RapportDTO rapportDTO = objectMapper.readValue(request, RapportDTO.class);
        Rapport rapport = rapportService.createRapport(files, rapportDTO, pv);
        return ResponseEntity.ok(rapport);

    }

    @GetMapping("/by-formation/{formationId}")
    public ResponseEntity<Rapport> getRapportByFormationId(@PathVariable Long formationId) {
        // Récupérer le rapport par ID de formation en utilisant votre service
        Rapport rapport = rapportService.getRapportByFormationId(formationId);

        // Vérifier si le rapport existe
        if (rapport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retourner le rapport avec le code de statut OK
        return ResponseEntity.ok(rapport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deleteRapport(@PathVariable Long id) {
        rapportService.deleteRapport(id);
        return ResponseEntity.ok(MFPAIResponse.success("Participant supprimée avec succès"));
    }

}
