package sn.gainde2000.backenmfpai.web.controllers.servicepta.parametre;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.mappers.servicepta.parametre.ParametreMapper;
import sn.gainde2000.backenmfpai.services.implementations.servicepta.parametre.ParametreService;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.parametre.ParametreRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.parametre.ParametreResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/pta/parametre")
@Tag(name = "Gestion parametre controller", description = "Permet de gérer les parametre")
public class ParametreController {
  private final ParametreService parametreService;
  private final ParametreMapper parametreMapper;



  @Operation(description = "Création Parametre")
  @PostMapping("/add")
  public ResponseEntity<Response<Object>> saveParametre(@RequestBody ParametreRequest parametreRequest){

    return ResponseEntity.ok(parametreService.saveParametre(parametreRequest));
  }

  @Operation(description = "Modifier Parametre")
  @PostMapping("/edit/{id}")
  public ResponseEntity<Response<Object>> editParametre(@RequestBody ParametreRequest parametreRequest, @PathVariable long id){
    return ResponseEntity.ok(parametreService.editParametre(parametreRequest, id));
  }


  @Operation(description = "recuperation parametre")
  @GetMapping("/find/{id}")
  public ResponseEntity<Response<Object>> findParametre(@PathVariable long id){
    return ResponseEntity.ok(parametreService.findParametre(id));
  }


  @Operation(description = "recuperation parametre - avancée")
  @GetMapping("/findOne/{id}")
  public ResponseEntity<MFPAIResponse> getParametre(@PathVariable Long id){
    Parametre parametre = parametreService.getParametre(id);

    MFPAIResponse response = MFPAIResponse.success(parametreMapper.mapToParametreResponse(parametre));
    return ResponseEntity.ok().body(response);
  }

  @Operation(description = "Activer ou desactiver parametre")
  @GetMapping("/activer-ou-desactiver/{id}")
  public ResponseEntity<Response<Object>> enableOrDisableParametre(@PathVariable long id){
    return ResponseEntity.ok(parametreService.enableOrDisableParametre(id));
  }

  @Operation(description = "Listes parametre")
  @GetMapping("/all")
  public ResponseEntity<Response<Object>> getAllParametre(
          @RequestParam(name = "page", defaultValue = "0") int page,
          @RequestParam(name = "size", defaultValue = "10") int size,
          @RequestParam(name = "filter", defaultValue = "") String filter,
          @RequestParam(name = "numero", defaultValue = "") String numero,
          @RequestParam(name = "libelle", defaultValue = "") String libelle,
          @RequestParam(name = "date", defaultValue = "") String date,
          @RequestParam(name = "responsableActivite", defaultValue = "") String responsableActivite,
          @RequestParam(name = "statut", defaultValue = "") String statut,
          @RequestParam(name = "divisions", defaultValue = "") String divisions
  ) {
    return ResponseEntity.ok().body(parametreService.getAllParametre(page, size, filter,numero, libelle, date,responsableActivite,statut,divisions));
  }



  @GetMapping("/indicateurs")
  public ResponseEntity<MFPAIResponse> listIndicateurs() {
    List<ParametreResponse> parametres = parametreService.getListParametre();

    MFPAIResponse response = MFPAIResponse.success(parametres);

    return ResponseEntity.ok().body(response);
  }
}
