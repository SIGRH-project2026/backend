package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.Permutation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Permutation.PermutationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.IPermutationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IPermutationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Permutation.PermutationRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PermutationResponseDto;

import java.io.FileNotFoundException;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RequestMapping("/permutation/")
@Tag(name = "gestionPermutation", description = "Permet de gérer les permutations entre professeurs et formateurs")
public class PermutationController {

    private final IPermutationService iPermutationService;
    private final IPermutationRepository iPermutationRepository;
    private final PermutationMapper permutationMapper;
    private final IUtilisateur iUtilisateur;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final CentralLevelRepository centralLevelRepository;

    @Operation(
            description = "Ajout demande de permutation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La demande de permutation a été ajouté avec succés!",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"demande de permutation Créè!\", \"payload\" :\"demande de permutation Créè!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    @PostMapping("/add")
    public ResponseEntity<MFPAIResponse> enregistrer(@RequestBody PermutationRequestDto dto){
        Permutation permutation = iPermutationService.addPermutation(dto);
        PermutationResponseDto permutationResponseDto = permutationMapper.toDto(permutation);
        MFPAIResponse response = MFPAIResponse.success(permutationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération de la liste des permutations selon l'utlisateurs ")
    @GetMapping("/list")
    public ResponseEntity<MFPAIResponse> getAllPermutation(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "") String matricule,
            @RequestParam (defaultValue = "") String region,
            @RequestParam (defaultValue = "") String statut,
            @RequestParam (defaultValue = "") String nom,
            @RequestParam (defaultValue = "") String prenom,
            @RequestParam (defaultValue = "") String type,
            @RequestParam (defaultValue = "") String ia,
            @RequestParam (defaultValue = "") String ief,
            @RequestParam (defaultValue = "") String etablissement
    ){

        System.out.println("salam controller");
        Response<Object> permutations = iPermutationService.getAllPermutation(page, size, matricule,region,statut, nom, prenom, type, ia, ief, etablissement);
        MFPAIResponse response = MFPAIResponse.success(permutations);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération d'un professeur ou formateur")
    @GetMapping("/recherche")
    public ResponseEntity<MFPAIResponse> rechercheParMatricule(@RequestParam (defaultValue = "") String matricule){

        DeconcentratedLevel deconcentratedLevel = deconcentratedLevelRepository.findByMatricule(matricule).get();
        MFPAIResponse response = MFPAIResponse.success(deconcentratedLevel);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération de l'utilisateur  connecté")
    @GetMapping("/currentUser")
    public ResponseEntity<MFPAIResponse> getCurrentUser(){
        Utilisateur utilisateur = iUtilisateur.getCurrentUser();
        MFPAIResponse response;
        if(utilisateur.getTypeUser().equals("DEC")){
            DeconcentratedLevel deconcentratedLevel = deconcentratedLevelRepository.findByMatricule(utilisateur.getMatricule()).get();
             response = MFPAIResponse.success(deconcentratedLevel);
        }else{
            // un utilisateur de type "CEN" sans CentralLevel (ex: ADMIN-DRH) : on renvoie l'utilisateur de base
            response = centralLevelRepository.findByEmail(utilisateur.getEmail())
                    .<MFPAIResponse>map(MFPAIResponse::success)
                    .orElseGet(() -> MFPAIResponse.success(utilisateur));
        }
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération d'une permutation")
    @GetMapping("/getOne/{id}")
    public ResponseEntity<MFPAIResponse> getOne(@PathVariable long id){
        PermutationResponseDto permutationResponseDto = iPermutationService.getOnePermutation(id);
        MFPAIResponse response = MFPAIResponse.success(permutationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour les traitements")
    @GetMapping("/traitement")
    public ResponseEntity<MFPAIResponse> traiterPermutation(@RequestParam long idPermutation, @RequestParam String action,@RequestParam String motif,@RequestParam String type){
        PermutationResponseDto permutationResponseDto = iPermutationService.traiterPermutation(idPermutation, action, motif,type);
        MFPAIResponse response = MFPAIResponse.success(permutationResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la génération d'ordre de service permutation")
    @GetMapping("/generate/{id}")
    public ResponseEntity<MFPAIResponse> generatedPermutation(@PathVariable long id) throws JRException, FileNotFoundException {
        System.out.println("entrer controller permutation");
        System.out.println("voir id +++++++ "+id);

        Permutation permutation = iPermutationService.genererPermutationOS(id);
        MFPAIResponse response = MFPAIResponse.success(permutation);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la génération d'imputation ou de bulletin de visite")
    @GetMapping("/generateAllPermutation")
    public ResponseEntity<MFPAIResponse> generatedAllPermutation() throws JRException, FileNotFoundException {
        System.out.println("entrer controller permutation");

        Response<Object>  permutations = iPermutationService.genererPermutationAllOS();
        MFPAIResponse response = MFPAIResponse.success(permutations);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/uploadPermutationOS/{idPermutation}/{idTraiteur}")
    @Operation(description = "Endpoint de traitement d'une mutation")
    public Response<Object> traitementUploadOS(@PathVariable Long idPermutation,
                                                      @PathVariable Long idTraiteur,
                                                      @RequestParam("file") MultipartFile file){
        return  iPermutationService.UploadPermutation(idPermutation,idTraiteur, file);
    }

    @GetMapping("/indicateurs")
    @Operation(description = "Endpoint de recupération des indicateurs de permutations")
    public Response<Object> indicateursPermutation(@RequestParam String codeProfile)  {
        return iPermutationService.indicateurPermutation(codeProfile);
    }
}
