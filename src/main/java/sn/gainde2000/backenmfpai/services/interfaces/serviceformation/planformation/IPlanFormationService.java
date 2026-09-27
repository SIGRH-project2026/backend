package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.planformation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationModificationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.planformation.PlanFormationResDTO;

public interface IPlanFormationService {
    PlanFormation savePlanFormation(MultipartFile[] files, PlanFormationDTO dto);

    Page<PlanFormation> getPagePlanFormation(int page, int size, String sortBy, boolean sortByDescending);

    PlanFormation getPlanFormationById(Long id);

    PlanFormation modifyPlanFormation(Long id, PlanFormationModificationDTO modificationDTO);

    PlanFormation changePlanFormationStatus(Long id, String statutCode);

    /*by baba*/
    Response<Object> getPagePlanFormationEnCours(int page, int size, String sortBy, boolean sortByDescending);
    /*fin*/

}