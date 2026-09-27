package sn.gainde2000.backenmfpai.web.controllers.servicepta.pta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.*;
import sn.gainde2000.backenmfpai.mappers.servicepta.pta.*;
import sn.gainde2000.backenmfpai.services.interfaces.servicepta.pta.PTAService;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:33
 * @project backend_mfpai
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/pta")
@Tag(name = "Gestion des PTA Controller", description = "Permet de gérer le PTA")
public class PTAController {

    private final PTAService ptaService;
    private final PlanDeTravailMapper planDeTravailMapper;
    private final SubActionPTAMapper subActionPTAMapper;
    private final ActionPTAMapper actionPTAMapper;
    private final ResultPTAMapper resultPTAMapper;
    private final ModeCalculMapper modeCalculMapper;
    private final ObjectMapper objectMapper;
    private final ReportRealisationMapper reportRealisationMapper;

    @Operation(summary = "Endpoint pour ajouter un nouveau action")
    @PostMapping("/action-pta/add")
    public ResponseEntity<MFPAIResponse> saveAction(@Valid @RequestBody ActionPTARequestDTO dto) {
        ActionPTA actionPTA =  ptaService.addActionPTA(dto);
        MFPAIResponse response = MFPAIResponse.success(actionPTAMapper.toDto(actionPTA));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour mettre à jour  une action")
    @PutMapping("/action-pta/update/{id}")
    public ResponseEntity<MFPAIResponse> updateAction(@PathVariable(name = "id")Long id ,@Valid @RequestBody ActionPTARequestDTO dto) {
        ActionPTA actionPTA =  ptaService.updateActionPTA(id, dto);
        MFPAIResponse response = MFPAIResponse.success(actionPTAMapper.toDto(actionPTA));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour ajouter un nouveau resultat d'une action existant")
    @PostMapping("/result-action/add")
    public ResponseEntity<MFPAIResponse> saveResult(@Valid @RequestBody ResultPTARequestDTO dto) {
        ResultPTA resultPTA =  ptaService.addResultPTA(dto);
        MFPAIResponse response = MFPAIResponse.success(resultPTAMapper.toDto(resultPTA));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour ajouter un nouveau resultat d'une action existant")
    @PutMapping("/result-action/update/{id}")
    public ResponseEntity<MFPAIResponse> updateResult(@PathVariable(name = "id")Long id ,@Valid @RequestBody ResultPTARequestDTO dto) {
        ResultPTA resultPTA =  ptaService.updateResultPTA(id, dto);
        MFPAIResponse response = MFPAIResponse.success(resultPTAMapper.toDto(resultPTA));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour ajouter un nouveau action avec un resultat")
    @PostMapping("/action-single-result/add")
    // @PreAuthorize("hasRole('ADMIN-DRH')")
    public ResponseEntity<MFPAIResponse> saveActionSingleResult(@Valid @RequestBody ActionPTARequestSingleDTO dto) {
        ActionPTA actionPTA =  ptaService.addActionPTASingle(dto);
        MFPAIResponse response = MFPAIResponse.success(actionPTAMapper.toDto(actionPTA));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour ajouter un nouveau sous action")
    @PostMapping("/action-sub-result/add")
    public ResponseEntity<MFPAIResponse> saveSubAction(@Valid @RequestBody SubActionPTAReqDTO dto) {
        SubActionPTA subActionPTA =  ptaService.addSubActionPTA(dto);
        MFPAIResponse response = MFPAIResponse.success(subActionPTAMapper.toDto(subActionPTA));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour ajouter un nouveau sous action")
    @PutMapping("/action-sub-result/update/{id}")
    public ResponseEntity<MFPAIResponse> updateSubAction(@PathVariable(name = "id")Long id   , @Valid @RequestBody SubActionPTAReqDTO dto) {
        SubActionPTA subActionPTA =  ptaService.updateSubActionPTA(id, dto);
        MFPAIResponse response = MFPAIResponse.success(subActionPTAMapper.toDto(subActionPTA));
        return ResponseEntity.ok().body(response);
    }




    @Operation(summary = "Endpoint pour ajouter un pta")
    @PostMapping("/initial-plan-travail/add")
    public ResponseEntity<MFPAIResponse> saveInitialPTA(@Valid @RequestBody InitialPTARequestDTO dto) {
        PlanDeTravail planDeTravail =  ptaService.addInitialPTA(dto);
        MFPAIResponse response = MFPAIResponse.success(planDeTravailMapper.toDto(planDeTravail));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour lister des plans de travail")
    @GetMapping(path = "/list-pta-page")
    public Response<Object> getPTAWithFilterAdvanced(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "filter", defaultValue = "") String filter,
                                                           @RequestParam(value = "numPta", defaultValue = "")String numPta,
                                                           @RequestParam(value = "libelle", defaultValue = "") String libelle,
                                                           @RequestParam(value = "responssable", defaultValue = "")String responssable,
                                                           @RequestParam(value = "division", defaultValue = "") String division,
                                                           @RequestParam(value = "debut", defaultValue = "") String debut,
                                                           @RequestParam(value = "fin", defaultValue = "") String fin) {

        return  ptaService.getPTAWithFilterAdvanced(page, size, filter, numPta, libelle, responssable, division, debut, fin);
    }


    @Operation(summary = "Endpoint pour lister des plans de travail")
    @GetMapping(path = "/list-sub-action/{id}")
    public Response<Object> getListSubAction(@PathVariable("id") Long id,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "size", defaultValue = "10") int size,
                                                     @RequestParam(value = "filter", defaultValue = "") String filter) {

        return  ptaService.getSubAction(id, page, size, filter);
    }




    @Operation(summary = "Endpoint pour récupérer un plan de travail")
    @GetMapping("/{id}")
    public ResponseEntity<MFPAIResponse> getPlanDetravaile(@PathVariable("id") Long id) {
        PlanDeTravail planDeTravail = ptaService.getPlanDeTravail(id);
        MFPAIResponse response = MFPAIResponse.success(planDeTravailMapper.toDto(planDeTravail));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer une action ")
    @GetMapping("/action-pta/{id}")
    public ResponseEntity<MFPAIResponse> getActionPTA(@PathVariable("id") Long id) {
        ActionPTA actionPTA = ptaService.getActionPTA(id);
        MFPAIResponse response = MFPAIResponse.success(actionPTAMapper.toDto(actionPTA));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour récupérer un resultat d'une action donnée ")
    @GetMapping("/result-action/{id}")
    public ResponseEntity<MFPAIResponse> getActionResult(@PathVariable("id") Long id) {
        ResultPTA resultPTA = ptaService.getResultPTA(id);
        MFPAIResponse response = MFPAIResponse.success(resultPTAMapper.toDto(resultPTA));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer la list des  action du pta ")
    @GetMapping("/action/list/{id}")
    public ResponseEntity<MFPAIResponse> getAllActionPTA(@PathVariable Long id) {
        List<ActionPTA> actionPTAS = ptaService.getListActionPTA(id);
        MFPAIResponse response = MFPAIResponse.success(actionPTAS);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour supprimer la list des  action du pta ")
    @GetMapping("/action/delete/{id}")
    public ResponseEntity<MFPAIResponse> deleteActionPTA(@PathVariable Long id) {
          ptaService.getListActionPTA(id);
        MFPAIResponse response = MFPAIResponse.success("supprimé");
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour récupérer la list des  resultat d'une action donnée ")
    @GetMapping("/result-action/list/{id}")
    public Response<Object> getAllResultPTA(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @PathVariable Long id) {
      return ptaService.getAllResultPTA(page,size,id);


    }


    @Operation(summary = "Endpoint pour ajouter un nouveau  un mode de calcul")
    @PostMapping("/mode-calcul/add")
    public ResponseEntity<MFPAIResponse> saveModeCalcul(@Valid @RequestBody ModeCalculRequestDTO dto) {
        ModeCalcul modeCalcul =  ptaService.addModeCalcul(dto);
        MFPAIResponse response = MFPAIResponse.success(modeCalculMapper.toDto(modeCalcul));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour récupérer un mode de calcul")
    @GetMapping("/mode-calcul/{id}")
    public ResponseEntity<MFPAIResponse> getModeCalcul(@PathVariable("id")Long id) {
        ModeCalcul modeCalcul =  ptaService.getModeCalcul(id);
        MFPAIResponse response = MFPAIResponse.success(modeCalculMapper.toDto(modeCalcul));
        return ResponseEntity.ok().body(response);
    }





    @Operation(summary = "Endpoint pour récupérer un resultat d'une action donnée ")
    @GetMapping("/action-sub-result/{id}")
    public ResponseEntity<MFPAIResponse> getSubResult(@PathVariable("id") Long id) {
        SubActionPTA subActionPTA = ptaService.getSubActionPTA(id);

        MFPAIResponse response = MFPAIResponse.success(subActionPTAMapper.toDto(subActionPTA));
        return ResponseEntity.ok().body(response);
    }



   /* @Operation(summary = "Endpoint pour ajouter un nouveau réalisateur")
    @PostMapping(value = "/report/add", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.TEXT_PLAIN_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MFPAIResponse> addReport(
            @RequestPart(name = "files") MultipartFile[] files,
            @RequestPart("realisationDTO") String request) throws JsonProcessingException {

        ReportRealisationDTO realisationDTO = objectMapper.readValue(request,
                ReportRealisationDTO.class);

        ReportRealisation reportRealisation = ptaService
                .addReportRealisation(files, realisationDTO);

        MFPAIResponse response = MFPAIResponse.success(reportRealisationMapper.toDto(reportRealisation));
        return ResponseEntity.ok().body(response);

    }

    */
   @Operation(summary = "Endpoint pour ajouter un report de réalisation")
    @PostMapping(value = "/report/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MFPAIResponse> addReport(
            @ModelAttribute ReportRealisationRequestDTO requestDTO) throws JsonProcessingException {

        ReportRealisationDTO realisationDTO = objectMapper.readValue(requestDTO.getReportRealisationDTO(), ReportRealisationDTO.class);
        ReportRealisation reportRealisation = ptaService.addReportRealisation(requestDTO.getFiles(), realisationDTO);

        MFPAIResponse response = MFPAIResponse.success(reportRealisationMapper.toDto(reportRealisation));
        return ResponseEntity.ok().body(response);
    }



    @Operation(summary = "Endpoint pour récupérer un report de réalisation")
    @GetMapping("/report/{id}")
    public ResponseEntity<MFPAIResponse> getReportRealisation(@PathVariable("id")Long id) {
        ReportRealisation reportRealisation =  ptaService.getReportRealisation(id);
        MFPAIResponse response = MFPAIResponse.success(reportRealisation);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/indicateurs")
    @Operation(description = "Endpoint de recupération des indicateurs de pta")
    public Response<Object> indicateurPTA()  {
        return ptaService.getStaPTA();
    }




}
