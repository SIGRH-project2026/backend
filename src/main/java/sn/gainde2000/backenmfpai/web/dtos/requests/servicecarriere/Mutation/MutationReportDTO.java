package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MutationReportDTO {

    private Long id;
    private String prenom;
    private String nom;
    private String matricule;
    private String corpsEtGrade;
    private String specialite;
    private String destination;
    private String origine;
    private String enQualiteDe;

}
