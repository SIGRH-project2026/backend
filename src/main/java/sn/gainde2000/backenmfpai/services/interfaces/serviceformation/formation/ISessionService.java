package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Convocation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Session;

public interface ISessionService {
    Session createSession(MultipartFile file, Date dateFin, Date dateDebut, String commentaire, Long formationId);

    List<Session> getSessionByFormationId(Long formationId);

    void deleteSession(Long id);
}
