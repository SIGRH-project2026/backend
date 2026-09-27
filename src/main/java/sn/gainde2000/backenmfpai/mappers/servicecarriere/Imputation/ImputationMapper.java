package sn.gainde2000.backenmfpai.mappers.servicecarriere.Imputation;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation.ImputationRequestdto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ImputationMapper extends EntityMapper<ImputationOuBulletin, ImputationRequestdto, ImputationResponseDto> {

    @Named("ignoreTraining")
    ImputationResponseDto toDtoWithout(ImputationOuBulletin role);

    ImputationOuBulletin toEntity(ImputationRequestdto dto);

    ImputationResponseDto toDto(ImputationOuBulletin dos);

    default List<ImputationResponseDto> toDtoList(List<ImputationOuBulletin> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<ImputationResponseDto> toDtoPage(Page<ImputationOuBulletin> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<ImputationResponseDto> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
