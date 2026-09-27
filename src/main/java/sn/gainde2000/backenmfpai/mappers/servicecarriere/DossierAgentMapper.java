package sn.gainde2000.backenmfpai.mappers.servicecarriere;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DossierAgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DossierAgentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bsdieme
 */
@Mapper
public interface DossierAgentMapper extends EntityMapper<DossierAgent, DossierAgentRequestDto, DossierAgentResponseDto> {

    @Named("ignoreTraining")
    DossierAgentResponseDto toDtoWithout(DossierAgent role);

    DossierAgent toEntity(DossierAgentRequestDto dto);

    DossierAgentResponseDto toDto(DossierAgent dos);

    default List<DossierAgentResponseDto> toDtoList(List<DossierAgent> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<DossierAgentResponseDto> toDtoPage(Page<DossierAgent> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<DossierAgentResponseDto> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }


}
