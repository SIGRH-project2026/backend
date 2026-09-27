package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.StatutFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.TypeFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class FormationDTO {

    private Long id;

    private TypeFormation typeFormation;

    private ThemeFormation themeFormation;

    private String reference;

    private String intitule;

    private Date dateDebut;

    private Date dateFin;

    private Date dateEnvoi;

    private Date dateReception;

    private String duree;

    private String prestataires;

    private String cout;

    private String description;

    private StatutFormation statutFormation;

    private File cahierCharge;

    private Set<Profile> profils;

    private PlanFormation planFormation;

    private Direction direction;

    private String effCode;

    private String specialiteCode;

    private Integer nombrePlace;

}