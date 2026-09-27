package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ActionPTA;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.InitialPTA;


import java.util.List;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:44
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PlanDeTravailRequestDTO {
    @NotNull(message = PLAN_DE_TRAVAIL_INITIAL_OBLIGATOIRE)
    private InitialPTA initialPTA;

    //@NotNull(message = PLAN_DE_TRAVAIL_ACTION_OBLIGATOIRE)
    private List<ActionPTA> actionPTAs;

}
