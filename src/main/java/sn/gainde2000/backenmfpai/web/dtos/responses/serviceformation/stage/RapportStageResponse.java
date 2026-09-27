package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.stage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RapportStageResponse {
    private long id;
    File piecesJoint;
}
