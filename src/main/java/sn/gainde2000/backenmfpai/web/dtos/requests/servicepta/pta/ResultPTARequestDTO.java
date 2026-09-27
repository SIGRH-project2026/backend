package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



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
public class ResultPTARequestDTO {
    private String libelleResultat;
    private Integer tauxAtteint;
    private Integer cible;
    private Long actionId;

}
