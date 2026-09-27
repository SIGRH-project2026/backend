package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.util.HashSet;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/02/2024-10:54
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@ToString
public class UserManagerRequestDTO extends UtilisateurDTO{


}
