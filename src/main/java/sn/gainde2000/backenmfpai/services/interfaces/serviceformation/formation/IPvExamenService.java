package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PvExamen;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Session;

public interface IPvExamenService {
    PvExamen createPvExamen(MultipartFile file, Long formationId);

    List<PvExamen> getPvExamenByFormationId(Long formationId);

    void deletePvExamen(Long id);
}
