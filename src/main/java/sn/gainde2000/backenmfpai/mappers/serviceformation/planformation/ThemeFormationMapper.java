package sn.gainde2000.backenmfpai.mappers.serviceformation.planformation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.ThemeFormationDTO;

@Mapper(componentModel = "spring")
public interface ThemeFormationMapper {

    @Mapping(source = "direction", target = "direction")
    @Mapping(source = "responsableSuivi.id", target = "responsableSuiviId")
    @Mapping(target = "profils", source = "profils")
    ThemeFormationDTO toDTO(ThemeFormation themeFormation);

    @Mapping(target = "profils", source = "dto.profils")
    ThemeFormation toEntity(ThemeFormationDTO dto);
}