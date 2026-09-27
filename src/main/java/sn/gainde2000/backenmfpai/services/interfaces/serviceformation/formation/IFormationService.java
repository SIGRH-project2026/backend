package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.FormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TrainingStatusCountDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IFormationService {

    Formation createFormation(MultipartFile cahierCharge, FormationDTO formationDTO);
    // Convocation createConvocation(MultipartFile tdr, String nom, Long
    // formationId);

    FormationDTO getFormationById(Long id);
    // Formation getFormationById(Long id);

    List<FormationDTO> getAllFormations();

    FormationDTO getFormationByReference(String reference);

    void deleteFormation(Long formationId);

    FormationDTO updateFormation(Long formationId, FormationDTO formationDTO);

    FormationDTO updateFormationStatus(Long id, String newStatutFormationCode);

    /* by baba dieme */
    List<FormationDTO> getAllFormationsContinue(String type);
    /* fin */

    Response<Object> getAllFormationsByTypeFormation(String type, int page, int size);

    TrainingStatusCountDTO getTrainingStatusCounts();

    Response<Object> formationByUser(int page, int size,String filter);


}
