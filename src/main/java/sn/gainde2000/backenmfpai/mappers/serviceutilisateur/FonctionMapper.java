package sn.gainde2000.backenmfpai.mappers.serviceutilisateur;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.FonctionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.central.FonctionResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 21/08/2024-11:00
 * @project backend_mfpai
 */

@Mapper
public interface FonctionMapper extends EntityMapper<Fonction, FonctionDTO, FonctionResDTO> {

    @Named("ignoreTraining")
    FonctionResDTO toDtoWithout(Fonction region);

    default List<FonctionResDTO> toDtoList(List<Fonction> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<FonctionResDTO> toDtoPage(Page<Fonction> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<FonctionResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
