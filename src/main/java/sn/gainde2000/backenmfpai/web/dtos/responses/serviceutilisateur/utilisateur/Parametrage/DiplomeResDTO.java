package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DiplomeReqDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-09:40
 * @project backend_mfpai
 */

@Getter
@Setter
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class DiplomeResDTO extends DiplomeReqDTO {
    private Long id;
    private Boolean statut;
}
