package sn.gainde2000.backenmfpai.mappers.serviceformation.formation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.FormationDTO;

@Mapper(componentModel = "spring")
public interface FormationMapper {

    @Mapping(source = "themeFormation.direction", target = "direction")
    @Mapping(source = "themeFormation.planFormation", target = "planFormation")
    @Mapping(source = "themeFormation.profils", target = "profils")
    FormationDTO entityToDto(Formation formation);

    Formation dtoToEntity(FormationDTO formationDTO);
}