package sn.gainde2000.backenmfpai.mappers.servicecarriere.Actes;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.TypeAADTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.TypeActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.TypeAAResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface TypeAAMapper {
    TypeAA toEntity(TypeAADTO typeAADTO);

    TypeAAResponseDTO toDto(TypeAA typeAA);
    List<TypeAAResponseDTO> toDtoList(List<TypeAA> typeAAList);
}
