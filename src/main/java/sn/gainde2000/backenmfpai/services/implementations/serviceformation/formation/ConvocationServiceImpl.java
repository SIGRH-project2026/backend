package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Convocation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ConvocationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IConvocationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ConvocationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ConvocationServiceImpl implements IConvocationService {

    private final ConvocationRepository convocationRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final ConvocationDTO convocationDTO;
    private final FormationRepository formationRepository;

    public ConvocationServiceImpl(ConvocationRepository convocationRepository, FileRepository fileRepository,
            FileService fileService, ConvocationDTO convocationDTO, FormationRepository formationRepository) {
        this.convocationRepository = convocationRepository;
        this.fileRepository = fileRepository;
        this.fileService = fileService;
        this.convocationDTO = convocationDTO;
        this.formationRepository = formationRepository;
    }

    @Override
    @Transactional
    public Convocation createConvocation(
            MultipartFile tdr, String nom, Long formationId) {

        Convocation convocationEntity = new Convocation();

        convocationEntity.setFormation(formationRepository.findById(formationId).get());
        convocationEntity.setNom(nom);

        if (tdr != null) {
            FileRspDTO uploadedFileDto = fileService.storeFile(tdr, "convocation", false);
            File uploadedFile = new File();
            uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
            uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
            uploadedFile.setIdAppartenance(0);
            fileRepository.save(uploadedFile);
            convocationEntity.setTdr(uploadedFile);
        }

        return convocationRepository.save(convocationEntity);
    }

    @Override
    public List<Convocation> getConvocationByFormationId(Long formationId) {
        // Utilisez votre repository pour récupérer les convocations par ID de formation
        List<Convocation> convocations = convocationRepository.findByFormationId(formationId);

        // Assurez-vous que les convocations existent
        if (!convocations.isEmpty()) {
            // Retournez la liste de convocations
            return convocations;
        } else {
            // Gérez le cas où aucune convocation n'est trouvée
            return Collections.emptyList(); // Ou lancez une exception appropriée
        }
    }

    @Override
    public void deleteConvocation(Long id) {
        convocationRepository.deleteById(id);
    }

}
