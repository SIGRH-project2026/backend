package sn.gainde2000.backenmfpai.web.controllers.servicesociale;

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
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.ActeRepository;
import sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes.ITypeActeImpl;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IActe;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.ITypeActe;
import sn.gainde2000.backenmfpai.services.interfaces.servicesociale.IPriseEnChargeService;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicesociale.PriseEnChargeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.FileNotFoundException;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/priseencharges")
@Tag(name = "Gestion des prises en charge", description = "Endpoint permettant de gérer les prises en charge")
public class PriseEnChargeController {

    private final IPriseEnChargeService iPriseEnCharge;

    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private ServletContext servletContext;

    @PostMapping(path ="/create"/*,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,  MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE,}*/)
    @Operation(description = "Endpoint de création d'une Demande de Prise en Charge")
    public Response<Object> createActe(@RequestBody PriseEnChargeDTO priseEnChargeDTO/*,@RequestPart(name = "files") MultipartFile[] files*/)/*throws MFPAIException*/ {
        return  iPriseEnCharge.createPEC(priseEnChargeDTO/*,files*/);
    }

    @GetMapping(path = "/listDemandes")
    @Operation(description = "Endpoint de recuperation de l'ensemble des Demande de Prise en Charge avec des filtres avancés")
    public Response<Object> listFiltree(@RequestParam(name = "page", defaultValue = "0") int page,
                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                        @RequestParam(name = "typeUserId", defaultValue = "0") long typeUserId,
                                        @RequestParam(name = "numero", defaultValue = "") String numero,
                                        @RequestParam(name = "matricule", defaultValue = "") String matricule,
                                        @RequestParam(name = "nom", defaultValue = "") String nom,
                                        @RequestParam(name = "prenom", defaultValue = "") String prenom,
                                        @RequestParam(name = "region", defaultValue = "") String region,
                                        @RequestParam(name = "ia", defaultValue = "") String ia,
                                        @RequestParam(name = "date",defaultValue = "") String date,
                                        @RequestParam(name = "objet", defaultValue = "") String objet,
                                        @RequestParam(name = "statut", defaultValue = "") String statut,
                                        @RequestParam(name = "type", defaultValue = "") String type
    )
    {
        return iPriseEnCharge.ListPECFiltreAvances (page,size,typeUserId,numero,matricule,nom,prenom,region,ia,date,objet,statut,type);
    }

    @ResponseBody
    @GetMapping(path = "/getOne/{id}")
    @Operation(description = "Endpoint de recuperation d'une Demande de Prise en Charge acte à partir de son id")
    public Response<Object> getPECById(@PathVariable long id){
        return  iPriseEnCharge.getPECById(id);
    }

    @PutMapping("/updateDemande/{id}")
    public Response<Object>  update(@PathVariable long id,@RequestBody PriseEnChargeDTO priseEnChargeDTO) {
        return iPriseEnCharge.updatePEC(id,priseEnChargeDTO);
    }

    @ResponseBody
    @DeleteMapping(path = "/delete/{id}")
    @Operation(description = "Endpoint de suppression d'une Demande de Prise en Charge par son id")
    public Response<Object> delete(@PathVariable long id) {
        return iPriseEnCharge.deletePEC(id);
    }

    @GetMapping("/traiterDemande/{id}/{idTraitant}/{traitement}")
    public Response<Object>  traiterActe(@PathVariable long id,@PathVariable long idTraitant,@PathVariable String traitement,
                                         @RequestParam(name = "motifModif", defaultValue = "") String motifModif,
                                         @RequestParam(name = "motifRejet", defaultValue = "") String motifRejet) throws JRException, FileNotFoundException {
        return iPriseEnCharge.traiterPEC(id,idTraitant,traitement,motifModif,motifRejet);
    }

    @GetMapping("/statistiques")
    @Operation(description = "Endpoint de récupération de statistique des actes")
    public Response<Object> getStat(@RequestParam(name = "codeProfile",defaultValue = "") String codeProfile) {
        return iPriseEnCharge.getPecStatistiques(codeProfile);
    }
}
