
package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.DOMAINE;
import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.EMAIL_DEJA_UTILISE;
import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.NON_AUTHORISER;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.CHECK_DATE_EN;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.CNI_ALREADY_EXISTS;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.EMAIL_ALREADY_EXISTS;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.MATRICULE_ALREADY_EXISTS;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.TELEPHONE_ALREADY_EXISTS;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.MATRICULE_OBLIGATOIRE_FONCTIONAIRE;
import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.REGION_OB;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.DuplicateMatriculeReportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.UserImportResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nTranslate;
import sn.gainde2000.backenmfpai.commons.utils.password.PasswordGenerator;
import sn.gainde2000.backenmfpai.entities.other.DisposableEmail;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomeACA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePED;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePROF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.QUtilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeMatricule;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypePoste;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.UserManager;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.QCentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.QDeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.UserManagerMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.central.CentralLevelMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.deconcentred.DeconcentratedLevelMapper;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.CorpsGradeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.DiplomeACARepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.DiplomePEDRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.DiplomePROFRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.FonctionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.GradeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IDisposableEmailRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.TypeMatriculeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.TypePosteRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.UserManagerRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.BureauRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DirectionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DivisionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.ServiceRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EtablissementRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.IARepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.IEFRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.RegionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.SpecialityRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.StructureRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.TypeSystemeEnseignementRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.INotificationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UserManagerRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UtilisateurDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.CentralLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DeconcentratedLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

