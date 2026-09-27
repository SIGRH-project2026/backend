package sn.gainde2000.backenmfpai.web.controllers.serviceformation.stage;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.services.interfaces.files.IFile;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IAttestionStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AttestationStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/attestationStage")
public class AttestationStageController {
    private final IFile iFile;
    private final IAttestionStage iAttestionStage;



    @PostMapping("/add")
    public ResponseEntity<Response<Object>> saveRapportStage(@RequestBody AttestationStageRequest attestationStageRequest){
        return ResponseEntity.ok(iAttestionStage.saveAttestionStage(attestationStageRequest));
    }



    @PostMapping(value = "/file/upload/{idAppartenance}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadFile(
            @RequestParam("file") MultipartFile file, @PathVariable long idAppartenance) {
        return ResponseEntity.ok(iFile.uploadFilesForAttestation(file,idAppartenance));
    }
}
