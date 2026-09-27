package sn.gainde2000.backenmfpai.mappers.servicepta.pta;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ResultPTA;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.ResultPTARequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.ResultPTAResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:41
 * @project backend_mfpai
 */

@Mapper
public interface ResultPTAMapper  extends EntityMapper<ResultPTA, ResultPTARequestDTO, ResultPTAResponseDTO> {

    @Named("ignoreTraining")
    ResultPTAResponseDTO toDtoWithout(ResultPTA entity);

    default List<ResultPTAResponseDTO> toDtoList(List<ResultPTA> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<ResultPTAResponseDTO> toDtoPage(Page<ResultPTA> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<ResultPTAResponseDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
