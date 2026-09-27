package sn.gainde2000.backenmfpai.mappers.servicecarriere;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.PieceJointes;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.PieceJointesDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PieceJointesReponseDTO;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PieceJointesMapper {

    PieceJointes toEntity(PieceJointesDTO pieceJointesDTO);
    PieceJointesReponseDTO toDto(PieceJointes pieceJointes);

    List<PieceJointesReponseDTO> toDtoList(List<PieceJointes> pieceJointes);
}
