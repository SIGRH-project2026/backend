package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.Mutation;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Mutation.Imutation;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.MutationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.TraitementMutationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.FileNotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mutations")
@Tag(name = "Gestion des mutations", description = "Endpoint permettant de gérer les demandes de mutations")

public class MutationController {
    private final Imutation imutation;
    @GetMapping(path = {"/list/{userId}"})
    @Operation(description = "Endpoint de récupération de l'ensemble des mutations")
    public Response<Object> list(@PathVariable Long userId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "")String statutMutation,
                                 @RequestParam(defaultValue = "") String region,
                                 @RequestParam(defaultValue = "") String ia,
                                 @RequestParam(defaultValue = "") String ief,
                                 @RequestParam(defaultValue = "") String etablissement,
                                 @RequestParam(defaultValue = "") String bureau,
                                 @RequestParam(defaultValue = "") String direction,
                                 @RequestParam(defaultValue = "") String division,
                                 @RequestParam(defaultValue = "") String service,
                                 @RequestParam(defaultValue = "") String numeroRef,
                                 @RequestParam(defaultValue = "false") boolean pourTraitement,
                                 @RequestParam(defaultValue = "") String filtre

                                 ) {

        return imutation.listMutation(page, pageSize, statutMutation,region, ia, ief, etablissement,
                                     bureau,   direction,  division, service,
                                     numeroRef,userId, pourTraitement, filtre);
    }
    @PostMapping("/create")
    @Operation(description = "Endpoint de soumission de demande mutation.")
    public Response<Object> createMutation(@RequestBody MutationDTO mutation) {
        return imutation.demandeMutation(mutation);
    }
    @PatchMapping("/traitement/{idMutation}")
    @Operation(description = "Endpoint de traitement d'une mutation")
    public Response<Object> traitementMutation(@PathVariable Long idMutation,
                                               @RequestParam(name = "bordereau", required = false) MultipartFile bordereau,
                                               @RequestPart(name = "TraitementMutation") String traitementMutationDTO,
                                               @RequestParam(name = "dossierSigne", required = false) MultipartFile dossierSigne) throws JsonProcessingException {

        return  imutation.traitementMutation(idMutation, bordereau, traitementMutationDTO, dossierSigne);
    }
    @PatchMapping("/traitement/valider/{idMutation}/{idTraiteur}")
    @Operation(description = "Endpoint de traitement d'une mutation")
    public Response<Object> traitementMutationValider(@PathVariable Long idMutation,
                                                      @PathVariable Long idTraiteur,
                                                      @RequestParam("file") MultipartFile file){
        return  imutation.validerMutation(idMutation,idTraiteur, file);
    }

    @GetMapping("/getOne/{idMutation}")
    @Operation(description = "Endpoint de récupération d'une mutation")
    public Response<Object> getOneMutation(@PathVariable Long idMutation){
        return imutation.getOneMutation(idMutation);
    }
    @PatchMapping("/update/{idMutation}")
    @Operation(description = "Endpoint de modificatio de mutation")
    public  Response<Object> updateMutation(@PathVariable Long idMutation,
                                            @RequestBody MutationDTO mutationDTO){
        return imutation.updateMutation(idMutation, mutationDTO);
    }
    @GetMapping("/generateOS/{allMutationAccepted}/{idMutation}")
    @Operation(description = "Endpoint de génération de OS")
    public Response<Object> generateOS(@PathVariable boolean allMutationAccepted, @PathVariable Long idMutation) throws JRException, FileNotFoundException {
        return imutation.genererMutation(allMutationAccepted, idMutation);
    }
    @GetMapping("/indicateurs")
    @Operation(description = "Endpoint de recupération des indicateurs de mutations")
    public Response<Object> indicateursMutation(@RequestParam String codeProfile)  {
        return imutation.indicateurMutation(codeProfile);
    }
}
