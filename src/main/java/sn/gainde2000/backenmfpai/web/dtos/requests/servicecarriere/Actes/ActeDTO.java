package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.PieceJointes;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.PieceJointesDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ActeDTO {

    @NotBlank(message = "L'agent est obligatoire")
    private long idAgent;

    @NotBlank(message = "Le type est obligatoire")
    private  String codetypeActe;

    private String codeTypeActeAA;
    private String autreTypeActe;

    private Division division;
    private Direction direction;

    private String codeTypeActeAG;

    @NotBlank(message = "Le statut est obligatoire")
    private  String codeStatutActe=new String("SOUMIS");

    @NotBlank(message = "La date de demande d'acte est obligatoire")
    private LocalDate dateDemandeActe=LocalDate.now();

    private LocalDate  dateDebut;
    private LocalDate  dateFin;

    private String commentaire;

    private boolean isDeleted=false;

    private boolean isActivated=false;

    private String motifRejetDemande;

    private String motifModification;
}
