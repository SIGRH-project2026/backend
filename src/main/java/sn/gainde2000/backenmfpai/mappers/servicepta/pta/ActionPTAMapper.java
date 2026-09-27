package sn.gainde2000.backenmfpai.mappers.servicepta.pta;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ActionPTA;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.ActionPTARequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.ActionPTAResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:37
 * @project backend_mfpai
 */

@Mapper
public interface ActionPTAMapper extends EntityMapper<ActionPTA, ActionPTARequestDTO, ActionPTAResponseDTO> {

    @Named("ignoreTraining")
    ActionPTAResponseDTO toDtoWithout(ActionPTA entity);

    default List<ActionPTAResponseDTO> toDtoList(List<ActionPTA> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<ActionPTAResponseDTO> toDtoPage(Page<ActionPTA> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<ActionPTAResponseDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
