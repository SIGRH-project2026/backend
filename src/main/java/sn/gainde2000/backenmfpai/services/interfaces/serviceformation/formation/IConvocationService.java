package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Convocation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ConvocationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;

public interface IConvocationService {

    Convocation createConvocation(MultipartFile tdr, String nom, Long formationId);

    List<Convocation> getConvocationByFormationId(Long formationId);

    void deleteConvocation(Long id);

}
