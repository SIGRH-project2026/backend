package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin;

import jakarta.servlet.http.HttpServletRequest;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.CampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.time.LocalDate;

public interface ICampagne {
    Response<Object> saveCampagne(CampagneRequestDTO campagneRequestDTO, HttpServletRequest request);

    Response<Object> editCampagne(CampagneRequestDTO campagneRequestDTO, long id);

    Response<Object> deleteCampagne(long id);

    Response<Object> getAllCampagne(int page,int  size, String filter, String nom, String dateDebut, String dateFin);

    Response<Object> startCampagne(long id, HttpServletRequest request);

    Response<Object> stopCampagne(long id, HttpServletRequest request);

    Response<Object> getCampagne(long id);

    Response<Object> getExpressionDeBesoinByCampagne(int page, int size, String filter, String statut, String besoin, String prenomDemandeur, String nomDemandeur, String date, long id);

    Response<Object> searchCampagne(String nom, LocalDate dateDebut, LocalDate dateFin);

  Response<Object> getExpressionDeBesoinByCampagneForExport(long id);
}
