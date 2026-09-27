package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AttestationStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IAttestionStage {
    Response<Object> saveAttestionStage(AttestationStageRequest attestationStageRequest);
}
