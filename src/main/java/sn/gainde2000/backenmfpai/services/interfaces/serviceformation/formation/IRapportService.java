package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.RapportDTO;

public interface IRapportService {

    Rapport createRapport(MultipartFile[] files, RapportDTO rapportDTO, MultipartFile[] pv);

    Rapport getRapportByFormationId(Long formationId);

    void deleteRapport(Long id);

}