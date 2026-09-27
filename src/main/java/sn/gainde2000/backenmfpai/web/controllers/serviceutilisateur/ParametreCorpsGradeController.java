package sn.gainde2000.backenmfpai.web.controllers.serviceutilisateur;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ParametreCorpsGrade;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.ParametreCorpsGradeMapper;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.ParametreCorpsGradeService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.ParametreCorpsGradeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/07/2024-10:36
 * @project backend_mfpai
 */


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/parametre-corps")
@Tag(name = "Gestion du Paramétrage Controller", description = "Permet de gérer le paramétrage du corps et grade")
public class ParametreCorpsGradeController {

    private final ParametreCorpsGradeService parametreCorpsGradeService;
    private final ParametreCorpsGradeMapper parametreCorpsGradeMapper;

    @Operation(summary = "Endpoint pour ajouter un corps et grade")
    @PostMapping("/add")
    public ResponseEntity<MFPAIResponse> saveParams(@Valid @RequestBody ParametreCorpsGradeDTO dto) {
        ParametreCorpsGrade param = parametreCorpsGradeService.addParam(dto);
        MFPAIResponse response = MFPAIResponse.success(parametreCorpsGradeMapper.toDto(param));
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Endpoint pour mettre à jour un corps et grade")
    @PutMapping("/update/{id}")
    public ResponseEntity<MFPAIResponse> updateParam(@PathVariable(value = "id") Long id,
                                                            @Valid @RequestBody ParametreCorpsGradeDTO dto) {
        ParametreCorpsGrade param = parametreCorpsGradeService.updateParam(id, dto);
        MFPAIResponse response = MFPAIResponse.success(parametreCorpsGradeMapper.toDto(param));
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "Endpoint pour récupérer un param")
    @GetMapping("/{id}")
    public ResponseEntity<MFPAIResponse> getParam(@PathVariable("id") Long id) {
        ParametreCorpsGrade param = parametreCorpsGradeService.getParam(id);
        MFPAIResponse response = MFPAIResponse.success(parametreCorpsGradeMapper.toDto(param));
        return ResponseEntity.ok().body(response);
    }



    @GetMapping(path = "/list-pages")
    @Operation(description = "Endpoint de recuperation de l'ensemble des params avec des filtres avancés")
    public Response<Object> listPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           @RequestParam(name = "filter", defaultValue = "") String filter,
                                           @RequestParam(name = "libelleCorps", defaultValue = "") String libelleCorps,
                                           @RequestParam(name = "libelleGrade", defaultValue = "")String libelleGrade,
                                           @RequestParam(name = "libelleSpecialite", defaultValue = "")String libelleSpecialite
                                          )
    {
        return parametreCorpsGradeService.getPageParamAdvanced (page, size, filter,libelleCorps, libelleGrade, libelleSpecialite);
    }



    @PutMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatut(@PathVariable("id") Long id) {
        ParametreCorpsGrade parametreCorpsGrade = parametreCorpsGradeService.activate(id);
        MFPAIResponse response = MFPAIResponse.success(parametreCorpsGrade);
        return ResponseEntity.ok().body(response);
    }

}
