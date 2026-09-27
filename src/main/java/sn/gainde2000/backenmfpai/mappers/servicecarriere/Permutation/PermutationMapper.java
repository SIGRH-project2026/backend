package sn.gainde2000.backenmfpai.mappers.servicecarriere.Permutation;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation.ImputationRequestdto;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Permutation.PermutationRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationResponseDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PermutationResponseDto;

import java.util.List;
import java.util.stream.Collectors;
@Mapper
public interface PermutationMapper extends EntityMapper<Permutation, PermutationRequestDto, PermutationResponseDto> {

    @Named("ignoreTraining")
    PermutationResponseDto toDtoWithout(Permutation role);

    Permutation toEntity(PermutationRequestDto dto);

    PermutationResponseDto toDto(Permutation dos);

    default List<PermutationResponseDto> toDtoList(List<Permutation> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<PermutationResponseDto> toDtoPage(Page<Permutation> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<PermutationResponseDto> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
