package sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.ResultPTARequestDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:42
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ResultPTAResponseDTO extends ResultPTARequestDTO {

    private Long id;
    private String numResult;
}
