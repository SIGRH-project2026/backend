package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Optional;

import jakarta.persistence.EntityNotFoundException;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.RapportRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IRapportService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.RapportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RapportServiceImpl implements IRapportService {

    private final RapportRepository rapportRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FormationRepository formationRepository;
    private final RapportDTO rapportDTO;

    public RapportServiceImpl(RapportRepository rapportRepository, FileService fileService,
            FileRepository fileRepository, FormationRepository formationRepository, RapportDTO rapportDTO) {
        this.rapportRepository = rapportRepository;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.rapportDTO = rapportDTO;
        this.formationRepository = formationRepository;
    }

    @Override
    public Rapport createRapport(MultipartFile[] files, RapportDTO rapportDTO, MultipartFile[] pv) {
        Rapport rapportEntity = new Rapport();

        // Récupérez l'entité Formation à partir de la base de données en fonction de
        // son identifiant
        Formation formation = formationRepository.findById(rapportDTO.getFormation().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Formation non trouvée avec l'ID : " + rapportDTO.getFormation().getId()));

        rapportEntity.setFormation(formation);

        if (files != null && files.length > 0) {
            Set<File> uploadedFiles = new HashSet<>();
            for (MultipartFile file : files) {
                FileRspDTO uploadedFileDto = fileService.storeFile(file, "rapport", false);

                File uploadedFile = new File();
                uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                uploadedFile.setIdAppartenance(0);
                fileRepository.save(uploadedFile);
                uploadedFiles.add(uploadedFile);
            }
            rapportEntity.setFiles(uploadedFiles);
        }

        if (pv != null && pv.length > 0) {
            Set<File> uploadedFiles = new HashSet<>();
            for (MultipartFile file : pv) {
                FileRspDTO uploadedFileDto = fileService.storeFile(file, "pv", false);

                File uploadedFile = new File();
                uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                uploadedFile.setIdAppartenance(0);
                fileRepository.save(uploadedFile);
                uploadedFiles.add(uploadedFile);
            }
            rapportEntity.setFiles(uploadedFiles);
        }

        rapportEntity.setCommentaire(rapportDTO.getCommentaire());

        return rapportRepository.save(rapportEntity);
    }

    @Override
    public Rapport getRapportByFormationId(Long formationId) {
        // Utilisez votre repository pour récupérer le rapport par ID de formation
        Optional<Rapport> rapportOptional = rapportRepository.findByFormationId(formationId);

        // Vérifiez si le rapport existe
        if (rapportOptional.isPresent()) {
            Rapport rapport = rapportOptional.get();
            // Assurez-vous que les fichiers sont chargés avec le rapport
            rapport.getFiles().size();
            rapport.getPv().size();
            // Cela force le chargement paresseux des fichiers
            return rapport;
        } else {
            // Gérez le cas où le rapport n'existe pas
            return null; // Ou lancez une exception appropriée
        }
    }

    @Override
    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }

}
