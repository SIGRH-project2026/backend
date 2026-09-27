package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeStageRequest {
    private String prenomDemandeur;
    private String nomDemandeur;
    private LocalDate dateNaissance;
    private String lieuDeNaissance;
    private String mail;
    private String tel;
    private String adresse;
    private String objet;
    private String codeNiveauScolaire;
    private String disciplineStage;
    private String directionCode;
    private String divisionCode;
    private String serviceCode;
    private String bureauCode;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String commentaire;
}
