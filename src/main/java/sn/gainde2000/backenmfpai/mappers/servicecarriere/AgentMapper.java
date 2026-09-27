package sn.gainde2000.backenmfpai.mappers.servicecarriere;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.AgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.AgentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bsdieme
 */
@Mapper
public interface AgentMapper extends EntityMapper<Agent, AgentRequestDto, AgentResponseDto> {

    @Named("ignoreTraining")
    AgentResponseDto toDtoWithout(Agent role);

    default List<AgentResponseDto> toDtoList(List<Agent> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<AgentResponseDto> toDtoPage(Page<Agent> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<AgentResponseDto> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }


}
