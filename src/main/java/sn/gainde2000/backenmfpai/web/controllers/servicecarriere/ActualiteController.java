package sn.gainde2000.backenmfpai.web.controllers.servicecarriere;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.attoparser.dom.Text;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.actualite.Actualite;
import sn.gainde2000.backenmfpai.entities.actualite.CategorieActualite;
import sn.gainde2000.backenmfpai.entities.actualite.TypeArticle;
import sn.gainde2000.backenmfpai.exceptions.MFPAIResponse;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite.IActualiteRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite.ICategorieActualite;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite.ITypeArticle;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IActualiteService;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/actualite/")
@Tag(name = "gestionActualite", description = "Permet la gestion des actualité")
public class ActualiteController {

    private final IActualiteService iActualiteService;
    private final IActualiteRepository iActualiteRepository;
    private final ITypeArticle iTypeArticleRepository;
    private final ICategorieActualite iCategorieActualiteRepository;

    @PostMapping("/add")
    public ResponseEntity<MFPAIResponse> addActu (
            @RequestParam (defaultValue = "") Text contenu,
            @RequestParam (defaultValue = "") String resume,
            @RequestParam (defaultValue = "") long typeArticle,
            @RequestParam (defaultValue = "") long categorieActualite,
            @RequestParam (defaultValue = "") String titre,
            @RequestParam("file") MultipartFile file) throws IOException {
        Actualite actualite = new Actualite();
        actualite.setContenu(contenu);
        actualite.setResume(resume);
        actualite.setCategorieActualite(iCategorieActualiteRepository.findById(categorieActualite).get());
        actualite.setTypeArticle(iTypeArticleRepository.findById(typeArticle).get());
        actualite.setTitre(titre);
        Actualite actualite1 = iActualiteService.create(actualite,file);
        MFPAIResponse response = MFPAIResponse.success(actualite1);
       return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<MFPAIResponse> getAll(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "") String statut){

        Response<Object> actualites = iActualiteService.getAll(page,size,statut);
        MFPAIResponse response = MFPAIResponse.success(actualites);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listActive")
    public ResponseEntity<MFPAIResponse> getAllActualitesActive(){

        Response<Object> actualites = iActualiteService.getAllActivate();
        MFPAIResponse response = MFPAIResponse.success(actualites);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listActuOrRecrutementActive/{code}")
    public ResponseEntity<MFPAIResponse> getAllRecrutementActive(@PathVariable String code){

        Response<Object> actualites = iActualiteService.getAllActuOrRecrutementActivate(code);
        MFPAIResponse response = MFPAIResponse.success(actualites);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listLastRecrutementActive")
    public ResponseEntity<MFPAIResponse> getlastRecrutementActive(){
        Response<Object> recrutement = iActualiteService.getLastRecrutement();
        MFPAIResponse response = MFPAIResponse.success(recrutement);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listLastActuActive")
    public ResponseEntity<MFPAIResponse> getlastActuActive(){
        Response<Object> actu = iActualiteService.getLastActu();
        MFPAIResponse response = MFPAIResponse.success(actu);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<MFPAIResponse> getOne(@PathVariable long id){
        Actualite actualite = iActualiteService.getOne(id);
        MFPAIResponse response = MFPAIResponse.success(actualite);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MFPAIResponse> deleteActualite(@RequestParam long id){
        Actualite actualite = iActualiteService.delete(id);
        MFPAIResponse response = MFPAIResponse.success(actualite);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listType")
    public ResponseEntity<MFPAIResponse> getListType(){
        List<TypeArticle> types = iActualiteService.getAllTypeArticle();
        MFPAIResponse response = MFPAIResponse.success(types);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/listCategorie")
    public ResponseEntity<MFPAIResponse> getListCategorie(){
        List<CategorieActualite> categorieActualites = iActualiteService.getAllCategorieActualite();
        MFPAIResponse response = MFPAIResponse.success(categorieActualites);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/changeStatus/{id}")
    public ResponseEntity<MFPAIResponse> changeStatus(@PathVariable long id){
        System.out.println("changeStatus ####### "+id);
        Actualite actualite = iActualiteService.changeStatus(id);
        MFPAIResponse response = MFPAIResponse.success(actualite);
        return ResponseEntity.ok().body(response);
    }
}

