package sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.StatutCampagne;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.CampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.StatutCampagneRequestDTO;

@Mapper(componentModel = "spring")
public interface StatutCampagneMapper {
    StatutCampagne map(StatutCampagneRequestDTO statutCampagneRequestDTO);
    StatutCampagneRequestDTO map(StatutCampagne  statutCampagne);

}
