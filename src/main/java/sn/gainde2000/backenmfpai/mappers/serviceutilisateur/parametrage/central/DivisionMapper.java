package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DivisionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.central.DivisionResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-09:28
 * @project backend_mfpai
 */

@Mapper
public interface DivisionMapper extends EntityMapper<Division, DivisionDTO, DivisionResDTO> {

    @Named("ignoreTraining")
    DivisionResDTO toDtoWithout(Division division);

    default List<DivisionResDTO> toDtoList(List<Division> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<DivisionResDTO> toDtoPage(Page<Division> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<DivisionResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}