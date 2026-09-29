package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PvExamen;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Session;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.PvExamenRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.SessionRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IPvExamenService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.ISessionService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.SessionDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

@Service
@Transactional
public class PvExamenServiceImpl implements IPvExamenService {
    private final FormationNotifications formationNotifications;


    private final PvExamenRepository pvExamenRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    // private final SessionDTO sessionDTO;
    private final FormationRepository formationRepository;

    public PvExamenServiceImpl(PvExamenRepository pvExamenRepository, FileRepository fileRepository,
            FileService fileService, FormationRepository formationRepository, FormationNotifications formationNotifications) {
        this.formationNotifications = formationNotifications;
        this.pvExamenRepository = pvExamenRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
        this.formationRepository = formationRepository;
    }

    @Override
    @Transactional
    public PvExamen createPvExamen(
            MultipartFile file, Long formationId) {

        PvExamen pvExamenEntity = new PvExamen();

        pvExamenEntity.setFormation(formationRepository.findById(formationId).get());

        if (file != null) {
            FileRspDTO uploadedFileDto = fileService.storeFile(file, "PvExamen", false);
            File uploadedFile = new File();
            uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
            uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
            uploadedFile.setIdAppartenance(0);
            fileRepository.save(uploadedFile);
            pvExamenEntity.setFile(uploadedFile);
        }

        PvExamen saved = pvExamenRepository.save(pvExamenEntity);
        formationNotifications.notifyConcerned(saved.getFormation(), "un procès-verbal d’examen est disponible");
        return saved;
    }

    @Override
    public List<PvExamen> getPvExamenByFormationId(Long formationId) {
        // Utilisez votre repository pour récupérer les sessions par ID de formation
        List<PvExamen> pvExamens = pvExamenRepository.findByFormationId(formationId);

        // Assurez-vous que les sessions existent
        if (!pvExamens.isEmpty()) {
            // Retournez la liste de sessions
            return pvExamens;
        } else {
            // Gérez le cas où aucune session n'est trouvée
            return Collections.emptyList(); // Ou lancez une exception appropriée
        }
    }

    @Override
    public void deletePvExamen(Long id) {
        pvExamenRepository.deleteById(id);
    }

}
