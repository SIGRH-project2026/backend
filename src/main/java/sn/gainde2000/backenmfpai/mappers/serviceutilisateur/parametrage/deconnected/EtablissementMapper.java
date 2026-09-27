package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.deconnected;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.EtablissementDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.IEFDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.deconcentred.EtablissementResDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.deconcentred.IEFResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 07/08/2024-10:23
 * @project backend_mfpai
 */

@Mapper
public interface EtablissementMapper extends EntityMapper<Etablissement, EtablissementDTO, EtablissementResDTO> {

    @Named("ignoreTraining")
    EtablissementResDTO toDtoWithout(Etablissement etablissement);

    default List<EtablissementResDTO> toDtoList(List<Etablissement> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<EtablissementResDTO> toDtoPage(Page<Etablissement> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<EtablissementResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}

