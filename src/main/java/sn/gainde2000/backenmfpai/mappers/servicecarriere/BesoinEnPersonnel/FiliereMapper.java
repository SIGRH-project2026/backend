
package sn.gainde2000.backenmfpai.mappers.servicecarriere.BesoinEnPersonnel;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.Filiere;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.FiliereDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel.FiliereRDTO;

import java.util.List;

@Mapper
public interface FiliereMapper extends EntityMapper<Filiere, FiliereDTO, FiliereRDTO> {
    Filiere toEntity(FiliereDTO filiereDTO);

    FiliereRDTO toDto(Filiere filiere);

    List<FiliereRDTO> toDtoList(List<Filiere> filiereList);

}
