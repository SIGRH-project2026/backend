
package sn.gainde2000.backenmfpai.mappers.servicecarriere.BesoinEnPersonnel;

import org.mapstruct.Mapper;


import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnel;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelRDTO;

import java.util.List;

@Mapper
public interface BesoinEnPersonnelMapper extends EntityMapper<BesoinEnPersonnel, BesoinEnPersonnelDTO, BesoinEnPersonnelRDTO> {
    BesoinEnPersonnel toEntity(BesoinEnPersonnelDTO besoinEnPersonnelDTO);
    BesoinEnPersonnelRDTO toDto(BesoinEnPersonnel besoinEnPersonnel);
    List<BesoinEnPersonnelRDTO> toDtoList(List<BesoinEnPersonnel> besoinEnPersonnels);
}
