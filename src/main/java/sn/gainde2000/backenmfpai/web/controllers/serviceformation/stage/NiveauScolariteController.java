package sn.gainde2000.backenmfpai.web.controllers.serviceformation.stage;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.INiveauScolarite;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/niveauScolarite")
public class NiveauScolariteController {
    private final INiveauScolarite iNiveauScolarite;


    @GetMapping("all")
    public ResponseEntity<Response<Object>> getAllNiveauScolarite(){
        return  ResponseEntity.ok(iNiveauScolarite.getAllNiveauScolarite());
    }
}
