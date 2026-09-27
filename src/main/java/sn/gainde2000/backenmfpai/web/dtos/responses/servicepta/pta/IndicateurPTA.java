package sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta;

import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ActionPTA;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/09/2024-10:39
 * @project backend_mfpai
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicateurPTA {
    private List<ActionPTA> actions;
    private long nombreAction;
    private long tauxAtteint;
}
