package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Session;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.SessionRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.ISessionService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.SessionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class SessionServiceImpl implements ISessionService {

    private final SessionRepository sessionRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final SessionDTO sessionDTO;
    private final FormationRepository formationRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, FileRepository fileRepository,
            FileService fileService, SessionDTO sessionDTO, FormationRepository formationRepository) {
        this.sessionRepository = sessionRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
        this.sessionDTO = sessionDTO;
        this.formationRepository = formationRepository;
    }

    @Override
    @Transactional
    public Session createSession(
            MultipartFile file, Date dateFin, Date dateDebut, String commentaire, Long formationId) {

        Session sessionEntity = new Session();

        sessionEntity.setFormation(formationRepository.findById(formationId).get());
        sessionEntity.setCommentaire(commentaire);
        sessionEntity.setDateDebut(dateDebut);
        sessionEntity.setDateFin(dateFin);

        if (file != null) {
            FileRspDTO uploadedFileDto = fileService.storeFile(file, "session", false);
            File uploadedFile = new File();
            uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
            uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
            uploadedFile.setIdAppartenance(0);
            fileRepository.save(uploadedFile);
            sessionEntity.setFile(uploadedFile);
        }

        return sessionRepository.save(sessionEntity);
    }

    @Override
    public List<Session> getSessionByFormationId(Long formationId) {
        // Utilisez votre repository pour récupérer les sessions par ID de formation
        List<Session> sessions = sessionRepository.findByFormationId(formationId);

        // Assurez-vous que les sessions existent
        if (!sessions.isEmpty()) {
            // Retournez la liste de sessions
            return sessions;
        } else {
            // Gérez le cas où aucune session n'est trouvée
            return Collections.emptyList(); // Ou lancez une exception appropriée
        }
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

}
