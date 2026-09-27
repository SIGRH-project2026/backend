package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DeconcentratedLevelDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-16:52
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeconcentratedLevelRespDTO  extends DeconcentratedLevelDTO {

        private Long id;

}
