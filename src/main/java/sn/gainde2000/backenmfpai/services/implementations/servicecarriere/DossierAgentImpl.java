package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAG;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.DossierAgentMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.*;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeAARepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeAGRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeActeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IDossierAgent;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DossierAgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DossierAgentResponseDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.QDossierAgent.dossierAgent;

/**
 * @author bsdieme
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DossierAgentImpl implements IDossierAgent {

    // Tous les champs sont maintenant final - plus de @Autowired
    private final IDossierAgentRepository dossierAgentRepository;
    private final DossierAgentMapper dossierAgentMapper;
    private final IAgentRepository iAgentRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final IAvancementRepository iAvancementRepository;
    private final IDiplomeRepository iDiplomeRepository;
    private final ISituationAdministrativeRepository iSituationAdministrativeRepository;
    private final IUtilisateur iUtilisataeur;
    private final IUtilisateurRepository iUtilisateurRepository;
    private final TypeActeRepository typeActeRepository;
    private final TypeAARepository typeAARepository;
    private final TypeAGRepository typeAGRepository;

    @Override
    public DossierAgentResponseDto rechercheDossierAgent(String matricule) {
        System.out.println("le matricule === " + matricule);
        Optional<Utilisateur> utilisateurOptional = iUtilisateurRepository.findUtilisateurByMatricule(matricule);

        if (utilisateurOptional.isPresent()) {
            DossierAgentResponseDto dossierAgentResponseDto = new DossierAgentResponseDto();
            Optional<DossierAgent> dossierAgentOptional = dossierAgentRepository
                    .findByUtilisateur(utilisateurOptional.get());

            if (dossierAgentOptional.isPresent()) {
                dossierAgentResponseDto = dossierAgentMapper.toDto(dossierAgentOptional.get());
                dossierAgentResponseDto.setHasDossier(true);

                if (!dossierAgentResponseDto.getDiplomes().isEmpty()) {
                    int j = 0;
                    List<Diplome> diplomesFiltered = new ArrayList<>();
                    while (j < dossierAgentResponseDto.getDiplomes().size()) {
                        if (!dossierAgentResponseDto.getDiplomes().get(j).getIsDeleted()) {
                            diplomesFiltered.add(dossierAgentResponseDto.getDiplomes().get(j));
                        }
                        j++;
                    }
                    dossierAgentResponseDto.setDiplomes(diplomesFiltered);
                }

                if (dossierAgentResponseDto.getEtatCivil().size() != 0) {
                    int h = 0;
                    List<EtatCivil> etatsFiltered = new ArrayList<>();
                    while (h < dossierAgentResponseDto.getEtatCivil().size()) {
                        if (!dossierAgentResponseDto.getEtatCivil().get(h).getIsDeleted()) {
                            etatsFiltered.add(dossierAgentResponseDto.getEtatCivil().get(h));
                        }
                        h++;
                    }
                    dossierAgentResponseDto.setEtatCivil(etatsFiltered);
                }

                if (!dossierAgentResponseDto.getSituationAdministrative().isEmpty()) {
                    int z = 0;
                    List<SituationAdministrative> situationFiltered = new ArrayList<>();
                    while (z < dossierAgentResponseDto.getSituationAdministrative().size()) {
                        if (!dossierAgentResponseDto.getSituationAdministrative().get(z).getIsDeleted()) {
                            situationFiltered.add(dossierAgentResponseDto.getSituationAdministrative().get(z));
                        }
                        z++;
                    }
                    dossierAgentResponseDto.setSituationAdministrative(situationFiltered);
                }

            } else {
                dossierAgentResponseDto.setId(0L);
                dossierAgentResponseDto.setUtilisateur(utilisateurOptional.get());
                dossierAgentResponseDto.setHasDossier(false);
            }

            return dossierAgentResponseDto;
        } else {
            throw new EntityNotFoundException("utilisateur introuvable");
        }
    }

    public static List<Object> paginate(List<Object> fullList, int pageNumber, int pageSize) {
        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, fullList.size());
        return fullList.subList(fromIndex, toIndex);
    }

    @Override
    public List<Diplome> getDiplomesByMatricule(String matricule, int page, int size) {
        System.out.println("get diplomes");
        List<Diplome> diplomes = this.rechercheDossierAgent(matricule).getDiplomes();
        return diplomes;
    }

    @Override
    @Transactional
    public DossierAgent createDossier(DossierAgentRequestDto dto) throws IOException {
        List<SituationAdministrative> listSituation = dto.getSituationAdministrative();

        if (dto.getId() == 0) {
            return createNewDossier(dto, listSituation);
        } else {
            return updateExistingDossier(dto, listSituation);
        }
    }

    private DossierAgent createNewDossier(DossierAgentRequestDto dto, List<SituationAdministrative> listSituation) {
        DossierAgent dossierAgent = dossierAgentMapper.toEntity(dto);

        Utilisateur utilisateur = iUtilisateurRepository.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utilisateur avec ID " + dto.getUtilisateurId() + " introuvable"));

        if (dossierAgentRepository.findByUtilisateur(utilisateur).isPresent()) {
            throw new MFPAIException(
                    "409_DOSSIER_EXISTANT",
                    "Cet agent possède déjà un dossier.",
                    HttpStatus.CONFLICT);
        }

        dossierAgent.setUtilisateur(utilisateur);
        dossierAgent.setDiplomes(saveNewDiplomes(dto.getDiplomes()));

        if (!dto.getSituationAdministrative().isEmpty()) {
            addSituationAdministrative(dto, listSituation);
        }

        dossierAgent.setSituationAdministrative(listSituation);
        return dossierAgentRepository.save(dossierAgent);
    }

    private DossierAgent updateExistingDossier(DossierAgentRequestDto dto,
            List<SituationAdministrative> listSituation) {

        DossierAgent dossierAgent = dossierAgentRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Dossier avec ID " + dto.getId() + " introuvable"));

        dossierAgent.setUtilisateur(dossierAgent.getUtilisateur());

        dossierAgent.setDiplomes(saveNewDiplomes(dto.getDiplomes()));

        if (!dto.getSituationAdministrative().isEmpty()) {
            addSituationAdministrative(dto, listSituation);
        }
        dossierAgent.setSituationAdministrative(listSituation);
        dossierAgent.setEtatCivil(dto.getEtatCivil());

        return dossierAgentRepository.save(dossierAgent);
    }

    private List<Diplome> saveNewDiplomes(List<Diplome> diplomes) {
        if (diplomes == null || diplomes.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(diplomes.stream()
                .map(diplome -> diplome.getId() == null || diplome.getId() == 0
                        ? iDiplomeRepository.save(diplome)
                        : diplome)
                .toList());
    }

    private void addSituationAdministrative(DossierAgentRequestDto dto, List<SituationAdministrative> listSituation) {
        int i = 0;
        while (i < dto.getSituationAdministrative().size()) {
            if (dto.getSituationAdministrative().get(i).getId() == 0) {
            }
            SituationAdministrative situationAdministrative = dto.getSituationAdministrative().get(i);
            Optional<TypeActe> typeActeOptional = typeActeRepository
                    .findTypeActeByCodeActe(situationAdministrative.getTypeActe().getCodeActe());
            typeActeOptional.ifPresent(situationAdministrative::setTypeActe);

            if ("aa".equals(situationAdministrative.getTypeActe().getCodeActe())) {
                typeAARepository.findTypeAAByCode(situationAdministrative.getActeAA().getCode())
                        .ifPresent(typeAA -> {
                            situationAdministrative.setActeAA(typeAA);
                            situationAdministrative.setActeAG(null);
                        });
            } else {
                typeAGRepository.findTypeAGByCode(situationAdministrative.getActeAG().getCode())
                        .ifPresent(typeAG -> {
                            situationAdministrative.setActeAG(typeAG);
                            situationAdministrative.setActeAA(null);
                        });
            }

            if (!listSituation.contains(situationAdministrative)) {
                listSituation.add(situationAdministrative);
            }
            i++;
        }
    }

    @Override
    public Page<DossierAgent> getAllDossierAgent(int page, int size, String adresse, String matricule, String nom,
            String prenom) {
        System.out.println("salam get Dossiers Agent impl");
        BooleanBuilder builder = new BooleanBuilder();
        QDossierAgent qDossierAgent = dossierAgent;
        builder.and(
                dossierAgent.isDeleted.isFalse());

        if (StringUtils.isNotBlank(nom)) {
            builder.and(
                    dossierAgent.utilisateur.nom.likeIgnoreCase("%" + nom + "%"));
        }

        if (StringUtils.isNotBlank(matricule)) {
            builder.and(
                    dossierAgent.utilisateur.matricule.likeIgnoreCase("%" + matricule + "%"));
        }

        if (StringUtils.isNotBlank(adresse)) {
            builder.and(
                    dossierAgent.utilisateur.adresse.likeIgnoreCase("%" + adresse + "%"));
        }

        if (StringUtils.isNotBlank(prenom)) {
            builder.and(
                    dossierAgent.utilisateur.prenom.likeIgnoreCase("%" + prenom + "%"));
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return dossierAgentRepository.findAll(builder, pageRequest);
    }

    @Override
    public DossierAgent deleteDossier(Long id) {
        DossierAgent dossierAgent = dossierAgentRepository.findById(id).get();
        dossierAgent.setIsDeleted(true);
        dossierAgentRepository.save(dossierAgent);
        return dossierAgent;
    }

    @Override
    public DossierAgentResponseDto getOneDossierAgent(Long id) {
        DossierAgentResponseDto dossierAgentResponseDto = new DossierAgentResponseDto();
        Optional<DossierAgent> dossierAgentOptional = dossierAgentRepository.findById(id);
        if (dossierAgentOptional.isPresent()) {
            dossierAgentResponseDto = dossierAgentMapper.toDto(dossierAgentOptional.get());
            dossierAgentResponseDto.setHasDossier(true);

            if (!dossierAgentResponseDto.getDiplomes().isEmpty()) {
                List<Diplome> diplomesFiltered = new ArrayList<>();
                for (Diplome diplome : dossierAgentResponseDto.getDiplomes()) {
                    if (!diplome.getIsDeleted()) {
                        diplomesFiltered.add(diplome);
                    }
                }
                dossierAgentResponseDto.setDiplomes(diplomesFiltered);
            }

            if (!dossierAgentResponseDto.getEtatCivil().isEmpty()) {
                List<EtatCivil> etatsFiltered = new ArrayList<>();
                for (EtatCivil etat : dossierAgentResponseDto.getEtatCivil()) {
                    if (!etat.getIsDeleted()) {
                        etatsFiltered.add(etat);
                    }
                }
                dossierAgentResponseDto.setEtatCivil(etatsFiltered);
            }

            if (!dossierAgentResponseDto.getSituationAdministrative().isEmpty()) {
                List<SituationAdministrative> situationFiltered = new ArrayList<>();
                for (SituationAdministrative situation : dossierAgentResponseDto.getSituationAdministrative()) {
                    if (!situation.getIsDeleted()) {
                        situationFiltered.add(situation);
                    }
                }
                dossierAgentResponseDto.setSituationAdministrative(situationFiltered);
            }

            return dossierAgentResponseDto;
        } else {
            throw new EntityNotFoundException("Dossier Agent introuvable");
        }
    }

    @Override
    public DossierAgentResponseDto getDossierCurrentUser() {
        System.out.println("entrer service implements ");
        try {
            Utilisateur user = iUtilisataeur.getCurrentUser();
            if (user == null) {
                System.out.println("Utilisateur non connecté");
                throw new RuntimeException("Utilisateur non authentifié");
            }

            System.out.println("user matricule ++++++++++++++++ " + user.getMatricule());

            DossierAgentResponseDto dossier = this.rechercheDossierAgent(user.getMatricule());

            // Log pour voir les autorités
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("=== DÉTAILS AUTHENTIFICATION ===");
            System.out.println("Nom: " + authentication.getName());
            System.out.println("Autorités: ");
            authentication.getAuthorities().forEach(auth -> {
                System.out.println("  - " + auth.getAuthority());
            });
        } else {
            System.out.println("Aucune authentification trouvée");
        }

            boolean canCreate = false;
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                canCreate = authentication.getAuthorities().stream()
                        .anyMatch(auth -> {
                            String authority = auth.getAuthority();
                            return "ADMIN-DRH".equals(authority)
                                    || "Directeur-DRH".equals(authority)
                                    || "Chef-division-dgcaa".equals(authority)
                                    || "Chef-bureau-dgcaa".equals(authority)
                                    || "Agent-bureau-dgcaa".equals(authority);
                        });
            }
            dossier.setCanCreateDossier(canCreate);

            return dossier;

        } catch (Exception e) {
            System.out.println("Erreur récupération utilisateur: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération du dossier", e);
        }
    }

    @Override
    public boolean hasDossier(String matricule) {
        Optional<Utilisateur> utilisateurOptional = iUtilisateurRepository.findUtilisateurByMatricule(matricule);
        if (utilisateurOptional.isPresent()) {
            Optional<DossierAgent> dossierAgentOptional = dossierAgentRepository
                    .findByUtilisateur(utilisateurOptional.get());
            return dossierAgentOptional.isPresent();
        }
        return false;
    }

    @Override
    @Transactional
    public DossierAgentResponseDto createEmptyDossierForUser(Utilisateur user) {
        Optional<DossierAgent> existingDossier = dossierAgentRepository.findByUtilisateur(user);
        if (existingDossier.isPresent()) {
            return dossierAgentMapper.toDto(existingDossier.get());
        }

        DossierAgent newDossier = new DossierAgent();
        newDossier.setUtilisateur(user);
        newDossier.setIsDeleted(false);
        newDossier.setDiplomes(new ArrayList<>());
        newDossier.setSituationAdministrative(new ArrayList<>());
        newDossier.setEtatCivil(new ArrayList<>());
        newDossier.setActes(new ArrayList<>());

        DossierAgent savedDossier = dossierAgentRepository.save(newDossier);
        return dossierAgentMapper.toDto(savedDossier);
    }
}
