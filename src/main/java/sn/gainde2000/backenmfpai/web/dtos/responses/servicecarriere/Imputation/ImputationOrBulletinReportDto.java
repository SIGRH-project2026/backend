package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation;

import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImputationOrBulletinReportDto {

    private Long id;
    private String typeDemande;

    private String nomBeneficiere;


    private String prenomBeneficiere;


    private String statusBeneficiere;


    private LocalDate dateImputation;

    private long numeroDemande;

    private String nomAgent;

    private String prenomAgent;

    private String direction;

    private String iaIef;

    private String matricule;

    private String corps;

    private String grade;

    private String adresse;

    private String qualite;

//private String fonction;

}
