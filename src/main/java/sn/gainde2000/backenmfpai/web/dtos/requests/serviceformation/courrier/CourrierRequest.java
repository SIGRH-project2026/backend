package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.courrier;

import lombok.*;
import sn.gainde2000.backenmfpai.repositories.serviceformation.courrier.NomTypeCourrier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CourrierRequest {
    private String reference;
    private String codeDemandeCourrier;
    private String directionCode;
    private String divisionCode;
    private NomTypeCourrier typeCourrier;
    private String otherField;
}
