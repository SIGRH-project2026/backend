package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PlanningFormation;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.PlanningFormationDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface IPlanningFormationService {

    PlanningFormation createPlanningFormation(MultipartFile[] files,
            PlanningFormationDTO planningFormationDTO);

    List<PlanningFormationDTO> getPlanningByFormationId(Long formationId);

    void deletePlanning(Long id);

    List<PlanningFormation> getAllPlanning();

}