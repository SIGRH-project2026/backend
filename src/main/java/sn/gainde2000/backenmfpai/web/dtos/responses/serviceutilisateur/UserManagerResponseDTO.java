package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UtilisateurDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-09:31
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserManagerResponseDTO extends UtilisateurDTO {
    private Long id;
}
