package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;

import java.time.LocalDate;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-11:45
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class InitialPTARequestDTO {

    @NotNull(message = PLAN_DE_TRAVAIL_NOM_PTA_OBLIGATOIRE)
    private String nomPTA;

    @NotNull(message = PLAN_DE_TRAVAIL_DATE_PTA_OBLIGATOIRE)
    private LocalDate date;

    @NotNull(message = PLAN_DE_TRAVAIL_DIRECTION_PTA_OBLIGATOIRE)
    private Direction direction;
}
