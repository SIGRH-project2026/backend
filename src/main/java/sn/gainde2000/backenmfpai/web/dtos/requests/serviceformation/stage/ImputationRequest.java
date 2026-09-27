package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImputationRequest {
    private String directionCode;
    private String divisionCode;
    private String serviceCode;
    private String bureauCode;
}
