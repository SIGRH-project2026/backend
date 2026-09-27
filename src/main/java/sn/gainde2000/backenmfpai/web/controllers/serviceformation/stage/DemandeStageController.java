package sn.gainde2000.backenmfpai.web.controllers.serviceformation.stage;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.NonAutoRiserDemandeStage;
import sn.gainde2000.backenmfpai.services.interfaces.files.IFile;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IDemandeStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AuthorizedDemandeStageDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DemandeStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.ImputationRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/demandeStage")
@Tag(name = "GestionDemandeStageController", description = "Permet de gérer les demandes de stage")
public class DemandeStageController {
    private final IDemandeStage iDemandeStage;
    private final IFile iFile;
    @PostMapping(value = "/file/upload/{idAppartenance}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadFile(
            @RequestParam("files") List<MultipartFile> files, @PathVariable long idAppartenance) {
        return ResponseEntity.ok(iFile.uploadFiles_(files,idAppartenance));
    }

    @Operation(description = "Création Demande de stage")
    @PostMapping("/add")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> saveDemandeStage(@RequestBody DemandeStageRequest demandeStageRequest) {
        return ResponseEntity.ok().body(iDemandeStage.saveDemandeStage(demandeStageRequest));
    }

    @Operation(description = "Verifier si l'utilisateur connecter est DFC bureau")
    @GetMapping("/isCurrentUserInDFRBureau")
    public ResponseEntity<Response<Object>> isCurrentUserInDFRBureau() {
        return ResponseEntity.ok().body(iDemandeStage.isCurrentUserInDFRBureau());
    }


    @Operation(description = "Autoriser Demande de stage")
    @GetMapping("/autoriserDemandeStage/{id}")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> autoriserDemande(@PathVariable long id) {
        return ResponseEntity.ok().body(iDemandeStage.autoriserDemande(id));
    }

    @Operation(description = "Ne pas autoriser de stage")
    @PostMapping("/nePasautoriserDemandeStage")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> nePasAutoriserDemande(@RequestBody NonAutoRiserDemandeStage nonAutoRiserDemandeStage) {
        return ResponseEntity.ok().body(iDemandeStage.nePasAutoriserDemande(nonAutoRiserDemandeStage.getIdDemandeStage(), nonAutoRiserDemandeStage.getAvis()));
    }

    @Operation(description = "Listes Demandes Stages")
    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> getAllDemandeStage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "filter", defaultValue = "") String filter,
            @RequestParam(name = "numero", defaultValue = "") String numero,
            @RequestParam(name = "prenomDemandeur", defaultValue = "") String prenomDemandeur,
            @RequestParam(name = "nomDemandeur", defaultValue = "") String nomDemandeur,
            @RequestParam(name = "discipline", defaultValue = "") String discipline,
            @RequestParam(name = "dateDebut", defaultValue = "") String dateDebut,
            @RequestParam(name = "dateFin", defaultValue = "") String dateFin,
            @RequestParam(name = "statut", defaultValue = "") String statut
            ) {
        return ResponseEntity.ok().body(iDemandeStage.getAllDemandeStage(page, size, filter,numero, prenomDemandeur, nomDemandeur, discipline,dateDebut,dateFin,statut));
    }


    @Operation(description = "Details demande stage")
    @PostMapping("/edit-demande-stage/{id}")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> editDemandeStage(@PathVariable long id, @RequestBody DemandeStageRequest demandeStageRequest){
        return ResponseEntity.ok().body(iDemandeStage.editDemandeStage(id, demandeStageRequest));
    }

    @Operation(description = "Details demande stage")
    @GetMapping("/detail-demande-stage/{id}")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> detailDemandeStage(@PathVariable long id){
        return ResponseEntity.ok().body(iDemandeStage.detailDemandeStage(id));
    }

    @Operation(description = "Imputation demande stage")
    @PostMapping("/imputation-demande-stage/{id}")
//    @PreAuthorize("hasAnyAuthority('Chef-division','Chef-bureau','Agent-bureau')")
    public ResponseEntity<Response<Object>> imputationDemandeStage(@PathVariable long id, @RequestBody ImputationRequest imputationRequest){
        return ResponseEntity.ok().body(iDemandeStage.imputationDemandeStage(id, imputationRequest));
    }


    @Operation(description = "Auhtorisation Stage")
    @PostMapping("/autorisation-demande-stage")
    public ResponseEntity<Response<Object>> authorisationStage(@RequestBody AuthorizedDemandeStageDTO authorizedDemandeStageDTO){
        return ResponseEntity.ok().body(iDemandeStage.authorisationStage(authorizedDemandeStageDTO));
    }



    @Operation(description = "Nombre de demande de stage enregistre, autoriser, non autoriser")
    @GetMapping("/nombre-demande-autoriser-non-autoriser-enregistrer")
    public ResponseEntity<Response<Object>> nombreDemandeStageAutoriserNonAutoriserEnregistrer(){
        return ResponseEntity.ok().body(iDemandeStage.nombreDemandeStageAutoriserNonAutoriserEnregistrer());
    }
}
