package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.courrier;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.courrier.CourrierRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface ICourrier {

    Response<Object> saveCourrier(CourrierRequest courrierRequest);

    Response<Object> getAllCourrier(int page, int size, String filter,String reference, String typeDemande, String direction, String division, String typeCourrier, String statut);

    Response<Object> verifyIfUserCanCreateCourrier();

    Response<Object> traiterCourrier(Long id);

    Response<Object> listTypeDemandeCourrierByDivisionAndNomTypeCourrier(String divisionCode, String nomTypeCourrier);
    Response<Object> listTypeDemandeCourrier();

    Response<Object> nombreDeCourrierTraiterEtNomTraiter();
}
