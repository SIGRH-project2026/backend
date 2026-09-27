package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rapport de détection/suppression des doublons de matricule
 * pour les utilisateurs de niveau déconcentré.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DuplicateMatriculeReportDTO {

    /** Nombre de matricules ayant plusieurs occurrences. */
    private int matriculesEnDoublon;

    /** Nombre total d'enregistrements qui seraient/ont été supprimés (conserve le plus ancien). */
    private int enregistrementsSupprimes;

    @Builder.Default
    private List<DuplicateGroup> groupes = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DuplicateGroup {
        private String matricule;
        private Long idConserve;
        private List<Long> idsSupprimes;
    }
}
