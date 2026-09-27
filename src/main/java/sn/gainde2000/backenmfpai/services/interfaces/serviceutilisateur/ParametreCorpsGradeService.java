package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ParametreCorpsGrade;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.ParametreCorpsGradeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-16:15
 * @project backend_mfpai
 */
public interface ParametreCorpsGradeService {

    ParametreCorpsGrade addParam(ParametreCorpsGradeDTO dto);
    ParametreCorpsGrade getParam(Long id);
    ParametreCorpsGrade updateParam(Long id, ParametreCorpsGradeDTO dto);

    ParametreCorpsGrade activate(Long id);

    Response<Object> getPageParamAdvanced(int page, int size, String filter,  String libelleCorps, String libelleGrade,
                                          String libelleSpecialite);
}
