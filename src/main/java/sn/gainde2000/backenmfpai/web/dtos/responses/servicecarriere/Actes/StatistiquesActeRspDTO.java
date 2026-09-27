package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor

public class StatistiquesActeRspDTO {

    private long countValidAA;
    private long countValidAG;
    private long countRejectAA;
    private long countRejectAG;
    private long countInProcessAA;
    private long countInProcessAG;
    private long sumAA;
    private long sumAG;

}
