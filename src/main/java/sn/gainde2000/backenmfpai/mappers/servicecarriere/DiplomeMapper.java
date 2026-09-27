package sn.gainde2000.backenmfpai.mappers.servicecarriere;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DiplomeRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.AgentResponseDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DiplomeResponseDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bsdieme
 */
@Mapper
public interface DiplomeMapper extends EntityMapper<Diplome, DiplomeRequestDto, DiplomeResponseDto> {

    @Named("ignoreTraining")
    DiplomeResponseDto toDtoWithout(Diplome role);

    default List<DiplomeResponseDto> toDtoList(List<Diplome> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<DiplomeResponseDto> toDtoPage(Page<Diplome> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<DiplomeResponseDto> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
