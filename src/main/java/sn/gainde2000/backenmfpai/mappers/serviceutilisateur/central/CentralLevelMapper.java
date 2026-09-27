package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.central;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.CentralLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.CentralLevelResDTO;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-12:06
 * @project backend_mfpai
 */

@Mapper
public interface CentralLevelMapper extends EntityMapper<CentralLevel, CentralLevelDTO, CentralLevelResDTO> {
    @Named("ignoreTraining")
    CentralLevelResDTO toDtoWithout(CentralLevel role);

    default List<CentralLevelResDTO> toDtoList(List<CentralLevel> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<CentralLevelResDTO> toDtoPage(Page<CentralLevel> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<CentralLevelResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}

