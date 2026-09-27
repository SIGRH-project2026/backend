package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation;

import java.time.LocalDate;

public record TraitementMutationDTO(String motif, Long idTraiteur, String codeStatutMutation) {
}
