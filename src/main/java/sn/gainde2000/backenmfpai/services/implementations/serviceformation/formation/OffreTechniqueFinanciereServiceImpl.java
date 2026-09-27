package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;

import jakarta.persistence.EntityNotFoundException;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.OffreTechniqueFinanciere;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.StatutOffreTechnique;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.OffreTechniqueFinanciereRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.RapportRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.StatutOffreTechniqueRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IOffreTechniqueFinanciereService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IRapportService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.OffreTechniqueFinanciereDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.RapportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
// @Sl4j
public class OffreTechniqueFinanciereServiceImpl implements IOffreTechniqueFinanciereService {

    private final OffreTechniqueFinanciereRepository offreTechniqueFinanciereRepository;
    private final FileService fileService;
    private final FileRepository fileRepository;
    private final FormationRepository formationRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final OffreTechniqueFinanciereDTO offreTechniqueFinanciereDTO;
    private final StatutOffreTechniqueRepository statutOffreTechniqueRepository;

    public OffreTechniqueFinanciereServiceImpl(StatutOffreTechniqueRepository statutOffreTechniqueRepository,
            DeconcentratedLevelRepository deconcentratedLevelRepository,
            OffreTechniqueFinanciereRepository offreTechniqueFinanciereRepository,
            FileService fileService,
            FileRepository fileRepository, FormationRepository formationRepository,
            OffreTechniqueFinanciereDTO offreTechniqueFinanciereDTO) {
        this.offreTechniqueFinanciereRepository = offreTechniqueFinanciereRepository;
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.offreTechniqueFinanciereDTO = offreTechniqueFinanciereDTO;
        this.formationRepository = formationRepository;
        this.deconcentratedLevelRepository = deconcentratedLevelRepository;
        this.statutOffreTechniqueRepository = statutOffreTechniqueRepository;
    }

    @Override
    public OffreTechniqueFinanciere createOffreTechniqueFinanciere(MultipartFile[] files,
            OffreTechniqueFinanciereDTO offreTechniqueFinanciereDTO) {
        OffreTechniqueFinanciere offreTechniqueFinanciereEntity = new OffreTechniqueFinanciere();

        // Récupérez l'entité Formation à partir de la base de données en fonction de
        // son identifiant
        Formation formation = formationRepository.findById(offreTechniqueFinanciereDTO.getFormation().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Formation non trouvée avec l'ID : " + offreTechniqueFinanciereDTO.getFormation().getId()));

        offreTechniqueFinanciereEntity.setFormation(formation);

        offreTechniqueFinanciereEntity.setStatutOffreTechnique(
                statutOffreTechniqueRepository
                        .findByCode(offreTechniqueFinanciereDTO.getStatutOffreTechnique().getCode()));

        DeconcentratedLevel user = deconcentratedLevelRepository
                .findById(offreTechniqueFinanciereDTO.getChefeff().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Formation non trouvée avec l'ID : " + offreTechniqueFinanciereDTO.getChefeff().getId()));

        offreTechniqueFinanciereEntity.setChefeff(user);

        if (files != null && files.length > 0) {
            Set<File> uploadedFiles = new HashSet<>();
            for (MultipartFile file : files) {
                FileRspDTO uploadedFileDto = fileService.storeFile(file, "offreTechniqueFinanciere", false);

                File uploadedFile = new File();
                uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                uploadedFile.setIdAppartenance(0);
                fileRepository.save(uploadedFile);
                uploadedFiles.add(uploadedFile);
            }
            offreTechniqueFinanciereEntity.setFiles(uploadedFiles);
        }
        offreTechniqueFinanciereEntity.setCommentaire(offreTechniqueFinanciereDTO.getCommentaire());

        return offreTechniqueFinanciereRepository.save(offreTechniqueFinanciereEntity);
    }

    // @Override
    // public List<OffreTechniqueFinanciere>
    // getOffreTechniqueFinanciereByFormationId(Long formationId) {
    // return offreTechniqueFinanciereRepository.findByFormationId(formationId);
    // }

    @Override
    @Transactional(readOnly = true)
    public List<OffreTechniqueFinanciereDTO> getOffreTechniqueFinanciereByFormationId(Long planFormationId) {
        List<OffreTechniqueFinanciere> offres = offreTechniqueFinanciereRepository
                .findByFormationId(planFormationId);

        List<OffreTechniqueFinanciereDTO> offreDTOs = offres.stream()
                .map((OffreTechniqueFinanciere offre) -> {
                    OffreTechniqueFinanciereDTO dto = mapToDTO(offre);

                    dto.getFiles().size();
                    return dto;

                }).collect(Collectors.toList());

        return offreDTOs;
    }

    @Override
    public void deleteOffreTechniqueFinanciere(Long id) {
        offreTechniqueFinanciereRepository.deleteById(id);
    }

    @Override
    public OffreTechniqueFinanciereDTO updateOffreTechniqueStatus(Long id, String newStatutOffreCode) {
        // Rechercher la formation par son ID
        OffreTechniqueFinanciere offre = offreTechniqueFinanciereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Formation introuvable avec l'ID : " + id));

        // Rechercher le nouveau statut de formation par son code
        StatutOffreTechnique newStatutOffre = statutOffreTechniqueRepository.findByCode(newStatutOffreCode);
        if (newStatutOffre == null) {
            throw new EntityNotFoundException(
                    "Statut de formation introuvable avec le code : " + newStatutOffreCode);
        }

        // Mettre à jour le statut de la formation avec le nouveau statut
        offre.setStatutOffreTechnique(newStatutOffre);

        // Enregistrer la formation mise à jour dans la base de données
        OffreTechniqueFinanciere savedOffre = offreTechniqueFinanciereRepository.save(offre);

        // Mapper la formation mise à jour vers le DTO et le retourner
        return mapToDTO(savedOffre);
    }

    @Override
    public List<OffreTechniqueFinanciere> getAllOffreTechniqueFinanciere() {
        return offreTechniqueFinanciereRepository.findAll();
    }

    private OffreTechniqueFinanciereDTO mapToDTO(OffreTechniqueFinanciere offre) {
        OffreTechniqueFinanciereDTO offreDTO = new OffreTechniqueFinanciereDTO();
        offreDTO.setId(offre.getId());
        offreDTO.setStatutOffreTechnique(offre.getStatutOffreTechnique());
        offreDTO.setChefeff(offre.getChefeff());
        offreDTO.setCommentaire(offre.getCommentaire());
        offreDTO.setFormation(offre.getFormation());
        offreDTO.setFiles(offre.getFiles()); // Assurez-vous que getFiles() retourne une liste non nulle
        return offreDTO;
    }

}