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
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PlanningFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IOffreTechniqueFinanciereService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IPlanningFormationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.OffreTechniqueFinanciereDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.PlanningFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

@RestController
@RequestMapping("/api/planningformation")
public class PlanningFormationController {

    private final IPlanningFormationService planningFormationService;
    private final ObjectMapper objectMapper;
    private final IUtilisateur iUtilisateur;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;

    public PlanningFormationController(IUtilisateur iUtilisateur,
            IPlanningFormationService planningFormationService,
            ObjectMapper objectMapper, DeconcentratedLevelRepository deconcentratedLevelRepository) {
        this.planningFormationService = planningFormationService;
        this.objectMapper = objectMapper;
        this.deconcentratedLevelRepository = deconcentratedLevelRepository;
        this.iUtilisateur = iUtilisateur;
    }

    @Operation(summary = "Endpoint pour ajouter un planning à une formation")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<PlanningFormation> addPlanningFormation(
            @RequestPart(name = "files") MultipartFile[] files,
            @RequestParam("planningDTO") String request) throws JsonMappingException, JsonProcessingException {

        PlanningFormationDTO planningFormationDTO = objectMapper.readValue(request,
                PlanningFormationDTO.class);
        // OffreTechniqueFinanciere offreTechniqueFinanciere =
        // IOffreTechniqueFinanciereService
        // .createOffreTechniqueFinanciere(files, offreTechniqueFinanciereDTO);

        PlanningFormation planningFormation = planningFormationService
                .createPlanningFormation(files, planningFormationDTO);
        return ResponseEntity.ok(planningFormation);

    }

    @GetMapping("/by-formation/{formationId}")
    public ResponseEntity<List<PlanningFormationDTO>> getPlanningByFormationId(
            @PathVariable Long formationId) {
        List<PlanningFormationDTO> planningFormation = planningFormationService
                .getPlanningByFormationId(formationId);

        if (planningFormation.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(planningFormation);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MFPAIResponse> deletePlanning(@PathVariable Long id) {
        planningFormationService.deletePlanning(id);
        return ResponseEntity.ok(MFPAIResponse.success("Planning supprimé avec succès"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PlanningFormation>> getAllPlanning() {
        List<PlanningFormation> planningFormation = planningFormationService
                .getAllPlanning();
        return ResponseEntity.ok(planningFormation);
    }

}
