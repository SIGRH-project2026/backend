package sn.gainde2000.backenmfpai.mappers.serviceformation.stage;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DemandeStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.stage.DemandeStageResponse;

@Mapper(componentModel = "spring")
public interface DemandeStageMapper {
    DemandeStage map(DemandeStageRequest demandeStageRequest);
    DemandeStageRequest mapToDemandeStageRequest(DemandeStage demandeStage);

    DemandeStageResponse mapToDemandeStageResponse(DemandeStage demandeStage);
}
