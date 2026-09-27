package sn.gainde2000.backenmfpai.commons.utils.mail;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class mailController {
    private final MailService mailService;
    @PostMapping("/sentMailWithPJ/{fileName:.+}")
    @Operation(description = "Endpoint d'envoi de mail avec Piece jointe.'")
    public void getOneMutation(@RequestBody MailInfosDTO mailInfosDTO, @PathVariable String fileName){
         mailService.sendMailWithPJ(mailInfosDTO,fileName);
    }
}
