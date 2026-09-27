package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participant;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ParticipantRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IParticipantService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParticipantServiceImpl implements IParticipantService {

    private final ParticipantRepository participantRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final FormationRepository formationRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, FileService fileService,
            FileRepository fileRepository, CentralLevelRepository centralLevelRepository,
            FormationRepository formationRepository) {
        this.participantRepository = participantRepository;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.centralLevelRepository = centralLevelRepository;
        this.formationRepository = formationRepository;
    }

    @Override
    public Participant createParticipant(MultipartFile file, ParticipantDTO participantDTO) {
        Participant participantEntity = new Participant();

        if (file != null) {
            FileRspDTO uploadedFileDto = fileService.storeFile(file, "participant",
                    false);
            File uploadedFile = new File();
            uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
            uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
            uploadedFile.setIdAppartenance(0);
            fileRepository.save(uploadedFile);
            participantEntity.setFileParticipant(uploadedFile);
        }

        // List<CentralLevel> participants = participantDTO.getParticipantIds().stream()
        // .map(id -> centralLevelRepository.findById(id)
        // .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec
        // l'ID : " + id)))
        // .collect(Collectors.toList());
        // // formation.setFormateurs(formateurs);
        // participantEntity.setListeParticipants(participants);

        participantEntity.setFormation(participantDTO.getFormation());

        return participantRepository.save(participantEntity);
    }

    @Override
    public Participant getParticipantByFormationId(Long formationId) {
        Optional<Participant> optionalParticipant = participantRepository.findByFormationId(formationId);
        return optionalParticipant.orElse(null);
    }

    @Override
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }
}
