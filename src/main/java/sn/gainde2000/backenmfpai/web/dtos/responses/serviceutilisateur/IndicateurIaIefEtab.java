package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicateurIaIefEtab {
    private long indIa;
    private long indIef;
    private long indEtab;
}

