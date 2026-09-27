package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.FicheEtablisement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.*;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.FicheEtablissement.IFicheSynoptique;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.FicheEtablissement.FicheSynoptiqueDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ficheSynoptique")
@Tag(name = "Gestion des fiche d'etablisement", description = "Endpoint permettant de gérer les fiches synoptiques")

public class FicheSynoptiqueController {

    private  final IFicheSynoptique iFicheSynoptique;
    @PostMapping("/create")
    @Operation(description = "Endpoint de création de fiche d'etablissement")
    public  Response<Object> createFS(@RequestBody FicheSynoptiqueDTO ficheSynoptiqueDTO){
        return  iFicheSynoptique.createFicheSynoptique(ficheSynoptiqueDTO);
    }
    @PostMapping("/update")
    @Operation(description = "Endpoint de modification de fiche d'etablissement")
    public  Response<Object> updateFS(@RequestBody FicheSynoptique ficheSynoptique){
        return  iFicheSynoptique.updateFicheSynoptique(ficheSynoptique);
    }
    @GetMapping( path = {"/getone/{chefEtablissementUserId}"})
    @Operation(description = "Endpoint de récupération d'une fiche synoptique'")
    public Response<Object> getOneBEP(@PathVariable Long chefEtablissementUserId) {
        return iFicheSynoptique.getMaFicheSynoptique(chefEtablissementUserId);
    }
    @GetMapping( path = {"/getoneByEtablissement/{codeEtab}"})
    @Operation(description = "Endpoint de récupération d'une fiche synoptique par code Etab'")
    public Response<Object> getOneFicheByCodeEtab(@PathVariable String codeEtab) {
        return iFicheSynoptique.getMaFicheSynoptiqueByCodeEtab(codeEtab);
    }

    @PatchMapping( path = {"/addFiliere/{idFiche}"})
    @Operation(description = "Endpoint de récupération d'ajout d'une filiére dans une fiche synoptique'")
    public Response<Object> addFiliere(@PathVariable Long idFiche, @RequestBody List<FiliereDiscipline>  filiereDiscipline) {
        return iFicheSynoptique.addFiliere(idFiche, filiereDiscipline);
    }
    @PatchMapping( path = {"/addSerie/{idFiche}"})
    @Operation(description = "Endpoint de récupération d'ajout d'une série dans une fiche synoptique'")
    public Response<Object> addSerie(@PathVariable Long idFiche, @RequestBody List<SerieNiveauDiscipline>  serieNiveauDisciplines) {
        return iFicheSynoptique.addSerie(idFiche, serieNiveauDisciplines);
    }
    @PatchMapping( path = {"/addClasse/{idFiche}"})
    @Operation(description = "Endpoint de récupération d'ajout d'une classe dans une fiche synoptique'")
    public Response<Object> addClasse(@PathVariable Long idFiche, @RequestBody List<ClasseProfDiscipline> classeProfDiscipline) {
        return iFicheSynoptique.addClasse(idFiche, classeProfDiscipline);
    }
    @PatchMapping( path = {"/addClasseSerie/{idFiche}"})
    @Operation(description = "Endpoint de récupération d'ajout d'une classe avec série dans une fiche synoptique'")
    public Response<Object> addClasseSerie(@PathVariable Long idFiche, @RequestBody List<SerieClasseProfDiscipline> serieClasseProfDisciplines) {
        return iFicheSynoptique.addClasseSerie(idFiche, serieClasseProfDisciplines);
    }

    @GetMapping( path = {"/list/{codeEtab}"})
    @Operation(description = "Endpoint de récupération de la liste des besoins en personnel")
    public Response<Object> listHoraireProfs(
            @PathVariable String codeEtab,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String matricule,
            @RequestParam(defaultValue = "")  String nomComplet


    ) {
        return iFicheSynoptique.HorairesProfs(codeEtab, page, size,matricule,nomComplet);
    }

    @GetMapping( path = {"/listDeficitaire/{codeEtab}"})
    @Operation(description = "Endpoint de récupération de la liste des matieres ayant des deficits")
    public Response<Object> listDisciplineDeficitaire(
            @PathVariable String codeEtab,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "")  String disciplineName


    ) {
        return iFicheSynoptique.discplinesAyantDeficit(codeEtab, page, size,disciplineName);
    }

    @GetMapping( path = {"/listDeficitaireAllEtab"})
    @Operation(description = "Endpoint de récupération de la liste des matieres ayant des deficits all etablissement")
    public Response<Object> listDisciplineDeficitaireAllEtab(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "")  String disciplineName


    ) {
        return iFicheSynoptique.discplinesAyantDeficitAllEtablisselent(page, size,disciplineName);
    }
    @GetMapping( path = {"/etabDeficitaire/{codeEtab}"})
    @Operation(description = "Endpoint de récupération horaire etablissement")
    public Response<Object> etablissementDeficitaire(
            @PathVariable String codeEtab
    ) {
        return iFicheSynoptique.ETablissementDeficit(codeEtab);
    }

    @GetMapping( path = {"/deficitSurUneDiscipline/{idDiscipline}"})
    @Operation(description = "Endpoint de récupération horaire etablissement")
    public Response<Object> etablissementAyantDeficitSurUneDescipline(
            @PathVariable Long idDiscipline,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return iFicheSynoptique.listEtablissementAyantDeficitSurUneDiscipline(page, size, idDiscipline);
    }

}
