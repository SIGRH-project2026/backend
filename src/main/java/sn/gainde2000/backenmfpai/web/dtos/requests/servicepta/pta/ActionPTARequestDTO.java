package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:38
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ActionPTARequestDTO {

    private String libelleAction;
   // private List<ResultPTARequestDTO> resultActions;
    private Long idPTA;

}
