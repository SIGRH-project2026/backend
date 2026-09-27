
package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur;

import java.util.List;
import java.util.Optional;

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
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeEtablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

/**
 * @author G2k R&D
 */
public interface ReferenceService {
    List<Profile> listeProfil();

    List<Profile> listeProfilByTypeCEN();

    List<Profile> listeProfilByTypeDEC();

    Optional<Profile> findByProId(Long proId);

    List<Direction> listDirection();

    List<Bureau> listBureau();

    List<Bureau> listBureauByDivisionCode(String code);

    List<Division> listDivision();

    List<Division> listDivisionByDirectionCode(String code);

    List<Services> listService();

    List<Services> listServiceByDirectionCode(String code);

    List<Fonction> listFonction();

    List<CorpsGrade> listCorpsGrade();

    List<Region> listRegion();

    List<IA> listIA();

    List<IA> listIAByRegionCode(String code);

    List<IEF> listIEFByIACode(String code);

    List<IEF> listIEF();

    List<Etablissement> listEtablissement();

    List<Etablissement> listEtablissementByCFPCode(String code);
    List<Etablissement> listEtablissementByType(String code);
    List<Etablissement> listEtablissementByTypeSystemeEnseignement(String code);
    List<TypeEtablissement> listTypeEtablissement();
    List<TypeSystemeEnseignement> listTypeSystemeEnseignement();

    List<EEF> lisEEF();

    List<EEFSpeciality> lisEEFSpecByEEFCode(String code);

    List<Structure> lisStructureMfpaas();

     List<Structure> lisStructure();

    List<Speciality> listTypeSpeciality();

    List<Discipline> listDisciplines();

    List<Grade> listGrade();
    List<Grade> listGradeFilter();

    List<Grade> listGradeByCorpsCode(String code);

    List<DeconcentratedLevel> listUserFromEtablissementCode(String code);

    List<Profile> getTypeProfileDivision(String code);

    List<Profile> getTypeProfileDirection(String code);

    List<Profile> getTypeProfileBureau(String code);
    List<Profile> getTypeProfileWithOutCD(String code);

    List<TypePoste> listTypePoste();

    List<TypeDiplome> listTypeDiplome();

    List<DiplomeACA> listDiplomeACA();

    List<DiplomePROF> listDiplomePROF();

    List<DiplomePED> listDiplomePED();

    List<TypeMatricule> listTypeMatricule();

    List<Etablissement> listEtablissementByIACode(String code);

    List<Etablissement> listEtablissementByIEFCode(String code);

    List<Direction> listDirectionByCode(String code);

    List<FormationProfessionel> lisFormationProfessionels();
    List<Serie> listSeries(String code);

    List<Niveau> listNiveaus(String code);

    List<Speciality> findByEEFCode(String code);
    List<EEFMinistere> findByMinistreEEFCode(String code);

    List<EEFMinistere> findByMinistreEEF();

    List<Etablissement> listEtablissementByEFFCode( String code);

    List<CorpsGrade> listCorpsByMatricule(String code);
    Response<Object> indicateurIaIefEtab(String codeProfile);

    TypeSystemeEnseignement getTypeSystemeEnseignementByCode(String code);

}
