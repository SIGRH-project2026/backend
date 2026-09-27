package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation;

import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImputationRequestdto {
    private Long id;
    private String typeDemande;

    private String nomBeneficiere;


    private String prenomBeneficiere;


    private String statusBeneficiere;


    private LocalDate dateImputation;

    private long numeroDemande;


    private long utilisateurId;


    private List<File> justificatifs = new ArrayList<>();

}
