package sn.gainde2000.backenmfpai.mappers.servicepta.pta;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ModeCalcul;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.ModeCalculRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.ModeCalculResponseDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-16:06
 * @project backend_mfpai
 */


@Mapper
public interface ModeCalculMapper extends EntityMapper<ModeCalcul, ModeCalculRequestDTO, ModeCalculResponseDTO> {
}
