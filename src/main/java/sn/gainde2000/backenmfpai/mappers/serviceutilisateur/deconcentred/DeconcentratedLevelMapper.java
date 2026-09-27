
package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.deconcentred;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DeconcentratedLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.DeconcentratedLevelRespDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-12:06
 * @project backend_mfpai
 */

@Mapper
public interface   DeconcentratedLevelMapper extends EntityMapper<DeconcentratedLevel, DeconcentratedLevelDTO, DeconcentratedLevelRespDTO> {
    @Named("ignoreTraining")
    DeconcentratedLevelRespDTO toDtoWithout(DeconcentratedLevel role);

    default List<DeconcentratedLevelRespDTO> toDtoList(List<DeconcentratedLevel> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<DeconcentratedLevelRespDTO> toDtoPage(Page<DeconcentratedLevel> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<DeconcentratedLevelRespDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}

