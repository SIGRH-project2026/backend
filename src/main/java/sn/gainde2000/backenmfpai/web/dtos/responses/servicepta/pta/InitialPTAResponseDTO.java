package sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.InitialPTARequestDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-11:46
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class InitialPTAResponseDTO extends InitialPTARequestDTO {

    private Long id;
    private String numeroPTA;
}
