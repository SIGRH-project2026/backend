package sn.gainde2000.backenmfpai.web.controllers.serviceformation.stage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.DisciplineRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IDisciplineStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DisciplineStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/disciplineStage")
public class DisciplineStageController {

    private final IDisciplineStage iDisciplineStage;

    @Operation(description = "Liste demande stage")
    @GetMapping("/all")
    public ResponseEntity<Response<Object>> getAllDisciplineStage(){
        return ResponseEntity.ok(iDisciplineStage.getAllDisciplineStage());
    }

    @Operation(description = "Enregistrer une discipline")
    @PostMapping("/all")
    public ResponseEntity<Response<Object>> saveDisciplineSTage(@RequestBody DisciplineStageRequest disciplineStageRequest){
        return ResponseEntity.ok(iDisciplineStage.saveDisciplineSTage(disciplineStageRequest));
    }

}
