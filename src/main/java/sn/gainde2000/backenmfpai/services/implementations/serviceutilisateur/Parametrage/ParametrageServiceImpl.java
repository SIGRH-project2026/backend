package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur.Parametrage;

import com.querydsl.core.BooleanBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.*;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.DiplomesMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.FonctionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central.BureauMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central.DirectionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.central.DivisionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.RegionMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.parametrage.deconnected.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.BureauRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DirectionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DivisionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.parametrage.ParametrageService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DiplomeReqDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.RegionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.BureauDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DirectionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DivisionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.FonctionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.DiplomeBaseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.DiplomeResDTO;


import java.util.*;


import static sn.gainde2000.backenmfpai.entities.serviceutilisateur.QDiplomes.diplomes;
import static sn.gainde2000.backenmfpai.entities.serviceutilisateur.QFonction.fonction;
import static sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.QBureau.bureau;
import static sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.QDirection.direction;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-09:52
 * @project backend_mfpai
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ParametrageServiceImpl implements ParametrageService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final DirectionRepository directionRepository;
    private final DirectionMapper directionMapper;
    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;
    private final BureauRepository bureauRepository;
    private final BureauMapper bureauMapper;
    private final IAMapper iaMapper;
    private final IEFMapper iefMapper;
    private final SpecialityMapper specialityMapper;
    private final IARepository iaRepository;
    private final SpecialityRepository specialityRepository;
    private final IEFRepository iefRepository;
    private final EtablissementRepository etablissementRepository;
    private final EtablissementMapper etablissementMapper;
    private final TypeEtablissementRepository typeEtablissementRepository;
    private final TypeSystemeEnseignementRepository typeSystemeEnseignementRepository;
    private final FonctionRepository fonctionRepository;
    private final FonctionMapper fonctionMapper;
    private final IUtilisateur iUtilisateur;
    private final SpecialityEtablissementRepository specialityEtablissementRepository;
    private final DiplomeRepository diplomeRepository;
    private final DiplomesMapper diplomesMapper;
    private final TypeDiplomeRepository typeDiplomeRepository;
    private final DiplomeACARepository diplomeACARepository;
    private final DiplomePEDRepository diplomePEDRepository;
    private final DiplomePROFRepository diplomePROFRepository;


    @Override
    public Region addRegion(RegionDTO dto) {
        Region region = regionMapper.toEntity(dto);

        Optional<Region> checkExistCode = regionRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }


        return regionRepository.save(region);
    }

    @Override
    public Region updateRegion(Long id, RegionDTO dto) {
        Region region = regionRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

       region.setCode(dto.getCode());
       region.setLabel(dto.getLabel());

        return regionRepository.save(region);
    }

    @Override
    public Region getRegion(Long id) {
        return regionRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Response<Object> getPageRegion(int page, int size,String filter) {
        Page<RegionDTO> regionDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        QRegion qRegion = QRegion.region;


        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QRegion.region.code.containsIgnoreCase(filter),
                    QRegion.region.label.containsIgnoreCase(filter)
            );

        }


        regionDTOPage = Objects.nonNull(builder.getValue()) ? regionRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(regionMapper::toDto)
                : regionRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(regionMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(regionDTOPage.getSize())
                .number(regionDTOPage.getNumber())
                .totalElements(regionDTOPage.getTotalElements())
                .totalPages(regionDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(regionDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des régions");
    }

    @Override
    public Direction addDirection(DirectionDTO dto) {
        Direction direction = directionMapper.toEntity(dto);

        Region region = regionRepository.findByCode(dto.getRegion().getCode()).orElse(null);

        direction.setRegion(region);
        direction.setStatut(true);

        Optional<Direction> checkExistCode = directionRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }




        if(!directionRepository.findAll().isEmpty()) {
            List<Direction> directions = directionRepository.findAll().stream().toList();

            Direction lastDirection = directions.get(directions.size() - 1);

            direction.setId(lastDirection.getId() + 1);
        }



        return directionRepository.save(direction);
    }

    @Override
    public Direction updateDirection(Long id, DirectionDTO dto) {
        Direction direction = directionRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

        Region region = regionRepository.findByCode(dto.getRegion().getCode()).orElse(null);

        Optional<Direction> checkExistCode = directionRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            Direction thisDirection = checkExistCode.get();

            if(!thisDirection.getCode().equals(direction.getCode())){
                throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
            }
        }

        direction.setRegion(region);
        direction.setStatut(true);

        direction.setCode(dto.getCode());
        direction.setLabel(dto.getLabel());

        return directionRepository.save(direction);
    }

    @Override
    public Direction getDirection(Long id) {
        return directionRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Direction activateDirection(Long id) {
        Direction direction = directionRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(direction)){
            direction.setStatut(!direction.getStatut());
            directionRepository.save(direction);
        }

        return null;
    }

    @Override
    public Response<Object> getPageDirection(int page, int size,String filter, Boolean  statut) {
        Page<DirectionDTO> directionDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(statut)) {
            builder.and(
                    direction.statut.eq(statut)
            );
        }

        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    direction.code.containsIgnoreCase(filter),
                    direction.label.containsIgnoreCase(filter),
                    direction.region.label.containsIgnoreCase(filter)
            );

        }


        directionDTOPage = Objects.nonNull(builder.getValue()) ? directionRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(directionMapper::toDto)
                : directionRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(directionMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(directionDTOPage.getSize())
                .number(directionDTOPage.getNumber())
                .totalElements(directionDTOPage.getTotalElements())
                .totalPages(directionDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(directionDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des régions");
    }

    @Override
    public Division addDivision(DivisionDTO dto) {
        Division division = divisionMapper.toEntity(dto);


        Direction direction = directionRepository.findByCode(dto.getDirection().getCode()).orElse(null);
        division.setDirection(direction);
        division.setStatut(true);

        Optional<Division> checkExistCode = divisionRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }

        if(!divisionRepository.findAll().isEmpty()) {
            List<Division> divisions = divisionRepository.findAll().stream().toList();

            Division lastDivision = divisions.get(divisions.size() - 1);

            division.setId(lastDivision.getId() + 1);
        }


        return divisionRepository.save(division);
    }

    @Override
    public Division updateDivision(Long id, DivisionDTO dto) {
        Division division = divisionRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

        Direction direction = directionRepository.findByCode(dto.getDirection().getCode()).orElse(null);

        division.setDirection(direction);
        division.setStatut(true);

        division.setCode(dto.getCode());
        division.setLabel(dto.getLabel());

        return divisionRepository.save(division);
    }

    @Override
    public Division getDivision(Long id) {
        return divisionRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Division activateDivision(Long id) {
        Division division = divisionRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(division)){
            division.setStatut(!division.getStatut());
            divisionRepository.save(division);
        }

        return null;
    }

    @Override
    public Response<Object> getPageDivision(int page, int size,String filter,   Boolean  statut) {
        Page<DivisionDTO> divisionPage;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(statut)) {
            builder.and(
                    QDivision.division.statut.eq(statut)
            );
        }


        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QDivision.division.code.containsIgnoreCase(filter),
                    QDivision.division.label.containsIgnoreCase(filter),
                    QDivision.division.direction.label.containsIgnoreCase(filter)
            );

        }



        divisionPage = Objects.nonNull(builder.getValue()) ? divisionRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(divisionMapper::toDto)
                : divisionRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(divisionMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(divisionPage.getSize())
                .number(divisionPage.getNumber())
                .totalElements(divisionPage.getTotalElements())
                .totalPages(divisionPage.getTotalPages())
                .build();

        return Response.ok().setPayload(divisionPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des divisions");
    }

    @Override
    public Bureau addBureau(BureauDTO dto) {
        Bureau bureau = bureauMapper.toEntity(dto);

        Optional<Bureau> checkExistCode = bureauRepository.findByCode(dto.getCode());

            if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }


        Division division = null;

      
        System.out.println("Division" +   dto.getDivision())
;

        if(Objects.nonNull(dto.getDivision().getCode())) {
             division  = divisionRepository.findByCode(dto.getDivision().getCode()).orElse(null);
             bureau.setDivision(division);
        }

        
        else 
            bureau.setDivision(null);

        bureau.setStatut(true);

    

        if(!bureauRepository.findAll().isEmpty()) {
            List<Bureau> bureaus = bureauRepository.findAll().stream().toList();

            Bureau lastBureau = bureaus.get(bureaus.size() - 1);

            bureau.setId(lastBureau.getId() + 1);
        }


        System.out.println("bureau" + bureau);



        return bureauRepository.save(bureau);
    }

    @Override
    public Bureau updateBureau(Long id, BureauDTO dto) {
        Bureau bureau = bureauRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

        Division division = divisionRepository.findByCode(dto.getDivision().getCode()).orElse(null);

        bureau.setDivision(division);
        bureau.setStatut(true);

        bureau.setCode(dto.getCode());
        bureau.setLabel(dto.getLabel());

        return bureauRepository.save(bureau);
    }

    @Override
    public Bureau getBureau(Long id) {
        return bureauRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Bureau activateBureau(Long id) {
        Bureau bureau = bureauRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(bureau)){
            bureau.setStatut(!bureau.getStatut());
            bureauRepository.save(bureau);
        }

        return null;
    }

    @Override
    public Response<Object> getPageBureau(int page, int size, String filter , Boolean  statut) {
        Page<BureauDTO> bureauDTOS;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(statut)) {
            builder.and(
                    bureau.statut.eq(statut)
            );
        }
        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    bureau.code.containsIgnoreCase(filter),
                    bureau.label.containsIgnoreCase(filter),
                    bureau.division.label.containsIgnoreCase(filter)
            );
        }


        bureauDTOS = Objects.nonNull(builder.getValue()) ? bureauRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(bureauMapper::toDto)
                : bureauRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(bureauMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(bureauDTOS.getSize())
                .number(bureauDTOS.getNumber())
                .totalElements(bureauDTOS.getTotalElements())
                .totalPages(bureauDTOS.getTotalPages())
                .build();

        return Response.ok().setPayload(bureauDTOS.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des bureaus");
    }

    @Override
    public IA addIA(IADTO dto) {
        IA ia = iaMapper.toEntity(dto);

        Optional<IA> checkExistCode = iaRepository.findByCode(dto.getCode());
        Optional<IA> checkExistLabel = iaRepository.findByLabel(dto.getCode());

        Region region = regionRepository.findByCode(dto.getRegion().getCode()).orElse(null);

        ia.setRegion(region);
        ia.setStatut(true);

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }

        if (checkExistLabel.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le libellé " + dto.getCode() +" existe déjà");
        }

        if(!iaRepository.findAll().isEmpty()) {
            List<IA> ias = iaRepository.findAll().stream().toList();

            IA lastIA = ias.get(ias.size() - 1);

            ia.setId(lastIA.getId() + 1);
        }


        return iaRepository.save(ia);
    }

    @Override
    public IA updateIA(Long id, IADTO dto) {
        IA ia = iaRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

        Region region = regionRepository.findByCode(dto.getRegion().getCode()).orElse(null);

        ia.setRegion(region);
        ia.setStatut(true);

        ia.setCode(dto.getCode());
        ia.setLabel(dto.getLabel());

        return iaRepository.save(ia);
    }

    @Override
    public IA getIA(Long id) {
        return iaRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public IA activateIA(Long id) {
        IA ia  = iaRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(ia)){
            ia.setStatut(!ia.getStatut());
            iaRepository.save(ia);
        }

        return null;
    }

    @Override
    public Response<Object> getPageIA(int page, int size, String filter,  Boolean  statut) {
        Page<IADTO> iadtoPage;
        BooleanBuilder builder = new BooleanBuilder();

        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        Profile profile = profiles.get(0);


        if (utilisateurConnected.getTypeUser().equalsIgnoreCase("DEC")){
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
        }

        if (profile.getCode().equalsIgnoreCase("Representant-IA")) {
            builder.and(QIA.iA.code.eq(deconcentratedLevel.getIa().getCode()));
        }




        if (Objects.nonNull(statut)) {
            builder.and(
                    QIA.iA.statut.eq(statut)
            );
        }


        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QIA.iA.code.containsIgnoreCase(filter),
                    QIA.iA.label.containsIgnoreCase(filter),
                    QIA.iA.region.label.containsIgnoreCase(filter)
            );
        }





        iadtoPage = Objects.nonNull(builder.getValue()) ? iaRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(iaMapper::toDto)
                : iaRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(iaMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(iadtoPage.getSize())
                .number(iadtoPage.getNumber())
                .totalElements(iadtoPage.getTotalElements())
                .totalPages(iadtoPage.getTotalPages())
                .build();

        return Response.ok().setPayload(iadtoPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des bureaus");
    }



    // IEF

    @Override
    public IEF addIEF(IEFDTO dto) {
        IEF ief = iefMapper.toEntity(dto);

        Optional<IEF> checkExistCode = iefRepository.findByCode(dto.getCode());
        Optional<IEF> checkExistLabel = iefRepository.findByLabel(dto.getCode());

        IA ia = iaRepository.findByCode(dto.getIa().getCode()).orElse(null);

        ief.setIa(ia);
        ief.setStatut(true);

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }


        if (checkExistLabel.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }


        if(!iefRepository.findAll().isEmpty()) {
            List<IEF> iefs = iefRepository.findAll().stream().toList();

            IEF lastIEF = iefs.get(iefs.size() - 1);

            ief.setId(lastIEF.getId() + 1);
        }

        return iefRepository.save(ief);
    }

    @Override
    public IEF updateIEF(Long id, IEFDTO dto) {
        IEF ief = iefRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

        IA ia = iaRepository.findByCode(dto.getIa().getCode()).orElse(null);

        ief.setIa(ia);
        ief.setStatut(true);

        ief.setCode(dto.getCode());
        ief.setLabel(dto.getLabel());

        return iefRepository.save(ief);
    }

    @Override
    public IEF getIEF(Long id) {
        return iefRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public IEF activateIEF(Long id) {
        IEF ief  = iefRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(ief)){
            ief.setStatut(!ief.getStatut());
            iefRepository.save(ief);
        }

        return null;
    }

    @Override
    public Response<Object> getPageIEF(int page, int size, String filter, Boolean  statut) {
        Page<IEFDTO> iefdtoPage;
        BooleanBuilder builder = new BooleanBuilder();


        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        Profile profile = profiles.get(0);


        if (utilisateurConnected.getTypeUser().equalsIgnoreCase("DEC")){
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
        }

        if (profile.getCode().equalsIgnoreCase("Representant-IA")) {
            builder.and(QIEF.iEF.ia.code.eq(deconcentratedLevel.getIa().getCode()));
        }

        if (profile.getCode().equalsIgnoreCase("Représentant-IEF")) {
            builder.and(QIEF.iEF.code.eq(deconcentratedLevel.getIef().getCode()));
        }



        if (Objects.nonNull(statut)) {
            builder.and(
                    QIEF.iEF.statut.eq(statut)
            );
        }



        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QIEF.iEF.code.containsIgnoreCase(filter),
                    QIEF.iEF.label.containsIgnoreCase(filter),
                    QIEF.iEF.ia.label.containsIgnoreCase(filter)
            );
        }



        iefdtoPage = Objects.nonNull(builder.getValue()) ? iefRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(iefMapper::toDto)
                : iefRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(iefMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(iefdtoPage.getSize())
                .number(iefdtoPage.getNumber())
                .totalElements(iefdtoPage.getTotalElements())
                .totalPages(iefdtoPage.getTotalPages())
                .build();

        return Response.ok().setPayload(iefdtoPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des iefs");
    }


    //  Etablissement
    @Override
    public Etablissement addEtablissement(EtablissementDTO dto) {
        Etablissement etablissement = etablissementMapper.toEntity(dto);

        Optional<Etablissement> checkExistCode = etablissementRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }

        if (!Objects.nonNull(dto.getIa()) || !StringUtils.isNotBlank(dto.getIa().getCode())) {
            throw new MFPAIException("Veuillez sélectionner une IA");
        }

        if (!Objects.nonNull(dto.getTypeEtablissement()) || !StringUtils.isNotBlank(dto.getTypeEtablissement().getCode())) {
            throw new MFPAIException("Veuillez sélectionner un type d'établissement");
        }

        IA ia = iaRepository.findByCode(dto.getIa().getCode())
                .orElseThrow(() -> new MFPAIException("L'IA de code " + dto.getIa().getCode() + " n'existe pas"));

        TypeEtablissement typeEtablissement = typeEtablissementRepository.findByCode(dto.getTypeEtablissement().getCode())
                .orElseThrow(() -> new MFPAIException("Le type d'établissement de code " + dto.getTypeEtablissement().getCode() + " n'existe pas"));

        etablissement.setIa(ia);
        etablissement.setTypeEtablissement(typeEtablissement);

        if(!Objects.nonNull(dto.getIef()) ||
                !Objects.nonNull((dto.getIef().getCode()))

                || !StringUtils.isNotBlank(dto.getIef().getCode()) ||
                Objects.equals(dto.getIef().getCode(), "")){
            etablissement.setIef(null);
        }else {
            IEF ief = iefRepository.findByCode(dto.getIef().getCode()).orElse(null);
            etablissement.setIef(ief);
        }

        if (typeEtablissement.getCode().equals("CFP") || typeEtablissement.getCode().equals("LYC")) {
            if (!Objects.nonNull(dto.getTypeSystemeEnseignement()) || !StringUtils.isNotBlank(dto.getTypeSystemeEnseignement().getCode())) {
                throw new MFPAIException("Veuillez sélectionner un type système enseignement");
            }

            TypeSystemeEnseignement typeSystemeEnseignement = typeSystemeEnseignementRepository.findByCode(dto.getTypeSystemeEnseignement().getCode())
                    .orElseThrow(() -> new MFPAIException("Le type système enseignement de code " + dto.getTypeSystemeEnseignement().getCode() + " n'existe pas"));

            etablissement.setTypeSystemeEnseignement(typeSystemeEnseignement);
        } else {
            etablissement.setTypeSystemeEnseignement(null);
        }

        etablissement.setStatut(true);


        if(!etablissementRepository.findAll().isEmpty()) {
            List<Etablissement> etablissements = etablissementRepository.findAll().stream().toList();

            Etablissement lastEtablissement = etablissements.get(etablissements.size() - 1);

            etablissement.setId(lastEtablissement.getId() + 1);
        }


        return etablissementRepository.save(etablissement);
    }

    @Override
    public Etablissement updateEtablissement(Long id, EtablissementDTO dto) {
        Etablissement etablissement = etablissementRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));

        if (!Objects.nonNull(dto.getIa()) || !StringUtils.isNotBlank(dto.getIa().getCode())) {
            throw new MFPAIException("Veuillez sélectionner une IA");
        }

        if (!Objects.nonNull(dto.getTypeEtablissement()) || !StringUtils.isNotBlank(dto.getTypeEtablissement().getCode())) {
            throw new MFPAIException("Veuillez sélectionner un type d'établissement");
        }

        IA ia = iaRepository.findByCode(dto.getIa().getCode())
                .orElseThrow(() -> new MFPAIException("L'IA de code " + dto.getIa().getCode() + " n'existe pas"));

        TypeEtablissement typeEtablissement = typeEtablissementRepository.findByCode(dto.getTypeEtablissement().getCode())
                .orElseThrow(() -> new MFPAIException("Le type d'établissement de code " + dto.getTypeEtablissement().getCode() + " n'existe pas"));

        etablissement.setIa(ia);
        etablissement.setTypeEtablissement(typeEtablissement);

        if(!Objects.nonNull(dto.getIef()) ||
                !Objects.nonNull((dto.getIef().getCode()))

                || !StringUtils.isNotBlank(dto.getIef().getCode()) ||
                Objects.equals(dto.getIef().getCode(), "")){
            etablissement.setIef(null);
        }else {
            IEF ief = iefRepository.findByCode(dto.getIef().getCode()).orElse(null);
            etablissement.setIef(ief);
        }

        if (typeEtablissement.getCode().equals("CFP") || typeEtablissement.getCode().equals("LYC")) {
            if (!Objects.nonNull(dto.getTypeSystemeEnseignement()) || !StringUtils.isNotBlank(dto.getTypeSystemeEnseignement().getCode())) {
                throw new MFPAIException("Veuillez sélectionner un type système enseignement");
            }

            TypeSystemeEnseignement typeSystemeEnseignement = typeSystemeEnseignementRepository.findByCode(dto.getTypeSystemeEnseignement().getCode())
                    .orElseThrow(() -> new MFPAIException("Le type système enseignement de code " + dto.getTypeSystemeEnseignement().getCode() + " n'existe pas"));

            etablissement.setTypeSystemeEnseignement(typeSystemeEnseignement);
        } else {
            etablissement.setTypeSystemeEnseignement(null);
        }

        etablissement.setCode(dto.getCode());
        etablissement.setLabel(dto.getLabel());

        return etablissementRepository.save(etablissement);
    }

    @Override
    public Etablissement getEtablissement(Long id) {
        return etablissementRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Etablissement activateEtablissement(Long id) {
        Etablissement etablissement  = etablissementRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(etablissement)){
            etablissement.setStatut(!etablissement.getStatut());
            etablissementRepository.save(etablissement);
        }

        return null;
    }

    @Override
    public Response<Object> getPageEtablissement(int page, int size, String filter,  Boolean  statut) {
        Page<EtablissementDTO> etablissementDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        List<Profile> profiles = new ArrayList<>(utilisateurConnected.getProfils());
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        Profile p = profiles.get(0);

        if (utilisateurConnected.getTypeUser().equalsIgnoreCase("DEC")){
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
        }

        if (p.getCode().equalsIgnoreCase("Representant-IA")) {
            builder.and(QEtablissement.etablissement.ia.code.eq(deconcentratedLevel.getIa().getCode()));
        }

        if (p.getCode().equalsIgnoreCase("Représentant-IEF")) {
            builder.and(QEtablissement.etablissement.ief.code.eq(deconcentratedLevel.getIef().getCode()));
        }



        if (Objects.nonNull(statut)) {
            builder.and(
                    QEtablissement.etablissement.statut.eq(statut)
            );
        }



        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QEtablissement.etablissement.code.containsIgnoreCase(filter),
                    QEtablissement.etablissement.label.containsIgnoreCase(filter),
                    QEtablissement.etablissement.ia.label.containsIgnoreCase(filter),
                    QEtablissement.etablissement.ief.label.containsIgnoreCase(filter)
            );
        }




        etablissementDTOPage = Objects.nonNull(builder.getValue()) ? etablissementRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(etablissementMapper::toDto)
                : etablissementRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(etablissementMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(etablissementDTOPage.getSize())
                .number(etablissementDTOPage.getNumber())
                .totalElements(etablissementDTOPage.getTotalElements())
                .totalPages(etablissementDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(etablissementDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des  etablissement");
    }



    @Override
    public Speciality addSpeciality(SpecialityDTO dto) {
        Speciality speciality = specialityMapper.toEntity(dto);

        Optional<Speciality> checkExistCode = specialityRepository.findByCode(dto.getCode());
        Optional<Speciality> checkLabel = specialityRepository.findByLabel(speciality.getLabel());

        speciality.setStatut(true);

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }

        if (checkLabel.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le libellé " + dto.getCode() +" existe déjà");
        }


        if(!specialityRepository.findAll().isEmpty()) {
            List<Speciality> specialities = specialityRepository.findAll().stream().toList();

            Speciality lastSpeciality = specialities.get(specialities.size() - 1);

            speciality.setId(lastSpeciality.getId() + 1);
        }

        return specialityRepository.save(speciality);
    }

    @Override
    public Speciality updateSpeciality(Long id, SpecialityDTO dto) {
        Speciality speciality = specialityRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));
        Optional<Speciality> checkExistCode = specialityRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            Speciality thisSpeciality = checkExistCode.get();

            if(!thisSpeciality.getCode().equals(speciality.getCode())){
                throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
            }
        }

        speciality.setCode(dto.getCode());
        speciality.setLabel(dto.getLabel());

        return specialityRepository.save(speciality);
    }

    @Override
    public Speciality getSpeciality(Long id) {
        return specialityRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Speciality activateSpeciality(Long id) {
        Speciality speciality  = specialityRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(speciality)){
            speciality.setStatut(!speciality.getStatut());
            specialityRepository.save(speciality);
        }

        return null;
    }

    @Override
    public Response<Object> getPageSpeciality(int page, int size, String filter, Boolean statut) {
        Page<SpecialityDTO> specialityDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(statut)) {
            builder.and(
                    QSpeciality.speciality.statut.eq(statut)
            );
        }



        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QSpeciality.speciality.code.containsIgnoreCase(filter),
                    QSpeciality.speciality.label.containsIgnoreCase(filter)
            );
        }



        specialityDTOPage = Objects.nonNull(builder.getValue()) ? specialityRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(specialityMapper::toDto)
                : specialityRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(specialityMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(specialityDTOPage.getSize())
                .number(specialityDTOPage.getNumber())
                .totalElements(specialityDTOPage.getTotalElements())
                .totalPages(specialityDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(specialityDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des specialites");
    }


    // Fonction

    @Override
    public Fonction addFonction(FonctionDTO dto) {
        Fonction fonction = fonctionMapper.toEntity(dto);

        Optional<Fonction> checkExistCode = fonctionRepository.findByCode(dto.getCode());
        Optional<Fonction> checkExistLabel = fonctionRepository.findByLabel(dto.getCode());


        fonction.setStatut(true);

        if (checkExistCode.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
        }

        if (checkExistLabel.isPresent()) {

            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le libellé " + dto.getCode() +" existe déjà");
        }


        if(!fonctionRepository.findAll().isEmpty()) {
            List<Fonction> fonctionList = fonctionRepository.findAll().stream().toList();

            Fonction lastFonction = fonctionList.get(fonctionList.size() - 1);

            fonction.setId(lastFonction.getId() + 1);
        }


        return fonctionRepository.save(fonction);
    }

    @Override
    public Fonction updateFonction(Long id, FonctionDTO dto) {
        Fonction fonction = fonctionRepository.findById(id).orElseThrow(() -> new MFPAIException("L'id " + id + " n'existe pas"));
        Optional<Fonction> checkExistCode = fonctionRepository.findByCode(dto.getCode());

        if (checkExistCode.isPresent()) {

            Fonction thisFonction = checkExistCode.get();

            if(!thisFonction.getCode().equals(fonction.getCode())){
                throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS, "le code " + dto.getCode() +" existe déjà");
            }
        }

        fonction.setCode(dto.getCode());
        fonction.setLabel(dto.getLabel());

        return fonctionRepository.save(fonction);
    }

    @Override
    public Fonction getFonction(Long id) {
        return fonctionRepository.findById(id).orElseThrow(() ->  new MFPAIException("L'id " + id + " n'existe pas"));
    }

    @Override
    public Fonction activateFonction(Long id) {
        Fonction fonction  = fonctionRepository.findById(id).orElseThrow(null);

        if(Objects.nonNull(fonction)){
            fonction.setStatut(!fonction.getStatut());
            fonctionRepository.save(fonction);
        }

        return null;
    }

    @Override
    public Response<Object> getPageFonction(int page, int size, String filter, Boolean statut) {
        Page<FonctionDTO> fonctionDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(statut)) {
            builder.and(
                    fonction.statut.eq(statut)
            );
        }



        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    fonction.code.containsIgnoreCase(filter),
                    fonction.label.containsIgnoreCase(filter)
            );
        }



        fonctionDTOPage = Objects.nonNull(builder.getValue()) ? fonctionRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(fonctionMapper::toDto)
                : fonctionRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(fonctionMapper::toDto);

        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(fonctionDTOPage.getSize())
                .number(fonctionDTOPage.getNumber())
                .totalElements(fonctionDTOPage.getTotalElements())
                .totalPages(fonctionDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(fonctionDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des fonctions");
    }


    // SpecialityEtablissementDTO

    @Override
    public SpecialityEtablissement addSpecialityEtablissement(SpecialityEtablissementDTO dto) {

        Etablissement etablissementDB =  etablissementRepository.findByCode(dto.getEtablissement().getCode()).orElse(null);


        if(etablissementDB != null){

            Optional<Etablissement> etablissement = specialityEtablissementRepository.findByEtablissement_Id(etablissementDB.getId()).stream().map(SpecialityEtablissement::getEtablissement).findFirst();

          if(etablissement.isPresent())
                throw new MFPAIException("L'établissement existe avec");
        }


        System.out.println("dsd");

        SpecialityEtablissement specialityEtablissement = new SpecialityEtablissement();

        for(Speciality speciality: dto.getSpecialities()){

            System.out.println("ddd" + speciality);

            Speciality specialityDB = specialityRepository.findByLabel(speciality.getLabel()).orElse(null);

            specialityEtablissement.setEtablissement(etablissementDB);
            specialityEtablissement.setSpeciality(specialityDB);


            if(!specialityEtablissementRepository.findAll().isEmpty()) {
                List<SpecialityEtablissement> specialityEtablissements = specialityEtablissementRepository.findAll().stream().toList();

                SpecialityEtablissement lastSpecialityEtablissement = specialityEtablissements.get(specialityEtablissements.size() - 1);

                specialityEtablissement.setId(lastSpecialityEtablissement.getId() + 1);
            }


           specialityEtablissementRepository.save(specialityEtablissement);

           specialityEtablissement = new SpecialityEtablissement();
        }

        return specialityEtablissement;
    }

    @Override
    public SpecialityEtablissement updateSpecialityEtablissement(Long id, SpecialityEtablissementDTO dto) {

        SpecialityEtablissement specialityEtablissementDB =  specialityEtablissementRepository.findById(id).orElse(null);

        Etablissement etablissementDB =  etablissementRepository.findByCode(dto.getEtablissement().getCode()).orElse(null);


        List<Speciality> specialities = specialityEtablissementRepository.findByEtablissement_Code(specialityEtablissementDB.getEtablissement().getCode())
                .stream()
                .map(SpecialityEtablissement::getSpeciality)
                .toList();

        SpecialityEtablissement specialityEtablissement = new SpecialityEtablissement();

        for(Speciality speciality: dto.getSpecialities()){
            Speciality specialityDB = specialityRepository.findByLabel(speciality.getLabel()).orElse(null);
            if(!specialities.contains(specialityDB)){
                specialityEtablissement.setEtablissement(etablissementDB);
                specialityEtablissement.setSpeciality(specialityDB);

                specialityEtablissementRepository.save(specialityEtablissement);

                specialityEtablissement = new SpecialityEtablissement();
            }
        }



        return specialityEtablissement;
    }

    @Override
    public SpecialityEtablissement getSpecialityEtablissement(Long id) {
        return specialityEtablissementRepository.findById(id).orElse(null);
    }

    @Override
    public SpecialityEtablissement activateSpecialityEtablissement(Long id) {
        return null;
    }

    @Override
    public Response<Object> getPageSpecialityEtablissement(int page, int size, String filter, Boolean statut) {
        Page<SpecialityEtablissement> specialityEtablissementDTOS;
        BooleanBuilder builder = new BooleanBuilder();


        Pageable pageRequest = createPageRequestUsing(page, size);
        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    QSpecialityEtablissement.specialityEtablissement.speciality.label.containsIgnoreCase(filter),
                    QSpecialityEtablissement.specialityEtablissement.etablissement.label.containsIgnoreCase(filter)
            );
        }

        //specialityEtablissementRepository.findByEtablissement_Id()


   /*     specialityEtablissementDTOS = Objects.nonNull(builder.getValue()) ? specialityEtablissementRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))

                //.map(specialityEtablissementMapper::toDto)
                : specialityEtablissementRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
              //  .map(specialityEtablissementMapper::toDto)
         ;
        */
        if(Objects.nonNull(builder.getValue())) {

            List<SpecialityEtablissement> listSpecEtabs = specialityEtablissementRepository.findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).toList();


            List<Etablissement> etablissements = listSpecEtabs.stream().map(SpecialityEtablissement::getEtablissement).toList();

            List<SpecialityEtablissement> listSpecEtab = new ArrayList<>();
            SpecialityEtablissement specialityEtablissement = new SpecialityEtablissement();
            List<SpecialityEtablissement> pageContent = List.of();

            List<Etablissement> etablissementTemps = new ArrayList<>();

            for (Etablissement etablissement : etablissements) {

                if(!etablissementTemps.contains(etablissement)) {
                    List<SpecialityEtablissement> specEtabs = specialityEtablissementRepository.findByEtablissement_Id(etablissement.getId());
                    List<Speciality> specialities = specEtabs.stream().map(SpecialityEtablissement::getSpeciality).toList();

                    specialityEtablissement.setSpecialities(specialities);
                    specialityEtablissement.setEtablissement(etablissement);


                    listSpecEtab.add(specialityEtablissement);
                    etablissementTemps.add(etablissement);
                }


            }

            if(!listSpecEtab.isEmpty()) {
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), listSpecEtab.size());


                pageContent = listSpecEtab.subList(start, end);


            }

            specialityEtablissementDTOS = new PageImpl<>(pageContent, pageRequest, listSpecEtab.size());


            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(specialityEtablissementDTOS.getSize())
                    .number(specialityEtablissementDTOS.getNumber())
                    .totalElements(specialityEtablissementDTOS.getTotalElements())
                    .totalPages(specialityEtablissementDTOS.getTotalPages())
                    .build();

            return Response.ok().setPayload(specialityEtablissementDTOS.getContent()).setMetadata(pageMetadata)
                    .setMessage("Liste des etablissements specialités");
        }else {
            List<SpecialityEtablissement> listSpecEtabs = specialityEtablissementRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).toList();


            List<Etablissement> etablissements = listSpecEtabs.stream().map(SpecialityEtablissement::getEtablissement).toList();

            List<SpecialityEtablissement> listSpecEtab = new ArrayList<>();
            SpecialityEtablissement specialityEtablissement = new SpecialityEtablissement();
            List<SpecialityEtablissement> pageContent = List.of();

            List<Etablissement> etablissementTemps = new ArrayList<>();


            for (Etablissement etablissement : etablissements) {


                if(!etablissementTemps.contains(etablissement)) {
                    List<SpecialityEtablissement> specEtabs = specialityEtablissementRepository.findByEtablissement_Id(etablissement.getId());
                    List<Speciality> specialities = specEtabs.stream().map(SpecialityEtablissement::getSpeciality).toList();

                    specialityEtablissement.setSpecialities(specialities);
                    specialityEtablissement.setEtablissement(etablissement);


                    listSpecEtab.add(specialityEtablissement);
                    etablissementTemps.add(etablissement);

                    specialityEtablissement = new SpecialityEtablissement();
                }

            }

            if(!listSpecEtab.isEmpty()) {
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), listSpecEtab.size());




                if (start < listSpecEtab.size()) {
                    pageContent = listSpecEtab.subList(start, end);
                } else {
                    pageContent = List.of(); // Return an empty list if the start is out of bounds
                }



            }


            specialityEtablissementDTOS = new PageImpl<>(pageContent, pageRequest, listSpecEtab.size());


            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(specialityEtablissementDTOS.getSize())
                    .number(specialityEtablissementDTOS.getNumber())
                    .totalElements(specialityEtablissementDTOS.getTotalElements())
                    .totalPages(specialityEtablissementDTOS.getTotalPages())
                    .build();

            return Response.ok().setPayload(specialityEtablissementDTOS.getContent()).setMetadata(pageMetadata)
                    .setMessage("Liste des etablissements specialités");
        }

    }

    @Override
    @Transactional
    public Diplomes addDiplome(DiplomeReqDTO dto) {
        Diplomes diplome = diplomesMapper.toEntity(dto);

        if (diplomeRepository.findByCode(dto.getCode()).isPresent()) {
            throw new MFPAIException(MFPAIMessage.CODE_ALREADY_EXISTS,
                    "le code " + dto.getCode() + " existe déjà");
        }

        TypeDiplome typeDiplome = typeDiplomeRepository.findByCode(diplome.getTypeDiplome().getCode())
                .orElseThrow(() -> new MFPAIException("Type Diplome code not valid"));

        Diplomes newDiplome = new Diplomes();

        newDiplome.setCode(diplome.getCode());
        newDiplome.setLabel(diplome.getLabel());
        newDiplome.setTypeDiplome(typeDiplome);
        newDiplome.setStatut(true);



        switch (typeDiplome.getCode()) {
            case "ACA":
                Optional<DiplomeACA> diplomeACA = diplomeACARepository.findByCode(diplome.getCode());
                if(diplomeACA.isPresent()) {
                    throw new MFPAIException("Diplome académique ne doit pas être code dupliqué");
                }



               DiplomeACA saveDiplomeACA =  DiplomeACA.builder()
                       .id(diplomeACARepository.findMaxId() + 1)
                       .code(diplome.getCode())
                       .label(diplome.getLabel())
                       .statut(true)
                        .build();
                diplomeACARepository.save(saveDiplomeACA);


                break;
            case "PED":
                Optional<DiplomePED> diplomePED = diplomePEDRepository.findByCode(diplome.getCode());
                if(diplomePED.isPresent()) {
                    throw new MFPAIException("Diplome pédagogique ne doit pas être code dupliqué");
                }

                DiplomePED saveDiplomePED =    DiplomePED.builder()
                        .id(diplomePEDRepository.findMaxId() + 1)
                        .code(diplome.getCode())
                        .label(diplome.getLabel())
                        .statut(true)
                        .build();

                     diplomePEDRepository.save(saveDiplomePED);

                break;
            case "PRO":
                Optional<DiplomePROF> diplomePROF = diplomePROFRepository.findByCode(diplome.getCode());
                if(diplomePROF.isPresent()) {
                    throw new MFPAIException("Diplome professionel ne doit pas être code dupliqué");
                }

                DiplomePROF saveDiplomePROF =    DiplomePROF.builder()
                        .id(diplomePROFRepository.findMaxId()+ 1)
                        .code(diplome.getCode())
                        .label(diplome.getLabel())
                        .statut(true)
                        .build();
                diplomePROFRepository.save(saveDiplomePROF);
                break;
            default:
                throw new MFPAIException("Type Diplome code invalid");
        }
        return diplomeRepository.save(newDiplome);
    }

    @Override
    public Diplomes updateDiplome(Long id, DiplomeReqDTO dto) {
        return null;
    }

    @Override
    public Diplomes getDiplome(Long id) {
        return null;
    }

    @Override
    public Diplomes activateDiplome(Long id) {
        return null;
    }


    public Page<DiplomeBaseDTO> getDiplomesPage(String codeFilter, String labelFilter, int page, int size) {
        // Création des spécifications de filtre
        Specification<Diplomes> spec = Specification.where(null);

        if (codeFilter != null && !codeFilter.isEmpty()) {
            spec = spec.and(DiplomeSpecifications.hasCode(codeFilter));
        }

        if (labelFilter != null && !labelFilter.isEmpty()) {
            spec = spec.and(DiplomeSpecifications.hasLabel(labelFilter));
        }

        // Créer un objet Pageable pour la pagination
        Pageable pageable = PageRequest.of(page, size);

        // Exécution de la requête avec la spécification et la pagination
        Page<Diplomes> diplomePage = diplomeRepository.findAll(spec, pageable);

        // Mapper les résultats en DTO et retourner une page de DTO
        return diplomePage.map(diplome -> new DiplomeBaseDTO(diplome.getId(), diplome.getCode(), diplome.getLabel(), "GEN"));
    }

    @Override
    public Response<Object> getPageDiplome(int page, int size, String filter, Boolean statut, String typeDiplome) {
        Page<DiplomeResDTO> diplomeResDTOPage;
        BooleanBuilder builder = new BooleanBuilder();

        if (Objects.nonNull(statut)) {
            builder.and(
                    diplomes.statut.eq(statut)
            );
        }



        if (StringUtils.isNotBlank(filter)) {

            builder.andAnyOf(
                    diplomes.code.containsIgnoreCase(filter),
                    diplomes.label.containsIgnoreCase(filter),
                    diplomes.typeDiplome.code.containsIgnoreCase(filter),
                    diplomes.typeDiplome.label.containsIgnoreCase(filter)

            );
        }else
            if (StringUtils.isNotBlank(typeDiplome)) {

            builder.andAnyOf(
                    diplomes.typeDiplome.code.containsIgnoreCase(typeDiplome),
                    diplomes.typeDiplome.label.containsIgnoreCase(typeDiplome)

            );
        }





        diplomeResDTOPage = Objects.nonNull(builder.getValue()) ? diplomeRepository
                .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(diplomesMapper::toDto)
                : diplomeRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
                .map(diplomesMapper::toDto);


        Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                .size(diplomeResDTOPage.getSize())
                .number(diplomeResDTOPage.getNumber())
                .totalElements(diplomeResDTOPage.getTotalElements())
                .totalPages(diplomeResDTOPage.getTotalPages())
                .build();

        return Response.ok().setPayload(diplomeResDTOPage.getContent()).setMetadata(pageMetadata)
                .setMessage("Liste des fonctions");
    }


    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }



    private String createSquence(String sequence, String numero) {
        return sequence + "0" + "" + numero;
    }


}
