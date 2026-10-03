package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermutationReportDto {

    private Long id;
    private String matricule;
    private String prenoms;
    private String nom;
    private String origine;
    private String destination;
    private String academie;
    private String specialite;
    private String corpsGrade;
    private String matriculeDemandeur;
    private String matriculeReceveur;
    private String prenomDemandeur;
    private String nomDemandeur;
    private String prenomReceveur;
    private String nomReceveur;
    private String destinationDemandeur;
    private String destinationReceveur;
}
