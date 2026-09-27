package sn.gainde2000.backenmfpai.web.controllers.serviceformation.planformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;

import sn.gainde2000.backenmfpai.exceptions.MFPAIResponse;
import sn.gainde2000.backenmfpai.mappers.serviceformation.planformation.PlanFormationMapper;

import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation.IPlanFormationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationModificationDTO;

import sn.gainde2000.backenmfpai.web.dtos.responses.Response;


// PlanFormationController.java
// ... (Vos annotations, imports, etc.)

@RestController
@RequestMapping("/plan-formation")
@RequiredArgsConstructor
// ... (Autres annotations et configurations nécessaires)
public class PlanFormationController {

    private final IPlanFormationService planFormationService;
    private final PlanFormationMapper planFormationMapper;
    private final ObjectMapper objectMapper;

    // @PostMapping("/add")
    // public ResponseEntity<MFPAIResponse> savePlanFormation(@Valid @RequestBody
    // PlanFormationDTO dto) {
    // PlanFormation planFormation = planFormationService.savePlanFormation(dto);
    // MFPAIResponse response =
    // MFPAIResponse.success(planFormationMapper.toDto(planFormation));
    // return ResponseEntity.ok().body(response);
    // }

    @Operation(summary = "Endpoint pour ajouter un nouveau plan de formation")
    @PostMapping(value = "/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MFPAIResponse> addPlanFormation(@RequestPart(name = "files") MultipartFile[] files,
            @RequestPart(name = "planFormationDTOString") String request) throws JsonProcessingException {
        PlanFormationDTO planFormationDTO = objectMapper.readValue(request, PlanFormationDTO.class);
        PlanFormation tokens = planFormationService.savePlanFormation(files, planFormationDTO);

        MFPAIResponse response = MFPAIResponse.success(tokens);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<MFPAIResponse> getPlanFormationListPage(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean sortByDescending) {
        Page<PlanFormation> planFormationPage = planFormationService.getPagePlanFormation(page, size, sortBy,
                sortByDescending);
        MFPAIResponse response = MFPAIResponse.success(planFormationMapper.toDtoPage(planFormationPage));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MFPAIResponse> getPlanFormationById(@PathVariable("id") Long id) {
        PlanFormation planFormation = planFormationService.getPlanFormationById(id);
        MFPAIResponse response = MFPAIResponse.success(planFormationMapper.toDto(planFormation));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/modify")
    public ResponseEntity<MFPAIResponse> modifyPlanFormation(@PathVariable("id") Long id,
            @RequestBody PlanFormationModificationDTO modificationDTO) {
        PlanFormation modifiedPlanFormation = planFormationService.modifyPlanFormation(id, modificationDTO);
        MFPAIResponse response = MFPAIResponse.success(modifiedPlanFormation);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/change-status")
    public ResponseEntity<MFPAIResponse> changePlanFormationStatus(@PathVariable("id") Long id,
            @RequestParam("statutCode") String statutCode) {
        PlanFormation planFormation = planFormationService.changePlanFormationStatus(id, statutCode);
        MFPAIResponse response = MFPAIResponse.success(planFormationMapper.toDto(planFormation));
        return ResponseEntity.ok().body(response);
    }

    /*by baba dieme*/

    @GetMapping("/listEnCours")
    public ResponseEntity<MFPAIResponse> getPlanFormationEnCoursListPage(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "id") String sortBy,
                                                                  @RequestParam(defaultValue = "true") boolean sortByDescending) {
        Response<Object> planFormationPage = planFormationService.getPagePlanFormationEnCours(page, size, sortBy,
                sortByDescending);
        MFPAIResponse response = MFPAIResponse.success(planFormationPage);
        return ResponseEntity.ok().body(response);
    }

    /*fin*/

}
