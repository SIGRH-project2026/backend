
package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.BesoinEnPersonnel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.BesoinEnPersonnel.IBesoinEnPersonnel;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.FiliereDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/besoinEnPersonnel")
@Tag(name = "Gestion des besoins en personnel", description = "Endpoint permettant de gérer les besoins en personnels")

public class BesoinEnPersonnelController {
    private final IBesoinEnPersonnel iBesoinEnPersonnel;
    @GetMapping( path = {"/list/{userId}"})
    @Operation(description = "Endpoint de récupération de la liste des besoins en personnel")
    public Response<Object> listBEP(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String matricule,
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "") String etablissement,
            @RequestParam(defaultValue = "") String region,
            @RequestParam(defaultValue = "") String ia,
            @RequestParam(defaultValue = "") String ief,
            @RequestParam(defaultValue = "") String prenom,
            @RequestParam(defaultValue = "") String nom,
            @RequestParam(defaultValue = "0") Long reference

    ) {
        return iBesoinEnPersonnel.listBEP(userId, page, size,statut,matricule, etablissement, region, ia, ief, prenom, nom, reference);
    }

    @GetMapping( path = {"/getone/{idBEP}"})
    @Operation(description = "Endpoint de récupération d'un besoin en personnel'")
    public Response<Object> getOneBEP(@PathVariable Long idBEP) {
        return iBesoinEnPersonnel.getOneBEP(idBEP);
    }

    @PostMapping("/create")
    @Operation(description = "Endpoint de soumission de besoin en personnel")
    public  Response<Object> createBEP(@RequestBody BesoinEnPersonnelDTO besoinEnPersonnelDTO){
            return  iBesoinEnPersonnel.createBEP(besoinEnPersonnelDTO);
    }
    @GetMapping( path = {"/indicateur"})
    @Operation(description = "Endpoint de récupération des indicateurs de BEP'")
    public Response<Object> indicateur(@RequestParam String profileCode) {
        return iBesoinEnPersonnel.indicateurBEP(profileCode);
    }
}

