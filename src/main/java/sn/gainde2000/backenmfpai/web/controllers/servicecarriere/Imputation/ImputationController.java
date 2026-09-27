package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.Imputation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Imputation.ImputationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.ImputationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Imputation.IImputation;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation.ImputationRequestdto;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationResponseDto;

import java.io.FileNotFoundException;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RequestMapping("/imputation/")
@Tag(name = "gestionDesImputationsEtBulletinsDeVisite", description = "Permet de gérer les imputations et bulletins de visite")
public class ImputationController {

    private final IImputation iImputation;

    private final ImputationMapper imputationMapper;

   /* public ImputationController(IImputation iImputation, ImputationMapper imputationMapper) {
        this.iImputation = iImputation;
        this.imputationMapper = imputationMapper;
    }*/

    @Operation(
            description = "Creation agent",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Le dossier de l'agent a été créé avec succés!",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Agent Créè!\", \"payload\" :\"Agent Créè!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<MFPAIResponse> enregistrer(@RequestBody ImputationRequestdto dto){
        ImputationOuBulletin imputationOuBulletin = iImputation.createimputation(dto);
        ImputationResponseDto imputationResponseDto = imputationMapper.toDto(imputationOuBulletin);
        MFPAIResponse response = MFPAIResponse.success(imputationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération de la liste des imputations ")
    @GetMapping("/list")
    public ResponseEntity<MFPAIResponse> getAllImputation(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "") String region,
            @RequestParam (defaultValue = "") String matricule,
            @RequestParam (defaultValue = "") String nom,
            @RequestParam (defaultValue = "") String prenom,
            @RequestParam (defaultValue = "") String date,
/*
            @RequestParam (defaultValue = "0") long numeroDemande,
*/
            @RequestParam (defaultValue = "") String typeDemande
            ){

        // System.out.println("salam controller");
        Page<ImputationOuBulletin> imputationOuBulletins = iImputation.getAllImputation(page, size, region, matricule, nom, prenom,date,typeDemande);
        MFPAIResponse response = MFPAIResponse.success(imputationOuBulletins);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération de la liste des imputations ")
    @GetMapping("/listDash")
    public ResponseEntity<MFPAIResponse> getAllImputationFromDashbaord(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "") String region,
            @RequestParam (defaultValue = "") String matricule,
            @RequestParam (defaultValue = "") String nom,
            @RequestParam (defaultValue = "") String prenom,
            @RequestParam (defaultValue = "") String date,
/*
            @RequestParam (defaultValue = "0") long numeroDemande,
*/
            @RequestParam (defaultValue = "") String typeDemande
    ){

        // System.out.println("salam controller");
        Page<ImputationOuBulletin> imputationOuBulletins = iImputation.getAllImputationFromDashbaord(page, size, region, matricule, nom, prenom,date,typeDemande);
        MFPAIResponse response = MFPAIResponse.success(imputationOuBulletins);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "EndPoint pour la recherche de l'agent par son matricule pour l'enregistrement d'une imputation")
    @GetMapping("/recherche")
    public ResponseEntity<MFPAIResponse> rechercheParMatricule(@RequestParam (defaultValue = "") String matricule){

        ImputationResponseDto imputationResponseDto = iImputation.recherche(matricule);
        MFPAIResponse response = MFPAIResponse.success(imputationResponseDto);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "EndPoint pour la récupération d'une imputation d'un  par l'ID")
    @GetMapping("/imputation/{id}")
    public ResponseEntity<MFPAIResponse> getOneImputation(@PathVariable long id){
        System.out.println("entrer controller imputation");
        System.out.println("voir id +++++++ "+id);

        ImputationResponseDto imputationResponseDto = iImputation.getOneImputation(id);
        MFPAIResponse response = MFPAIResponse.success(imputationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la suppression d'une imputation set true to IsDeleted variable")
    @DeleteMapping("/imputation/{id}")
    public ResponseEntity<MFPAIResponse> deleteImputation(@PathVariable Long id){
        ImputationResponseDto imputationResponseDto = iImputation.deleteImputation(id);
        MFPAIResponse response = MFPAIResponse.success(imputationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la génération d'imputation ou de bulletin de visite")
    @GetMapping("/generate/{id}")
    public ResponseEntity<MFPAIResponse> generatedImputation(@PathVariable long id) throws JRException, FileNotFoundException {
        System.out.println("entrer controller imputation");
        System.out.println("voir id +++++++ "+id);

        ImputationOuBulletin imputationResponseDto = iImputation.generateimputation(id);
        MFPAIResponse response = MFPAIResponse.success(imputationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/indicateurs")
    @Operation(description = "Endpoint de recupération des indicateurs des imputation et bulletin de visite")
    public Response<Object> indicateursImputation(@RequestParam String codeProfile)  {
        return iImputation.indicateurImputationOrBulletin(codeProfile);
    }

    // À ajouter dans ImputationController.java
@Operation(summary = "EndPoint pour la récupération de mes propres créations (imputations et bulletins que j'ai créés)")
@GetMapping("/mes-creations")
public ResponseEntity<MFPAIResponse> getMesCreations(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String typeDemande) {
    
    Page<ImputationOuBulletin> mesCreations = iImputation.getMesCreations(page, size, typeDemande);
    MFPAIResponse response = MFPAIResponse.success(mesCreations);
    return ResponseEntity.ok().body(response);
}

}
