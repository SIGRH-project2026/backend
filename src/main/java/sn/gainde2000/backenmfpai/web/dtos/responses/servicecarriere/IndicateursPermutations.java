package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicateursPermutations {
    private long validated;
    private long rejected;
    private long all;

}