/**
 * @author G2k R&D
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilisateurImpl implements IUtilisateur {

    public static final String DEC = "DEC";
    public static final String CEN = "CEN";
    private final IUtilisateurRepository utilisateurRepository;
    private final DeconcentratedLevelMapper deconcentratedLevelMapper;
    private final CentralLevelMapper centralLevelMapper;
    private final IProfilRepository iProfilRepository;
    private final DivisionRepository divisionRepository;
    private final DirectionRepository directionRepository;
    private final BureauRepository bureauRepository;
    private final ServiceRepository serviceRepository;
    private final FonctionRepository fonctionRepository;
    private final CorpsGradeRepository corpsGradeRepository;
    private final GradeRepository gradeRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final RegionRepository regionRepository;
    private final StructureRepository structureRepository;
    private final IARepository iaRepository;
    private final IEFRepository iefRepository;
    private final EtablissementRepository etablissementRepository;
    private final TypeSystemeEnseignementRepository typeSystemeEnseignementRepository;
    private final SpecialityRepository specialityRepository;
    private final I18nTranslate i18nTranslat;
    private final IDisposableEmailRepository disposableEmailRepository;
    private final PasswordEncoder encoder;
    private final UserManagerMapper userManagerMapper;
    private final UserManagerRepository userManagerRepository;
    public static final String FIRST_CONNEXION = "FIRST_CONNEXION";
    private static final String BLANK = " ";
    private final INotificationService notificationService;
    private final TypePosteRepository typePosteRepository;
    private final TypeMatriculeRepository typeMatriculeRepository;
    private final DiplomePEDRepository diplomePEDRepository;
    private final DiplomePROFRepository diplomePROFRepository;
    private final DiplomeACARepository diplomeACARepository;
    private final PlatformTransactionManager transactionManager;

    private static final int IMPORT_BATCH_SIZE = 1000;


    @Override
    @Transactional
    public DeconcentratedLevel saveUtilisateurDL(DeconcentratedLevelDTO dto) {
        Optional<Utilisateur> utilisateurByEmail = utilisateurRepository.findUtilisateurByEmail(dto.getEmail());
        Optional<Utilisateur> utilisateurCNI = utilisateurRepository.findUtilisateurByCni(dto.getCni());

        throwExceptionWithMatAndValidDate(dto);
        checkMatricule(dto);

      
        Optional<Utilisateur> matriculeOptional = utilisateurRepository.findUtilisateurByMatricule(dto.getMatricule());

        if (matriculeOptional.isPresent()) {
            throw new MFPAIException(MATRICULE_ALREADY_EXISTS, "Le matricule " + dto.getMatricule() + " existe déjà");

        } else if (utilisateurByEmail.isPresent()) {
            throw new MFPAIException(EMAIL_ALREADY_EXISTS,
                    "L'utilisateur avec l' email " + dto.getEmail() + " existe déjà");

        } else if (utilisateurCNI.isPresent()) {
            throw new MFPAIException(EMAIL_ALREADY_EXISTS,
                    "L'utilisateur avec le cni " + dto.getCni() + " existe déjà");

        } else {

            DeconcentratedLevel deconcentratedLevel = deconcentratedLevelMapper.toEntity(dto);

            if (Objects.nonNull(dto.getQuantumHoraire())) {
                checkValidQuantum(dto, deconcentratedLevel);
            }

            if (!Objects.isNull(dto.getMatriculeContratuel())) {

                deconcentratedLevel.setIsFonctionnaire(false);

            } else if (!Objects.isNull(dto.getMatriculeFonctionnaire())) {
                deconcentratedLevel.setIsFonctionnaire(true);
            }

            String password = PasswordGenerator.generateRandomString();

            deconcentratedLevel.setPassword(encoder.encode(password));

            deconcentratedLevel.setFirstLog(true);
            deconcentratedLevel.setStatus(true);

            deconcentratedLevel.setTypeUser(DEC);

            deconcentratedLevel.setNom(dto.getNom());
            deconcentratedLevel.setPrenom(dto.getPrenom());
            deconcentratedLevel.setMatricule(dto.getMatricule());
            deconcentratedLevel.setSexe(dto.getSexe());
            deconcentratedLevel.setTelephone(dto.getTelephone());
            deconcentratedLevel.setEmail(dto.getEmail());
            deconcentratedLevel.setAdresse(dto.getAdresse());
            deconcentratedLevel.setSituationMatrimoniale(dto.getSituationMatrimoniale());
            deconcentratedLevel.setDateDEntree(dto.getDateDEntree());
            deconcentratedLevel.setNombreEnfants(dto.getNombreEnfants());
            deconcentratedLevel.setNationalite(dto.getNationalite());

            deconcentratedLevel.setMatriculeContratuel(dto.getMatriculeContratuel());
            deconcentratedLevel.setMatriculeFonctionnaire(dto.getMatriculeFonctionnaire());

            deconcentratedLevel.setMatriculeVacataire(dto.getMatriculeVacataire());
            deconcentratedLevel.setMatriculeDecisionnaire(dto.getMatriculeDecisionnaire());

            deconcentratedLevel.setCni(dto.getCni());
            deconcentratedLevel.setDateEntreEnseignement(dto.getDateEntreEnseignement());
            deconcentratedLevel.setDateEntreEtablissement(dto.getDateEntreEtablissement());
            deconcentratedLevel.setDateDEntreeFonctionPub(dto.getDateDEntreeFonctionPub());
            deconcentratedLevel.setDateCorp(dto.getDateCorp());
            deconcentratedLevel.setLieuDeNaissance(dto.getLieuDeNaissance());
            deconcentratedLevel.setDateNaissance(dto.getDateNaissance());
            deconcentratedLevel.setDateEntreService(dto.getDateEntreService());

            Fonction fonction = fonctionRepository.findByCode(dto.getFonction().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevel.setFonction(fonction);

            CorpsGrade corpsGrade = corpsGradeRepository.findByCode(dto.getCorpsGrade().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));

            deconcentratedLevel.setCorpsGrade(corpsGrade);

            Grade grade = gradeRepository.findByCode(dto.getGrade().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevel.setGrade(grade);

            TypePoste typePoste = typePosteRepository.findByCode(dto.getTypePoste().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevel.setTypePoste(typePoste);

            TypeMatricule typeMatricule = typeMatriculeRepository.findByCode(dto.getTypeMatricule().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevel.setTypeMatricule(typeMatricule);

            if (!Objects.nonNull(dto.getRegion())) {
                throw new MFPAIException(REGION_OB, " La région est obligatoire");
            }

            // System.out.println("==> " +dto.getRegion());
            if (!Objects.nonNull(dto.getRegion()) ||

                    !Objects.nonNull((dto.getRegion().getCode()))
                    || !StringUtils.isNotBlank(dto.getRegion().getCode()) ||
                    Objects.equals(dto.getRegion().getCode(), "")) {
                deconcentratedLevel.setRegion(null);
            } else {
                Region region = regionRepository.findByCode(dto.getRegion().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                deconcentratedLevel.setRegion(region);
            }

            if (!Objects.nonNull(dto.getIa()) ||
                    !Objects.nonNull((dto.getIa().getCode()))
                    || !StringUtils.isNotBlank(dto.getIa().getCode()) ||
                    Objects.equals(dto.getIa().getCode(), "")) {
                deconcentratedLevel.setIa(null);
            } else {

                IA ia = iaRepository.findByCode(dto.getIa().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                deconcentratedLevel.setIa(ia);
                // deconcentratedLevel.setEefMinistere(null);

            }
            if (!Objects.nonNull(dto.getIef()) ||
                    !Objects.nonNull((dto.getIef().getCode()))

                    || !StringUtils.isNotBlank(dto.getIef().getCode()) ||
                    Objects.equals(dto.getIef().getCode(), "")) {

                deconcentratedLevel.setIef(null);
            } else {

                IEF ief = iefRepository.findByCode(dto.getIef().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " IEF"));
                deconcentratedLevel.setIef(ief);

            }

            if (!Objects.nonNull((dto.getEtablissement()))
                    ||
                    !Objects.nonNull((dto.getEtablissement().getCode()))
                    || !StringUtils.isNotBlank(dto.getEtablissement().getCode()) ||
                    Objects.equals(dto.getEtablissement().getCode(), "")) {

                deconcentratedLevel.setEtablissement(null);

            } else {
                Etablissement etablissement = etablissementRepository.findByCode(dto.getEtablissement().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Etablissement"));
                deconcentratedLevel.setEtablissement(etablissement);

            }

            if (!Objects.nonNull(dto.getTypeSystemeEnseignement()) ||
                    !Objects.nonNull(dto.getTypeSystemeEnseignement().getCode())
                    || !StringUtils.isNotBlank(dto.getTypeSystemeEnseignement().getCode()) ||
                    Objects.equals(dto.getTypeSystemeEnseignement().getCode(), "")) {

                deconcentratedLevel.setTypeSystemeEnseignement(null);

            } else {
                TypeSystemeEnseignement typeSystemeEnseignement = typeSystemeEnseignementRepository
                        .findByCode(dto.getTypeSystemeEnseignement().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Type système enseignement"));
                deconcentratedLevel.setTypeSystemeEnseignement(typeSystemeEnseignement);
            }


            if (!Objects.nonNull(dto.getDiplomeACA()) ||
                    !Objects.nonNull(dto.getDiplomeACA().getCode()) ||
                    !StringUtils.isNotBlank(dto.getDiplomeACA().getCode()) ||
                    !Objects.equals(dto.getDiplomeACA().getCode(), "")) {
                deconcentratedLevel.setDiplomeACA(null);
            } else {

                DiplomeACA diplomeACA = diplomeACARepository.findByCode(dto.getDiplomeACA().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                deconcentratedLevel.setDiplomeACA(diplomeACA);
            }

            if (!Objects.nonNull(dto.getDiplomePED()) ||
                    !Objects.nonNull(dto.getDiplomePED().getCode()) ||
                    !StringUtils.isNotBlank(dto.getDiplomePED().getCode()) ||
                    !Objects.equals(dto.getDiplomePED().getCode(), "")) {
                deconcentratedLevel.setDiplomePED(null);
            } else {

                DiplomePED diplomePED = diplomePEDRepository.findByCode(dto.getDiplomePED().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                deconcentratedLevel.setDiplomePED(diplomePED);
            }

            if (!Objects.nonNull(dto.getDiplomePROF()) ||
                    !Objects.nonNull(dto.getDiplomePROF().getCode()) ||
                    !StringUtils.isNotBlank(dto.getDiplomePROF().getCode()) ||
                    !Objects.equals(dto.getDiplomePROF().getCode(), "")) {
                deconcentratedLevel.setDiplomePROF(null);
            } else {

                DiplomePROF diplomePROF = diplomePROFRepository.findByCode(dto.getDiplomePROF().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                deconcentratedLevel.setDiplomePROF(diplomePROF);
            }

            Speciality speciality = specialityRepository.findByCode(dto.getSpeciality().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevel.setSpeciality(speciality);

            Set<Profile> profiles = new HashSet<>();

            dto.getProfils().forEach(profile -> {
                Optional<Profile> profileDB = iProfilRepository.findByCode(profile.getCode());
                profileDB.ifPresent(profiles::add);
            });

            deconcentratedLevel.setProfils(profiles);

            DeconcentratedLevel deconcentratedLevelSave = utilisateurRepository.save(deconcentratedLevel);

            if (Objects.nonNull(deconcentratedLevelSave)) {
                notificationService.sendNotificationToNewUserRegistred(
                        new LoginFormDTO(deconcentratedLevelSave.getEmail(), password), FIRST_CONNEXION);
                return deconcentratedLevelSave;

            }

            return null;
        }
    }




    /** Champs obligatoires lors du chargement en masse (niveau déconcentré). */
    private static final List<String[]> IMPORT_REQUIRED_FIELDS = List.of(
            new String[] { "matricule", "Matricule" },
            new String[] { "prenom_prenoms", "Prénom" },
            new String[] { "nom", "Nom" },
            new String[] { "sexe", "Sexe" },
            new String[] { "etablissement", "Établissement" },
            new String[] { "typesystemeenseignement_typeenseignement", "Type système enseignement" },
            new String[] { "ief", "IEF" },
            new String[] { "ia", "IA" });

    /** Champs obligatoires (par ligne) pour l'import du niveau central. */
    private static final List<String[]> IMPORT_REQUIRED_FIELDS_CL = List.of(
            new String[] { "matricule", "Matricule" },
            new String[] { "prenom_prenoms", "Prénom" },
            new String[] { "nom", "Nom" },
            new String[] { "sexe", "Sexe" },
            new String[] { "direction", "Direction" });

    @Override
    public UserImportResultDTO importUtilisateursDL(MultipartFile file) {
        UserImportResultDTO result = UserImportResultDTO.builder()
                .errors(new ArrayList<>())
                .build();

        if (file == null || file.isEmpty()) {
            throw new MFPAIException(MFPAIMessage.GENERIC_ERROR, "Le fichier est vide ou absent");
        }

        List<Map<String, String>> rows;
        try {
            String filename = Optional.ofNullable(file.getOriginalFilename()).orElse("").toLowerCase();
            rows = filename.endsWith(".csv") ? readCsv(file) : readExcel(file, "deconcentre");
        } catch (IOException e) {
            log.error("Erreur lors de la lecture du fichier d'import", e);
            throw new MFPAIException(MFPAIMessage.GENERIC_ERROR,
                    "Impossible de lire le fichier : " + e.getMessage());
        }

        result.setTotal(rows.size());

        // Pré-chargement des valeurs existantes (une seule requête chacune) afin
        // d'éviter une requête de contrôle de doublon par ligne.
        Set<String> knownMatricules = new HashSet<>(utilisateurRepository.findAllMatricules());
        Set<String> knownEmails = new HashSet<>(utilisateurRepository.findAllEmails());
        Set<String> knownCnis = new HashSet<>(utilisateurRepository.findAllCnis());

        // Caches de résolution de références : une valeur (code ou libellé) n'est
        // requêtée qu'une seule fois pour tout l'import, quel que soit le nombre
        // de lignes qui la partagent (très fréquent : IA, IEF, établissement...).
        ImportReferenceCaches caches = new ImportReferenceCaches();

        List<PendingImportRow> batch = new ArrayList<>(IMPORT_BATCH_SIZE);

        int ligne = 1; // ligne 1 = en-tête
        for (Map<String, String> row : rows) {
            ligne++;

            UserImportResultDTO.RowError.RowErrorBuilder errorBuilder = rowErrorBuilder(ligne, row);
            String nom = pick(row, "nom");
            String matricule = pick(row, "matricule");

            List<String> missing = missingRequiredFields(row, IMPORT_REQUIRED_FIELDS);
            if (!missing.isEmpty()) {
                result.addError(errorBuilder
                        .message("Champs obligatoires manquants : " + String.join(", ", missing))
                        .build());
                continue;
            }

            String prenom = pick(row, "prenom", "prenoms");
            String iaValue = pick(row, "ia");
            String iefValue = pick(row, "ief");
            String etablissementValue = pick(row, "etablissement");
            String typeSystemeValue = pick(row, "typesystemeenseignement", "typeenseignement");
            if (!isValidPersonName(prenom) || !isValidPersonName(nom)
                    || containsCorruptedCharacters(iaValue)
                    || containsCorruptedCharacters(iefValue)
                    || containsCorruptedCharacters(etablissementValue)
                    || containsCorruptedCharacters(typeSystemeValue)) {
                result.addError(errorBuilder
                        .message("La ligne contient des caractères invalides ou un texte mal encodé")
                        .build());
                continue;
            }

            String matriculeKey = matricule.trim();
            if (knownMatricules.contains(matriculeKey)) {
                result.addError(errorBuilder.message("Le matricule existe déjà").build());
                continue;
            }

            String email = pick(row, "email");
            String emailKey = email == null ? null : email.trim().toLowerCase();
            if (emailKey != null && knownEmails.contains(emailKey)) {
                result.addError(errorBuilder.message("L'email existe déjà").build());
                continue;
            }

            String cni = pick(row, "cni");
            String cniKey = cni == null ? null : cni.trim();
            if (cniKey != null && knownCnis.contains(cniKey)) {
                result.addError(errorBuilder.message("Le CNI existe déjà").build());
                continue;
            }

            try {
                DeconcentratedLevel user = new DeconcentratedLevel();
                user.setTypeUser(DEC);
                // Le compte reste inactif jusqu'à la validation du parcours
                // d'activation (AuthentificationImpl active alors le compte).
                user.setStatus(false);
                user.setFirstLog(true);

                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(email);
                user.setMatricule(matricule);
                user.setCni(cni);
                user.setTelephone(pick(row, "telephone"));
                user.setAdresse(pick(row, "adresse"));
                user.setSexe(pick(row, "sexe"));
                user.setSituationMatrimoniale(pick(row, "situationmatrimoniale", "situation"));
                user.setNationalite(pick(row, "nationalite"));
                user.setLieuDeNaissance(pick(row, "lieudenaissance"));
                user.setDateNaissance(parseDate(pick(row, "datedenaissance", "datenaissance")));

                user.setMatriculeFonctionnaire(pick(row, "matriculefonctionnaire"));
                user.setMatriculeContratuel(pick(row, "matriculecontractuel", "matriculecontratuel"));
                user.setMatriculeVacataire(pick(row, "matriculevacataire"));
                user.setMatriculeDecisionnaire(pick(row, "matriculedecisionnaire"));

                user.setDateCorp(parseDate(pick(row, "datecorp", "datecorps")));
                user.setDateDEntreeFonctionPub(parseDate(pick(row, "datedentreefonctionpub", "datefonctionpublique")));
                user.setDateEntreEnseignement(parseDate(pick(row, "dateentreeenseignement", "dateenseignement")));
                user.setDateEntreEtablissement(parseDate(pick(row, "dateentreeetablissement", "dateentreetablissement")));
                user.setDateEntreService(parseDate(pick(row, "dateentreeservice", "dateservice")));

                String nbEnfants = pick(row, "nombreenfants", "nbenfants");
                if (!isBlank(nbEnfants)) {
                    try {
                        user.setNombreEnfants(Integer.parseInt(nbEnfants.trim()));
                    } catch (NumberFormatException ignore) {
                        // valeur non numérique -> ignorée
                    }
                }
                String quantum = pick(row, "quantumhoraire", "quantum");
                if (!isBlank(quantum)) {
                    try {
                        user.setQuantumHoraire(Integer.parseInt(quantum.trim()));
                    } catch (NumberFormatException ignore) {
                        // valeur non numérique -> ignorée
                    }
                }

                // Références résolues via les caches (une requête par valeur distincte
                // pour tout l'import, au lieu d'une requête par ligne).
                cached(caches.corpsGrade, pick(row, "corps", "corpsgrade"), corpsGradeRepository::findByCode)
                        .ifPresent(user::setCorpsGrade);
                cached(caches.grade, pick(row, "grade"), gradeRepository::findByCode).ifPresent(user::setGrade);
                cached(caches.fonction, pick(row, "fonction"), fonctionRepository::findByCode)
                        .ifPresent(user::setFonction);
                cached(caches.speciality, pick(row, "specialite"), specialityRepository::findByCode)
                        .ifPresent(user::setSpeciality);
                cached(caches.structure, pick(row, "structure"), structureRepository::findByCode)
                        .ifPresent(user::setStructure);
                cached(caches.typePoste, pick(row, "typeposte"), typePosteRepository::findByCode)
                        .ifPresent(user::setTypePoste);
                cached(caches.typeMatricule, pick(row, "typematricule"), typeMatriculeRepository::findByCode)
                        .ifPresent(user::setTypeMatricule);
                cached(caches.region, pick(row, "region"), regionRepository::findByCode).ifPresent(user::setRegion);

                // Les référentiels absents sont alimentés par l'import. Ils ne doivent
                // pas empêcher le chargement d'un agent valide.
                IA ia = resolveOrCreateIA(caches, iaValue);
                IEF ief = resolveOrCreateIEF(caches, iefValue, ia);
                TypeSystemeEnseignement typeSysteme = resolveOrCreateTypeSysteme(caches, typeSystemeValue);
                Etablissement etablissement = resolveOrCreateEtablissement(caches,
                        etablissementValue, ia, ief);

                user.setIa(ia);
                user.setIef(ief);
                user.setEtablissement(etablissement);
                user.setTypeSystemeEnseignement(typeSysteme);

                cached(caches.diplomeACA, pick(row, "diplomeaca"), diplomeACARepository::findByCode)
                        .ifPresent(user::setDiplomeACA);
                cached(caches.diplomePED, pick(row, "diplomeped"), diplomePEDRepository::findByCode)
                        .ifPresent(user::setDiplomePED);
                cached(caches.diplomePROF, pick(row, "diplomeprof"), diplomePROFRepository::findByCode)
                        .ifPresent(user::setDiplomePROF);

                String profil = pick(row, "profil", "profils", "codeprofil");
                if (!isBlank(profil)) {
                    Set<Profile> profiles = new HashSet<>();
                    for (String code : profil.split("[,;/]")) {
                        if (!isBlank(code)) {
                            cached(caches.profile, code.trim(), iProfilRepository::findByCode)
                                    .ifPresent(profiles::add);
                        }
                    }
                    user.setProfils(profiles);
                }

                if (!isBlank(user.getMatriculeFonctionnaire())) {
                    user.setIsFonctionnaire(true);
                } else if (!isBlank(user.getMatriculeContratuel())) {
                    user.setIsFonctionnaire(false);
                }

                String password = PasswordGenerator.generateRandomString();
                user.setPassword(encoder.encode(password));

                // Réservation immédiate des clés d'unicité pour détecter les doublons
                // au sein même du fichier importé (avant tout flush en base).
                knownMatricules.add(matriculeKey);
                if (emailKey != null) {
                    knownEmails.add(emailKey);
                }
                if (cniKey != null) {
                    knownCnis.add(cniKey);
                }

                LoginFormDTO login = isBlank(email) ? null : new LoginFormDTO(email, password);
                batch.add(new PendingImportRow(user, login, errorBuilder));
                result.setImported(result.getImported() + 1);

                if (batch.size() >= IMPORT_BATCH_SIZE) {
                    flushImportBatch(batch, result);
                }
            } catch (Exception e) {
                log.error("Erreur import ligne {} : {}", ligne, e.getMessage());
                result.addError(errorBuilder.message(e.getMessage()).build());
            }
        }

        flushImportBatch(batch, result);

        return result;
    }

    /**
     * Persiste un lot d'utilisateurs déconcentrés dans sa propre transaction
     * (commit indépendant toutes les {@link #IMPORT_BATCH_SIZE} lignes), puis
     * déclenche l'envoi asynchrone des mails d'activation sans bloquer l'import.
     * <p>
     * Si l'enregistrement groupé échoue (une seule ligne invalide suffit à faire
     * échouer tout le lot), on retente ligne par ligne afin de ne perdre que les
     * lignes réellement fautives et de conserver un comptage exact.
     */
    private void flushImportBatch(List<PendingImportRow> batch, UserImportResultDTO result) {
        if (batch.isEmpty()) {
            return;
        }
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        boolean batchSucceeded = true;
        try {
            transactionTemplate.executeWithoutResult(status -> utilisateurRepository
                    .saveAll(batch.stream().map(PendingImportRow::entity).collect(Collectors.toList())));
        } catch (Exception e) {
            batchSucceeded = false;
            log.warn("Échec de l'enregistrement groupé de {} ligne(s), nouvelle tentative ligne par ligne : {}",
                    batch.size(), e.getMessage());
        }

        if (batchSucceeded) {
            for (PendingImportRow pending : batch) {
                sendActivationEmailAsync(pending.login());
            }
        } else {
            // Le comptage optimiste effectué pendant la construction du lot est annulé :
            // seules les lignes réellement persistées ci-dessous seront recomptées.
            result.setImported(result.getImported() - batch.size());
            for (PendingImportRow pending : batch) {
                try {
                    TransactionTemplate rowTransaction = new TransactionTemplate(transactionManager);
                    rowTransaction.executeWithoutResult(status -> utilisateurRepository.save(pending.entity()));
                    result.setImported(result.getImported() + 1);
                    sendActivationEmailAsync(pending.login());
                } catch (Exception rowException) {
                    result.addError(pending.errorBuilder().message(rowException.getMessage()).build());
                }
            }
        }

        batch.clear();
    }

    private void sendActivationEmailAsync(LoginFormDTO login) {
        if (login == null) {
            return;
        }
        try {
            notificationService.sendNotificationToNewUserRegistredAsync(login, FIRST_CONNEXION);
        } catch (Exception e) {
            log.warn("Notification non envoyée pour {} : {}", login.login(), e.getMessage());
        }
    }

    /** Ligne validée en attente de persistance, conservée pour permettre un retraitement unitaire. */
    private record PendingImportRow(Utilisateur entity, LoginFormDTO login,
            UserImportResultDTO.RowError.RowErrorBuilder errorBuilder) {
    }

    private UserImportResultDTO.RowError.RowErrorBuilder rowErrorBuilder(int ligne, Map<String, String> row) {
        return UserImportResultDTO.RowError.builder()
                .ligne(ligne)
                .matricule(pick(row, "matricule"))
                .prenom(pick(row, "prenom", "prenoms"))
                .nom(pick(row, "nom"))
                .sexe(pick(row, "sexe"))
                .etablissement(pick(row, "etablissement"))
                .typeSystemeEnseignement(pick(row, "typesystemeenseignement", "typeenseignement"))
                .ief(pick(row, "ief"))
                .ia(pick(row, "ia"));
    }

    private static List<String> missingRequiredFields(Map<String, String> row, List<String[]> requiredFields) {
        List<String> missing = new ArrayList<>();
        for (String[] field : requiredFields) {
            String value = pick(row, field[0].split("_"));
            if (isBlank(value)) {
                missing.add(field[1]);
            }
        }
        return missing;
    }

    /** Retient en cache, pour toute la durée de l'import, le résultat de résolution d'une valeur. */
    private static <T> Optional<T> cached(Map<String, Optional<T>> cache, String rawValue,
            java.util.function.Function<String, Optional<T>> loader) {
        if (isBlank(rawValue)) {
            return Optional.empty();
        }
        String key = rawValue.trim();
        return cache.computeIfAbsent(key, loader);
    }

    private static <T> Optional<T> firstPresent(Optional<T> first, java.util.function.Supplier<Optional<T>> second) {
        return first.isPresent() ? first : second.get();
    }

    private IA resolveOrCreateIA(ImportReferenceCaches caches, String value) {
        String key = normalizeHeader(value);
        Optional<IA> cachedValue = caches.ia.get(key);
        if (cachedValue != null) {
            return cachedValue.orElseThrow();
        }
        IA ia = firstPresent(iaRepository.findByCode(value), () -> iaRepository.findByLabel(value))
                .orElseGet(() -> iaRepository.findAll().stream()
                        .filter(item -> normalizeHeader(item.getLabel()).equals(key))
                        .findFirst()
                        .orElseGet(() -> iaRepository.save(IA.builder()
                                .code(importReferenceCode("IA", value, 20))
                                .label(value.trim())
                                .statut(true)
                                .build())));
        caches.ia.put(key, Optional.of(ia));
        return ia;
    }

    private IEF resolveOrCreateIEF(ImportReferenceCaches caches, String value, IA ia) {
        String key = normalizeHeader(value);
        Optional<IEF> cachedValue = caches.ief.get(key);
        IEF ief = cachedValue == null
                ? firstPresent(iefRepository.findByCode(value), () -> iefRepository.findByLabel(value))
                        .orElseGet(() -> iefRepository.findAll().stream()
                                .filter(item -> normalizeHeader(item.getLabel()).equals(key))
                                .findFirst()
                                .orElseGet(() -> iefRepository.save(IEF.builder()
                                        .code(importReferenceCode("IEF", value, 10))
                                        .label(value.trim())
                                        .ia(ia)
                                        .statut(true)
                                        .build())))
                : cachedValue.orElseThrow();
        if (ief.getIa() == null || !Objects.equals(ief.getIa().getId(), ia.getId())) {
            ief.setIa(ia);
            ief = iefRepository.save(ief);
        }
        caches.ief.put(key, Optional.of(ief));
        return ief;
    }

    private TypeSystemeEnseignement resolveOrCreateTypeSysteme(ImportReferenceCaches caches, String value) {
        String key = normalizeHeader(value);
        Optional<TypeSystemeEnseignement> cachedValue = caches.typeSystemeEnseignement.get(key);
        if (cachedValue != null) {
            return cachedValue.orElseThrow();
        }
        TypeSystemeEnseignement typeSysteme = firstPresent(
                typeSystemeEnseignementRepository.findByCode(value),
                () -> typeSystemeEnseignementRepository.findByLabelIgnoreCase(value).stream().findFirst())
                .orElseGet(() -> typeSystemeEnseignementRepository.save(TypeSystemeEnseignement.builder()
                        .code(importReferenceCode("TSE", value, 10))
                        .label(value.trim())
                        .build()));
        caches.typeSystemeEnseignement.put(key, Optional.of(typeSysteme));
        return typeSysteme;
    }

    private Etablissement resolveOrCreateEtablissement(ImportReferenceCaches caches, String value,
            IA ia, IEF ief) {
        String key = normalizeHeader(value);
        Optional<Etablissement> cachedValue = caches.etablissement.get(key);
        Etablissement etablissement = cachedValue == null
                ? firstPresent(etablissementRepository.findByCode(value),
                        () -> etablissementRepository.findByLabelIgnoreCase(value).stream().findFirst())
                        .orElseGet(() -> etablissementRepository.save(Etablissement.builder()
                                .code(importReferenceCode("ETA", value, 10))
                                .label(value.trim())
                                .ia(ia)
                                .ief(ief)
                                .statut(true)
                                .build()))
                : cachedValue.orElseThrow();
        boolean changed = !Objects.equals(idOf(etablissement.getIa()), idOf(ia))
                || !Objects.equals(idOf(etablissement.getIef()), idOf(ief));
        if (changed) {
            etablissement.setIa(ia);
            etablissement.setIef(ief);
            etablissement = etablissementRepository.save(etablissement);
        }
        caches.etablissement.put(key, Optional.of(etablissement));
        return etablissement;
    }

    private static Long idOf(IA value) {
        return value == null ? null : value.getId();
    }

    private static Long idOf(IEF value) {
        return value == null ? null : value.getId();
    }

    private static String importReferenceCode(String prefix, String label, int maxLength) {
        String hash = Integer.toUnsignedString(normalizeHeader(label).hashCode(), 36).toUpperCase();
        String code = prefix + hash;
        return code.substring(0, Math.min(code.length(), maxLength));
    }

    /** Regroupe les caches de résolution de références utilisés pendant un import. */
    private static final class ImportReferenceCaches {
        final Map<String, Optional<CorpsGrade>> corpsGrade = new HashMap<>();
        final Map<String, Optional<Grade>> grade = new HashMap<>();
        final Map<String, Optional<Fonction>> fonction = new HashMap<>();
        final Map<String, Optional<Speciality>> speciality = new HashMap<>();
        final Map<String, Optional<Structure>> structure = new HashMap<>();
        final Map<String, Optional<TypePoste>> typePoste = new HashMap<>();
        final Map<String, Optional<TypeMatricule>> typeMatricule = new HashMap<>();
        final Map<String, Optional<Region>> region = new HashMap<>();
        final Map<String, Optional<IA>> ia = new HashMap<>();
        final Map<String, Optional<IEF>> ief = new HashMap<>();
        final Map<String, Optional<Etablissement>> etablissement = new HashMap<>();
        final Map<String, Optional<TypeSystemeEnseignement>> typeSystemeEnseignement = new HashMap<>();
        final Map<String, Optional<DiplomeACA>> diplomeACA = new HashMap<>();
        final Map<String, Optional<DiplomePED>> diplomePED = new HashMap<>();
        final Map<String, Optional<DiplomePROF>> diplomePROF = new HashMap<>();
        final Map<String, Optional<Profile>> profile = new HashMap<>();
    }

    // ==========================================================================
    // Import en masse d'utilisateurs de niveau central (Excel/CSV)
    // ==========================================================================

    @Override
    public UserImportResultDTO importUtilisateursCL(MultipartFile file) {
        UserImportResultDTO result = UserImportResultDTO.builder()
                .errors(new ArrayList<>())
                .build();

        if (file == null || file.isEmpty()) {
            throw new MFPAIException(MFPAIMessage.GENERIC_ERROR, "Le fichier est vide ou absent");
        }

        List<Map<String, String>> rows;
        try {
            String filename = Optional.ofNullable(file.getOriginalFilename()).orElse("").toLowerCase();
            rows = filename.endsWith(".csv") ? readCsv(file) : readExcel(file, "central");
        } catch (IOException e) {
            log.error("Erreur lors de la lecture du fichier d'import (niveau central)", e);
            throw new MFPAIException(MFPAIMessage.GENERIC_ERROR,
                    "Impossible de lire le fichier : " + e.getMessage());
        }

        result.setTotal(rows.size());

        // Pré-chargement des clés d'unicité existantes (une requête chacune) afin
        // de bloquer les doublons ligne par ligne sans requête par ligne.
        Set<String> knownMatricules = new HashSet<>(utilisateurRepository.findAllMatricules());
        Set<String> knownEmails = new HashSet<>(utilisateurRepository.findAllEmails());
        Set<String> knownCnis = new HashSet<>(utilisateurRepository.findAllCnis());

        // Caches de résolution de références : une valeur (code ou libellé) n'est
        // requêtée qu'une seule fois pour tout l'import.
        ImportCentralReferenceCaches caches = new ImportCentralReferenceCaches();

        // Index en mémoire des directions, construit une seule fois. La colonne
        // « Service » (ou « Etablissement ») du fichier représente la Direction
        // de l'utilisateur : on indexe par code ET par libellé normalisés
        // (accents/casse/ponctuation ignorés) pour maximiser les rapprochements.
        List<Direction> allDirections = directionRepository.findAll();
        Map<String, Direction> directionIndex = buildOrgIndex(allDirections,
                Direction::getCode, Direction::getLabel);
        List<PendingImportRow> batch = new ArrayList<>(IMPORT_BATCH_SIZE);

        int ligne = 1; // ligne 1 = en-tête
        for (Map<String, String> row : rows) {
            ligne++;

            UserImportResultDTO.RowError.RowErrorBuilder errorBuilder = rowErrorBuilderCL(ligne, row);
            String nom = pick(row, "nom");
            String matricule = pick(row, "matricule");

            List<String> missing = missingRequiredFields(row, IMPORT_REQUIRED_FIELDS_CL);
            if (!missing.isEmpty()) {
                result.addError(errorBuilder
                        .message("Champs obligatoires manquants : " + String.join(", ", missing))
                        .build());
                continue;
            }

            String matriculeKey = matricule.trim();
            if (knownMatricules.contains(matriculeKey)) {
                result.addError(errorBuilder.message("Le matricule existe déjà").build());
                continue;
            }

            String email = pick(row, "email");
            String emailKey = email == null ? null : email.trim().toLowerCase();
            if (emailKey != null && knownEmails.contains(emailKey)) {
                result.addError(errorBuilder.message("L'email existe déjà").build());
                continue;
            }

            String cni = pick(row, "cni");
            String cniKey = cni == null ? null : cni.trim();
            if (cniKey != null && knownCnis.contains(cniKey)) {
                result.addError(errorBuilder.message("Le CNI existe déjà").build());
                continue;
            }

            try {
                CentralLevel user = new CentralLevel();
                user.setTypeUser(CEN);
                // Le compte reste inactif jusqu'à la validation du parcours
                // d'activation (AuthentificationImpl active alors le compte).
                user.setStatus(false);
                user.setFirstLog(true);

                user.setNom(nom);
                user.setPrenom(pick(row, "prenom", "prenoms"));
                user.setEmail(email);
                user.setMatricule(matricule);
                user.setCni(cni);
                user.setTelephone(pick(row, "telephone"));
                user.setAdresse(pick(row, "adresse"));
                user.setSexe(pick(row, "sexe"));
                user.setSituationMatrimoniale(pick(row, "situationmatrimoniale", "situation"));
                user.setNationalite(pick(row, "nationalite"));
                user.setLieuDeNaissance(pick(row, "lieudenaissance"));
                user.setDateNaissance(parseDate(pick(row, "datedenaissance", "datenaissance")));

                user.setMatriculeFonctionnaire(pick(row, "matriculefonctionnaire"));
                user.setMatriculeContratuel(pick(row, "matriculecontractuel", "matriculecontratuel"));
                user.setMatriculeVacataire(pick(row, "matriculevacataire"));
                user.setMatriculeDecisionnaire(pick(row, "matriculedecisionnaire"));

                user.setDateCorp(parseDate(pick(row, "datecorp", "datecorps")));
                user.setDateDEntree(parseDate(pick(row, "datedentree", "dateentree")));
                user.setDateDEntreeFonctionPub(parseDate(pick(row, "datedentreefonctionpub", "datefonctionpublique")));
                user.setDateEntreEnseignement(parseDate(pick(row, "dateentreeenseignement", "dateenseignement")));
                user.setDateEntreService(parseDate(pick(row, "dateentreeservice", "dateservice")));

                String nbEnfants = pick(row, "nombreenfants", "nbenfants");
                if (!isBlank(nbEnfants)) {
                    try {
                        user.setNombreEnfants(Integer.parseInt(nbEnfants.trim()));
                    } catch (NumberFormatException ignore) {
                        // valeur non numérique -> ignorée
                    }
                }

                // Références résolues via les caches (une requête par valeur distincte
                // pour tout l'import, au lieu d'une requête par ligne).
                cached(caches.corpsGrade, pick(row, "corps", "corpsgrade"), corpsGradeRepository::findByCode)
                        .ifPresent(user::setCorpsGrade);
                cached(caches.grade, pick(row, "grade"), gradeRepository::findByCode).ifPresent(user::setGrade);
                cached(caches.fonction, pick(row, "fonction"), fonctionRepository::findByCode)
                        .ifPresent(user::setFonction);
                cached(caches.speciality, pick(row, "specialite"), specialityRepository::findByCode)
                        .ifPresent(user::setSpeciality);
                cached(caches.typePoste, pick(row, "typeposte"), typePosteRepository::findByCode)
                        .ifPresent(user::setTypePoste);
                cached(caches.typeMatricule, pick(row, "typematricule"), typeMatriculeRepository::findByCode)
                        .ifPresent(user::setTypeMatricule);
                cached(caches.region, pick(row, "region"), regionRepository::findByCode).ifPresent(user::setRegion);

                // Un agent central ne peut être rattaché qu'à une direction déjà
                // validée dans le référentiel. Aucune unité n'est créée par l'import.
                String directionValue = pick(row, "direction");
                Direction direction = directionIndex.get(normalizeHeader(directionValue));
                if (direction == null) {
                    throw new IllegalArgumentException("Direction introuvable : " + directionValue);
                }
                user.setDirection(direction);

                cached(caches.diplomeACA, pick(row, "diplomeaca"), diplomeACARepository::findByCode)
                        .ifPresent(user::setDiplomeACA);
                cached(caches.diplomePED, pick(row, "diplomeped"), diplomePEDRepository::findByCode)
                        .ifPresent(user::setDiplomePED);
                cached(caches.diplomePROF, pick(row, "diplomeprof"), diplomePROFRepository::findByCode)
                        .ifPresent(user::setDiplomePROF);

                String profil = pick(row, "profil", "profils", "codeprofil");
                if (!isBlank(profil)) {
                    Set<Profile> profiles = new HashSet<>();
                    for (String code : profil.split("[,;/]")) {
                        if (!isBlank(code)) {
                            cached(caches.profile, code.trim(), iProfilRepository::findByCode)
                                    .ifPresent(profiles::add);
                        }
                    }
                    user.setProfils(profiles);
                }

                if (!isBlank(user.getMatriculeFonctionnaire())) {
                    user.setIsFonctionnaire(true);
                } else if (!isBlank(user.getMatriculeContratuel())) {
                    user.setIsFonctionnaire(false);
                }

                String password = PasswordGenerator.generateRandomString();
                user.setPassword(encoder.encode(password));

                // Réservation immédiate des clés d'unicité pour détecter les doublons
                // au sein même du fichier importé (avant tout flush en base).
                knownMatricules.add(matriculeKey);
                if (emailKey != null) {
                    knownEmails.add(emailKey);
                }
                if (cniKey != null) {
                    knownCnis.add(cniKey);
                }

                LoginFormDTO login = isBlank(email) ? null : new LoginFormDTO(email, password);
                batch.add(new PendingImportRow(user, login, errorBuilder));
                result.setImported(result.getImported() + 1);

                if (batch.size() >= IMPORT_BATCH_SIZE) {
                    flushImportBatch(batch, result);
                }
            } catch (Exception e) {
                log.error("Erreur import ligne {} (niveau central) : {}", ligne, e.getMessage());
                result.addError(errorBuilder.message(e.getMessage()).build());
            }
        }

        flushImportBatch(batch, result);

        return result;
    }

    private UserImportResultDTO.RowError.RowErrorBuilder rowErrorBuilderCL(int ligne, Map<String, String> row) {
        return UserImportResultDTO.RowError.builder()
                .ligne(ligne)
                .matricule(pick(row, "matricule"))
                .prenom(pick(row, "prenom", "prenoms"))
                .nom(pick(row, "nom"))
                .sexe(pick(row, "sexe"))
                .service(pick(row, "direction"));
    }

    /**
     * Construit un index d'unités d'organisation par code et libellé normalisés
     * (accents, casse et ponctuation ignorés) afin de rapprocher des valeurs
     * saisies librement dans le fichier. En cas de doublon de clé, la première
     * occurrence est conservée.
     */
    private static <T> Map<String, T> buildOrgIndex(List<T> items,
            java.util.function.Function<T, String> codeFn, java.util.function.Function<T, String> labelFn) {
        Map<String, T> index = new HashMap<>();
        for (T item : items) {
            String label = normalizeHeader(labelFn.apply(item));
            if (!label.isEmpty()) {
                index.putIfAbsent(label, item);
            }
            String code = normalizeHeader(codeFn.apply(item));
            if (!code.isEmpty()) {
                index.putIfAbsent(code, item);
            }
        }
        return index;
    }

    /** Regroupe les caches de résolution de références utilisés pendant un import de niveau central. */
    private static final class ImportCentralReferenceCaches {
        final Map<String, Optional<CorpsGrade>> corpsGrade = new HashMap<>();
        final Map<String, Optional<Grade>> grade = new HashMap<>();
        final Map<String, Optional<Fonction>> fonction = new HashMap<>();
        final Map<String, Optional<Speciality>> speciality = new HashMap<>();
        final Map<String, Optional<TypePoste>> typePoste = new HashMap<>();
        final Map<String, Optional<TypeMatricule>> typeMatricule = new HashMap<>();
        final Map<String, Optional<Region>> region = new HashMap<>();
        final Map<String, Optional<DiplomeACA>> diplomeACA = new HashMap<>();
        final Map<String, Optional<DiplomePED>> diplomePED = new HashMap<>();
        final Map<String, Optional<DiplomePROF>> diplomePROF = new HashMap<>();
        final Map<String, Optional<Profile>> profile = new HashMap<>();
    }

    @Override
    public DuplicateMatriculeReportDTO findDuplicateMatriculesDL() {
        return buildDuplicateReport(false);
    }

    @Override
    @Transactional
    public DuplicateMatriculeReportDTO removeDuplicateMatriculesDL() {
        return buildDuplicateReport(true);
    }

    private DuplicateMatriculeReportDTO buildDuplicateReport(boolean delete) {
        List<String> duplicateMatricules = deconcentratedLevelRepository.findDuplicateMatricules();
        List<DuplicateMatriculeReportDTO.DuplicateGroup> groups = new ArrayList<>();
        List<DeconcentratedLevel> toDelete = new ArrayList<>();

        for (String matricule : duplicateMatricules) {
            List<DeconcentratedLevel> records = deconcentratedLevelRepository.findByMatriculeOrderById(matricule);
            if (records.size() <= 1) {
                continue;
            }
            DeconcentratedLevel keep = records.get(0);
            List<DeconcentratedLevel> duplicates = records.subList(1, records.size());
            toDelete.addAll(duplicates);

            groups.add(DuplicateMatriculeReportDTO.DuplicateGroup.builder()
                    .matricule(matricule)
                    .idConserve(keep.getId())
                    .idsSupprimes(duplicates.stream().map(DeconcentratedLevel::getId).collect(Collectors.toList()))
                    .build());
        }

        if (delete && !toDelete.isEmpty()) {
            deconcentratedLevelRepository.deleteAll(toDelete);
        }

        return DuplicateMatriculeReportDTO.builder()
                .matriculesEnDoublon(groups.size())
                .enregistrementsSupprimes(toDelete.size())
                .groupes(groups)
                .build();
    }

    private static void resolve(Map<String, String> row, String key, String altKey,
            java.util.function.Consumer<String> setter) {
        String value = altKey == null ? pick(row, key) : pick(row, key, altKey);
        if (!isBlank(value)) {
            try {
                setter.accept(value);
            } catch (Exception e) {
                // Référence introuvable ou ambiguë : ignorée pour ne pas
                // bloquer l'import de la ligne (champ laissé vide).
                log.debug("Référence '{}' non résolue pour '{}' : {}", key, value, e.getMessage());
            }
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private static boolean isValidPersonName(String value) {
        if (isBlank(value) || containsCorruptedCharacters(value)) {
            return false;
        }
        // Lettres Unicode (accents compris), espaces, points, apostrophes et
        // traits d'union sont les seuls caractères admis dans les noms.
        return value.matches("[\\p{L}\\p{M} .’'\\-]+")
                && value.chars().noneMatch(Character::isISOControl);
    }

    private static boolean containsCorruptedCharacters(String value) {
        return value == null || value.indexOf('\uFFFD') >= 0
                || value.chars().anyMatch(Character::isISOControl);
    }

    private static String pick(Map<String, String> row, String... keys) {
        for (String key : keys) {
            String v = row.get(key);
            if (v != null && !v.trim().isEmpty()) {
                return v.trim();
            }
        }
        return null;
    }

    private static String normalizeHeader(String header) {
        if (header == null) {
            return "";
        }
        String normalized = Normalizer.normalize(header, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalized.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    private static LocalDate parseDate(String value) {
        if (isBlank(value)) {
            return null;
        }
        String v = value.trim();
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(v, formatter);
            } catch (DateTimeParseException ignore) {
                // essai du format suivant
            }
        }
        return null;
    }

    private List<Map<String, String>> readExcel(MultipartFile file, String... sheetNameHints) throws IOException {
        List<Map<String, String>> rows = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        try (InputStream is = file.getInputStream();
                Workbook workbook = WorkbookFactory.create(is)) {
            ImportSheetHeader importHeader = findBestImportHeader(workbook, formatter, sheetNameHints);
            if (importHeader == null) {
                return rows;
            }
            Sheet sheet = importHeader.sheet();
            Row headerRow = importHeader.headerRow();

            Map<Integer, String> headers = new LinkedHashMap<>();
            for (Cell cell : headerRow) {
                String key = normalizeHeader(formatter.formatCellValue(cell));
                if (!key.isEmpty()) {
                    headers.put(cell.getColumnIndex(), key);
                }
            }

            for (int r = headerRow.getRowNum() + 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Map<String, String> values = new HashMap<>();
                boolean empty = true;
                for (Map.Entry<Integer, String> entry : headers.entrySet()) {
                    Cell cell = row.getCell(entry.getKey());
                    String value;
                    if (cell != null && cell.getCellType() == CellType.NUMERIC
                            && DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getLocalDateTimeCellValue().toLocalDate().toString();
                    } else {
                        value = formatter.formatCellValue(cell);
                    }
                    if (value != null && !value.trim().isEmpty()) {
                        empty = false;
                    }
                    values.put(entry.getValue(), value);
                }
                if (!empty) {
                    rows.add(values);
                }
            }
        }
        return rows;
    }

    /**
     * Sélectionne la feuille et la vraie ligne d'en-tête à partir des champs du
     * modèle d'import, et non uniquement du nom de l'onglet. Certains fichiers
     * contiennent une feuille active différente, une ligne de titre ou des
     * onglets dont le nom ne correspond pas exactement au niveau importé.
     */
    private ImportSheetHeader findBestImportHeader(Workbook workbook, DataFormatter formatter,
            String... sheetNameHints) {
        Set<String> expectedHeaders = isCentralImport(sheetNameHints)
                ? Set.of("matricule", "prenom", "prenoms", "nom", "sexe", "direction")
                : Set.of("matricule", "prenom", "prenoms", "nom", "sexe", "etablissement",
                        "typesystemeenseignement", "typeenseignement", "ief", "ia");

        ImportSheetHeader best = null;
        int bestScore = 0;
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet candidateSheet = workbook.getSheetAt(sheetIndex);
            int lastCandidateRow = Math.min(candidateSheet.getLastRowNum(),
                    candidateSheet.getFirstRowNum() + 20);
            for (int rowIndex = candidateSheet.getFirstRowNum(); rowIndex <= lastCandidateRow; rowIndex++) {
                Row candidateRow = candidateSheet.getRow(rowIndex);
                if (candidateRow == null) {
                    continue;
                }
                int score = 0;
                for (Cell cell : candidateRow) {
                    if (expectedHeaders.contains(normalizeHeader(formatter.formatCellValue(cell)))) {
                        score++;
                    }
                }
                if (score > bestScore) {
                    bestScore = score;
                    best = new ImportSheetHeader(candidateSheet, candidateRow);
                }
            }
        }

        // Deux colonnes reconnues au minimum évitent de prendre une ligne de
        // données ou un titre isolé pour l'en-tête.
        return bestScore >= 2 ? best : null;
    }

    private static boolean isCentralImport(String... sheetNameHints) {
        if (sheetNameHints == null) {
            return false;
        }
        return Arrays.stream(sheetNameHints)
                .filter(Objects::nonNull)
                .map(UtilisateurImpl::normalizeHeader)
                .anyMatch("central"::equals);
    }

    private record ImportSheetHeader(Sheet sheet, Row headerRow) {
    }

    private List<Map<String, String>> readCsv(MultipartFile file) throws IOException {
        List<Map<String, String>> rows = new ArrayList<>();
        String csvContent = decodeCsvContent(file.getBytes());
        try (BufferedReader reader = new BufferedReader(new StringReader(csvContent))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return rows;
            }

            // Certains exports Excel ajoutent une première ligne « sep=; ».
            // Elle décrit le séparateur et ne fait pas partie des en-têtes.
            Character declaredDelimiter = parseDeclaredCsvDelimiter(headerLine);
            if (declaredDelimiter != null) {
                headerLine = reader.readLine();
                if (headerLine == null) {
                    return rows;
                }
            }

            char delimiter = declaredDelimiter != null
                    ? declaredDelimiter
                    : detectCsvDelimiter(headerLine);
            String delimiterRegex = Pattern.quote(String.valueOf(delimiter));
            String[] rawHeaders = headerLine.split(delimiterRegex, -1);
            List<String> headers = new ArrayList<>();
            for (String h : rawHeaders) {
                headers.add(normalizeHeader(h));
            }

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] cells = line.split(delimiterRegex, -1);
                Map<String, String> values = new HashMap<>();
                for (int i = 0; i < headers.size() && i < cells.length; i++) {
                    String key = headers.get(i);
                    if (!key.isEmpty()) {
                        values.put(key, cells[i].trim());
                    }
                }
                rows.add(values);
            }
        }
        return rows;
    }

    /**
     * Décode un export CSV sans remplacer silencieusement les caractères
     * accentués. Les exports Excel francophones sont fréquemment enregistrés en
     * Windows-1252 ; une lecture forcée en UTF-8 transformait alors é/è/ç en �.
     */
    private static String decodeCsvContent(byte[] bytes) throws CharacterCodingException {
        try {
            return StandardCharsets.UTF_8.newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes))
                    .toString();
        } catch (CharacterCodingException invalidUtf8) {
            return Charset.forName("windows-1252").newDecoder()
                    .onMalformedInput(CodingErrorAction.REPORT)
                    .onUnmappableCharacter(CodingErrorAction.REPORT)
                    .decode(ByteBuffer.wrap(bytes))
                    .toString();
        }
    }

    private static char detectCsvDelimiter(String headerLine) {
        char[] candidates = { ';', ',', '\t' };
        char bestDelimiter = ';';
        int bestCount = -1;
        for (char candidate : candidates) {
            int count = 0;
            for (int i = 0; i < headerLine.length(); i++) {
                if (headerLine.charAt(i) == candidate) {
                    count++;
                }
            }
            if (count > bestCount) {
                bestCount = count;
                bestDelimiter = candidate;
            }
        }
        return bestDelimiter;
    }

    private static Character parseDeclaredCsvDelimiter(String firstLine) {
        if (firstLine == null) {
            return null;
        }
        String normalized = firstLine.replace("\uFEFF", "").trim();
        if (normalized.length() == 5 && normalized.regionMatches(true, 0, "sep=", 0, 4)) {
            char delimiter = normalized.charAt(4);
            if (delimiter == ';' || delimiter == ',' || delimiter == '\t') {
                return delimiter;
            }
        }
        return null;
    }

    private static void throwExceptionWithMatAndValidDate(UtilisateurDTO dto) {
        if (dto.getTypeMatricule().getCode().equals("MATFONC")) {
            if (!Objects.nonNull(dto.getDateDEntreeFonctionPub())) {
                throw new MFPAIException(MATRICULE_OBLIGATOIRE_FONCTIONAIRE,
                        "La date entrée en fonction publique est obligatoire avec le matricule fonctionnaire ");

            }

           /* if (Objects.nonNull(dto.getDateEntreEnseignement())) {
                if (dto.getDateEntreEnseignement().isAfter(dto.getDateDEntreeFonctionPub())) {
                    throw new MFPAIException(CHECK_DATE_EN,
                            "La date d'enseignement   doit être antérieur   à la  date entrée en fonction publique");
                }
            }
            */
        }
    }

    private void checkValidQuantum(DeconcentratedLevelDTO dto, DeconcentratedLevel deconcentratedLevel) {
        CorpsGrade corpsGrade = corpsGradeRepository.findByCode(dto.getCorpsGrade().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));

        switch (dto.getCorpsGrade().getCode()) {
            case "PES":
            case "PEPS":
            case "PEM":
            case "PCS":
                if (dto.getQuantumHoraire() != 525) {

                    throw new MFPAIException(MFPAIMessage.QUANTUM_INVALIDE,
                            "Le quantum  doit être  égale à 525 heures/an pour le corps : " + corpsGrade.getLabel());
                } else {
                    deconcentratedLevel.setQuantumHoraire(dto.getQuantumHoraire());
                }
                break;

            case "PCEMG":
            case "MEPS":
            case "MEAM":
            case "METP":
            case "PCI":
            case "CCC":
                if (dto.getQuantumHoraire() != 625) {

                    throw new MFPAIException(MFPAIMessage.QUANTUM_INVALIDE,
                            "Le quantum  doit être  égale à  625 heures/an pour le corps : " + corpsGrade.getLabel());
                } else {
                    deconcentratedLevel.setQuantumHoraire(dto.getQuantumHoraire());
                }
                break;

            case "VACATAIRE":

                if (dto.getQuantumHoraire() != 500) {

                    throw new MFPAIException(MFPAIMessage.QUANTUM_INVALIDE,
                            "Le quantum  doit être  égale à 500 heures/an pour le corps : " + corpsGrade.getLabel());
                } else {
                    deconcentratedLevel.setQuantumHoraire(dto.getQuantumHoraire());
                }
                break;
            default:
                break;

        }
    }

    @Override
    @Transactional
    public DeconcentratedLevel updateUtilisateurDL(Long id, DeconcentratedLevelDTO dto) {

        DeconcentratedLevel deconcentratedLevelDB = deconcentratedLevelRepository.findById(id).orElseThrow();

        if (dto.getTypeMatricule().getCode().equals("MATFONC")) {
            if (!Objects.nonNull(dto.getDateDEntreeFonctionPub())) {
                throw new MFPAIException(MATRICULE_OBLIGATOIRE_FONCTIONAIRE,
                        "La date entrée en fonction publique est obligatoire avec le matricule fonctionnaire ");

            }

            if (Objects.nonNull(dto.getDateEntreEnseignement())) {
                if (dto.getDateEntreEnseignement().isAfter(dto.getDateDEntreeFonctionPub())) {
                    throw new MFPAIException(CHECK_DATE_EN,
                            "La date d'enseignement   doit être antérieur  à la date entrée en fonction publique");
                }
            }
        }

        checkRightMatricule(dto);

        if (StringUtils.isNotBlank(dto.getMatricule()) && utilisateurRepository
                .findAllByMatricule(dto.getMatricule()).stream()
                .anyMatch(utilisateur -> !Objects.equals(utilisateur.getId(), id))) {
            throw new MFPAIException(MATRICULE_ALREADY_EXISTS,
                    "Modification impossible : le matricule " + dto.getMatricule()
                            + " est déjà utilisé par un autre utilisateur.");
        }

        checkFieldUniqueForUpdate(dto.getEmail(), id, utilisateurRepository::findAllByEmail,
                EMAIL_ALREADY_EXISTS, "email");
        checkFieldUniqueForUpdate(dto.getTelephone(), id, utilisateurRepository::findAllByTelephone,
                TELEPHONE_ALREADY_EXISTS, "téléphone");
        checkFieldUniqueForUpdate(dto.getCni(), id, utilisateurRepository::findAllByCni,
                CNI_ALREADY_EXISTS, "CNI");

        deconcentratedLevelDB.setNom(dto.getNom());
        deconcentratedLevelDB.setPrenom(dto.getPrenom());
        deconcentratedLevelDB.setMatricule(dto.getMatricule());
        deconcentratedLevelDB.setTelephone(dto.getTelephone());
        deconcentratedLevelDB.setEmail(dto.getEmail());

        deconcentratedLevelDB.setAdresse(dto.getAdresse());
        deconcentratedLevelDB.setSexe(dto.getSexe());
        deconcentratedLevelDB.setSituationMatrimoniale(dto.getSituationMatrimoniale());
        deconcentratedLevelDB.setDateDEntree(dto.getDateDEntree());

        deconcentratedLevelDB.setNombreEnfants(dto.getNombreEnfants());
        deconcentratedLevelDB.setNationalite(dto.getNationalite());
        deconcentratedLevelDB.setCni(dto.getCni());
        deconcentratedLevelDB.setMatriculeContratuel(dto.getMatriculeContratuel());
        deconcentratedLevelDB.setMatriculeFonctionnaire(dto.getMatriculeFonctionnaire());

        deconcentratedLevelDB.setMatriculeVacataire(dto.getMatriculeVacataire());
        deconcentratedLevelDB.setMatriculeDecisionnaire(dto.getMatriculeDecisionnaire());
        deconcentratedLevelDB.setDateEntreEnseignement(dto.getDateEntreEnseignement());
        deconcentratedLevelDB.setDateEntreEtablissement(dto.getDateEntreEtablissement());
        deconcentratedLevelDB.setDateDEntreeFonctionPub(dto.getDateDEntreeFonctionPub());
        deconcentratedLevelDB.setDateCorp(dto.getDateCorp());
        deconcentratedLevelDB.setLieuDeNaissance(dto.getLieuDeNaissance());
        deconcentratedLevelDB.setDateNaissance(dto.getDateNaissance());
        deconcentratedLevelDB.setDateEntreService(dto.getDateEntreService());



        Fonction fonction = fonctionRepository.findByCode(dto.getFonction().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "=> Fonction"));
        deconcentratedLevelDB.setFonction(fonction);



        CorpsGrade corpsGrade = corpsGradeRepository.findByCode(dto.getCorpsGrade().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "=> Corps"));
        deconcentratedLevelDB.setCorpsGrade(corpsGrade);


        Grade grade = gradeRepository.findByCode(dto.getGrade().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "=> Grade"));
        deconcentratedLevelDB.setGrade(grade);


        TypePoste typePoste = typePosteRepository.findByCode(dto.getTypePoste().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Type poste"));
        deconcentratedLevelDB.setTypePoste(typePoste);

        TypeMatricule typeMatricule = typeMatriculeRepository.findByCode(dto.getTypeMatricule().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Type matricule"));
        deconcentratedLevelDB.setTypeMatricule(typeMatricule);

        String profil = dto.getProfils().stream().map(Profile::getCode).findAny().get();

        if (
                profil.equals("Représentant-IEF") ||
                profil.equals("Representant-IA")
               ) {
            deconcentratedLevelDB.setEtablissement(null);

        } else {

            Etablissement etablissement = etablissementRepository.findByCode(dto.getEtablissement().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Etablissement"));
            deconcentratedLevelDB.setEtablissement(etablissement);
        }

        if (!Objects.nonNull(dto.getRegion()) ||
                !Objects.nonNull((dto.getRegion().getCode()))
                || !StringUtils.isNotBlank(dto.getRegion().getCode()) ||
                Objects.equals(dto.getRegion().getCode(), "")) {
            deconcentratedLevelDB.setRegion(null);
        } else {
            Region region = regionRepository.findByCode(dto.getRegion().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevelDB.setRegion(region);
        }

        if (!Objects.nonNull(dto.getIa()) ||
                !Objects.nonNull((dto.getIa().getCode()))
                || !StringUtils.isNotBlank(dto.getIa().getCode()) ||
                Objects.equals(dto.getIa().getCode(), "")) {
            deconcentratedLevelDB.setIa(null);
        } else {

            IA ia = iaRepository.findByCode(dto.getIa().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            deconcentratedLevelDB.setIa(ia);
            // deconcentratedLevelDB.setEefMinistere(null);

        }
        if (!Objects.nonNull(dto.getIef()) ||
                !Objects.nonNull((dto.getIef().getCode()))

                || !StringUtils.isNotBlank(dto.getIef().getCode()) ||
                Objects.equals(dto.getIef().getCode(), "")) {

            deconcentratedLevelDB.setIef(null);
        } else {

            IEF ief = iefRepository.findByCode(dto.getIef().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " IEF"));
            deconcentratedLevelDB.setIef(ief);

        }

        if (!Objects.nonNull(dto.getTypeSystemeEnseignement()) ||
                !Objects.nonNull(dto.getTypeSystemeEnseignement().getCode())
                || !StringUtils.isNotBlank(dto.getTypeSystemeEnseignement().getCode()) ||
                Objects.equals(dto.getTypeSystemeEnseignement().getCode(), "")) {

            deconcentratedLevelDB.setTypeSystemeEnseignement(null);

        } else {
            TypeSystemeEnseignement typeSystemeEnseignement = typeSystemeEnseignementRepository
                    .findByCode(dto.getTypeSystemeEnseignement().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Type système enseignement"));
            deconcentratedLevelDB.setTypeSystemeEnseignement(typeSystemeEnseignement);
        }


        if (dto.getDiplomeACA() == null || StringUtils.isBlank(dto.getDiplomeACA().getCode())) {
            deconcentratedLevelDB.setDiplomeACA(null);
        } else {

            DiplomeACA diplomeACA = diplomeACARepository.findByCode(dto.getDiplomeACA().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Diplome aca"));
            deconcentratedLevelDB.setDiplomeACA(diplomeACA);
        }

        if (dto.getDiplomePED() == null || StringUtils.isBlank(dto.getDiplomePED().getCode())) {
            deconcentratedLevelDB.setDiplomePED(null);
        } else {

            DiplomePED diplomePED = diplomePEDRepository.findByCode(dto.getDiplomePED().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " diplome ped"));
            deconcentratedLevelDB.setDiplomePED(diplomePED);
        }

        if (dto.getDiplomePROF() == null || StringUtils.isBlank(dto.getDiplomePROF().getCode())) {
            deconcentratedLevelDB.setDiplomePROF(null);
        } else {

            DiplomePROF diplomePROF = diplomePROFRepository.findByCode(dto.getDiplomePROF().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " diplome prof"));
            deconcentratedLevelDB.setDiplomePROF(diplomePROF);
        }

        Speciality speciality = specialityRepository.findByCode(dto.getSpeciality().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " spécialité"));
        deconcentratedLevelDB.setSpeciality(speciality);

        Set<Profile> profiles = new HashSet<>();

        dto.getProfils().forEach(profile -> {
            Optional<Profile> profileDB = iProfilRepository.findByCode(profile.getCode());
            profileDB.ifPresent(profiles::add);
        });

        deconcentratedLevelDB.setProfils(profiles);

        if (Objects.equals(dto.getEmail(), deconcentratedLevelDB.getEmail())) {
            deconcentratedLevelDB.setEmail(dto.getEmail());
            // Le compte est déjà activé (l'agent a déjà effectué sa première
            // connexion) : on le notifie que l'admin vient de compléter/modifier
            // ses informations. Si l'email a changé (branche ci-dessous), l'agent
            // reçoit déjà un email avec ses nouveaux identifiants, inutile de le
            // notifier une seconde fois.
            if (Boolean.FALSE.equals(deconcentratedLevelDB.getFirstLog())) {
                notificationService.sendNotificationProfilMisAJourParAdmin(
                        deconcentratedLevelDB.getEmail(), deconcentratedLevelDB.getPrenom());
            }
        } else {
            deconcentratedLevelDB.setEmail(dto.getEmail());
            String password = PasswordGenerator.generateRandomString();
            deconcentratedLevelDB.setPassword(encoder.encode(password));
            notificationService.sendNotificationToNewUserRegistred(
                    new LoginFormDTO(deconcentratedLevelDB.getEmail(), password), FIRST_CONNEXION);

        }

        return utilisateurRepository.save(deconcentratedLevelDB);
    }

    /**
     * Vérifie qu'un champ censé être unique (email, téléphone, CNI) n'est pas
     * déjà utilisé par un AUTRE utilisateur que celui en cours de modification.
     * Ignoré si la valeur est vide (champ optionnel non renseigné). Utilisé
     * uniquement lors des mises à jour : les créations ont déjà leur propre
     * contrôle de doublon (cf. validateDuplicateUser / saveUtilisateurDL).
     */
    private void checkFieldUniqueForUpdate(String value, Long currentId,
            java.util.function.Function<String, List<Utilisateur>> finder,
            MFPAIMessage errorCode, String fieldLabel) {
        if (!StringUtils.isNotBlank(value)) {
            return;
        }
        if (finder.apply(value).stream().anyMatch(existing -> !Objects.equals(existing.getId(), currentId))) {
            throw new MFPAIException(errorCode,
                    "Modification impossible : " + fieldLabel + " « " + value
                            + " » est déjà utilisé par un autre utilisateur.");
        }
    }

    private static void checkRightMatricule(UtilisateurDTO dto) {
        if (dto.getTypeMatricule().getCode().equals("MATFONC")) {
            if (Objects.nonNull(dto.getMatriculeFonctionnaire())) {
                dto.setMatricule(dto.getMatriculeFonctionnaire());
            }

        } else if (dto.getTypeMatricule().getCode().equals("MATCON")) {

            if (Objects.nonNull(dto.getMatriculeContratuel())) {
                dto.setMatricule(dto.getMatriculeContratuel());
            }
        }

        else if (dto.getTypeMatricule().getCode().equals("MATDEE")) {

            if (Objects.nonNull(dto.getMatriculeDecisionnaire())) {
                dto.setMatricule(dto.getMatriculeDecisionnaire());
            }
        }

        else {

            if (Objects.nonNull(dto.getMatriculeVacataire())) {
                dto.setMatricule(dto.getMatriculeVacataire());
            }
        }
    }

    @Transactional
    @Override
    public UserManager saveUtilisateurManager(UserManagerRequestDTO dto, boolean isRegister) {

        Optional<UserManager> utilisateurExisting = userManagerRepository.findUserManagerByEmail(dto.getEmail());
        if (utilisateurExisting.isPresent()) {
            throw new MFPAIException(i18nTranslat.toTranslate(EMAIL_DEJA_UTILISE));
        }
        UserManager utilisateur = userManagerMapper.toEntity(dto);
        String password = PasswordGenerator.generateRandomString();
        utilisateur.setPassword(password);
        utilisateur.setMatricule(dto.getMatricule());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setNom(dto.getNom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setAdresse(dto.getAdresse());
        utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));
        utilisateur.setFirstLog(true);
        utilisateur.setStatus(true);

        Set<Profile> profiles = new HashSet<>();

        dto.getProfils().forEach(profile -> {

            Optional<Profile> profileDB = iProfilRepository.findByCode(profile.getCode());
            profileDB.ifPresent(profiles::add);
        });

        utilisateur.setProfils(profiles);

        utilisateur.setCorpsGrade(null);
        utilisateur.setFonction(null);

        UserManager userManager = utilisateurRepository.saveAndFlush(utilisateur);
        notificationService.sendNotificationToNewUserRegistredByAdmin(
                new LoginFormDTO(utilisateur.getEmail(), password), FIRST_CONNEXION);

        if (isRegister) {
            notificationService.sendNotificationToNewUserRegistred(new LoginFormDTO(utilisateur.getEmail(), password),
                    FIRST_CONNEXION);
        } else {
            notificationService.sendNotificationToNewUserRegistredByAdmin(
                    new LoginFormDTO(utilisateur.getEmail(), password), FIRST_CONNEXION);
        }

        // return
        // Response.ok().setPayload(userManagerMapper.toDto(utilisateur)).setMessage(i18nTranslat.toTranslate(UTILISATEUR_RECEIVE_EMAIL));
        return userManager;
    }

    @Override
    public Utilisateur getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String identifier = authentication.getName();

            return utilisateurRepository.findUtilisateurByEmailIgnoreCase(identifier)
                    .or(() -> utilisateurRepository.findUtilisateurByNormalizedMatricule(identifier))
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.USER_NOT_FOUND));
        }

        return null;
    }

    @Override
    public Utilisateur getUserId(Long id) {
        return utilisateurRepository.findById(id).orElseThrow(() -> new MFPAIException(MFPAIMessage.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public Utilisateur changeStatut(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.USER_NOT_FOUND));
        utilisateur.setStatus(!utilisateur.getStatus());

        Utilisateur utilisateurSaving = utilisateurRepository.save(utilisateur);
        notificationService.sendNotificationStatut(utilisateurSaving);
        return utilisateurSaving;
    }

    @Override
    public List<CentralLevel> getUsersByDirection(String code) {
        return centralLevelRepository.findByDirection_Code(code);
    }

    @Override
    public List<Utilisateur> listUtilisateurByCodeProfile(String code) {

        iProfilRepository.findByCode(code).orElseThrow(
                () -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));

        List<String> profileTraitants = new ArrayList<>(Collections.singletonList(code));

        return utilisateurRepository.findByProfileCodes(profileTraitants);

    }

    private void validateProdvidedEmail(String email) {
        String domain = email.substring(email.indexOf("@") + 1);
        Optional<DisposableEmail> optionalDisposableEmail = disposableEmailRepository.findByDomain(domain);
        if (optionalDisposableEmail.isPresent())
            throw new MFPAIException(i18nTranslat.toTranslate(DOMAINE) + BLANK + domain + BLANK
                    + i18nTranslat.toTranslate(NON_AUTHORISER));
    }
    /*
     * @Override
     * public Page<Utilisateur> getPageUsers(int page, int size, String sortBy,
     * boolean sortByDescending) {
     * return null;
     * }
     * 
     */

    @Override
    public long getAllUsersCount() {
        return utilisateurRepository.findAll().size();
    }

   @Override
    @Transactional
    public CentralLevel saveUtilisateurCL(CentralLevelDTO dto) {
        Optional<Utilisateur> utilisateurByEmail = utilisateurRepository.findUtilisateurByEmail(dto.getEmail());
        Optional<Utilisateur> utilisateurCNI = utilisateurRepository.findUtilisateurByCni(dto.getCni());


        checkMatricule(dto);

        Optional<Utilisateur> matriculeOptional = utilisateurRepository.findUtilisateurByMatricule(dto.getMatricule());

        if (matriculeOptional.isPresent()) {
            throw new MFPAIException(MATRICULE_ALREADY_EXISTS, "Le matricule " + dto.getMatricule() + " existe déjà");

        }

        else if (utilisateurByEmail.isPresent()) {
            throw new MFPAIException(EMAIL_ALREADY_EXISTS,
                    "L'utilisateur avec l' email " + dto.getEmail() + " existe déjà");

        } else if (utilisateurCNI.isPresent()) {
            throw new MFPAIException(EMAIL_ALREADY_EXISTS,
                    "L'utilisateur avec le cni " + dto.getCni() + " existe déjà");
        } else {


            CentralLevel centralLevel = centralLevelMapper.toEntity(dto);

            throwExceptionWithMatAndValidDate(dto);

            if (Objects.nonNull(dto.getMatriculeContratuel())) {

                centralLevel.setIsFonctionnaire(false);

            } else if (Objects.nonNull(dto.getMatriculeFonctionnaire())) {
                centralLevel.setIsFonctionnaire(true);
            }

            String password = PasswordGenerator.generateRandomString();
            System.out.println(dto.getMatricule());
            centralLevel.setPassword(encoder.encode(password));
            centralLevel.setFirstLog(true);
            centralLevel.setStatus(true);
            centralLevel.setTypeUser(CEN);
            centralLevel.setSituationMatrimoniale(dto.getSituationMatrimoniale());
            centralLevel.setNom(dto.getNom());
            centralLevel.setPrenom(dto.getPrenom());
            centralLevel.setMatricule(dto.getMatricule());
            centralLevel.setTelephone(dto.getTelephone());
            centralLevel.setEmail(dto.getEmail());
            centralLevel.setAdresse(dto.getAdresse());
            centralLevel.setSexe(dto.getSexe());
            centralLevel.setDateDEntree(dto.getDateDEntree());
            centralLevel.setMatriculeContratuel(dto.getMatriculeContratuel());
            centralLevel.setMatriculeFonctionnaire(dto.getMatriculeFonctionnaire());
            centralLevel.setMatriculeVacataire(dto.getMatriculeVacataire());
            centralLevel.setMatriculeDecisionnaire(dto.getMatriculeDecisionnaire());
            centralLevel.setNombreEnfants(dto.getNombreEnfants());
            centralLevel.setNationalite(dto.getNationalite());
            centralLevel.setCni(dto.getCni());
            centralLevel.setLieuDeNaissance(dto.getLieuDeNaissance());
            centralLevel.setDateNaissance(dto.getDateNaissance());
            centralLevel.setDateDEntreeFonctionPub(dto.getDateDEntreeFonctionPub());
            centralLevel.setDateEntreEnseignement(dto.getDateEntreEnseignement());
            centralLevel.setDateCorp(dto.getDateCorp());
            centralLevel.setDateEntreService(dto.getDateEntreService());

            Direction direction = directionRepository.findByCode(dto.getDirection().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setDirection(direction);

            Division division = null;
            if (dto.getDivision() != null && StringUtils.isNotBlank(dto.getDivision().getCode())) {
                division = divisionRepository.findByCode(dto.getDivision().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                                "La division sélectionnée n'existe pas"));
                if (division.getDirection() == null
                        || !Objects.equals(division.getDirection().getCode(), direction.getCode())) {
                    throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                            "La division sélectionnée n'appartient pas à la direction choisie");
                }
            }
            centralLevel.setDivision(division);

            Bureau bureau = null;
            if (dto.getBureau() != null && StringUtils.isNotBlank(dto.getBureau().getCode())) {
                bureau = bureauRepository.findByCode(dto.getBureau().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                                "Le bureau sélectionné n'existe pas"));
                if (bureau.getDivision() != null
                        && (division == null
                        || !Objects.equals(bureau.getDivision().getCode(), division.getCode()))) {
                    throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                            "Le bureau sélectionné n'appartient pas à la division choisie");
                }
                if (bureau.getDivision() == null && division != null) {
                    throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                            "Le bureau sélectionné est rattaché directement à la direction et ne dépend d'aucune division");
                }
            }
            centralLevel.setBureau(bureau);

            if (dto.getService() != null && StringUtils.isNotBlank(dto.getService().getCode())) {
                Services service = serviceRepository.findByCode(dto.getService().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                if (service.getDirection() == null
                        || !Objects.equals(service.getDirection().getCode(), direction.getCode())) {
                    throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                            "Le service sélectionné n'appartient pas à la direction choisie");
                }
                centralLevel.setService(service);
            } else {
                centralLevel.setService(null);
            }

            if (dto.getRegion() == null || StringUtils.isBlank(dto.getRegion().getCode())) {
                throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                        "La région est obligatoire");
            }

            Region region = regionRepository.findByCode(dto.getRegion().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                            "La région sélectionnée n'existe pas"));

            if (direction.getRegion() != null
                    && !Objects.equals(direction.getRegion().getCode(), region.getCode())) {
                throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                        "La direction choisie n'appartient pas à la région sélectionnée");
            }
            centralLevel.setRegion(region);

            Fonction fonction = fonctionRepository.findByCode(dto.getFonction().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setFonction(fonction);

            CorpsGrade corpsGrade = corpsGradeRepository.findByCode(dto.getCorpsGrade().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setCorpsGrade(corpsGrade);

            Grade grade = gradeRepository.findByCode(dto.getGrade().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setGrade(grade);

            Speciality speciality = specialityRepository.findByCode(dto.getSpeciality().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setSpeciality(speciality);

            TypePoste typePoste = typePosteRepository.findByCode(dto.getTypePoste().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setTypePoste(typePoste);

            TypeMatricule typeMatricule = typeMatriculeRepository.findByCode(dto.getTypeMatricule().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
            centralLevel.setTypeMatricule(typeMatricule);

            if (!Objects.nonNull(dto.getDiplomeACA()) || !Objects.nonNull(dto.getDiplomeACA().getCode())
                    || !Objects.equals(dto.getDiplomeACA().getCode(), "")) {
                centralLevel.setDiplomeACA(null);
            } else {

                DiplomeACA diplomeACA = diplomeACARepository.findByCode(dto.getDiplomeACA().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                centralLevel.setDiplomeACA(diplomeACA);
            }

            if (!Objects.nonNull(dto.getDiplomePED()) || !Objects.nonNull(dto.getDiplomePED().getCode())
                    || !Objects.equals(dto.getDiplomePED().getCode(), "")) {
                centralLevel.setDiplomePED(null);
            } else {

                DiplomePED diplomePED = diplomePEDRepository.findByCode(dto.getDiplomePED().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                centralLevel.setDiplomePED(diplomePED);
            }

            if (!Objects.nonNull(dto.getDiplomePROF()) || !Objects.nonNull(dto.getDiplomePROF().getCode())
                    || !Objects.equals(dto.getDiplomePROF().getCode(), "")) {
                centralLevel.setDiplomePROF(null);
            } else {

                DiplomePROF diplomePROF = diplomePROFRepository.findByCode(dto.getDiplomePROF().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                centralLevel.setDiplomePROF(diplomePROF);
            }

            Set<Profile> profiles = new HashSet<>();

            dto.getProfils().forEach(profile -> {
                Optional<Profile> profileDB = iProfilRepository.findByCode(profile.getCode());
                profiles.add(profileDB.get());
            });

            centralLevel.setProfils(profiles);

            CentralLevel centralLevelSave = utilisateurRepository.save(centralLevel);

            if (Objects.nonNull(centralLevelSave)) {
                notificationService.sendNotificationToNewUserRegistred(
                        new LoginFormDTO(centralLevelSave.getEmail(), password), FIRST_CONNEXION);
                return centralLevelSave;

            }

            return null;
        }
    }



    @Override
    @Transactional
    public CentralLevel saveUtilisateurCLBis(CentralLevelDTO dto) {
        validateDuplicateUser(dto);

        CentralLevel centralLevel = centralLevelMapper.toEntity(dto);
        configureUserDetails(centralLevel, dto);
        setUserRelations(centralLevel, dto);

        // Assignation des profils
        assignProfiles(centralLevel, dto.getProfils());

        // Sauvegarde
        CentralLevel centralLevelSave = utilisateurRepository.save(centralLevel);

        if (Objects.nonNull(centralLevelSave)) {
            sendNotificationToNewUser(centralLevelSave);
            return centralLevelSave;
        }

        return null;
    }

    // ✅ 1. Vérification des doublons
    private void validateDuplicateUser(CentralLevelDTO dto) {
        if (utilisateurRepository.findUtilisateurByMatricule(dto.getMatricule()).isPresent()) {
            throw new MFPAIException(MATRICULE_ALREADY_EXISTS, "Le matricule " + dto.getMatricule() + " existe déjà");
        }
        if (utilisateurRepository.findUtilisateurByEmail(dto.getEmail()).isPresent()) {
            throw new MFPAIException(EMAIL_ALREADY_EXISTS, "L'utilisateur avec l'email " + dto.getEmail() + " existe déjà");
        }
        if (utilisateurRepository.findUtilisateurByCni(dto.getCni()).isPresent()) {
            throw new MFPAIException(EMAIL_ALREADY_EXISTS, "L'utilisateur avec le CNI " + dto.getCni() + " existe déjà");
        }
    }

    // ✅ 2. Configuration des détails de l'utilisateur
    private void configureUserDetails(CentralLevel centralLevel, CentralLevelDTO dto) {
        String password = PasswordGenerator.generateRandomString();
        centralLevel.setPassword(encoder.encode(password));
        centralLevel.setFirstLog(true);
        centralLevel.setStatus(true);
        centralLevel.setTypeUser(CEN);
        centralLevel.setNom(dto.getNom());
        centralLevel.setPrenom(dto.getPrenom());
        centralLevel.setMatricule(dto.getMatricule());
        centralLevel.setEmail(dto.getEmail());
    }

    // ✅ 3. Affectation des relations (direction, région, etc.)
    private void setUserRelations(CentralLevel centralLevel, CentralLevelDTO dto) {
        centralLevel.setDirection(getDirection(dto.getDirection().getCode()));
        centralLevel.setRegion(getRegion(dto.getRegion().getCode()));
        centralLevel.setFonction(getFonction(dto.getFonction().getCode()));
        centralLevel.setCorpsGrade(getCorpsGrade(dto.getCorpsGrade().getCode()));
    }

    // ✅ 4. Gestion des profils
    private void assignProfiles(CentralLevel centralLevel, Set<Profile> profils) {
        Set<Profile> profileEntities = profils.stream()
                .map(profile -> iProfilRepository.findByCode(profile.getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Profil introuvable")))
                .collect(Collectors.toSet());

        centralLevel.setProfils(profileEntities);
    }

    // ✅ 5. Méthodes utilitaires pour récupérer les relations
    private Direction getDirection(String code) {
        return directionRepository.findByCode(code)
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Direction introuvable"));
    }

    private Region getRegion(String code) {
        return regionRepository.findByCode(code)
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Région introuvable"));
    }

    private Fonction getFonction(String code) {
        return fonctionRepository.findByCode(code)
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Fonction introuvable"));
    }

    private CorpsGrade getCorpsGrade(String code) {
        return corpsGradeRepository.findByCode(code)
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Corps/Grade introuvable"));
    }

    // ✅ 6. Envoi de la notification
    private void sendNotificationToNewUser(CentralLevel user) {
        notificationService.sendNotificationToNewUserRegistred(
                new LoginFormDTO(user.getEmail(), "password"), FIRST_CONNEXION);
    }
    private static void checkMatricule(UtilisateurDTO dto) {
        if (Objects.nonNull(dto.getMatriculeContratuel()) && StringUtils.isNotBlank(dto.getMatriculeContratuel())) {

            dto.setMatricule(dto.getMatriculeContratuel());

        } else if (Objects.nonNull(dto.getMatriculeDecisionnaire())
                && StringUtils.isNotBlank(dto.getMatriculeDecisionnaire())) {

            dto.setMatricule(dto.getMatriculeDecisionnaire());

        } else if (Objects.nonNull(dto.getMatriculeVacataire())
                && StringUtils.isNotBlank(dto.getMatriculeVacataire())) {

            dto.setMatricule(dto.getMatriculeVacataire());

        }

        else if (Objects.nonNull(dto.getMatriculeFonctionnaire())
                && StringUtils.isNotBlank(dto.getMatriculeFonctionnaire())) {

            dto.setMatricule(dto.getMatriculeFonctionnaire());

        }
    }


    @Transactional
    @Override
    public Utilisateur switchUserType(Long userId) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new MFPAIException("Utilisateur non trouvé"));

        if (utilisateur instanceof CentralLevel) {
            return convertToDeconcentratedLevel((CentralLevel) utilisateur);
        } else if (utilisateur instanceof DeconcentratedLevel) {
            return convertToCentralLevel((DeconcentratedLevel) utilisateur);
        } else {
            throw new MFPAIException("Type d'utilisateur inconnu");
        }
    }

    private DeconcentratedLevel convertToDeconcentratedLevel(CentralLevel centralUser) {
        DeconcentratedLevel deconcentratedUser = new DeconcentratedLevel();
        copyCommonFields(centralUser, deconcentratedUser);
        deconcentratedUser.setRegion(centralUser.getRegion());

        centralUser.setEmail(centralUser.getEmail()+ ".update");
        centralUser.setStatus(false);

        return utilisateurRepository.save(deconcentratedUser);
    }

    private CentralLevel convertToCentralLevel(DeconcentratedLevel deconcentratedUser) {
        CentralLevel centralUser = new CentralLevel();
        copyCommonFields(deconcentratedUser, centralUser);
        centralUser.setRegion(deconcentratedUser.getRegion());

        deconcentratedUser.setEmail(deconcentratedUser.getEmail()+ ".update");
        deconcentratedUser.setMatricule(deconcentratedUser.getMatricule()+ ".update");
        deconcentratedUser.setTelephone(deconcentratedUser.getTelephone()+ ".update");
        deconcentratedUser.setStatus(false);


        return utilisateurRepository.save(centralUser);
    }

    private void copyCommonFields(Utilisateur source, Utilisateur target) {
        target.setNom(source.getNom());
        target.setPrenom(source.getPrenom());
        target.setEmail(source.getEmail());
        target.setMatricule(source.getMatricule());
        target.setTelephone(source.getTelephone());
        target.setAdresse(source.getAdresse());
        target.setSexe(source.getSexe());
        target.setDateNaissance(source.getDateNaissance());
        target.setCni(source.getCni());
        target.setSituationMatrimoniale(source.getSituationMatrimoniale());
        target.setPassword(source.getPassword());
        target.setStatus(source.getStatus());
    }

    @Override
    @Transactional
    public CentralLevel updateUtilisateurCL(Long id, CentralLevelDTO dto) {

        CentralLevel centralLevelFromDB = centralLevelRepository.findById(id).orElseThrow();
        throwExceptionWithMatAndValidDate(dto);


        checkRightMatricule(dto);

        if (StringUtils.isNotBlank(dto.getMatricule()) && utilisateurRepository
                .findAllByMatricule(dto.getMatricule()).stream()
                .anyMatch(utilisateur -> !Objects.equals(utilisateur.getId(), id))) {
            throw new MFPAIException(MATRICULE_ALREADY_EXISTS,
                    "Modification impossible : le matricule " + dto.getMatricule()
                            + " est déjà utilisé par un autre utilisateur.");
        }

        checkFieldUniqueForUpdate(dto.getEmail(), id, utilisateurRepository::findAllByEmail,
                EMAIL_ALREADY_EXISTS, "email");
        checkFieldUniqueForUpdate(dto.getTelephone(), id, utilisateurRepository::findAllByTelephone,
                TELEPHONE_ALREADY_EXISTS, "téléphone");
        checkFieldUniqueForUpdate(dto.getCni(), id, utilisateurRepository::findAllByCni,
                CNI_ALREADY_EXISTS, "CNI");



        centralLevelFromDB.setNom(dto.getNom());
        centralLevelFromDB.setPrenom(dto.getPrenom());
        centralLevelFromDB.setMatricule(dto.getMatricule());
        centralLevelFromDB.setTelephone(dto.getTelephone());
        centralLevelFromDB.setEmail(dto.getEmail());
        centralLevelFromDB.setAdresse(dto.getAdresse());
        centralLevelFromDB.setSexe(dto.getSexe());
        centralLevelFromDB.setSituationMatrimoniale(dto.getSituationMatrimoniale());
        centralLevelFromDB.setDateDEntree(dto.getDateDEntree());
        centralLevelFromDB.setMatriculeContratuel(dto.getMatriculeContratuel());
        centralLevelFromDB.setMatriculeFonctionnaire(dto.getMatriculeFonctionnaire());

        centralLevelFromDB.setMatriculeVacataire(dto.getMatriculeVacataire());
        centralLevelFromDB.setMatriculeDecisionnaire(dto.getMatriculeDecisionnaire());

        centralLevelFromDB.setNombreEnfants(dto.getNombreEnfants());
        centralLevelFromDB.setNationalite(dto.getNationalite());
        centralLevelFromDB.setCni(dto.getCni());
        centralLevelFromDB.setLieuDeNaissance(dto.getLieuDeNaissance());
        centralLevelFromDB.setDateNaissance(dto.getDateNaissance());
        centralLevelFromDB.setDateDEntreeFonctionPub(dto.getDateDEntreeFonctionPub());
        centralLevelFromDB.setDateEntreEnseignement(dto.getDateEntreEnseignement());
        centralLevelFromDB.setDateCorp(dto.getDateCorp());
        centralLevelFromDB.setDateEntreService(dto.getDateEntreService());



        Direction direction = directionRepository.findByCode(dto.getDirection().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setDirection(direction);

        if (direction.getCode().equals("DRH")) {

            if (Objects.nonNull(dto.getDivision()) &&
                Objects.nonNull(dto.getDivision().getCode()) &&
                !Objects.equals(dto.getDivision().getCode(), "")) {


                Division division = divisionRepository.findByCode(dto.getDivision().getCode())
                        .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                centralLevelFromDB.setDivision(division);

                if (!Objects.nonNull(dto.getBureau()) || !Objects.nonNull(dto.getBureau().getCode())
                        || Objects.equals(dto.getBureau().getCode(), "")) {

                    centralLevelFromDB.setBureau(null);
                    centralLevelFromDB.setService(null);
                } else {

                    Bureau bureau = bureauRepository.findByCode(dto.getBureau().getCode())
                            .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
                    centralLevelFromDB.setBureau(bureau);
                    centralLevelFromDB.setService(null);
                }
            }

            else {

                centralLevelFromDB.setService(null);
                centralLevelFromDB.setDivision(null);
                centralLevelFromDB.setBureau(null);
            }

        } else {
            centralLevelFromDB.setService(null);
            centralLevelFromDB.setDivision(null);
            centralLevelFromDB.setBureau(null);
        }

        Region region = regionRepository.findByCode(dto.getRegion().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setRegion(region);

        Fonction fonction = fonctionRepository.findByCode(dto.getFonction().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setFonction(fonction);

        CorpsGrade corpsGrade = corpsGradeRepository.findByCode(dto.getCorpsGrade().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setCorpsGrade(corpsGrade);

        Grade grade = gradeRepository.findByCode(dto.getGrade().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setGrade(grade);

        Speciality speciality = specialityRepository.findByCode(dto.getSpeciality().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setSpeciality(speciality);

        TypePoste typePoste = typePosteRepository.findByCode(dto.getTypePoste().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setTypePoste(typePoste);

        TypeMatricule typeMatricule = typeMatriculeRepository.findByCode(dto.getTypeMatricule().getCode())
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND));
        centralLevelFromDB.setTypeMatricule(typeMatricule);

        if (dto.getDiplomeACA() == null || StringUtils.isBlank(dto.getDiplomeACA().getCode())) {
            centralLevelFromDB.setDiplomeACA(null);
        } else {

            DiplomeACA diplomeACA = diplomeACARepository.findByCode(dto.getDiplomeACA().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " Diplome aca"));
            centralLevelFromDB.setDiplomeACA(diplomeACA);
        }

        if (dto.getDiplomePED() == null || StringUtils.isBlank(dto.getDiplomePED().getCode())) {
            centralLevelFromDB.setDiplomePED(null);
        } else {

            DiplomePED diplomePED = diplomePEDRepository.findByCode(dto.getDiplomePED().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " diplome ped"));
            centralLevelFromDB.setDiplomePED(diplomePED);
        }

        if (dto.getDiplomePROF() == null || StringUtils.isBlank(dto.getDiplomePROF().getCode())) {
            centralLevelFromDB.setDiplomePROF(null);
        } else {

            DiplomePROF diplomePROF = diplomePROFRepository.findByCode(dto.getDiplomePROF().getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, " diplome prof"));
            centralLevelFromDB.setDiplomePROF(diplomePROF);
        }

        Set<Profile> profiles = new HashSet<>();

        if (dto.getProfils() == null || dto.getProfils().isEmpty()) {
            throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Profil obligatoire");
        }
        dto.getProfils().forEach(profile -> {
            if (profile == null || StringUtils.isBlank(profile.getCode())) {
                throw new MFPAIException(MFPAIMessage.CODE_NOT_FOUND, "Profil obligatoire");
            }
            Profile profileDB = iProfilRepository.findByCode(profile.getCode())
                    .orElseThrow(() -> new MFPAIException(MFPAIMessage.CODE_NOT_FOUND,
                            "Profil " + profile.getCode()));
            profiles.add(profileDB);
        });

        centralLevelFromDB.setProfils(profiles);

        CentralLevel centralLevelSave = utilisateurRepository.save(centralLevelFromDB);

        if (Objects.nonNull(centralLevelSave)) {
            // Le compte est déjà activé (l'agent a déjà effectué sa première
            // connexion) : on le notifie que l'admin vient de compléter/modifier
            // ses informations.
            if (Boolean.FALSE.equals(centralLevelSave.getFirstLog())
                    && StringUtils.isNotBlank(centralLevelSave.getEmail())) {
                notificationService.sendNotificationProfilMisAJourParAdmin(
                        centralLevelSave.getEmail(), centralLevelSave.getPrenom());
            }
            return centralLevelSave;

        }

        return null;

    }

    @Override
    public Page<CentralLevel> getPageCentral(int page, int size, String sortBy, boolean sortByDescending) {
        BooleanBuilder builder = new BooleanBuilder();

        Sort sort = sortByDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return centralLevelRepository.findAll(builder, pageRequest);
    }

    @Override
    public Response<Object> getPageCentralWithFilter(int page, int size, String filter) {
        Page<CentralLevelDTO> centralLevelPage;
        BooleanBuilder builder = new BooleanBuilder();
        addCentralFreeTextCriteria(builder, QCentralLevel.centralLevel, filter);
        centralLevelPage = Objects.nonNull(builder.getValue()) ? centralLevelRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(centralLevelMapper::toDto)
                : centralLevelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(centralLevelMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(centralLevelPage.getSize())
                .number(centralLevelPage.getNumber())
                .totalElements(centralLevelPage.getTotalElements())
                .totalPages(centralLevelPage.getTotalPages())
                .build();

        return Response.ok().setPayload(centralLevelPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des  demandes de plaintes");

    }

    @Override
    public Response<Object> getPageCentralWithFilterAdvanced(int page, int size, String filter, String profile,
            String matricule, String prenom, String nom, String direction) {

        Page<CentralLevelDTO> centralLevelPage;
        BooleanBuilder builder = new BooleanBuilder();

        QCentralLevel centralLevel = QCentralLevel.centralLevel;

        if (StringUtils.isNotBlank(profile)) {
            String value = profile.trim();
            builder.and(new BooleanBuilder()
                    .or(centralLevel.profils.any().code.containsIgnoreCase(value))
                    .or(centralLevel.profils.any().label.containsIgnoreCase(value)));
        }
        addCentralMatriculeCriteria(builder, centralLevel, matricule);
        addCentralIdentityCriteria(builder, centralLevel, prenom, nom);
        if (StringUtils.isNotBlank(direction)) {
            builder.and(centralLevel.direction.code.containsIgnoreCase(direction));
        }

        addCentralFreeTextCriteria(builder, centralLevel, filter);

        centralLevelPage = Objects.nonNull(builder.getValue()) ? centralLevelRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(centralLevelMapper::toDto)
                : centralLevelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(centralLevelMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(centralLevelPage.getSize())
                .number(centralLevelPage.getNumber())
                .totalElements(centralLevelPage.getTotalElements())
                .totalPages(centralLevelPage.getTotalPages())
                .build();

        return Response.ok().setPayload(centralLevelPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste utilisateurs centrales");

    }

    @Override
    public Response<Object> getPageDeconectedWithFilter(int page, int size, String filter) {
        Page<DeconcentratedLevelDTO> deconcentratedLevelPage;
        BooleanBuilder builder = new BooleanBuilder();
        addDeconcentratedFreeTextCriteria(builder, QDeconcentratedLevel.deconcentratedLevel, filter);

        deconcentratedLevelPage = Objects.nonNull(builder.getValue()) ? deconcentratedLevelRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(deconcentratedLevelMapper::toDto)
                : deconcentratedLevelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(deconcentratedLevelMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(deconcentratedLevelPage.getSize())
                .number(deconcentratedLevelPage.getNumber())
                .totalElements(deconcentratedLevelPage.getTotalElements())
                .totalPages(deconcentratedLevelPage.getTotalPages())
                .build();

        return Response.ok().setPayload(deconcentratedLevelPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des  utilisateurs centrales");

    }

    @Override
    public Response<Object> getPageDeconectedWithFilterAdvanced(int page, int size, String filter, String profile,
            String matricule, String prenom, String nom, String region, String ia, String ief, String typeSystemeEnseignement,String etablissement) {
        Page<DeconcentratedLevelDTO> deconcentratedLevelPage;
        BooleanBuilder builder = new BooleanBuilder();
        QDeconcentratedLevel deconcentratedLevel = QDeconcentratedLevel.deconcentratedLevel;

        addDeconcentratedMatriculeCriteria(builder, deconcentratedLevel, matricule);
        addDeconcentratedIdentityCriteria(builder, deconcentratedLevel, prenom, nom);
        if (!Objects.equals(profile, "")) {
            String value = profile.trim();
            builder.and(new BooleanBuilder()
                    .or(deconcentratedLevel.profils.any().code.containsIgnoreCase(value))
                    .or(deconcentratedLevel.profils.any().label.containsIgnoreCase(value)));
        }
        if (!Objects.equals(region, "")) {
            builder.and(deconcentratedLevel.region.code.containsIgnoreCase(region));
        }
        if (!Objects.equals(ia, "")) {
            builder.and(deconcentratedLevel.ia.code.containsIgnoreCase(ia));
        }
        if (!Objects.equals(ief, "")) {
            builder.and(deconcentratedLevel.ief.code.containsIgnoreCase(ief));
        }
        if (!Objects.equals(etablissement, "")) {
            builder.and(deconcentratedLevel.etablissement.code.containsIgnoreCase(etablissement));
        }
        if (StringUtils.isNotBlank(typeSystemeEnseignement)) {
            String value = typeSystemeEnseignement.trim();
            builder.and(new BooleanBuilder()
                    .or(deconcentratedLevel.typeSystemeEnseignement.code.containsIgnoreCase(value))
                    .or(deconcentratedLevel.typeSystemeEnseignement.label.containsIgnoreCase(value)));
        }

        addDeconcentratedFreeTextCriteria(builder, deconcentratedLevel, filter);

        deconcentratedLevelPage = Objects.nonNull(builder.getValue()) ? deconcentratedLevelRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(deconcentratedLevelMapper::toDto)
                : deconcentratedLevelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(deconcentratedLevelMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(deconcentratedLevelPage.getSize())
                .number(deconcentratedLevelPage.getNumber())
                .totalElements(deconcentratedLevelPage.getTotalElements())
                .totalPages(deconcentratedLevelPage.getTotalPages())
                .build();

        return Response.ok().setPayload(deconcentratedLevelPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des utilisateurs déconcentrés");

    }

    @Override
    public CentralLevel getUserCentral(Long id) {
        return centralLevelRepository.findById(id).orElseThrow(() -> new MFPAIException(MFPAIMessage.USER_NOT_FOUND));
    }

    @Override
    public DeconcentratedLevel getUserDeconected(Long id) {
        return deconcentratedLevelRepository.findById(id)
                .orElseThrow(() -> new MFPAIException(MFPAIMessage.USER_NOT_FOUND));
    }

    @Override
    public Page<DeconcentratedLevel> getPageDecocentred(int page, int size, String sortBy, boolean sortByDescending) {
        BooleanBuilder builder = new BooleanBuilder();

        Sort sort = sortByDescending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return deconcentratedLevelRepository.findAll(builder, pageRequest);
    }

    @Override
    public Response<Object> getPageCentralAdvanced(
            int page, int size, String region, String direction, String division,
            String bureau, String specialite, String corps, String grade, String matricule, String prenom,
            String nom, String dateNaissance, String cni, String telephone, String email) {

        Page<CentralLevelDTO> centralLevelPage;
        BooleanBuilder builder = new BooleanBuilder();

        QCentralLevel centralLevel = QCentralLevel.centralLevel;

        if (StringUtils.isNotBlank(region)) {

            builder.and(centralLevel.region.code.containsIgnoreCase(region.trim()));
        }

        if (StringUtils.isNotBlank(direction.trim())) {
            builder.and(centralLevel.direction.code.containsIgnoreCase(direction.trim()));
        }

        if (StringUtils.isNotBlank(division)) {

            builder.and(centralLevel.division.code.containsIgnoreCase(division.trim()));

        }

        if (StringUtils.isNotBlank(bureau)) {

            builder.and(centralLevel.bureau.code.containsIgnoreCase(bureau.trim()));
        }
        if (StringUtils.isNotBlank(specialite)) {

            builder.and(centralLevel.speciality.code.containsIgnoreCase(specialite.trim()));
        }
        if (StringUtils.isNotBlank(corps)) {

            builder.and(centralLevel.corpsGrade.code.containsIgnoreCase(corps.trim()));
        }
        if (StringUtils.isNotBlank(grade)) {

            builder.and(centralLevel.grade.code.containsIgnoreCase(grade.trim()));
        }

        addCentralMatriculeCriteria(builder, centralLevel, matricule);
        addCentralIdentityCriteria(builder, centralLevel, prenom, nom);

        if (StringUtils.isNotBlank(cni)) {
            builder.and(centralLevel.cni.containsIgnoreCase(cni));
        }

        if (StringUtils.isNotBlank(telephone)) {
            builder.and(centralLevel.telephone.containsIgnoreCase(telephone));
        }

        if (StringUtils.isNotBlank(email)) {

            builder.and(centralLevel.email.containsIgnoreCase(email));
        }

        if (StringUtils.isNotBlank(dateNaissance)) {
            // stringToLocalDate renvoie null si la valeur ne correspond pas au format
            // attendu (ex. le littéral "null" envoyé par erreur par le client) : on
            // ignore alors le filtre au lieu de passer null à .eq(), qui lève une
            // IllegalArgumentException côté QueryDSL ("Use isNull() instead").
            LocalDate parsedDateNaissance = stringToLocalDate(dateNaissance.trim(), "yyyy-MM-dd");
            if (parsedDateNaissance != null) {
                builder.and(centralLevel.dateNaissance.eq(parsedDateNaissance));
            }
        }

        centralLevelPage = Objects.nonNull(builder.getValue()) ? centralLevelRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(centralLevelMapper::toDto)
                : centralLevelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(centralLevelMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(centralLevelPage.getSize())
                .number(centralLevelPage.getNumber())
                .totalElements(centralLevelPage.getTotalElements())
                .totalPages(centralLevelPage.getTotalPages())
                .build();

        return Response.ok().setPayload(centralLevelPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste utilisateurs centrales");
    }

    @Override
    public Response<Object> getPageDecoAdvanced(int page, int size, String region, String ia, String ief,
            String etablissement, String typeSystemeEnseignement, String specialite, String corps, String grade, String matricule, String prenom,
            String nom, String dateNaissance, String cni, String telephone, String email) {

        Page<DeconcentratedLevelDTO> deconcentratedLevelPage;
        BooleanBuilder builder = new BooleanBuilder();

        QDeconcentratedLevel deconcentratedLevel = QDeconcentratedLevel.deconcentratedLevel;
        if (StringUtils.isNotBlank(region)) {

            builder.and(deconcentratedLevel.region.code.containsIgnoreCase(region));
        }

        // if (StringUtils.isNotBlank(eefMinistere)) {

        // builder.and(deconcentratedLevel.eefMinistere.code.containsIgnoreCase(eefMinistere));
        // }
        if (StringUtils.isNotBlank(ia)) {

            builder.and(deconcentratedLevel.ia.code.containsIgnoreCase(ia));
        }
        if (StringUtils.isNotBlank(ief)) {

            builder.and(deconcentratedLevel.ief.code.containsIgnoreCase(ief));
        }
        if (StringUtils.isNotBlank(etablissement)) {
            builder.and(deconcentratedLevel.etablissement.code.containsIgnoreCase(etablissement));
        }

        if (StringUtils.isNotBlank(typeSystemeEnseignement)) {
            String value = typeSystemeEnseignement.trim();
            builder.and(new BooleanBuilder()
                    .or(deconcentratedLevel.typeSystemeEnseignement.code.containsIgnoreCase(value))
                    .or(deconcentratedLevel.typeSystemeEnseignement.label.containsIgnoreCase(value)));
        }

        if (StringUtils.isNotBlank(specialite)) {
            builder.and(deconcentratedLevel.speciality.code.containsIgnoreCase(specialite));
        }

        if (StringUtils.isNotBlank(corps)) {

            builder.and(deconcentratedLevel.corpsGrade.code.containsIgnoreCase(corps));
        }
        if (StringUtils.isNotBlank(grade)) {

            builder.and(deconcentratedLevel.grade.code.containsIgnoreCase(grade));
        }
        /*
         * if (StringUtils.isNotBlank(matricule)) {
         * builder.and(deconcentratedLevel.matricule.containsIgnoreCase(matricule));
         * }
         * 
         */

        addDeconcentratedMatriculeCriteria(builder, deconcentratedLevel, matricule);
        addDeconcentratedIdentityCriteria(builder, deconcentratedLevel, prenom, nom);

        if (StringUtils.isNotBlank(cni)) {
            builder.and(deconcentratedLevel.cni.containsIgnoreCase(cni));
        }

        if (StringUtils.isNotBlank(telephone)) {
            builder.and(deconcentratedLevel.telephone.containsIgnoreCase(telephone));
        }

        if (StringUtils.isNotBlank(email)) {

            builder.and(deconcentratedLevel.email.containsIgnoreCase(email));
        }

        if (StringUtils.isNotBlank(dateNaissance)) {
            // stringToLocalDate renvoie null si la valeur ne correspond pas au format
            // attendu (ex. le littéral "null" envoyé par erreur par le client) : on
            // ignore alors le filtre au lieu de passer null à .eq(), qui lève une
            // IllegalArgumentException côté QueryDSL ("Use isNull() instead").
            LocalDate parsedDateNaissance = stringToLocalDate(dateNaissance.trim(), "yyyy-MM-dd");
            if (parsedDateNaissance != null) {
                builder.and(deconcentratedLevel.dateNaissance.eq(parsedDateNaissance));
            }
        }

        deconcentratedLevelPage = Objects.nonNull(builder.getValue()) ? deconcentratedLevelRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(deconcentratedLevelMapper::toDto)
                : deconcentratedLevelRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                        .map(deconcentratedLevelMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(deconcentratedLevelPage.getSize())
                .number(deconcentratedLevelPage.getNumber())
                .totalElements(deconcentratedLevelPage.getTotalElements())
                .totalPages(deconcentratedLevelPage.getTotalPages())
                .build();

        return Response.ok().setPayload(deconcentratedLevelPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste utilisateurs déconcentré");
    }

    private void addCentralMatriculeCriteria(BooleanBuilder builder, QCentralLevel user, String matricule) {
        if (StringUtils.isBlank(matricule)) {
            return;
        }
        String value = matricule.trim();
        builder.and(new BooleanBuilder()
                .or(user.matricule.containsIgnoreCase(value))
                .or(user.matriculeFonctionnaire.containsIgnoreCase(value))
                .or(user.matriculeContratuel.containsIgnoreCase(value))
                .or(user.matriculeVacataire.containsIgnoreCase(value))
                .or(user.matriculeDecisionnaire.containsIgnoreCase(value)));
    }

    private void addDeconcentratedMatriculeCriteria(BooleanBuilder builder, QDeconcentratedLevel user,
            String matricule) {
        if (StringUtils.isBlank(matricule)) {
            return;
        }
        String value = matricule.trim();
        builder.and(new BooleanBuilder()
                .or(user.matricule.containsIgnoreCase(value))
                .or(user.matriculeFonctionnaire.containsIgnoreCase(value))
                .or(user.matriculeContratuel.containsIgnoreCase(value))
                .or(user.matriculeVacataire.containsIgnoreCase(value))
                .or(user.matriculeDecisionnaire.containsIgnoreCase(value)));
    }

    /**
     * Chaque mot saisi dans prénom et/ou nom peut se trouver dans l'un ou l'autre
     * champ. La recherche fonctionne ainsi même si l'administrateur inverse le nom
     * et le prénom ou saisit plusieurs prénoms séparés par des espaces.
     */
    private void addCentralIdentityCriteria(BooleanBuilder builder, QCentralLevel user, String prenom, String nom) {
        for (String token : identityTokens(prenom, nom)) {
            builder.and(new BooleanBuilder()
                    .or(user.prenom.containsIgnoreCase(token))
                    .or(user.nom.containsIgnoreCase(token)));
        }
    }

    private void addDeconcentratedIdentityCriteria(BooleanBuilder builder, QDeconcentratedLevel user,
            String prenom, String nom) {
        for (String token : identityTokens(prenom, nom)) {
            builder.and(new BooleanBuilder()
                    .or(user.prenom.containsIgnoreCase(token))
                    .or(user.nom.containsIgnoreCase(token)));
        }
    }

    private String[] identityTokens(String prenom, String nom) {
        return StringUtils.split(StringUtils.trimToEmpty(prenom) + " " + StringUtils.trimToEmpty(nom));
    }

    private void addCentralFreeTextCriteria(BooleanBuilder builder, QCentralLevel user, String filter) {
        for (String token : StringUtils.split(StringUtils.trimToEmpty(filter))) {
            builder.and(new BooleanBuilder()
                    .or(user.matricule.containsIgnoreCase(token))
                    .or(user.matriculeFonctionnaire.containsIgnoreCase(token))
                    .or(user.matriculeContratuel.containsIgnoreCase(token))
                    .or(user.matriculeVacataire.containsIgnoreCase(token))
                    .or(user.matriculeDecisionnaire.containsIgnoreCase(token))
                    .or(user.prenom.containsIgnoreCase(token))
                    .or(user.nom.containsIgnoreCase(token))
                    .or(user.email.containsIgnoreCase(token))
                    .or(user.telephone.containsIgnoreCase(token))
                    .or(user.direction.code.containsIgnoreCase(token))
                    .or(user.direction.label.containsIgnoreCase(token)));
        }
    }

    private void addDeconcentratedFreeTextCriteria(BooleanBuilder builder, QDeconcentratedLevel user,
            String filter) {
        for (String token : StringUtils.split(StringUtils.trimToEmpty(filter))) {
            builder.and(new BooleanBuilder()
                    .or(user.matricule.containsIgnoreCase(token))
                    .or(user.matriculeFonctionnaire.containsIgnoreCase(token))
                    .or(user.matriculeContratuel.containsIgnoreCase(token))
                    .or(user.matriculeVacataire.containsIgnoreCase(token))
                    .or(user.matriculeDecisionnaire.containsIgnoreCase(token))
                    .or(user.prenom.containsIgnoreCase(token))
                    .or(user.nom.containsIgnoreCase(token))
                    .or(user.email.containsIgnoreCase(token))
                    .or(user.telephone.containsIgnoreCase(token))
                    .or(user.region.code.containsIgnoreCase(token))
                    .or(user.region.label.containsIgnoreCase(token))
                    .or(user.ia.code.containsIgnoreCase(token))
                    .or(user.ia.label.containsIgnoreCase(token))
                    .or(user.ief.code.containsIgnoreCase(token))
                    .or(user.ief.label.containsIgnoreCase(token))
                    .or(user.etablissement.code.containsIgnoreCase(token))
                    .or(user.etablissement.label.containsIgnoreCase(token)));
        }
    }




    @Override
    public Utilisateur searchUser(String filter) {


        BooleanBuilder builder = new BooleanBuilder();

        QUtilisateur utilisateur = QUtilisateur.utilisateur;



        if (StringUtils.isNotBlank(filter)) {
            builder.andAnyOf(
                    utilisateur.matricule.containsIgnoreCase(filter),
                    utilisateur.cni.containsIgnoreCase(filter),
                    utilisateur.email.containsIgnoreCase(filter));
        }


      var user    = Objects.nonNull(builder.getValue()) ? utilisateurRepository
                .findOne(builder.getValue())
              : Optional.<Utilisateur>empty();

        System.out.println(user);

        return user.orElse(null);
    }

    @Override
public Response<Object> getPersonnels(int page, int size, String region, String structureCode, 
                                       String iaCode, String iefCode, String etablissementCode) {
    try {
        Page<DeconcentratedLevelDTO> deconcentratedLevelPage;
        BooleanBuilder builder = new BooleanBuilder();
        
        QDeconcentratedLevel deconcentratedLevel = QDeconcentratedLevel.deconcentratedLevel;
        Utilisateur utilisateurConnected = this.getCurrentUser();
        
        // Gestion des profils spécifiques (Chef-etablissement, etc.)
        if(utilisateurConnected != null && utilisateurConnected.getTypeUser().equals("DEC")) {
            DeconcentratedLevel deconcentratedLevel1 = (DeconcentratedLevel) utilisateurConnected;
            List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
            if(!profiles.isEmpty()) {
                Profile profile = profiles.get(0);
                switch (profile.getCode()) {
                    case "Chef-etablissement":
                        if(deconcentratedLevel1.getEtablissement() != null) {
                            builder.and(deconcentratedLevel.etablissement.code.eq(deconcentratedLevel1.getEtablissement().getCode()));
                        }
                        break;
                    case "Représentant-IEF":
                        if(deconcentratedLevel1.getIef() != null) {
                            builder.and(deconcentratedLevel.ief.code.eq(deconcentratedLevel1.getIef().getCode()));
                        }
                        break;
                    case "Representant-IA":
                        if(deconcentratedLevel1.getIa() != null) {
                            builder.and(deconcentratedLevel.ia.code.eq(deconcentratedLevel1.getIa().getCode()));
                        }
                        break;
                    default:
                        if("IA".equals(structureCode) && StringUtils.isNotBlank(iaCode)) {
                            builder.and(deconcentratedLevel.ia.code.eq(iaCode));
                        }
                        if("IA".equals(structureCode) && StringUtils.isNotBlank(iefCode)) {
                            builder.and(deconcentratedLevel.ief.code.eq(iefCode));
                        }
                        break;
                }
            }
        }
        
        // Filtres généraux
        if (StringUtils.isNotBlank(region)) {
            builder.and(deconcentratedLevel.region.code.eq(region));
        }
        if (StringUtils.isNotBlank(etablissementCode)) {
            builder.and(deconcentratedLevel.etablissement.code.eq(etablissementCode));
        }
        
        // Pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        
        deconcentratedLevelPage = Objects.nonNull(builder.getValue()) 
            ? deconcentratedLevelRepository.findAll(builder.getValue(), pageable).map(deconcentratedLevelMapper::toDto)
            : deconcentratedLevelRepository.findAll(pageable).map(deconcentratedLevelMapper::toDto);
        
        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(deconcentratedLevelPage.getSize())
                .number(deconcentratedLevelPage.getNumber())
                .totalElements(deconcentratedLevelPage.getTotalElements())
                .totalPages(deconcentratedLevelPage.getTotalPages())
                .build();
        
        return Response.ok()
                .setPayload(deconcentratedLevelPage.getContent())
                .setMetadata(pageMetadata)
                .setMessage("Liste des utilisateurs déconcentrés");
        
    } catch (Exception e) {
        log.error("Erreur lors de la récupération du personnel", e);
        return Response.exception()
                .setMessage("Une erreur est survenue lors de la récupération de la liste du personnel: " + e.getMessage());
    }
}

  @Override
public Response<Object> getPersonnelsNiveauCentral(int page, int size, String directionCode, 
                                                    String serviceCode, String divisionCode, 
                                                    String bureauCode) {
    try {
        log.info("=== DÉBUT getPersonnelsNiveauCentral ===");
        log.info("Paramètres reçus - page: {}, size: {}, direction: {}, service: {}, division: {}, bureau: {}", 
                 page, size, directionCode, serviceCode, divisionCode, bureauCode);
        
        BooleanBuilder builder = new BooleanBuilder();
        
        QCentralLevel centralLevel = QCentralLevel.centralLevel;
        
        // CRITIQUE: Filtrer UNIQUEMENT les utilisateurs de type CENTRAL
        builder.and(centralLevel.typeUser.eq("CEN"));
        log.info("Filtre ajouté: typeUser = CEN");
        
        // Filtre par Direction
        if (StringUtils.isNotBlank(directionCode)) {
            builder.and(centralLevel.direction.code.eq(directionCode));
            log.info("Filtre ajouté: direction = {}", directionCode);
        }
        
        // Filtre par Service
        if (StringUtils.isNotBlank(serviceCode)) {
            builder.and(centralLevel.service.code.eq(serviceCode));
            log.info("Filtre ajouté: service = {}", serviceCode);
        }
        
        // Filtre par Division
        if (StringUtils.isNotBlank(divisionCode)) {
            builder.and(centralLevel.division.code.eq(divisionCode));
            log.info("Filtre ajouté: division = {}", divisionCode);
        }
        
        // Filtre par Bureau
        if (StringUtils.isNotBlank(bureauCode)) {
            builder.and(centralLevel.bureau.code.eq(bureauCode));
            log.info("Filtre ajouté: bureau = {}", bureauCode);
        }
        
        // Appliquer la pagination et le tri
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        
        // Exécution de la requête
        Page<CentralLevel> centralLevelPage = centralLevelRepository.findAll(builder.getValue(), pageable);
        log.info("Nombre d'utilisateurs centraux trouvés: {}", centralLevelPage.getTotalElements());
        
        // Afficher les matricules pour vérification
        for (CentralLevel cl : centralLevelPage.getContent()) {
            log.info("Utilisateur central trouvé: matricule={}, nom={}, typeUser={}", 
                     cl.getMatricule(), cl.getNom(), cl.getTypeUser());
        }
        
        // Conversion en DTO
        Page<CentralLevelDTO> centralLevelDTOPage = centralLevelPage.map(centralLevelMapper::toDto);
        
        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(centralLevelDTOPage.getSize())
                .number(centralLevelDTOPage.getNumber())
                .totalElements(centralLevelDTOPage.getTotalElements())
                .totalPages(centralLevelDTOPage.getTotalPages())
                .build();
        
        log.info("=== FIN getPersonnelsNiveauCentral ===");
        
        return Response.ok()
                .setPayload(centralLevelDTOPage.getContent())
                .setMetadata(pageMetadata)
                .setMessage("Liste des utilisateurs du niveau central");
                
    } catch (Exception e) {
        log.error("Erreur lors de la récupération du personnel du niveau central", e);
        return Response.exception()
                .setMessage("Une erreur est survenue: " + e.getMessage());
    }
}
    

public static LocalDate stringToLocalDate(String dateString, String formatPattern) {

        if (dateString == null || dateString.trim().isEmpty())
            return null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeException e) {
            System.out.println("Date parsing failled: " + e.getMessage());
        }

        return null;
    }

    /* COK*/
    @Override
    public List<DeconcentratedLevel> getPrioritaires(int n) {

        // List<Utilisateur> utilisateurs = utilisateurRepository.findByProfileCode();
        List<DeconcentratedLevel> deconcentratedLevels = deconcentratedLevelRepository.findByProfileCode();


        deconcentratedLevels = deconcentratedLevels.stream()
                .filter(DeconcentratedLevel.class::isInstance)
                //.map(DeconcentratedLevel.class::cast)
                .toList();

        return deconcentratedLevels.stream()
                .filter(DeconcentratedLevel.class::isInstance)
        .map(DeconcentratedLevel.class::cast).toList();
    }

    @Override
    public boolean getPrioritaire(int n, String speciality) {

        List<String> profilesTraiteurs = new ArrayList<>();
        profilesTraiteurs.add("Formateur-EFF");
        List<Utilisateur> deconcentratedLevels = utilisateurRepository.findByProfileCodes(profilesTraiteurs);

        deconcentratedLevels = deconcentratedLevels.stream()

                .filter(u -> u.getSpeciality() != null && u.getSpeciality().getCode().equals(speciality))
                // Trie d'abord par date d'entrée dans l'enseignement, puis par âge (le plus âgé en premier)
                .sorted(Comparator.comparing(Utilisateur::getDateEntreEnseignement)
                        .thenComparing(u -> calculateAge(u.getDateNaissance()), Comparator.reverseOrder())
                        .thenComparing(u -> u.getSituationMatrimoniale().equals("Marié (e)"), Comparator.reverseOrder())
                    //   .thenComparingInt(Utilisateur::getNombreEnfants).reversed()


                )

                .limit(n)
                .toList();

        var currentUser =  getCurrentUser();
        return currentUser != null && deconcentratedLevels.contains(currentUser);
    }



    private int calculateAge(LocalDate dateNaissance) {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    private boolean getSituationMatrimoniale(Utilisateur utilisateur) {
        return utilisateur.getSituationMatrimoniale().equals("Célibataire");
    }

// ==================== NOUVELLES MÉTHODES POUR NIVEAU CENTRAL ====================

    @Override
    public Response<List<Direction>> getAllDirections() {
        try {
            // Récupérer toutes les directions triées par label
            List<Direction> directions = directionRepository.findAll(
                Sort.by(Sort.Direction.ASC, "label")
            );
            
            Response<List<Direction>> response = Response.ok();
            response.setPayload(directions);
            response.setMessage("Liste des directions");
            return response;
            
        } catch (Exception e) {
            log.error("Erreur lors du chargement des directions", e);
            Response<List<Direction>> response = Response.exception();
            response.setMessage("Erreur: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Response<List<Services>> getServicesByDirection(String directionCode) {
        try {
            List<Services> allServices = serviceRepository.findAll();
            List<Services> filteredServices = allServices;
            
            // Filtrer par direction si un code est fourni
            if (StringUtils.isNotBlank(directionCode)) {
                filteredServices = allServices.stream()
                        .filter(service -> service.getDirection() != null && 
                                        service.getDirection().getCode() != null &&
                                        service.getDirection().getCode().equals(directionCode))
                        .collect(Collectors.toList());
            }
            
            // Trier par label
            filteredServices.sort(Comparator.comparing(
                Services::getLabel, 
                Comparator.nullsLast(String::compareTo)
            ));
            
            Response<List<Services>> response = Response.ok();
            response.setPayload(filteredServices);
            response.setMessage("Liste des services");
            return response;
            
        } catch (Exception e) {
            log.error("Erreur lors du chargement des services", e);
            Response<List<Services>> response = Response.exception();
            response.setMessage("Erreur: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Response<List<Division>> getDivisionsByDirection(String directionCode) {
        try {
            List<Division> allDivisions = divisionRepository.findAll();
            List<Division> filteredDivisions = allDivisions;
            
            // Filtrer par direction si un code est fourni
            if (StringUtils.isNotBlank(directionCode)) {
                filteredDivisions = allDivisions.stream()
                        .filter(division -> division.getDirection() != null && 
                                        division.getDirection().getCode() != null &&
                                        division.getDirection().getCode().equals(directionCode))
                        .collect(Collectors.toList());
            }
            
            // Trier par label
            filteredDivisions.sort(Comparator.comparing(
                Division::getLabel, 
                Comparator.nullsLast(String::compareTo)
            ));
            
            Response<List<Division>> response = Response.ok();
            response.setPayload(filteredDivisions);
            response.setMessage("Liste des divisions");
            return response;
            
        } catch (Exception e) {
            log.error("Erreur lors du chargement des divisions", e);
            Response<List<Division>> response = Response.exception();
            response.setMessage("Erreur: " + e.getMessage());
            return response;
        }
    }

    @Override
    public Response<List<Bureau>> getBureausByDivision(String divisionCode) {
        try {
            List<Bureau> allBureaus = bureauRepository.findAll();
            List<Bureau> filteredBureaus = allBureaus;
            
            // Filtrer par division si un code est fourni
            if (StringUtils.isNotBlank(divisionCode)) {
                filteredBureaus = allBureaus.stream()
                        .filter(bureau -> bureau.getDivision() != null && 
                                        bureau.getDivision().getCode() != null &&
                                        bureau.getDivision().getCode().equals(divisionCode))
                        .collect(Collectors.toList());
            }
            
            // Trier par label
            filteredBureaus.sort(Comparator.comparing(
                Bureau::getLabel, 
                Comparator.nullsLast(String::compareTo)
            ));
            
            Response<List<Bureau>> response = Response.ok();
            response.setPayload(filteredBureaus);
            response.setMessage("Liste des bureaux");
            return response;
            
        } catch (Exception e) {
            log.error("Erreur lors du chargement des bureaux", e);
            Response<List<Bureau>> response = Response.exception();
            response.setMessage("Erreur: " + e.getMessage());
            return response;
        }
    }
}
