package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PlanFormationDTO {

    private String reference;

    private String titre;

    private String commentaire;

    private Date dateDebut;

    private Date dateFin;

    private Date datePublication;

    private Long createdByUserId;

    private Long statutPlanFormationId;

    private List<ThemeFormationDTO> themesFormation;

}