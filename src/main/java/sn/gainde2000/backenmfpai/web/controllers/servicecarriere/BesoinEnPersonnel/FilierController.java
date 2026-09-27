
package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.BesoinEnPersonnel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.BesoinEnPersonnel.IFiliere;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.FiliereDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filieres")
@Tag(name = "Gestion des filiéres", description = "Endpoint permettant de gérer les filiéres")

public class FilierController {
private  final IFiliere iFiliere;
    @GetMapping( path = {"/list"})
    @Operation(description = "Endpoint de récupération de la liste des filiéres")
    public Response<Object> listFiliere() {
        return iFiliere.listeFiliere();
    }

    @PostMapping("/create")
    @Operation(description = "Endpoint de création de filiéres")
    public  Response<Object> createFiliere(@RequestBody FiliereDTO filiereDTO){
        return  iFiliere.createFiliere(filiereDTO);
    }

}
