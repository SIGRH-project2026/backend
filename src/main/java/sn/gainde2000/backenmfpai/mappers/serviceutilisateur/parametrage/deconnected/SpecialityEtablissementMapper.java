package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.deconnected;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.SpecialityEtablissement;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.SpecialityEtablissementDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.deconcentred.SpecialityEtablissementResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/08/2024-16:17
 * @project backend_mfpai
 */

@Mapper
public interface SpecialityEtablissementMapper  extends EntityMapper <SpecialityEtablissement, SpecialityEtablissementDTO, SpecialityEtablissementResDTO>{

    @Named("ignoreTraining")
    SpecialityEtablissementResDTO toDtoWithout(SpecialityEtablissement role);

    default List<SpecialityEtablissementResDTO> toDtoList(List<SpecialityEtablissement> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<SpecialityEtablissementResDTO> toDtoPage(Page<SpecialityEtablissement> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<SpecialityEtablissementResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
