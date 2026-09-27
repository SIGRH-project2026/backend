package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TraitementActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.CentralLevelResDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ActeReportDTO {

    private String referenceActe;

    private String matricule;
    private String nom;

    private String prenom;

    private String direction;
    private String grade;

    private String bordereau;

    private String typeActeAA;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private LocalDate dateDemandeActe;

}
