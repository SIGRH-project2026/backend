package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.RapportStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IRapportStage {
    Response<Object> saveRapportStage(RapportStageRequest rapportStageRequest);
}
