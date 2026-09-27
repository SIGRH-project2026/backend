package sn.gainde2000.backenmfpai.mappers.servicepta.parametre;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.parametre.ParametreResponse;


@Mapper(componentModel = "spring")
public interface ParametreMapper {
//  Parametre map(ParametreRequest parametreRequest);
  ParametreResponse mapToParametreResponse(Parametre parametre);
}
