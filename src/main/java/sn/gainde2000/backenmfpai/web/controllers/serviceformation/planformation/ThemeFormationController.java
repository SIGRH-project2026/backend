package sn.gainde2000.backenmfpai.web.controllers.serviceformation.planformation;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation.IThemeFormationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.ThemeFormationDTO;

@RestController
@RequestMapping("/themeformations")
@RequiredArgsConstructor
public class ThemeFormationController {

    private final IThemeFormationService themeFormationService;

    @PostMapping(value = "/add")
    public ResponseEntity<ThemeFormationDTO> saveThemeFormation(@RequestBody ThemeFormationDTO dto) {
        ThemeFormationDTO savedDTO = themeFormationService.saveThemeFormation(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    @GetMapping("/byPlanFormation/{planFormationId}")
    public ResponseEntity<List<ThemeFormationDTO>> getThemeFormationsByPlanFormationId(
            @PathVariable Long planFormationId) {
        List<ThemeFormationDTO> themeFormationDTOs = themeFormationService
                .getThemeFormationsByPlanFormationId(planFormationId);
        return ResponseEntity.ok(themeFormationDTOs);
    }

    // Endpoint pour récupérer un thème de formation par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ThemeFormationDTO> getThemeFormationById(@PathVariable Long id) {
        ThemeFormationDTO themeFormationDTO = themeFormationService.getThemeFormationById(id);

        if (themeFormationDTO != null) {
            return ResponseEntity.ok(themeFormationDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/by-direction/{directionId}")
    // public ResponseEntity<List<CentralLevel>> getUsersByDirectionId(@PathVariable
    // Long directionId) {
    // List<CentralLevel> users =
    // themeFormationService.getUsersByDirectionId(directionId);
    // return ResponseEntity.ok(users);
    // }
}
