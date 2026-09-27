package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.CentralLevelDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-15:20
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class CentralLevelResDTO extends CentralLevelDTO {

   private Long id;

  /* private Division division;
   private Services service;
   private Bureau bureau;
   private Direction direction;

   */

}
