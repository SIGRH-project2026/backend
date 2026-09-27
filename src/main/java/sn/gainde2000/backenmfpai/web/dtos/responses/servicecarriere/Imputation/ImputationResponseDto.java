package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImputationResponseDto {

    private Long id;
    private String typeDemande;

    private String nomBeneficiere;


    private String prenomBeneficiere;


    private String statusBeneficiere;


    private LocalDate dateImputation;

    private long numeroDemande;

    private String imputationGeneree;

    private Utilisateur utilisateur;

    private List<File> justificatifs = new ArrayList<>();

    private List<File> justificatifsNoPiecesJointes = new ArrayList<>();

}
