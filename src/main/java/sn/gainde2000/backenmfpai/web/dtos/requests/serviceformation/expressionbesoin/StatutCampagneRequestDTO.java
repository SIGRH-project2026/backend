package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatutCampagneRequestDTO {
    private String code;
    private String libelle;
}
