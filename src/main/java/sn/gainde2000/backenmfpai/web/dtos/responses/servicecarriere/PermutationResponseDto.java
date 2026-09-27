package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.StatusPermutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.TraitementPermutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermutationResponseDto {

    private Long id;
    private String numeroDemande;
    private Utilisateur utilisateur1;
    private Utilisateur utilisateur2;
    private LocalDate datePermutation;
    private String motifPermutation;
    private StatusPermutation statut;
    private LocalDate dateValidation;
    private Boolean isActive;
    private TraitementPermutation traitementPermutation;
    private String ordreService;

}
