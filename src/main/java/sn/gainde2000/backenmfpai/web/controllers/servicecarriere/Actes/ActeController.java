package sn.gainde2000.backenmfpai.web.controllers.servicecarriere.Actes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.ActeRepository;
import sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes.ITypeActeImpl;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IActe;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.ITypeActe;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ValidesActesDate;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/actes")
@Tag(name = "Gestion des actes", description = "Endpoint permettant de gérer les actes")
public class ActeController {

    private final IActe iActe;
    private final ITypeActe iTypeActe;
    private final ITypeActeImpl find;
    private final ActeRepository acteRepository;
    @Value("${upload.path}")
    private String uploadPath;
    private static final String DEFAULT_FILE_NAME = "acte.pdf";
    @Autowired
    private ServletContext servletContext;


    @PostMapping(path = "/create"/*,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,  MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE,}*/)
    @Operation(description = "Endpoint de création d'un acte")
    public Response<Object> createActe(@RequestBody ActeDTO acteDTO/*,@RequestPart(name = "files") MultipartFile[] files*/)/*throws MFPAIException*/ {
        return iActe.createActe(acteDTO/*,files*/);
    }

    @GetMapping(path = "/listActes")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes avec des filtres avancés")
    public Response<Object> listFiltree(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                        @RequestParam(name = "typeUserId", defaultValue = "0") long typeUserId,
                                        @RequestParam(name = "reference", defaultValue = "") String reference,
                                        @RequestParam(name = "date", defaultValue = "") String date,
                                        @RequestParam(name = "type", defaultValue = "") String type,
                                        @RequestParam(name = "codeTypeActe", defaultValue = "") String codeTypeActe,
                                        @RequestParam(name = "statut", defaultValue = "") String statut,
                                        @RequestParam(name = "matricule", defaultValue = "") String matricule

    ) {
        return iActe.filtreAvances(page, size, typeUserId, reference, date, type,codeTypeActe, statut,matricule);
    }

    @GetMapping(path = "/listActesInProcess")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes encours de traitement")
    public Response<Object> listActesInProcess(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                        @RequestParam(name = "typeUserId", defaultValue = "0") long typeUserId,
                                        @RequestParam(name = "reference", defaultValue = "") String reference,
                                        @RequestParam(name = "date", defaultValue = "") String date,
                                        @RequestParam(name = "type", defaultValue = "") String type,
                                        @RequestParam(name = "codeTypeActe", defaultValue = "") String codeTypeActe,
                                        @RequestParam(name = "statut", defaultValue = "") String statut,
                                        @RequestParam(name = "matricule", defaultValue = "") String matricule
    ) {
        return iActe.listActesInProcess(page, size, typeUserId, reference, date,codeTypeActe, type,matricule);
    }

    @GetMapping(path = "/listActesAA")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes d'administration")
    public Response<Object> listAA(
    ) {
        System.out.println("AriAA");
        return iActe.getAllAA();
    }

    @GetMapping(path = "/listActesAG")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes de gestion")
    public Response<Object> listAG(
    ) {
        System.out.println("AriAG");
        return iActe.getAllAG();
    }

    @PostMapping(path = "/envoyerFP/{idAgent}")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes à envoyer à la fonction publique")
    public Response<Object> listAEnvoyer(@PathVariable long idAgent, @RequestBody List<Long>actes) throws JRException, FileNotFoundException {
        return iActe.envoyerFP(actes,idAgent);
    }



  /*  @GetMapping(path = "soties/listActes")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes avec des filtres avancés")
    public Response<Object> listSortie(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                        @RequestParam(name = "typeUserId", defaultValue = "10") long typeUserId,
                                        @RequestParam(name = "reference", defaultValue = "") String reference,
                                        @RequestParam(name = "date",defaultValue = "") String date,
                                        @RequestParam(name = "type", defaultValue = "") String type,
                                        @RequestParam(name = "statut", defaultValue = "") String statut
    )
    {
        return iActe.sotieTemplaire (page,size,typeUserId,reference,date,type,statut);
    }
*/




