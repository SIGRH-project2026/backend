package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage;

import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DisciplineStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DisciplineStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IDisciplineStage {
    Response<Object> getAllDisciplineStage();

    Response<Object> saveDisciplineSTage(DisciplineStageRequest disciplineStageRequest);
}
