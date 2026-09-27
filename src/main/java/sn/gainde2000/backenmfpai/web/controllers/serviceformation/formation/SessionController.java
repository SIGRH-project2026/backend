package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Session;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.ISessionService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.SessionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final ISessionService sessionService;

    public SessionController(ISessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Operation(summary = "Endpoint pour ajouter une nouvelle session")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MFPAIResponse> addSession(
            @RequestPart(name = "file") MultipartFile file,
            @RequestParam(name = "formationId") Long formationId,
            @RequestParam(name = "commentaire") String commentaire,
            @RequestParam(name = "dateDebut") Date dateDebut,
            @RequestParam(name = "dateFin") Date dateFin) {

        SessionDTO sessionDTO = new SessionDTO();

        Session sessionEntity = sessionService.createSession(file, dateFin, dateDebut, commentaire,
                formationId);

        MFPAIResponse response = MFPAIResponse.success(sessionEntity);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{formationId}")
    public ResponseEntity<MFPAIResponse> getSessionByFormationId(@PathVariable Long formationId) {
        List<Session> sessions = sessionService.getSessionByFormationId(formationId);
        if (!sessions.isEmpty()) {
            MFPAIResponse response = MFPAIResponse.success(sessions);
            return ResponseEntity.ok().body(response);
        } else {
            // Retournez une réponse avec un statut 404 Not Found si aucune session
            // n'est trouvée
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok(MFPAIResponse.success("Session supprimée avec succès"));
    }

}
