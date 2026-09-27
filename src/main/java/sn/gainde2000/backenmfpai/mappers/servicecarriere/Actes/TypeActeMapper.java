package sn.gainde2000.backenmfpai.mappers.servicecarriere.Actes;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.TypeActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface TypeActeMapper {

    TypeActe toEntity(TypeActeDTO typeActeDTO);
}
