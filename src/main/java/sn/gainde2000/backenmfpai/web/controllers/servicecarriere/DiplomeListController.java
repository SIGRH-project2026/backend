package sn.gainde2000.backenmfpai.web.controllers.servicecarriere;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DiplomeList;
import sn.gainde2000.backenmfpai.exceptions.MFPAIResponse;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IDiplomeList;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/diplomeList/")
@Tag(name = "listeDiplomeDispo", description = "Permet d'avoir une liste de diplomes pour le select list ")
public class DiplomeListController {

    @Autowired
    private IDiplomeList iDiplomeList;



    @Operation(summary = "EndPoint pour la récupération de la liste ")
    @GetMapping("/list")
    public ResponseEntity<MFPAIResponse> getAllDossierAgent(){

        List<DiplomeList> diplomeList = iDiplomeList.getAll();
        MFPAIResponse response = MFPAIResponse.success(diplomeList);
        System.out.println(" list dossier agent success ici ##############");
        return ResponseEntity.ok().body(response);
    }
}
