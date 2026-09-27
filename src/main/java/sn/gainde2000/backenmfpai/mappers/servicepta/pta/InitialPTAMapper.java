package sn.gainde2000.backenmfpai.mappers.servicepta.pta;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.InitialPTA;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.InitialPTARequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.InitialPTAResponseDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-11:52
 * @project backend_mfpai
 */

@Mapper
public interface InitialPTAMapper extends EntityMapper<InitialPTA, InitialPTARequestDTO, InitialPTAResponseDTO> {
}
