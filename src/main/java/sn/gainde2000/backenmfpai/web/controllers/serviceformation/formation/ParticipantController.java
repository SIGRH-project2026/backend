package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participant;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IParticipantService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final IParticipantService participantService;
    private final ObjectMapper objectMapper;

    public ParticipantController(IParticipantService participantService, ObjectMapper objectMapper) {
        this.participantService = participantService;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Endpoint pour ajouter un nevelle rubrique participant")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<Participant> createParticipant(@RequestPart(name = "participantDTO") String request,
            @RequestPart(name = "file") MultipartFile partic)
            throws JsonMappingException, JsonProcessingException {
        ParticipantDTO participantDTO = objectMapper.readValue(request, ParticipantDTO.class);
        Participant participant = participantService.createParticipant(partic, participantDTO);
        // Gérez la réponse en conséquencer
        return ResponseEntity.ok(participant);
    }

    @GetMapping("/formation/{formationId}")
    public ResponseEntity<Participant> getParticipantByFormationId(@PathVariable Long formationId) {
        Participant participant = participantService.getParticipantByFormationId(formationId);
        if (participant != null) {
            return ResponseEntity.ok(participant);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteParticipant(@PathVariable Long id) {
    // participantService.deleteParticipant(id);
    // return ResponseEntity.ok("Participant supprimé avec succès.");
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deleteParticipant(@PathVariable Long id) {
        participantService.deleteParticipant(id);

        return ResponseEntity.ok(MFPAIResponse.success("Participant supprimée avec succès"));
    }

}
