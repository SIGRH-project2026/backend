package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ThemeFormationDTO {
    private Long id;

    private String libelle;

    private String duree;

    private String modalites;

    private String operateur;

    private String bailleur;

    private String budget;

    private Direction direction;

    private Long responsableSuiviId;

    private Utilisateur responsableSuivi;

    private Long planFormationId;

    private List<Formation> formations;

    private Set<Profile> profils;

}
