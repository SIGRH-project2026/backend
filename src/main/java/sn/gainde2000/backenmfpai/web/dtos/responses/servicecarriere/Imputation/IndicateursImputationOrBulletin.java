package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicateursImputationOrBulletin {
    private long imputation;
    private long bulletin;
    private long all;

}
