package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ModeCalcul;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ResultPTA;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.parametre.ParametreResponse;


import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-16:03
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubActionPTAReqDTO {

    private String libelleSubAction;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal budget;
    private String sourceFinancement;
    private String moyenRH;
    private ModeCalcul modeCalcul;
    private ResultPTA resultPTA;

    private ParametreResponse indicateur;

}
