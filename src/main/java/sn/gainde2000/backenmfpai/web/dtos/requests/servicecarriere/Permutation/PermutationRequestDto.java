package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Permutation;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermutationRequestDto {
    private Long id;
    private String numeroDemande;
    private String matriculeUtilisateur1;
    private String matriculeUtilisateur2;
    private LocalDate datePermutation;
    private String motifPermutation;
    private String motifModification;
    private String motifRejet;
}
