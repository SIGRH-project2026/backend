package sn.gainde2000.backenmfpai.mappers.serviceutilisateur;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Diplomes;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DiplomeReqDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.DiplomeResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 21/08/2024-11:00
 * @project backend_mfpai
 */

@Mapper
public interface DiplomesMapper extends EntityMapper<Diplomes, DiplomeReqDTO, DiplomeResDTO> {

    @Named("ignoreTraining")
    DiplomeResDTO toDtoWithout(Diplomes diplome);

    default List<DiplomeResDTO> toDtoList(List<Diplomes> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<DiplomeResDTO> toDtoPage(Page<Diplomes> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<DiplomeResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
