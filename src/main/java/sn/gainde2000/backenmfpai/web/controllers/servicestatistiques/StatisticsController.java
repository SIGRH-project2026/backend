package sn.gainde2000.backenmfpai.web.controllers.servicestatistiques;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:33
 * @project backend_mfpai
 */

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/statistiques")
@Tag(name = "Gestion Utilisateur Controller", description = "Permet de gérer le profil de l'utilisateur connecte")
public class StatisticsController {
}
