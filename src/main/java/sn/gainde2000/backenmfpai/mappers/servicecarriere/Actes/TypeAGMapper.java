package sn.gainde2000.backenmfpai.mappers.servicecarriere.Actes;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAG;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.TypeAADTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.TypeAGDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.TypeAAResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.TypeAGResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface TypeAGMapper {
    TypeAG toEntity(TypeAGDTO typeAGDTO);

    TypeAGResponseDTO toDto(TypeAG typeAG);
    List<TypeAAResponseDTO> toDtoList(List<TypeAG> typeAGList);
}
