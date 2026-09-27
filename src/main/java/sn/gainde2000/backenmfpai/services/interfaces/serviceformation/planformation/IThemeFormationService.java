package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation;

import java.util.List;

import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.ThemeFormationDTO;

public interface IThemeFormationService {

    ThemeFormationDTO saveThemeFormation(ThemeFormationDTO dto);

    ThemeFormationDTO getThemeFormationById(Long id);

    List<ThemeFormationDTO> getThemeFormationsByPlanFormationId(Long planFormationId);

    // List<CentralLevel> getUsersByDirection(String directionCode);

    // Autres méthodes de service
}
