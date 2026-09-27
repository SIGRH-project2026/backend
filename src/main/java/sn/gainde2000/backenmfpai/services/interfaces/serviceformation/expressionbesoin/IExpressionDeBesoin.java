package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin;

import jakarta.servlet.http.HttpServletRequest;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AuthorizedDemandeStageDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinDTO;

import java.util.List;

public interface IExpressionDeBesoin {
    public Response<Object> saveExpressionDeBesoin(ExpressionDeBesoinDTO expressionDeBesoinDTO, long idCampagne, HttpServletRequest request);

    Response<Object> getExpressionDeBesoin(long id);

    public Response<Object> traiterExpressionDeBesoins(String ids,HttpServletRequest request, String themeProvisoire);

    Response<Object> editExpressionDeBesoins(long id, ExpressionDeBesoinDTO expressionDeBesoinDTO);

//  Response<Object> enregisterAutorisationStage(AuthorizedDemandeStageDTO authorizedDemandeStageDTO);
}
