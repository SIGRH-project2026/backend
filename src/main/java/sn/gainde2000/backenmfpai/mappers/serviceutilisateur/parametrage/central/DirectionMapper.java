package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DirectionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.central.DirectionResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-09:28
 * @project backend_mfpai
 */

@Mapper
public interface DirectionMapper extends EntityMapper<Direction, DirectionDTO, DirectionResDTO> {

    @Named("ignoreTraining")
    DirectionResDTO toDtoWithout(Direction direction);

    default List<DirectionResDTO> toDtoList(List<Direction> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<DirectionResDTO> toDtoPage(Page<Direction> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<DirectionResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}