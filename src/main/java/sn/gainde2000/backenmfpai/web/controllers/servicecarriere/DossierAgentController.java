package sn.gainde2000.backenmfpai.web.controllers.servicecarriere;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Avancement;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.exceptions.MFPAIResponse;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.DossierAgentMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.IDossierAgentRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IDossierAgent;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DossierAgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DossierAgentResponseDto;
import java.util.Map;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;

/**
 * @author bsdieme
 */

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/dossieragent/")
@Tag(name = "gestionDossierAgentController", description = "Permet de gérer les dossiers des agents")
public class DossierAgentController {

    @Autowired
    private IDossierAgent iDossierAgent;

    @Autowired
    private IUtilisateur iUtilisateur;

    @Autowired
    private DossierAgentMapper dossierAgentMapper;

    @Autowired
    private IDossierAgentRepository dossierAgentRepository;

    @Operation(description = "Creation agent", responses = {
            @ApiResponse(responseCode = "200", description = "Le dossier de l'agent a été créé avec succés!", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Agent Créè!\", \"payload\" :\"Agent Créè!\"}"),
            }))
    })
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN-DRH', 'Directeur-DRH', 'Chef-division-dgcaa', 'Chef-bureau-dgcaa', 'Agent-bureau-dgcaa')")
    public ResponseEntity<MFPAIResponse> createDossierAgent(@RequestBody DossierAgentRequestDto dto)
            throws IOException {
        DossierAgent dossierAgent = iDossierAgent.createDossier(dto);
        DossierAgentResponseDto dossierAgentResponseDto = dossierAgentMapper.toDto(dossierAgent);
        int i = 0, j = 0, k = 0;
        while (i < dossierAgentResponseDto.getDiplomes().size()) {
            if (dossierAgent.getDiplomes().get(i).getPieceJointes() == null
                    && !dossierAgent.getDiplomes().get(i).getIsDeleted()) {
                dossierAgentResponseDto.getDiplomesNoPiecejointes().add(dossierAgentResponseDto.getDiplomes().get(i));
            }
            i++;
        }

        while (j < dossierAgentResponseDto.getSituationAdministrative().size()) {
            if (dossierAgent.getSituationAdministrative().get(j).getPieceJointes() == null
                    && !dossierAgent.getSituationAdministrative().get(j).getIsDeleted()) {
                dossierAgentResponseDto.getSituationAdministrativeNoPiecesjointes()
                        .add(dossierAgentResponseDto.getSituationAdministrative().get(j));
            }
            j++;
        }

        while (k < dossierAgentResponseDto.getEtatCivil().size()) {
            if (dossierAgent.getEtatCivil().get(k).getPiecejointes() == null
                    && !dossierAgent.getEtatCivil().get(k).getIsDeleted()) {
                dossierAgentResponseDto.getEtatCivilsNoPiecesjointes()
                        .add(dossierAgentResponseDto.getEtatCivil().get(k));
            }
            k++;
        }

        System.out.println("data retourner 3 === " + dossierAgentResponseDto);
        MFPAIResponse response = MFPAIResponse.success(dossierAgentResponseDto);
        System.out.println("data response === " + response);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération de la liste des Dossiers ")
    @GetMapping("/dossier/list")
    public ResponseEntity<MFPAIResponse> getAllDossierAgent(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String adresse,
            @RequestParam(defaultValue = "") String matricule,
            @RequestParam(defaultValue = "") String nom,
            @RequestParam(defaultValue = "") String prenom) {

        Page<DossierAgent> dossierAgentPage = iDossierAgent.getAllDossierAgent(page, size, adresse, matricule, nom,
                prenom);

        MFPAIResponse response = MFPAIResponse.success(dossierAgentPage);
        System.out.println(" list dossier agent success ici ##############");
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la suppression d'un Dossiers set true to dosIsDeleted variable")
    @DeleteMapping("/dossier/{id}")
    public ResponseEntity<MFPAIResponse> deleteDossierAgent(@RequestParam Long id) {
        DossierAgent dossierAgent = iDossierAgent.deleteDossier(id);
        MFPAIResponse response = MFPAIResponse.success(dossierAgent);
        return ResponseEntity.ok().body(response);

    }

    @Operation(summary = "EndPoint pour la recherche de l'agent par son matricule ")
    @GetMapping("/recherche/agent")
    public ResponseEntity<MFPAIResponse> rechercheParMatricule(@RequestParam(defaultValue = "") String matricule) {
        DossierAgentResponseDto dossierAgentResponseDto = iDossierAgent.rechercheDossierAgent(matricule);
        MFPAIResponse response = MFPAIResponse.success(dossierAgentResponseDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "EndPoint pour la récupération du dossier d'un agent par l'ID du dossier ")
    @GetMapping("/dossier/{id}")
    public ResponseEntity<Response> getOneDossierAgent(@PathVariable Long id) {
        System.out.println("entrer controller dossier Agent");
        DossierAgentResponseDto dossierAgent = iDossierAgent.getOneDossierAgent(id);
        // MFPAIResponse response = MFPAIResponse.success(dossierAgent);
        return ResponseEntity.ok(Response.ok().setPayload(dossierAgent).setMessage(""));
    }

    @Operation(summary = "EndPoint pour la récupération des diplomes d'un dossier d'un agent par son matricule ")
    @GetMapping("/dossier/diplomes")
    public ResponseEntity<MFPAIResponse> getDiplomesByMatricule(
            @RequestParam(defaultValue = "") String matricule,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Diplome> diplomes = iDossierAgent.getDiplomesByMatricule(matricule, page, size);
        MFPAIResponse response = MFPAIResponse.success(diplomes);
        return ResponseEntity.ok().body(response);
    }

    /*
     * @Operation(summary =
     * "EndPoint pour la récupération des avancements d'un dossier d'un agent par son matricule "
     * )
     * 
     * @GetMapping("/dossier/avancements")
     * public ResponseEntity<MFPAIResponse> getAvancementsByMatricule(
     * 
     * @RequestParam (defaultValue = "") String matricule,
     * 
     * @RequestParam (defaultValue = "0") int page,
     * 
     * @RequestParam (defaultValue = "10") int size
     * ){
     * List<Avancement> avancements =
     * iDossierAgent.getAvancementsByMatricule(matricule,page,size);
     * MFPAIResponse response = MFPAIResponse.success(avancements);
     * return ResponseEntity.ok().body(response);
     * }
     */

    // @Operation(summary = "EndPoint pour la récupération du dossier d'un agent par
    // l'ID du dossier ")
    // @GetMapping("/current")
    // public ResponseEntity<Response> getCurrentUser(){
    // System.out.println("entrer controller dossier Agent");
    // return
    // ResponseEntity.ok(Response.ok().setPayload(iDossierAgent.getDossierCurrentUser()).setMessage("Recupèration
    // dossier utilisateur connecter"));
    // }

    @Operation(summary = "EndPoint pour la récupération du dossier d'un agent par l'ID du dossier ")
    @GetMapping("/current")
    public ResponseEntity<MFPAIResponse> getCurrentUser() {
        System.out.println("entrer controller dossier Agent");
        try {
            DossierAgentResponseDto dossier = iDossierAgent.getDossierCurrentUser();

            // Vérifier si le dossier existe
            if (dossier.getId() == null || dossier.getId() == 0L) {
                MFPAIResponse response = MFPAIResponse.success(dossier);
                response.setMessage(
                        "Vous n'avez pas encore de dossier. Veuillez contacter l'administration pour en créer un.");
                return ResponseEntity.ok(response);
            }

            MFPAIResponse response = MFPAIResponse.success(dossier);
            response.setMessage("Récupération dossier utilisateur connecté avec succès");
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            System.out.println("Erreur: " + e.getMessage());
            // Utiliser le builder directement
            MFPAIResponse errorResponse = MFPAIResponse.builder()
                    .success(false)
                    .status("UNAUTHORIZED")
                    .message("Vous devez vous connecter pour accéder à cette page")
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
            e.printStackTrace();
            // Utiliser le builder directement
            MFPAIResponse errorResponse = MFPAIResponse.builder()
                    .success(false)
                    .status("INTERNAL_ERROR")
                    .message("Erreur lors de la récupération du dossier")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "Vérifier si l'utilisateur connecté a un dossier")
    @GetMapping("/has-dossier")
    public ResponseEntity<MFPAIResponse> hasDossier() {
        try {
            Utilisateur user = iUtilisateur.getCurrentUser();
            if (user == null) {
                MFPAIResponse errorResponse = MFPAIResponse.builder()
                        .success(false)
                        .status("UNAUTHORIZED")
                        .message("Utilisateur non connecté")
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            DossierAgentResponseDto dossier = iDossierAgent.rechercheDossierAgent(user.getMatricule());
            boolean hasDossier = dossier.getId() != null && dossier.getId() != 0L;

            Map<String, Object> data = Map.of(
                    "hasDossier", hasDossier,
                    "userId", user.getId(),
                    "matricule", user.getMatricule());

            MFPAIResponse response = MFPAIResponse.success(data);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            MFPAIResponse errorResponse = MFPAIResponse.builder()
                    .success(false)
                    .status("ERROR")
                    .message("Erreur lors de la vérification")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @Operation(summary = "EndPoint pour modifier un dossier existant")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN-DRH', 'Directeur-DRH', 'Chef-division-dgcaa', 'Chef-bureau-dgcaa', 'Agent-bureau-dgcaa')")
    public ResponseEntity<MFPAIResponse> updateDossierAgent(
            @PathVariable Long id,
            @RequestBody DossierAgentRequestDto dto) throws IOException {

        try {
            // Vérifier si le dossier existe
            DossierAgentResponseDto existingDossier = iDossierAgent.getOneDossierAgent(id);
            if (existingDossier.getId() == null || existingDossier.getId() == 0L) {
                MFPAIResponse errorResponse = MFPAIResponse.builder()
                        .success(false)
                        .status("NOT_FOUND")
                        .message("Dossier non trouvé")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            // Mettre à jour le dossier
            dto.setId(id);
            DossierAgent updatedDossier = iDossierAgent.createDossier(dto);
            DossierAgentResponseDto responseDto = dossierAgentMapper.toDto(updatedDossier);

            MFPAIResponse response = MFPAIResponse.success(responseDto);
            response.setMessage("Dossier mis à jour avec succès");
            return ResponseEntity.ok(response);

        } catch (EntityNotFoundException e) {
            MFPAIResponse errorResponse = MFPAIResponse.builder()
                    .success(false)
                    .status("NOT_FOUND")
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            MFPAIResponse errorResponse = MFPAIResponse.builder()
                    .success(false)
                    .status("ERROR")
                    .message("Erreur lors de la mise à jour du dossier: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

@Operation(summary = "EndPoint pour la récupération de la liste des Dossiers avec pagination et filtres")
@GetMapping("/admin/list")
@PreAuthorize("hasAnyAuthority('ADMIN-DRH', 'Directeur-DRH', 'Chef-division-dgcaa', 'Chef-bureau-dgcaa', 'Agent-bureau-dgcaa')")
public ResponseEntity<MFPAIResponse> getAllDossierAgents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "") String adresse,
        @RequestParam(defaultValue = "") String matricule,
        @RequestParam(defaultValue = "") String nom,
        @RequestParam(defaultValue = "") String prenom) {

    Page<DossierAgent> dossierAgentPage = iDossierAgent.getAllDossierAgent(page, size, adresse, matricule, nom, prenom);
    MFPAIResponse response = MFPAIResponse.success(dossierAgentPage);
    return ResponseEntity.ok(response);
}

@Operation(summary = "EndPoint pour la suppression logique d'un dossier")
@DeleteMapping("/admin/delete/{id}")
@PreAuthorize("hasAnyAuthority('ADMIN-DRH', 'Directeur-DRH', 'Chef-division-dgcaa', 'Chef-bureau-dgcaa')")
public ResponseEntity<MFPAIResponse> deleteDossier(@PathVariable Long id) {
    try {
        DossierAgent dossierAgent = iDossierAgent.deleteDossier(id);
        
        MFPAIResponse response = MFPAIResponse.success(dossierAgent);
        response.setMessage("Dossier supprimé avec succès");
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("ERROR")
            .message("Erreur lors de la suppression du dossier: " + e.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

// @Operation(summary = "EndPoint pour les statistiques des dossiers")
// @GetMapping("/admin/stats")
// @PreAuthorize("hasAnyRole('ADMIN-DRH', 'Chef-division-dgcaa')")
// public ResponseEntity<MFPAIResponse> getDossierStats() {
//     try {
//         long totalDossiers = dossierAgentRepository.count();
//         long activeDossiers = dossierAgentRepository.countByIsDeleted(false);
//         long deletedDossiers = dossierAgentRepository.countByIsDeleted(true);
        
//         Map<String, Object> stats = new HashMap<>();
//         stats.put("total", totalDossiers);
//         stats.put("active", activeDossiers);
//         stats.put("deleted", deletedDossiers);
        
//         MFPAIResponse response = MFPAIResponse.success(stats);
//         response.setMessage("Statistiques récupérées avec succès");
//         return ResponseEntity.ok(response);
        
//     } catch (Exception e) {
//         MFPAIResponse errorResponse = MFPAIResponse.builder()
//             .success(false)
//             .status("ERROR")
//             .message("Erreur lors de la récupération des statistiques")
//             .build();
//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//     }
// }

@Operation(summary = "EndPoint pour les statistiques des dossiers")
@GetMapping("/admin/stats")
@PreAuthorize("hasAnyAuthority('ADMIN-DRH', 'Directeur-DRH', 'CHEF_DIVISION_CARRIERE')")
public ResponseEntity<MFPAIResponse> getDossierStats() {
    try {
        // Récupérer tous les dossiers
        List<DossierAgent> allDossiers = dossierAgentRepository.findAll();
        
        long totalDossiers = allDossiers.size();
        long activeDossiers = allDossiers.stream()
            .filter(d -> !d.isDeleted())
            .count();
        long deletedDossiers = totalDossiers - activeDossiers;
        
        // Compter les dossiers avec diplômes
        long dossiersWithDiplomes = allDossiers.stream()
            .filter(d -> d.getDiplomes() != null && !d.getDiplomes().isEmpty())
            .count();
        
        // Compter les dossiers avec situation administrative
        long dossiersWithSituation = allDossiers.stream()
            .filter(d -> d.getSituationAdministrative() != null && !d.getSituationAdministrative().isEmpty())
            .count();
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", totalDossiers);
        stats.put("active", activeDossiers);
        stats.put("deleted", deletedDossiers);
        stats.put("withDiplomes", dossiersWithDiplomes);
        stats.put("withSituationAdministrative", dossiersWithSituation);
        
        MFPAIResponse response = MFPAIResponse.success(stats);
        response.setMessage("Statistiques récupérées avec succès");
        return ResponseEntity.ok(response);
        
    } catch (Exception e) {
        e.printStackTrace();
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("ERROR")
            .message("Erreur lors de la récupération des statistiques: " + e.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@Operation(summary = "Récupérer les diplômes d'un dossier")
@GetMapping("/{dossierId}/diplomes")
public ResponseEntity<MFPAIResponse> getDiplomesByDossier(@PathVariable Long dossierId) {
    try {
        DossierAgentResponseDto dossier = iDossierAgent.getOneDossierAgent(dossierId);
        
        MFPAIResponse response = MFPAIResponse.success(dossier.getDiplomes());
        response.setMessage("Diplômes récupérés avec succès");
        return ResponseEntity.ok(response);
        
    } catch (EntityNotFoundException e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("NOT_FOUND")
            .message(e.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (Exception e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("ERROR")
            .message("Erreur lors de la récupération des diplômes")
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@Operation(summary = "Récupérer la situation administrative d'un dossier")
@GetMapping("/{dossierId}/situation-administrative")
public ResponseEntity<MFPAIResponse> getSituationAdministrativeByDossier(@PathVariable Long dossierId) {
    try {
        DossierAgentResponseDto dossier = iDossierAgent.getOneDossierAgent(dossierId);
        
        MFPAIResponse response = MFPAIResponse.success(dossier.getSituationAdministrative());
        response.setMessage("Situation administrative récupérée avec succès");
        return ResponseEntity.ok(response);
        
    } catch (EntityNotFoundException e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("NOT_FOUND")
            .message(e.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (Exception e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("ERROR")
            .message("Erreur lors de la récupération de la situation administrative")
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@Operation(summary = "Récupérer l'état civil d'un dossier")
@GetMapping("/{dossierId}/etat-civil")
public ResponseEntity<MFPAIResponse> getEtatCivilByDossier(@PathVariable Long dossierId) {
    try {
        DossierAgentResponseDto dossier = iDossierAgent.getOneDossierAgent(dossierId);
        
        MFPAIResponse response = MFPAIResponse.success(dossier.getEtatCivil());
        response.setMessage("État civil récupéré avec succès");
        return ResponseEntity.ok(response);
        
    } catch (EntityNotFoundException e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("NOT_FOUND")
            .message(e.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (Exception e) {
        MFPAIResponse errorResponse = MFPAIResponse.builder()
            .success(false)
            .status("ERROR")
            .message("Erreur lors de la récupération de l'état civil")
            .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

@Operation(summary = "Exporter le dossier en PDF")
@GetMapping("/{dossierId}/export-pdf")
@PreAuthorize("hasAnyAuthority('ADMIN-DRH', 'Directeur-DRH', 'CHEF_DIVISION_CARRIERE', 'CHEF_BUREAU_CARRIERE', 'AGENT_BUREAU_CARRIERE')")
public ResponseEntity<byte[]> exportDossierToPdf(@PathVariable Long dossierId) {
    try {
        DossierAgentResponseDto dossier = iDossierAgent.getOneDossierAgent(dossierId);
        
        // Ici vous devrez implémenter la génération du PDF
        // byte[] pdfBytes = pdfGenerator.generateDossierPdf(dossier);
        
        // Pour l'instant, retournez un message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "dossier_" + dossierId + ".pdf");
        
        // return ResponseEntity.ok().headers(headers).body(pdfBytes);
        
        // Temporairement
        return ResponseEntity.ok().build();
        
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

}
