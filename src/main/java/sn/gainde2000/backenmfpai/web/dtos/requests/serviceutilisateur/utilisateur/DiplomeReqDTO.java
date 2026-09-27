package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeDiplome;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-09:38
 * @project backend_mfpai
 */


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiplomeReqDTO {

    private String code;
    private String label;
    private TypeDiplome typeDiplome;
}
