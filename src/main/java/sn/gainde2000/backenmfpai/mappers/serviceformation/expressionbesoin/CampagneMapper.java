package sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.CampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.CampagneResponseDTO;

@Mapper(componentModel = "spring")
public interface CampagneMapper {
    Campagne map(CampagneRequestDTO campagneRequestDTO);
    CampagneRequestDTO map(Campagne  campagne);

    CampagneResponseDTO mapToCampagneResponseDTO(Campagne  campagne);
}
