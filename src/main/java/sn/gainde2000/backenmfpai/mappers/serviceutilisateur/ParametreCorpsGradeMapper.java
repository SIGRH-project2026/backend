package sn.gainde2000.backenmfpai.mappers.serviceutilisateur;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ParametreCorpsGrade;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.ParametreCorpsGradeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.ParametreCorpsGradeRspDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-16:12
 * @project backend_mfpai
 */

@Mapper
public interface ParametreCorpsGradeMapper extends EntityMapper<ParametreCorpsGrade, ParametreCorpsGradeDTO, ParametreCorpsGradeRspDTO> {
    @Named("ignoreTraining")
    ParametreCorpsGradeRspDTO toDtoWithout(ParametreCorpsGrade role);

    default List<ParametreCorpsGradeRspDTO> toDtoList(List<ParametreCorpsGrade> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<ParametreCorpsGradeRspDTO> toDtoPage(Page<ParametreCorpsGrade> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<ParametreCorpsGradeRspDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}