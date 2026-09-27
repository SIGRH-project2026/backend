package sn.gainde2000.backenmfpai.web.controllers.serviceutilisateur;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEFMinistere;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeEtablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.ReferenceService;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

/**
 * @author G2k R&D
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/static")
@Tag(name = "ReferenceslController", description = "Permet de gérer les endpoints des références")
public class ReferenceslController {
    private final ReferenceService iprofil;

    @GetMapping("/profils")
    public ResponseEntity<MFPAIResponse> listeProfil() {
        List<Profile> profiles = iprofil.listeProfil();

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profils/cen")
    public ResponseEntity<MFPAIResponse> listeProfilCEN() {
        List<Profile> profiles = iprofil.listeProfilByTypeCEN();

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profils/dec")
    public ResponseEntity<MFPAIResponse> listeProfilDEC() {
        List<Profile> profiles = iprofil.listeProfilByTypeDEC();

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/divisions")
    public ResponseEntity<MFPAIResponse> listDivision() {
        List<Division> divisions = iprofil.listDivision();

        MFPAIResponse response = MFPAIResponse.success(divisions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/divisions/{code}")
    public ResponseEntity<MFPAIResponse> listDivisionByDirectionCode(@PathVariable("code") String code) {
        List<Division> divisions = iprofil.listDivisionByDirectionCode(code);

        MFPAIResponse response = MFPAIResponse.success(divisions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/directions")
    public ResponseEntity<MFPAIResponse> listDirection() {
        List<Direction> divisions = iprofil.listDirection();

        MFPAIResponse response = MFPAIResponse.success(divisions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/services")
    public ResponseEntity<MFPAIResponse> listServices() {
        List<Services> services = iprofil.listService();

        MFPAIResponse response = MFPAIResponse.success(services);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/services/{code}")
    public ResponseEntity<MFPAIResponse> listServicesByDirectionCode(@PathVariable("code") String code) {
        List<Services> services = iprofil.listServiceByDirectionCode(code);

        MFPAIResponse response = MFPAIResponse.success(services);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/bureaus")
    public ResponseEntity<MFPAIResponse> listBureau() {
        List<Bureau> bureaus = iprofil.listBureau();

        MFPAIResponse response = MFPAIResponse.success(bureaus);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/bureaus/{code}")
    public ResponseEntity<MFPAIResponse> listBureauByDivisionCode(@PathVariable(name = "code") String code) {
        List<Bureau> bureaus = iprofil.listBureauByDivisionCode(code);

        MFPAIResponse response = MFPAIResponse.success(bureaus);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/fonctions")
    public ResponseEntity<MFPAIResponse> listFonction() {
        List<Fonction> fonctions = iprofil.listFonction();

        MFPAIResponse response = MFPAIResponse.success(fonctions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/corpsgrade")
    public ResponseEntity<MFPAIResponse> listCorpsGrade() {
        List<CorpsGrade> corpsGrades = iprofil.listCorpsGrade();

        MFPAIResponse response = MFPAIResponse.success(corpsGrades);

        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/corps/{code}")
    public ResponseEntity<MFPAIResponse> listCorpsByMatricule(@PathVariable(name = "code") String code) {
        List<CorpsGrade> corpsGrades = iprofil.listCorpsByMatricule(code);
        MFPAIResponse response = MFPAIResponse.success(corpsGrades);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/regions")
    public ResponseEntity<MFPAIResponse> listRegion() {
        List<Region> regions = iprofil.listRegion();

        MFPAIResponse response = MFPAIResponse.success(regions);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/specialities")
    public ResponseEntity<MFPAIResponse> listTypeSpeciality() {
        List<Speciality> specialities = iprofil.listTypeSpeciality();

        MFPAIResponse response = MFPAIResponse.success(specialities);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ia")
    public ResponseEntity<MFPAIResponse> listIA() {
        List<IA> ia = iprofil.listIA();

        MFPAIResponse response = MFPAIResponse.success(ia);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ia/{code}")
    public ResponseEntity<MFPAIResponse> listIARegionCode(@PathVariable(name = "code") String code) {
        List<IA> ia = iprofil.listIAByRegionCode(code);

        MFPAIResponse response = MFPAIResponse.success(ia);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ief")
    public ResponseEntity<MFPAIResponse> listIEF() {
        List<IEF> ia = iprofil.listIEF();

        MFPAIResponse response = MFPAIResponse.success(ia);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/ief/{code}")
    public ResponseEntity<MFPAIResponse> listIEFByIACode(@PathVariable(name = "code") String code) {
        List<IEF> ief = iprofil.listIEFByIACode(code);

        MFPAIResponse response = MFPAIResponse.success(ief);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etablissement")
    public ResponseEntity<MFPAIResponse> listEtablissement() {
        List<Etablissement> etablissements = iprofil.listEtablissement();

        MFPAIResponse response = MFPAIResponse.success(etablissements);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etablissement/{code}")
    public ResponseEntity<MFPAIResponse> listEtablissementByCFPCode(@PathVariable(name = "code") String code) {
        List<Etablissement> etablissements = iprofil.listEtablissementByCFPCode(code);

        MFPAIResponse response = MFPAIResponse.success(etablissements);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etablissement-type/{code}")
    public ResponseEntity<MFPAIResponse> listEtablissementByType(@PathVariable(name = "code") String code) {
        List<Etablissement> etablissements = iprofil.listEtablissementByType(code);

        MFPAIResponse response = MFPAIResponse.success(etablissements);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etablissement-type")
    public ResponseEntity<MFPAIResponse> listTypeEtablissement() {
        List<TypeEtablissement> typeEtablissement = iprofil.listTypeEtablissement();

        MFPAIResponse response = MFPAIResponse.success(typeEtablissement);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etablissement-type-systeme-enseignement/{code}")
    public ResponseEntity<MFPAIResponse> listEtablissementByTypeSystemeEnseignement(@PathVariable(name = "code") String code) {
        List<Etablissement> etablissements = iprofil.listEtablissementByTypeSystemeEnseignement(code);

        MFPAIResponse response = MFPAIResponse.success(etablissements);

        return ResponseEntity.ok().body(response);
    } 
    
    @GetMapping("/type-systeme-enseignement/{code}")
    public ResponseEntity<MFPAIResponse> listTypeSystemeEnseignementByCode(@PathVariable(name = "code") String code) {
        TypeSystemeEnseignement typeSystemeEnseignement = iprofil.getTypeSystemeEnseignementByCode(code);

        MFPAIResponse response = MFPAIResponse.success(typeSystemeEnseignement);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/type-systeme-enseignement")
    public ResponseEntity<MFPAIResponse> listTypeSystemeEnseignement() {
        List<TypeSystemeEnseignement> typeSystemeEnseignements = iprofil.listTypeSystemeEnseignement();

        MFPAIResponse response = MFPAIResponse.success(typeSystemeEnseignements);

        return ResponseEntity.ok().body(response);
    }    

    @GetMapping("/eef")
    public ResponseEntity<MFPAIResponse> listEEF() {
        List<EEF> etablissements = iprofil.lisEEF();

        MFPAIResponse response = MFPAIResponse.success(etablissements);

        return ResponseEntity.ok().body(response);
    }

   /* @GetMapping("/eef-speciality/{code}")
    public ResponseEntity<MFPAIResponse> lisEEFSpecByEEFCode(@PathVariable(name = "code") String code) {
        List<EEFSpeciality> eefSpecialities = iprofil.lisEEFSpecByEEFCode(code);

        MFPAIResponse response = MFPAIResponse.success(eefSpecialities);

        return ResponseEntity.ok().body(response);
    }*/

    @GetMapping("/profile-divison/{code}")
    public ResponseEntity<MFPAIResponse> listTypeProfileDivision(@PathVariable(name = "code") String code) {
        List<Profile> profiles = iprofil.getTypeProfileDivision(code);

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile-direction/{code}")
    public ResponseEntity<MFPAIResponse> listTypeProfileDirection(@PathVariable(name = "code") String code) {
        List<Profile> profiles = iprofil.getTypeProfileDirection(code);

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile-bureau/{code}")
    public ResponseEntity<MFPAIResponse> listTypeProfileBureau(@PathVariable(name = "code") String code) {
        List<Profile> profiles = iprofil.getTypeProfileBureau(code);

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/profile-bureau-wcd/{code}")
    public ResponseEntity<MFPAIResponse> getTypeProfileWithOutCD(@PathVariable(name = "code") String code) {
        List<Profile> profiles = iprofil.getTypeProfileWithOutCD(code);

        MFPAIResponse response = MFPAIResponse.success(profiles);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/mfpaa")
    public ResponseEntity<MFPAIResponse> lisStructureMfpaas() {
        List<Structure> etablissements = iprofil.lisStructureMfpaas();

        MFPAIResponse response = MFPAIResponse.success(etablissements);

        return ResponseEntity.ok().body(response);
    }

   @GetMapping("/structure")
    public ResponseEntity<MFPAIResponse> lisStructure() {
        List<Structure> structure = iprofil.lisStructure();

        MFPAIResponse response = MFPAIResponse.success(structure);

        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/discipline")
    public ResponseEntity<MFPAIResponse> listDiscipline() {
        List<Discipline> dsiciplines = iprofil.listDisciplines();

        MFPAIResponse response = MFPAIResponse.success(dsiciplines);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/grade")
    public ResponseEntity<MFPAIResponse> listGrade() {
        List<Grade> grades = iprofil.listGrade();

        MFPAIResponse response = MFPAIResponse.success(grades);

        return ResponseEntity.ok().body(response);
    }




    @GetMapping("/grade-filter")
    public ResponseEntity<MFPAIResponse> listGradeFilter() {
        List<Grade> grades = iprofil.listGradeFilter();

        MFPAIResponse response = MFPAIResponse.success(grades);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/type-poste")
    public ResponseEntity<MFPAIResponse> listTypePoste() {
        List<TypePoste> typePostes = iprofil.listTypePoste();

        MFPAIResponse response = MFPAIResponse.success(typePostes);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/type-diplome")
    public ResponseEntity<MFPAIResponse> listTypeDiplome() {
        List<TypeDiplome> typeDiplomes = iprofil.listTypeDiplome();
        MFPAIResponse response = MFPAIResponse.success(typeDiplomes);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/diplome-aca")
    public ResponseEntity<MFPAIResponse> listDiplomeACA() {
        List<DiplomeACA> diplomeACAs = iprofil.listDiplomeACA();
        MFPAIResponse response = MFPAIResponse.success(diplomeACAs);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/diplome-prof")
    public ResponseEntity<MFPAIResponse> listDiplomePROF() {
        List<DiplomePROF> diplomePROFs = iprofil.listDiplomePROF();
        MFPAIResponse response = MFPAIResponse.success(diplomePROFs);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/diplome-ped")
    public ResponseEntity<MFPAIResponse> listDiplomePED() {
        List<DiplomePED> diplomePEDs = iprofil.listDiplomePED();
        MFPAIResponse response = MFPAIResponse.success(diplomePEDs);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/type-matricule")
    public ResponseEntity<MFPAIResponse> listTypeMatricule() {
        List<TypeMatricule> typeMatricules = iprofil.listTypeMatricule();
        MFPAIResponse response = MFPAIResponse.success(typeMatricules);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/grade/{code}")
    public ResponseEntity<MFPAIResponse> listGradeByCorps(@PathVariable(name = "code") String code) {
        List<Grade> grades = iprofil.listGradeByCorpsCode(code);
        MFPAIResponse response = MFPAIResponse.success(grades);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etabliessement-ia/{code}")
    public ResponseEntity<MFPAIResponse> listEtablissementByIACode(@PathVariable(name = "code") String code) {
        List<Etablissement> etablissements = iprofil.listEtablissementByIACode(code);
        MFPAIResponse response = MFPAIResponse.success(etablissements);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/etabliessement-eef/{code}")
    public ResponseEntity<MFPAIResponse> listEtablissementByEFFode(@PathVariable(name = "code") String code) {
        List<Etablissement> etablissements = iprofil.listEtablissementByEFFCode(code);
        MFPAIResponse response = MFPAIResponse.success(etablissements);
        return ResponseEntity.ok().body(response);
    }











    @GetMapping("/etabliessement-ief/{code}")
    public ResponseEntity<MFPAIResponse> listEtablissementByIEFCode(@PathVariable(name = "code") String code) {
        List<Etablissement> etablissements = iprofil.listEtablissementByIEFCode(code);
        MFPAIResponse response = MFPAIResponse.success(etablissements);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/direction/{code}")
    public ResponseEntity<MFPAIResponse> listDirectionByCode(@PathVariable(name = "code") String code) {
        List<Direction> directions = iprofil.listDirectionByCode(code);
        MFPAIResponse response = MFPAIResponse.success(directions);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/formation-pro")
    public ResponseEntity<MFPAIResponse> listFormationPro() {
        List<FormationProfessionel> directions = iprofil.lisFormationProfessionels();
        MFPAIResponse response = MFPAIResponse.success(directions);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/formation-pro/niveau/{code}")
    public ResponseEntity<MFPAIResponse> listNiveau(@PathVariable(name = "code") String code) {
        List<Niveau> directions = iprofil.listNiveaus(code);
        MFPAIResponse response = MFPAIResponse.success(directions);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/formation-pro/serie/{code}")
    public ResponseEntity<MFPAIResponse> listSerie(@PathVariable(name = "code") String code) {
        List<Serie> series = iprofil.listSeries(code);
        MFPAIResponse response = MFPAIResponse.success(series);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/eef/speciality/list/{code}")
    public ResponseEntity<MFPAIResponse> listfindByEEFCode(@PathVariable(name = "code") String code) {
        List<Speciality> etablissements = iprofil.findByEEFCode(code);
        MFPAIResponse response = MFPAIResponse.success(etablissements);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/eeministre")
    public ResponseEntity<MFPAIResponse> findByMinistreEEF() {
        List<EEFMinistere> etablissements = iprofil.findByMinistreEEF();
        MFPAIResponse response = MFPAIResponse.success(etablissements);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/eeministre/{code}")
    public ResponseEntity<MFPAIResponse> findByMinistreEEFCode(@PathVariable(name = "code") String code) {
        List<EEFMinistere> etablissements = iprofil.findByMinistreEEFCode(code);
        MFPAIResponse response = MFPAIResponse.success(etablissements);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping( path = {"/indicateurs"})
    @Operation(description = "Endpoint de récupération des indicateurs : Ia, Ief et Etablissement'")
    public Response<Object> indicateur(@RequestParam String profileCode) {
        return iprofil.indicateurIaIefEtab(profileCode);
    }
}
