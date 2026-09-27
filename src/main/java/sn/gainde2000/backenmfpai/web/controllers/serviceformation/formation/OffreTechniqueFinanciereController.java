package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.OffreTechniqueFinanciere;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IOffreTechniqueFinanciereService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.OffreTechniqueFinanciereDTO;

import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/offreTechniqueFinanciere")
public class OffreTechniqueFinanciereController {

    private final IOffreTechniqueFinanciereService offreTechniqueFinanciereService;
    private final ObjectMapper objectMapper;
    private final IUtilisateur iUtilisateur;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;

    public OffreTechniqueFinanciereController(IUtilisateur iUtilisateur,
            IOffreTechniqueFinanciereService offreTechniqueFinanciereService,
            ObjectMapper objectMapper, DeconcentratedLevelRepository deconcentratedLevelRepository) {
        this.offreTechniqueFinanciereService = offreTechniqueFinanciereService;
        this.objectMapper = objectMapper;
        this.deconcentratedLevelRepository = deconcentratedLevelRepository;
        this.iUtilisateur = iUtilisateur;
    }

    @Operation(summary = "Endpoint pour ajouter une nouvelle offre technique et financière")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<OffreTechniqueFinanciere> addOffreTechniqueFinanciere(
            @RequestPart(name = "pieces") MultipartFile[] files,
            @RequestParam("offreDTO") String request) throws JsonMappingException, JsonProcessingException {

        OffreTechniqueFinanciereDTO offreTechniqueFinanciereDTO = objectMapper.readValue(request,
                OffreTechniqueFinanciereDTO.class);
        // OffreTechniqueFinanciere offreTechniqueFinanciere =
        // IOffreTechniqueFinanciereService
        // .createOffreTechniqueFinanciere(files, offreTechniqueFinanciereDTO);

        OffreTechniqueFinanciere offreTechniqueFinanciere = offreTechniqueFinanciereService
                .createOffreTechniqueFinanciere(files, offreTechniqueFinanciereDTO);
        return ResponseEntity.ok(offreTechniqueFinanciere);

    }

    @GetMapping("/by-formation/{formationId}")
    public ResponseEntity<List<OffreTechniqueFinanciereDTO>> getOffreTechniqueFinanciereByFormationId(
            @PathVariable Long formationId) {
        List<OffreTechniqueFinanciereDTO> offreTechniqueFinancieres = offreTechniqueFinanciereService
                .getOffreTechniqueFinanciereByFormationId(formationId);

        if (offreTechniqueFinancieres.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(offreTechniqueFinancieres);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deleteOffreTechniqueFinanciere(@PathVariable Long id) {
        offreTechniqueFinanciereService.deleteOffreTechniqueFinanciere(id);
        return ResponseEntity.ok(MFPAIResponse.success("Offre supprimée avec succès"));
    }

    @Operation(summary = "EndPoint pour la récupération de l'utilisateur  connecté")
    @GetMapping("/currentUser")
    public ResponseEntity<MFPAIResponse> getCurrentUser() {
        Utilisateur utilisateur = iUtilisateur.getCurrentUser();
        // DeconcentratedLevel deconcentratedLevel = deconcentratedLevelRepository
        // .findByMatricule(utilisateur.getMatricule()).get();
        MFPAIResponse response = MFPAIResponse.success(utilisateur);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OffreTechniqueFinanciere>> getAllOffreTechniqueFinanciere() {
        List<OffreTechniqueFinanciere> offreTechniqueFinancieres = offreTechniqueFinanciereService
                .getAllOffreTechniqueFinanciere();
        return ResponseEntity.ok(offreTechniqueFinancieres);
    }

    @PutMapping("/{id}/update-status")
    public ResponseEntity<OffreTechniqueFinanciereDTO> updateOffreTechniqueStatus(
            @PathVariable Long id,
            @RequestParam("newStatutOffreCode") String newStatutOffreCode) {
        OffreTechniqueFinanciereDTO updatedOffre = offreTechniqueFinanciereService.updateOffreTechniqueStatus(id,
                newStatutOffreCode);
        return ResponseEntity.ok(updatedOffre);
    }

}
