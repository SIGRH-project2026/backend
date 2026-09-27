package sn.gainde2000.backenmfpai.web.dtos.responses.servicesociale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.sl.draw.geom.PresetGeometries;
import org.springframework.data.annotation.LastModifiedDate;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TraitementActe;
import sn.gainde2000.backenmfpai.entities.servicesociale.StatutPriseEnCharge;
import sn.gainde2000.backenmfpai.entities.servicesociale.TraitementPriseEnCharge;
import sn.gainde2000.backenmfpai.entities.servicesociale.TypeDemande;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PriseEnChargeResponseDTO {

    private long id;

    private String numeroDemande;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private LocalDate dateDemande=LocalDate.now();

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private LocalDate lastModified;

    private Utilisateur utilisateur;

    private String objetDemande;

    private StatutPriseEnCharge statutPriseEnCharge;
    private TypeDemande typeDemandePeec;

    private  String motifRejetDemande;

    private  String motifModification;

    private List<FileRspDTO> pieceJointes = new ArrayList<>();

    private Division division;

    private Direction direction;

    private Services service;

    private Bureau bureau;

    private Region region;

    private IA ia;

    private IEF ief;

    private Etablissement etablissement;



}
