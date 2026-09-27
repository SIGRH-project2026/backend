package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.UtilisateurResponseDTO;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpressionDeBesoinResponseDTO {
    private long id;
    private String besoin;
    private String motif;
    private LocalDate date;
    private String reference;
    private String statut;
    UtilisateurResponseDTO utilisateurResponseDTO;
    CampagneResponseDTO campagneResponseDTO;
    String themeProvisoire;
}
