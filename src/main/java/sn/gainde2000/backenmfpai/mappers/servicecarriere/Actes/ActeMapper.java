package sn.gainde2000.backenmfpai.mappers.servicecarriere.Actes;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnel;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelRDTO;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ActeMapper {
    Acte toEntity(ActeDTO acteDTO);
    ActeResponseDTO toDto(Acte acte);
    List<ActeResponseDTO> toDtoList(List<Acte> actes);


}
