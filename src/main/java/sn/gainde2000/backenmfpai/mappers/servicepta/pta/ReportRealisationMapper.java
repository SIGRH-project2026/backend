package sn.gainde2000.backenmfpai.mappers.servicepta.pta;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ReportRealisation;

import sn.gainde2000.backenmfpai.mappers.EntityMapper;

import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.ReportRealisationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.ReportRealisationResDTO;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 28/08/2024-11:26
 * @project backend_mfpai
 */

@Mapper
public interface ReportRealisationMapper extends EntityMapper<ReportRealisation, ReportRealisationDTO, ReportRealisationResDTO> {

    @Named("ignoreTraining")
    ReportRealisationResDTO toDtoWithout(ReportRealisation entity);

    default List<ReportRealisationResDTO> toDtoList(List<ReportRealisation> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<ReportRealisationResDTO> toDtoPage(Page<ReportRealisation> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<ReportRealisationResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
