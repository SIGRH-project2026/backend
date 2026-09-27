package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.StatutCampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IStatutCampagne {
    Response<Object> saveStatutCampagne(StatutCampagneRequestDTO campagneRequestDTO);

    Response<Object>  findByCode(String statutCampagne);

    Response<Object> getAllStatutCampagne();
}
