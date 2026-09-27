
package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nTranslate;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Discipline;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.FormationProfessionel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Niveau;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Serie;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomeACA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePED;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePROF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeMatricule;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypePoste;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeDiplome;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEFMinistere;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEFSpeciality;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.SpecialityEtablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeEtablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.FormationProfessionelRespository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.NiveauRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.SerieRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.FicheEtablissement.IDisciplineRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.CorpsGradeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.DiplomeACARepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.DiplomePEDRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.DiplomePROFRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.FonctionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.GradeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.TypeMatriculeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.TypePosteRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.TypeDiplomeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.BureauRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DirectionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DivisionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.ServiceRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EEFMinistereReporitory;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EEFRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EEFSpecilialityRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EtablissementRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.IARepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.IEFRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.RegionRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.SpecialityEtablissementRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.SpecialityRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.StructureRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.TypeEtablissementRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.TypeSystemeEnseignementRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.ReferenceService;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.IndicateurIaIefEtab;

/**
 * @author G2k R&D
 */

@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService {
    private static final String CEN = "CEN";
    private static final String DEC = "DEC";
    private final IProfilRepository profilRepository;
    private final I18nTranslate i18nTranslate;

    private final DirectionRepository directionRepository;
    private final BureauRepository bureauRepository;
    private final DivisionRepository divisionRepository;
    private final ServiceRepository serviceRepository;
    private final FonctionRepository fonctionRepository;
    private final CorpsGradeRepository corpsGradeRepository;
    private final IARepository iaRepository;
    private final IEFRepository iefRepository;
    private final SpecialityRepository specialityRepository;
    private final EtablissementRepository typeDetablissementRepository;
    private final TypeEtablissementRepository typeEtablissementRepository;
    private final TypeSystemeEnseignementRepository typeSystemeEnseignementRepository;
    private final StructureRepository structureMFPAARepository;
    private final EEFRepository eefRepository;
    private final RegionRepository regionRepository;

    private final IDisciplineRepository iDisciplineRepository;
    private final GradeRepository gradeRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;

    private final TypePosteRepository typePosteRepository;
    private final TypeDiplomeRepository typeDiplomeRepository;
    private final DiplomeACARepository diplomeACARepository;
    private final DiplomePROFRepository diplomePROFRepository;
    private final DiplomePEDRepository diplomePEDRepository;
    private final TypeMatriculeRepository typeMatriculeRepository;

    private final SpecialityEtablissementRepository specialiteEEFRepository;
    private final EEFMinistereReporitory eefMinistereReporitory;



    private final FormationProfessionelRespository formationProfessionelRespository;
    private final NiveauRepository niveauRepository;
    private final SerieRepository serieRepository;
    private final EEFSpecilialityRepository eefSpecilialityRepository;
    private final IUtilisateur iUtilisateur;
    private final EtablissementRepository etablissementRepository;

    @Override
    public List<Profile> listeProfil() {
        return profilRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Profile> listeProfilByTypeCEN() {
        return profilRepository.findByTypeProfile(CEN);
    }

    @Override
    public List<Profile> listeProfilByTypeDEC() {
        return profilRepository.findByTypeProfile(DEC);
    }

    @Override
    public Optional<Profile> findByProId(Long proId) {
        return profilRepository.findById(proId);
    }

    @Override
    public List<Direction> listDirection() {
        return directionRepository.findAll(Sort.by("label").ascending()).stream().filter(Direction::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<Bureau> listBureau() {
        return bureauRepository.findAll(Sort.by("label").ascending()).stream().filter(Bureau::getStatut).collect(Collectors.toList());
    }


    @Override
    public List<Division> listDivision() {
        return divisionRepository.findAll(Sort.by("label").ascending()).stream().filter(Division::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<Division> listDivisionByDirectionCode(String code) {
        return divisionRepository.findByDirection_Code(code).stream().filter(Division::getStatut).collect(Collectors.toList());

    }

    @Override
    public List<Services> listService() {
        return serviceRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Services> listServiceByDirectionCode(String code) {
        return serviceRepository.findByDirection_Code(code);
    }

    @Override
    public List<Fonction> listFonction() {
        return fonctionRepository.findAll(Sort.by("label").ascending()).stream().filter(Fonction::getStatut).collect(Collectors.toList());

    }

    @Override
    public List<CorpsGrade> listCorpsGrade() {
        return corpsGradeRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Region> listRegion() {
        return regionRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<IA> listIA() {
        return iaRepository.findAll(Sort.by("label").ascending()).stream().filter(IA::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<IA> listIAByRegionCode(String code) {
        return iaRepository.findByRegion_Code(code).stream().filter(IA::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<IEF> listIEFByIACode(String code) {
        return iefRepository.findByIa_Code(code).stream().filter(IEF::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<IEF> listIEF() {
        return iefRepository.findAll(Sort.by("label").ascending()).stream().filter(IEF::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<Etablissement> listEtablissement() {
        return typeDetablissementRepository.findAll(Sort.by("label").ascending()).stream().filter(Etablissement::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<Etablissement> listEtablissementByCFPCode(String code) {
        if (!Objects.isNull(code) | !StringUtils.isBlank(code)) {
            return typeDetablissementRepository.findByIef_Code(code).stream().filter(Etablissement::getStatut).collect(Collectors.toList());
        }

        return List.of();
    }



    @Override
    public List<Etablissement> listEtablissementByType(String code) {
        if (!Objects.isNull(code) | !StringUtils.isBlank(code)) {
            return typeDetablissementRepository.findByTypeEtablissement_Code(code)
                    .stream().filter(Etablissement::getStatut).toList();
        }

        return List.of();
    }

    @Override
    public TypeSystemeEnseignement getTypeSystemeEnseignementByCode(String code) {
        if (!Objects.isNull(code) | !StringUtils.isBlank(code)) {
            return typeSystemeEnseignementRepository.findByCode(code).orElse(null);
        }

        return null;
    }


    @Override
    public List<TypeEtablissement> listTypeEtablissement() {
        return typeEtablissementRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Etablissement> listEtablissementByTypeSystemeEnseignement(String code) {
        if (!Objects.isNull(code) | !StringUtils.isBlank(code)) {
            return typeDetablissementRepository.findByTypeSystemeEnseignement_Code(code)
                    .stream().filter(Etablissement::getStatut).toList();
        }
        return List.of();
    }

    // public List<TypeSystemeEnseignement> listTypeSystemeEnseignement() {
    //     List<TypeSystemeEnseignement> list = typeSystemeEnseignementRepository.findAll();
    //     System.out.println("listTypeSystemeEnseignement: " + list);
    //     return list;
    // }

    public List<TypeSystemeEnseignement> listTypeSystemeEnseignement() {
        return typeSystemeEnseignementRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Speciality> listTypeSpeciality() {
        return specialityRepository.findAll(Sort.by("label").ascending()).stream().filter(Speciality::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<Discipline> listDisciplines() {
        return iDisciplineRepository.findAll(Sort.by("libelle").ascending());
    }

    @Override
    public List<Grade> listGrade() {
        return gradeRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Grade> listGradeFilter() {
      //  return gradeRepository.getGradeUniqueLabel();
        List<Grade> allGrades = gradeRepository.getGradeUniqueLabel();

        return allGrades.stream()
                .filter(distinctByKey(Grade::getLabel))
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(java.util.function.Function<? super T, Object> keyExtractor) {
      Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Override
    public List<Grade> listGradeByCorpsCode(String code) {
        return gradeRepository.findByCorpsGrade_CodeOrderByIdAsc(code);
    }

    @Override
    public List<DeconcentratedLevel> listUserFromEtablissementCode(String code) {
        return deconcentratedLevelRepository.getDeconnectedByProfilEtablissement(code);
    }

    @Override
    public List<Profile> getTypeProfileDivision(String code) {

        if (Objects.nonNull(code)) {
            return switch (code) {
                case "DFC", "DGPEEC", "DGCAA", "DAS", "ADIV" -> profilRepository.findByTypeProfileDivision(code);
                /*
                 * case "ADIV":
                 * return profilRepository.findByTypeProfileBureau(code);
                 * 
                 */
                default -> List.of();// profilRepository.findByTypeProfile(CEN);
            };

        }

        // return profilRepository.findByTypeProfile(CEN);
        return List.of();
    }

    @Override
    public List<Profile> getTypeProfileDirection(String code) {
        if (Objects.nonNull(code)) {
            if (code.equals("DRH")) {
                return profilRepository.findByTypeProfileDirection(code);
            }else {
                return profilRepository.findAll().stream()
                        .filter(
                                codeProfile ->
                               // codeProfile.getCode().equals("Coordinateur") ||
                              //  codeProfile.getCode().equals("SG-Cabinet") ||
                              //  codeProfile.getCode().equals("Agent-ministre") ||
                                codeProfile.getCode().equals("Agent") ||
                                codeProfile.getCode().equals("Chef-service")
                        ).collect(Collectors.toList());
            }
        }
        return List.of();
    }

    @Override
    public List<Bureau> listBureauByDivisionCode(String code) {

        /*if(Objects.nonNull(code)) {
            if (code.equals("ADIV")) {
                System.out.println(code);
                return bureauRepository.findByDivision_Code(code).stream().filter(
                        codeBureau -> codeBureau.getCode().equals("BUCO")).collect(Collectors.toList());
            }
        }*/
      return bureauRepository.findByDivision_Code(code).stream().filter(Bureau::getStatut).collect(Collectors.toList());
    }


    @Override
    public List<Profile> getTypeProfileBureau(String code) {
        if (Objects.nonNull(code)) {

            return switch (code) {
                /*
                 case "BUAS", "ASDD", "BUGE", "BUPA", "BUSE", "BUCO",
                     "BUAA", "BUSI", "BUGC", "BUMR", "BSES", "BUFN",
                     "BSAMS","BFNC", "BUMC"
                 */
                case "BUAS", "ASDD", "BUGE", "BUPA", "BUSE", "BUCO" -> profilRepository.findByTypeProfileBureau(code);
                case "DFC", "DGCAA", "DGPEEC", "DAS" -> profilRepository.findByTypeProfileBureau(code);
                default -> List.of();
            };

        }

        return List.of();
    }

    @Override
    public List<Profile> getTypeProfileWithOutCD(String code) {
        if (Objects.nonNull(code)) {

            return switch (code) {

                case "DFC", "DGCAA", "DGPEEC", "DAS" -> profilRepository.findByTypeProfileBureau(code);
                default -> List.of();
            };

        }

        return List.of();

    }

    @Override
    public List<TypePoste> listTypePoste() {
        return typePosteRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<TypeDiplome> listTypeDiplome() {
        return typeDiplomeRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<DiplomeACA> listDiplomeACA() {
        return diplomeACARepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<DiplomePROF> listDiplomePROF() {
        return diplomePROFRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<DiplomePED> listDiplomePED() {
        return diplomePEDRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<TypeMatricule> listTypeMatricule() {
        return typeMatriculeRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Etablissement> listEtablissementByIACode(String code) {
        return typeDetablissementRepository.findByIa_Code(code).stream().filter(Etablissement::getStatut)
                .filter(rep -> rep.getTypeEtablissement() != null)
                .filter(rep -> "LYC".equals(rep.getTypeEtablissement().getCode())
                        || "EFF".equals(rep.getTypeEtablissement().getCode()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Etablissement> listEtablissementByIEFCode(String code) {
        if (Objects.nonNull(code)) {
            return typeDetablissementRepository.findByIef_Code(code).stream().filter(Etablissement::getStatut).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<Direction> listDirectionByCode(String code) {
        return directionRepository.findDirectionByCode(code);
    }

    @Override
    public List<FormationProfessionel> lisFormationProfessionels() {
        return formationProfessionelRespository.findAll(Sort.by("libelle").ascending());
    }

    @Override
    public List<Serie> listSeries(String code) {
        return serieRepository.findByFormationProfessionel_Code(code);
    }

    @Override
    public List<Niveau> listNiveaus(String code) {
        return niveauRepository.findByFormationProfessionel_Code(code);
    }

    @Override
    public List<Speciality> findByEEFCode(String code) {
        return  specialiteEEFRepository.findByEtablissement_Code(code).stream().map(SpecialityEtablissement::getSpeciality).filter(Speciality::getStatut).collect(Collectors.toList());
    }

    @Override
    public List<EEFMinistere> findByMinistreEEFCode(String code) {
        return eefMinistereReporitory.findByRegion_Code(code);
    }

    @Override
    public List<EEFMinistere> findByMinistreEEF() {
        return eefMinistereReporitory.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Etablissement> listEtablissementByEFFCode(String code) {
        if (!Objects.isNull(code) | !StringUtils.isBlank(code)) {
            return typeDetablissementRepository.findByStructure_Code( code).stream().filter(Etablissement::getStatut).collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public List<CorpsGrade> listCorpsByMatricule(String code) {
        if (Objects.nonNull(code)) {

            return corpsGradeRepository.findByTypeMatricule(code);

        }

        return List.of();
    }

    @Override
    public List<EEF> lisEEF() {
        return eefRepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<EEFSpeciality> lisEEFSpecByEEFCode(String code) {
        return eefSpecilialityRepository.findByEef_Code(code);
    }

    @Override
    public List<Structure> lisStructureMfpaas() {
        return structureMFPAARepository.findAll(Sort.by("label").ascending());
    }

    @Override
    public List<Structure> lisStructure() {
        return structureMFPAARepository.findAll(Sort.by("label").ascending());
    }






    @Override
    public Response<Object> indicateurIaIefEtab(String codeProfile) {
        long indIa = 0;
        long indIef = 0;
        long indEtab = 0;
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (utilisateurConnected.getTypeUser().equals("DEC"))
           deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;

            switch (codeProfile) {

                case "Representant-IA":
                    indIef = iefRepository.countAllIefIa(deconcentratedLevel.getIa().getCode());
                    indEtab = etablissementRepository.countAllEtablissementIa(deconcentratedLevel.getIa().getCode());
                    indIa = 0;
                    break;
                case "Représentant-IEF":
                    indEtab = etablissementRepository.countAllEtablissementIef(deconcentratedLevel.getIef().getCode());
                    indIa = 0;
                    indIef = 0;
                    break;
                default:
                    indIef = iefRepository.countAllIef();
                    indIa = iaRepository.countAllIa();
                    indEtab = etablissementRepository.countAllEtablissemen();
                    break;
            }
        IndicateurIaIefEtab indicateurIaIefEtab = new IndicateurIaIefEtab();
            indicateurIaIefEtab.setIndEtab(indEtab);
            indicateurIaIefEtab.setIndIef(indIef);
            indicateurIaIefEtab.setIndIa(indIa);

        return Response.ok().setMessage("Indicateurs besoin en personnels").setPayload(indicateurIaIefEtab);
    }

    

}
