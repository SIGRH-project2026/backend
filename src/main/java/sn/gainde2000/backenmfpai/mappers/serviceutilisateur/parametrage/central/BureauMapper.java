package sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.BureauDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.central.BureauResDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-09:28
 * @project backend_mfpai
 */

@Mapper
public interface BureauMapper  extends EntityMapper<Bureau, BureauDTO, BureauResDTO> {

    @Named("ignoreTraining")
    BureauResDTO toDtoWithout(Bureau bureau);

    default List<BureauResDTO> toDtoList(List<Bureau> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<BureauResDTO> toDtoPage(Page<Bureau> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<BureauResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