   @GetMapping(path = "/soties/listActesPage/{code}")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes avec des filtres avancés")
    public Response<Object> listSortiePage(@PathVariable(name = "code") String code,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           @RequestParam(name = "filter", defaultValue = "") String filter,
                                           @RequestParam(name = "type", defaultValue = "") String type)
    {


        return iActe.findBySortiePage (code, page, size, filter, type);
    }

   /* @GetMapping(path = "soties/listActesBisPage")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes avec des filtres avancés")
    public Response<Object> listSortieBisPage(
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           @RequestParam(name = "filter", defaultValue = "") String filter,
                                           @RequestParam(name = "type", defaultValue = "") String type)
    {

        System.out.println("########"+type);
        return iActe.findBySortieBisPage (page, size, filter, type);
    }*/


 /*   @GetMapping(path = "soties/listActes/{code}")
    @Operation(description = "Endpoint de recuperation de l'ensemble des actes avec des filtres avancés")
    public List<Acte> listSortie(@PathVariable(name = "code") String code)
    {

        return iActe.findBySortie (code);
    }
*/

    @ResponseBody
    @GetMapping(path = "/getOne/{id}")
    @Operation(description = "Endpoint de recuperation d'un acte à partir deson id")
    public Response<Object> getActeById(@PathVariable Long id) {
        return iActe.getActeByActeId(id);
    }

    @ResponseBody
    @GetMapping(path = "/delete/{id}")
    @Operation(description = "Endpoint de suppression d'un acte par son id")
    public Response<Object> delete(@PathVariable long id) {
        return iActe.deleteActe(id);
    }


    @GetMapping("/traiterActe/{acteId}/{idAgent}")
    public Response<Object> traiterActe(
                                        @PathVariable long acteId,
                                        @PathVariable long idAgent,
                                        @RequestParam(name = "traitement",defaultValue = "") String traitement,
                                        @RequestParam(name = "motifModification",defaultValue = "") String motifModification,
                                        @RequestParam(name = "motifRejet",defaultValue = "") String motifRejet

                                        ) throws JRException, FileNotFoundException {
        return iActe.traiterActe(acteId, idAgent, traitement,motifModification,motifRejet);
    }


    @PatchMapping("/validerCen/{acteId}/{idAgent}")
    public Response<Object> Cen(@RequestBody ValidesActesDate validesActesDate,
                                @PathVariable long acteId,
                                @PathVariable long idAgent
    ) throws JRException, FileNotFoundException {


        return iActe.validerCen(acteId, idAgent,validesActesDate);
    }
    @GetMapping("/validerCenDiv/{acteId}/{idAgent}")
    public Response<Object> Cen(
                                @PathVariable long acteId,
                                @PathVariable long idAgent
    ) throws JRException, FileNotFoundException {

     ValidesActesDate validesActesDate  = new ValidesActesDate();
        return iActe.validerCen(acteId, idAgent,validesActesDate);
    }





    @PatchMapping("/updateActe/{id}")
    @Operation(description = "Endpoint de soumission de mise à jour d'un acte")
    public Response<Object> updateActe(@PathVariable long id, @RequestBody ActeDTO acteDTO) {
        return iActe.updateActe(id, acteDTO);
    }


    //Partie Statistiques


    @GetMapping("/statistiques")
    @Operation(description = "Endpoint de récupération de statistique des actes")
    public Response<Object> getStat(
                     @RequestParam(name = "codeProfile",defaultValue = "")String codeProfile,
                     @RequestParam(name = "codeTypeActe",defaultValue = "") String codeTypeActe) {
        return iActe.getActeStatistiques(codeProfile,codeTypeActe);
    }

    @GetMapping("/getTypeActe")
    @Operation(description = "EndPoint de recuperation des types d'actes")
    public Response<Object> getTypeActe() {
        return iActe.getAllTypeActe();
    }

}
