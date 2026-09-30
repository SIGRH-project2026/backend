package sn.gainde2000.backenmfpai.services.implementations.servicepta.pta;

import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.servicepta.pta.*;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.servicepta.parametre.ParametreRepository;
import sn.gainde2000.backenmfpai.repositories.servicepta.pta.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DirectionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DivisionRepository;
import sn.gainde2000.backenmfpai.services.interfaces.shared.IFile;
import sn.gainde2000.backenmfpai.services.interfaces.servicepta.pta.PTAService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IAuthentification;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.IndicateurPTA;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.SubActionPTAResDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:31
 * @project backend_mfpai
 */


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PTAServiceImpl implements PTAService {
    private final BusinessNotificationService businessNotifications;

    private final DirectionRepository directionRepository;

    private final PlanDeTravailRepository planDeTravailRepository;
    private final InitialPTARepository initialPTARepository;
    private final PlanDeTravailMapper planDeTravailMapper;
    private final InitialPTAMapper initialPTAMapper;
    private final TDSequenceRepository sequenceRepository;
    private final ActionPTAMapper actionPTAMapper;
    private final ResultPTAMapper resultPTAMapper;
    private final ResultPTARepository resultPTARepository;
    private final ActionPTARepository actionPTARepository;
    private final SubActionPTARepository subActionPTARepository;
    private final SubActionPTAMapper subActionPTAMapper;
    private final IAuthentification iAuthentification;
    private final ModeCalculMapper modeCalculMapper;
    private final ModeCalculRepository modeCalculRepository;
    private final ParametreRepository parametreRepository;
    private final DivisionRepository divisionRepository;
    private final ReportRealisationRepository reportRealisationRepository;
    private final ReportRealisationMapper reportRealisationMapper;
    private final IFile fileService;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public PlanDeTravail addInitialPTA(InitialPTARequestDTO dto) {

        InitialPTA initialPTA = initialPTAMapper.toEntity(dto);
        Direction direction = directionRepository.findByCode(dto.getDirection().getCode()).orElseThrow(
                () -> new MFPAIException("La direction: " + dto.getDirection().getLabel() + " est non valide")
        );

        initialPTA.setDirection(direction);
        TDSequence sequence = updateSequence();
        initialPTA.setNumeroPTA(createSquence("PTA", sequence.getNumero() + "-" + initialPTA.getDate().getYear()));

        InitialPTA initailPTASaved = initialPTARepository.save(initialPTA);

        PlanDeTravail planDeTravail = PlanDeTravail.builder()
                .initialPTA(initailPTASaved).build();

        try {
            PlanDeTravail saved = planDeTravailRepository.save(planDeTravail);
            businessNotifications.notify(iAuthentification.getCurrentConnectedUser(), "Création du PTA",
                    "Le plan de travail " + initailPTASaved.getNumeroPTA() + " a été créé.");
            return saved;
        } catch (Exception e) {

            throw new MFPAIException("Erreur d'enregistrement lors de creation du PTA");
        }


    }


    @Override
    public Response<Object> getPTAWithFilterAdvanced(int page, int size, String filter, String numPta, String libelle, String responssable, String division, String debut, String fin) {
        Page<PlanDeTravailRequestDTO> planDeTravailRequestDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        QPlanDeTravail planDeTravail = QPlanDeTravail.planDeTravail;

        if (StringUtils.isNotBlank(numPta)) {

            builder.and(planDeTravail.initialPTA.numeroPTA.containsIgnoreCase(numPta));
        }
        if (StringUtils.isNotBlank(libelle)) {

            builder.and(planDeTravail.initialPTA.nomPTA.containsIgnoreCase(libelle));
        }
        if (StringUtils.isNotBlank(division)) {
            builder.and(planDeTravail.initialPTA.direction.label.containsIgnoreCase(division));
        }

        if (StringUtils.isNotBlank(debut)) {
            builder.and(planDeTravail.initialPTA.date.after(stringToLocalDate(debut, "yyyy-MM-dd")));
        }

        if (StringUtils.isNotBlank(fin)) {
            builder.and(planDeTravail.initialPTA.date.before(stringToLocalDate(fin, "yyyy-MM-dd")));
        }


        if (StringUtils.isNotBlank(filter)) {
            builder.andAnyOf(
                    QPlanDeTravail.planDeTravail.initialPTA.numeroPTA.containsIgnoreCase(filter),
                    QPlanDeTravail.planDeTravail.initialPTA.nomPTA.containsIgnoreCase(filter))
            // QPlanDeTravail.planDeTravail.initialPTA.date.before(stringToLocalDate(filter, "yyyy-MM-dd")),
            // QPlanDeTravail.planDeTravail.initialPTA.date.after(stringToLocalDate(filter, "yyyy-MM-dd")))
            ;
        }

        planDeTravailRequestDTOPage = Objects.nonNull(builder.getValue()) ? planDeTravailRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(planDeTravailMapper::toDto)
                : planDeTravailRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(planDeTravailMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(planDeTravailRequestDTOPage.getSize())
                .number(planDeTravailRequestDTOPage.getNumber())
                .totalElements(planDeTravailRequestDTOPage.getTotalElements())
                .totalPages(planDeTravailRequestDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(planDeTravailRequestDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des plans de travail");
    }


    public static LocalDate stringToLocalDate(String dateString, String formatPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return LocalDate.parse(dateString, formatter);
    }

    @Override
    @Transactional
    public ActionPTA addActionPTA(ActionPTARequestDTO dto) {

        ActionPTA actionPTA = actionPTAMapper.toEntity(dto);


        PlanDeTravail planDeTravail = planDeTravailRepository.findById(dto.getIdPTA()).orElseThrow(
                () -> new MFPAIException("Le pta n'est pas valide")
        );

        try {

            int countAction = actionPTARepository.getActionPTAForAPTA(planDeTravail.getId()) + 1;


            List<ActionPTA> actionPTASS = new ArrayList<>();
            actionPTA.setNumAction("A." + countAction);
            ActionPTA actionSaved = actionPTARepository.save(actionPTA);

            if (Objects.nonNull(planDeTravail.getActionPTAs())) {
                actionPTASS.addAll(planDeTravail.getActionPTAs());
            }

            actionPTASS.add(actionSaved);
            planDeTravail.setActionPTAs(actionPTASS);
            planDeTravailRepository.save(planDeTravail);

            businessNotifications.notify(iAuthentification.getCurrentConnectedUser(), "Création d’une action PTA",
                    "L’action PTA " + actionSaved.getNumAction() + " a été créée.");
            return actionSaved;
        } catch (Exception e) {
            throw new MFPAIException("Erreur d'enregistrement lors de creation de l'action du PTA");
        }

    }


    @Override
    public ActionPTA updateActionPTA(Long id, ActionPTARequestDTO dto) {
        ActionPTA actionPTA = actionPTARepository.findById(id).orElseThrow(null);



        actionPTA.setLibelleAction(dto.getLibelleAction());
        return actionPTARepository.save(actionPTA);
    }



    @Override
    public ResultPTA addResultPTA(ResultPTARequestDTO dto) {
        ResultPTA resultPTA = resultPTAMapper.toEntity(dto);

        ActionPTA actionPTA = actionPTARepository.findById(dto.getActionId()).orElseThrow(
                () -> new MFPAIException("L'action n'est pas valide")
        );


        try {

            List<ResultPTA> resultPTAS = new ArrayList<>();


            ResultPTA saveResultPTA = resultPTARepository.save(resultPTA);

            if (Objects.nonNull(actionPTA.getResultActions())) {
                resultPTAS.addAll(actionPTA.getResultActions());
            }

            resultPTAS.add(saveResultPTA);

            actionPTA.setResultActions(resultPTAS);


            actionPTARepository.save(actionPTA);


            return saveResultPTA;
        } catch (Exception e) {
            throw new MFPAIException("Erreur d'enregistrement lors de creation de l'action du PTA");
        }
    }

    @Override
    public ResultPTA updateResultPTA(Long id, ResultPTARequestDTO dto) {

        ResultPTA resultPTA =  resultPTARepository.findById(id).orElseThrow(null);

        ActionPTA actionPTA = actionPTARepository.findById(dto.getActionId()).orElseThrow(
                () -> new MFPAIException("L'action n'est pas valide")
        );

       resultPTA.setCible(dto.getCible());
       resultPTA.setTauxAtteint(dto.getTauxAtteint());
       resultPTA.setLibelleResultat(dto.getLibelleResultat());

        try {

            List<ResultPTA> resultPTAS = new ArrayList<>();


            ResultPTA saveResultPTA = resultPTARepository.save(resultPTA);

            if (Objects.nonNull(actionPTA.getResultActions())) {
                resultPTAS.addAll(actionPTA.getResultActions());
            }

            resultPTAS.add(saveResultPTA);

            actionPTA.setResultActions(resultPTAS);


            actionPTARepository.save(actionPTA);


            return saveResultPTA;
        } catch (Exception e) {
            throw new MFPAIException("Erreur d'enregistrement lors de creation de l'action du PTA");
        }
    }

    @Override
    public ActionPTA addActionPTASingle(ActionPTARequestSingleDTO dto) {
        ActionPTA actionPTA = ActionPTA.builder()
                .libelleAction(dto.getLibelleAction())
                .build();


        PlanDeTravail planDeTravail = planDeTravailRepository.findById(dto.getIdPTA()).orElseThrow(
                () -> new MFPAIException("Le pta n'est pas valide")
        );

        try {

            List<ResultPTA> resultPTAS = new ArrayList<>();


            ResultPTA resultPTA = resultPTAMapper.toEntity(dto.getResultAction());
            ResultPTA saveResultPTA = resultPTARepository.save(resultPTA);
            resultPTAS.add(saveResultPTA);

            actionPTA.setResultActions(resultPTAS);

            List<ActionPTA> actionPTASS = new ArrayList<>();
            ActionPTA actionSaved = actionPTARepository.save(actionPTA);

            if (Objects.nonNull(planDeTravail.getActionPTAs())) {
                actionPTASS.addAll(planDeTravail.getActionPTAs());
            }

            actionPTASS.add(actionSaved);
            planDeTravail.setActionPTAs(actionPTASS);
            planDeTravailRepository.save(planDeTravail);

            businessNotifications.notify(iAuthentification.getCurrentConnectedUser(), "Création d’une action PTA",
                    "L’action PTA " + actionSaved.getNumAction() + " a été créée.");
            return actionSaved;
        } catch (Exception e) {
            throw new MFPAIException("Erreur d'enregistrement lors de creation de l'action du PTA");
        }
    }

    @Override
    @Transactional
    public SubActionPTA addSubActionPTA(SubActionPTAReqDTO dto) {

        SubActionPTA subActionPTA = subActionPTAMapper.toEntity(dto);

        ResultPTA resultPTA = resultPTARepository.findById(dto.getResultPTA().getId()).orElseThrow(
                () -> new MFPAIException("Le result n'existe pas "));


        Parametre indicateur = parametreRepository.findById(dto.getIndicateur().getId()).orElseThrow();

        subActionPTA.setIndicateur(indicateur);
        subActionPTA.setUtilisateur(iAuthentification.getCurrentConnectedUser());
        subActionPTA.setResultPTA(resultPTA);
        subActionPTA.setModeCalcul(null);
        subActionPTA.setReportRealisation(null);


        SubActionPTA saved = subActionPTARepository.save(subActionPTA);
        businessNotifications.notify(saved.getUtilisateur(), "Création d’une sous-action PTA",
                "La sous-action PTA " + saved.getLibelleSubAction() + " a été créée.");
        return saved;
    }

    @Override
    public SubActionPTA updateSubActionPTA(Long id, SubActionPTAReqDTO dto) {

        SubActionPTA subActionPTA = subActionPTARepository.findById(id).orElseThrow(null);

        ResultPTA resultPTA = resultPTARepository.findById(dto.getResultPTA().getId()).orElseThrow(
                () -> new MFPAIException("Le result n'existe pas "));


        Parametre indicateur = parametreRepository.findById(dto.getIndicateur().getId()).orElseThrow();

        subActionPTA.setLibelleSubAction(dto.getLibelleSubAction());
        subActionPTA.setDateFin(dto.getDateFin());
        subActionPTA.setDateDebut(dto.getDateDebut());
        subActionPTA.setBudget(dto.getBudget());
        subActionPTA.setMoyenRH(dto.getMoyenRH());
        subActionPTA.setSourceFinancement(dto.getSourceFinancement());

        subActionPTA.setIndicateur(indicateur);
        subActionPTA.setUtilisateur(iAuthentification.getCurrentConnectedUser());
        subActionPTA.setResultPTA(resultPTA);
        subActionPTA.setModeCalcul(null);
        subActionPTA.setReportRealisation(null);

        return subActionPTARepository.save(subActionPTA);
    }

    @Override
    public ModeCalcul addModeCalcul(ModeCalculRequestDTO dto) {
        ModeCalcul modeCalcul = modeCalculMapper.toEntity(dto);



        ResultPTA resultPTA = resultPTARepository.findById(dto.getResultAction().getId())
                .orElseThrow(() -> new MFPAIException("Le subaction n'existe pas "));

        Set<Division> divisions = new HashSet<>();

        for (Division division: dto.getDivisions()) {

            Division thisDivision = divisionRepository.findByCode(division.getCode()).orElseThrow();

            divisions.add(thisDivision);
        }


        modeCalcul.setDivisions(divisions);

        modeCalcul.setResultAction(resultPTA);
        ModeCalcul saveMode = modeCalculRepository.save(modeCalcul);




        SubActionPTA subActionPTA = subActionPTARepository.findById(dto.getSubAction().getId()).orElseThrow(null);


        subActionPTA.setModeCalcul(saveMode);

        subActionPTARepository.save(subActionPTA);
        return saveMode;
    }

    @Override
    public PlanDeTravail getPlanDeTravail(Long id) {
        return planDeTravailRepository.findById(id).orElseThrow(() -> new MFPAIException("Le plan de travail n'existe pas "));
    }

    @Override
    public ActionPTA getActionPTA(Long id) {
        return actionPTARepository.findById(id).orElseThrow(() -> new MFPAIException("L'action n'existe pas "));
    }

    @Override
    public List<ActionPTA> getListActionPTA(Long id) {
     return actionPTARepository.getListAction(id);

    }

    @Override
    public ActionPTA deleteActionPTA(Long id) {

       actionPTARepository.deleteById(id);

       return null;

    }

    @Override
    public ResultPTA getResultPTA(Long id) {
        return resultPTARepository.findById(id).orElseThrow(() -> new MFPAIException("Le result n'existe pas "));
    }

    @Override
    public SubActionPTA getSubActionPTA(Long id) {

        return subActionPTARepository.findById(id).orElseThrow(() -> new MFPAIException("L'id subaction n'existe pas "));
    }

    @Override
    public ModeCalcul getModeCalcul(Long id) {
        return modeCalculRepository.findById(id).orElseThrow(() -> new MFPAIException("Le mode calcul n'existe pas "));
    }

    @Override
    public ReportRealisation addReportRealisation(MultipartFile[] files,ReportRealisationDTO dto) {
        ReportRealisation reportRealisation =  reportRealisationMapper.toEntity(dto);

        SubActionPTA subActionPTA = subActionPTARepository.findById(dto.getSubAction().getId()).orElseThrow(null);

        ReportRealisation saveReport = reportRealisationRepository.save(reportRealisation);

        Set<File> uploadedFiles = new HashSet<>();
        if (files != null && files.length > 0) {

            for (MultipartFile file : files) {
                System.out.println(file.getOriginalFilename());
                FileRspDTO uploadedFileDto = fileService.storeFile(file, "reportRealisation", false);

                File uploadedFile = new File();
                uploadedFile.setOriginalName(uploadedFileDto.getOriginalName());
                uploadedFile.setGeneratedName(uploadedFileDto.getGeneratedName());
                uploadedFile.setIdAppartenance(0);
                uploadedFile.setFileType(uploadedFileDto.getFileType());
                File saveFile = fileRepository.save(uploadedFile);
                uploadedFiles.add(saveFile);
            }

        }

        saveReport.setFiles(uploadedFiles);

        System.out.println(saveReport);
        reportRealisationRepository.save(saveReport);





        subActionPTA.setReportRealisation(saveReport);

        subActionPTARepository.save(subActionPTA);

        businessNotifications.notify(subActionPTA.getUtilisateur(), "Rapport de réalisation PTA",
                "Un rapport de réalisation a été enregistré pour la sous-action " + subActionPTA.getLibelleSubAction() + ".");
        return saveReport;
    }

    @Override
    public ReportRealisation getReportRealisation(Long id) {
        return reportRealisationRepository.findById(id).orElseThrow(()-> new MFPAIException("Le reportRealisation n'existe pas "));
    }


    @Override
    public Response<Object> getAllResultPTA(int page, int size ,Long id) {
        Page<ResultPTA> resultPTAS =resultPTARepository.getResultFromAction(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")),
                id);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(resultPTAS.getSize())
                .number(resultPTAS.getNumber())
                .totalElements(resultPTAS.getTotalElements())
                .totalPages(resultPTAS.getTotalPages())
                .build();

        return Response.ok().setPayload(resultPTAS.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des sous actions");

    }


    @Override
    public Response<Object> getSubAction(Long id, int page, int size, String filter) {

        Page<SubActionPTAResDTO> subActionPTAS = subActionPTARepository.findByResultPTA_Id(id, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(subActionPTAMapper::toDto);

        for (SubActionPTAResDTO subActionPTA :  subActionPTAS.getContent()) {

            String bureaux = subActionPTA.getIndicateur().getBureaux().stream().map(Bureau::getLabel).collect(Collectors.joining(","));
            String divisions = subActionPTA.getIndicateur().getDivisions().stream().map(Division::getLabel).collect(Collectors.joining(","));

            subActionPTA.getIndicateur().setListDivisions(divisions);
            subActionPTA.getIndicateur().setListBureaux(bureaux);
        }

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(subActionPTAS.getSize())
                .number(subActionPTAS.getNumber())
                .totalElements(subActionPTAS.getTotalElements())
                .totalPages(subActionPTAS.getTotalPages())
                .build();

        return Response.ok().setPayload(subActionPTAS.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des sous actions");
    }

    @Override
    public Response<Object> getStaPTA() {

        var lastPta = planDeTravailRepository.getLastPTA();

        System.out.println(lastPta.getId());

        List<ActionPTA> actionPTAS = actionPTARepository.countActionPTAByPtaId(lastPta.getId());


        return Response.ok().setPayload(
                IndicateurPTA.builder()
                        .nombreAction(actionPTAS.size())
                        .actions(actionPTAS)
                        .build()
        );
    }


    private TDSequence updateSequence() {
        TDSequence lastSequence = sequenceRepository.findTopByOrderByIdDesc();
        lastSequence.setNumero(lastSequence.getNumero() + 1);
        return lastSequence;

    }


    private String createSquence(String sequence, String numero) {
        return sequence + "0" + "" + numero;
    }
}
