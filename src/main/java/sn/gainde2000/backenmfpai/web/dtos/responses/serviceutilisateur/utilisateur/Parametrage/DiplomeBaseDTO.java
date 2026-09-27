package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-12:15
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiplomeBaseDTO {
    private Long id;
    private String code;
    private String label;
    private String type; // ou tout autre champ supplémentaire
}