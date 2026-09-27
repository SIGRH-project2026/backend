package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutCampagneEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatutCampagneResponseDTO {
    private StatutCampagneEnum code;
    private String libelle;

}
