package sn.gainde2000.backenmfpai.mappers.servicepta.pta;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.PlanDeTravail;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.PlanDeTravailRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.PlanDeTravailResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:45
 * @project backend_mfpai
 */

@Mapper
public interface PlanDeTravailMapper extends EntityMapper<PlanDeTravail, PlanDeTravailRequestDTO, PlanDeTravailResponseDTO> {

    @Named("ignoreTraining")
    PlanDeTravailResponseDTO toDtoWithout(PlanDeTravail entity);

    default List<PlanDeTravailResponseDTO> toDtoList(List<PlanDeTravail> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<PlanDeTravailResponseDTO> toDtoPage(Page<PlanDeTravail> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<PlanDeTravailResponseDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
