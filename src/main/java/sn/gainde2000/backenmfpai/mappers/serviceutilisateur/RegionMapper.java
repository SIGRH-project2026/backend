package sn.gainde2000.backenmfpai.mappers.serviceutilisateur;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.RegionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.RegionResTDO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-09:28
 * @project backend_mfpai
 */

@Mapper
public interface RegionMapper extends EntityMapper<Region, RegionDTO, RegionResTDO> {

    @Named("ignoreTraining")
    RegionResTDO toDtoWithout(Region region);

    default List<RegionResTDO> toDtoList(List<Region> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<RegionResTDO> toDtoPage(Page<Region> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<RegionResTDO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}