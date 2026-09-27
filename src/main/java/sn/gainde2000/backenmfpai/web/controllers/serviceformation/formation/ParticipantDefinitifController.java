package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IParticipantDefinitifService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDefinitifDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TableauSuiviDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/participant-definitif")
public class ParticipantDefinitifController {

    private final IParticipantDefinitifService participantDefinitifService;

    @Autowired
    public ParticipantDefinitifController(IParticipantDefinitifService participantDefinitifService) {
        this.participantDefinitifService = participantDefinitifService;
    }

    @PostMapping("/add")
    public ResponseEntity<ParticipantDefinitifDTO> createTableauSuivi(
            @RequestBody ParticipantDefinitifDTO participantDTO) {
        ParticipantDefinitifDTO createdTableauSuivi = participantDefinitifService
                .createParticipantDefinitif(participantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTableauSuivi);
    }

    @GetMapping("/byFormation/{formationId}")
    public ResponseEntity<List<ParticipantDefinitifDTO>> getParticipantDefinitifByFormationId(
            @PathVariable Long formationId) {
        List<ParticipantDefinitifDTO> participantDefinitif = participantDefinitifService
                .getParticipantDefinitifByFormationId(formationId);
        return ResponseEntity.ok(participantDefinitif);
    }

    @GetMapping("/byMatricule")
    public ResponseEntity<List<ParticipantDefinitifDTO>> getParticipantDefinitifByMatricule(
            @RequestParam(defaultValue = "") String matricule) {
        List<ParticipantDefinitifDTO> participantDefinitif = participantDefinitifService
                .getParticipantDefinitifByMatricule(matricule);
        return ResponseEntity.ok(participantDefinitif);
    }

    @GetMapping("/all")
    public ResponseEntity<MFPAIResponse> getAllTableauxSuivi() {

        List<ParticipantDefinitifDTO> participantDefinitif = participantDefinitifService.getAllParticipantDefinitif();

        MFPAIResponse response = MFPAIResponse.success(participantDefinitif);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDefinitifDTO> getTableauSuiviById(@PathVariable Long id) {
        ParticipantDefinitifDTO participantDefinitif = participantDefinitifService.getParticipantDefinitifById(id);
        if (participantDefinitif != null) {
            return ResponseEntity.ok(participantDefinitif);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ParticipantDefinitifDTO> updatePartialParticipantDefinitif(
            @PathVariable Long id,
            @RequestBody ParticipantDefinitifDTO participantDefinitifDTO) {
        try {
            ParticipantDefinitifDTO updatedParticipantDefinitif = participantDefinitifService
                    .updatePartialParticipantDefinitif(id, participantDefinitifDTO);
            return ResponseEntity.ok(updatedParticipantDefinitif);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
