package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.PieceJointes;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.TypeAADTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.CentralLevelResDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.DeconcentratedLevelRespDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.UtilisateurResponseDTO;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor

public class
ActeResponseDTO {

    private Long id;

    private String referenceActe;

    private UtilisateurResponseDTO agent;

    private BordereauResponseDTO currentBordereau;

    private BordereauResponseDTO predBordereau;

    private TypeActe typeActe;

    private TypeAAResponseDTO typeActeAA;

    private TypeAGResponseDTO typeActeAG;

    private StatutActe statutActe;

    private Division division;

    private Direction direction;

    private Services service;

    private Bureau bureau;

    private Region region;

    private IA ia;

    private IEF ief;

    private Etablissement etablissement;

    private List<TraitementActe> traitementActe;

    private boolean isDeleted;

    private boolean isActivated;

    private String commentaire;

    private List<FileRspDTO> pieceJointes = new ArrayList<>();

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private LocalDate dateDemandeActe;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private LocalDate  dateDebut;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private LocalDate  dateFin;

    private String profilDevantTraiter;

    private String motifRejetDemande;

    private String motifModification;




}
