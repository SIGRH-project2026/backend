package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.deconnected;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.IADTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.deconcentred.IAResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 07/08/2024-10:23
 * @project backend_mfpai
 */

@Mapper
public interface IAMapper extends EntityMapper<IA, IADTO, IAResDTO> {

    @Named("ignoreTraining")
    IAResDTO toDtoWithout(IA ia );

    default List<IAResDTO> toDtoList(List<IA> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<IAResDTO> toDtoPage(Page<IA> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<IAResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}

