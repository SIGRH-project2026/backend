package sn.gainde2000.backenmfpai.mappers.serviceformation.courrier;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.Courrier;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.courrier.CourrierRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.courrier.CourrierResponse;

@Mapper(componentModel = "spring")
public interface CourrierMapper {
    Courrier map(CourrierRequest courrierRequest);
    CourrierRequest mapToCourrierRequest(Courrier courrier);
    CourrierResponse mapToCourrierResponse(Courrier courrier);
}
