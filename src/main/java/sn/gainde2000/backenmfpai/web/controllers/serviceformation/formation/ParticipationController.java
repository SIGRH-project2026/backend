package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IParticipationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    private final IParticipationService participationService;

    @Autowired
    public ParticipationController(IParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/add")
    public ResponseEntity<ParticipationDTO> createParticipation(@RequestBody ParticipationDTO participationDTO) {
        ParticipationDTO createdParticipation = participationService.createParticipation(participationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipation);
    }

    @GetMapping("/byFormation/{formationId}")
    public ResponseEntity<List<ParticipationDTO>> getParticipationsByFormationId(@PathVariable Long formationId) {
        List<ParticipationDTO> participations = participationService.getParticipationsByFormationId(formationId);
        return ResponseEntity.ok(participations);
    }

    @GetMapping("/all")
    public ResponseEntity<MFPAIResponse> getAllParticipations() {


        List<ParticipationDTO> participations = participationService.getAllParticipations();

        MFPAIResponse response = MFPAIResponse.success(participations);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipationDTO> getParticipationById(@PathVariable Long id) {
        ParticipationDTO participation = participationService.getParticipationById(id);
        if (participation != null) {
            return ResponseEntity.ok(participation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}