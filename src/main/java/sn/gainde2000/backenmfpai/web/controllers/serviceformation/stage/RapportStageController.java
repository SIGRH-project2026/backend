package sn.gainde2000.backenmfpai.web.controllers.serviceformation.stage;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.services.interfaces.files.IFile;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IRapportStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.RapportStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import javax.ws.rs.POST;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/rapportStage")
public class RapportStageController {
    private final IRapportStage iRapportStage;
    private final IFile iFile;


    @PostMapping("/add")
    public ResponseEntity<Response<Object>>   saveRapportStage(@RequestBody RapportStageRequest rapportStageRequest){
        return ResponseEntity.ok(iRapportStage.saveRapportStage(rapportStageRequest));
    }

    @PostMapping(value = "/file/upload/{idAppartenance}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadFile(
            @RequestParam("file") MultipartFile file, @PathVariable long idAppartenance) {
        return ResponseEntity.ok(iFile.uploadFilesForRapport(file,idAppartenance));
    }
}
