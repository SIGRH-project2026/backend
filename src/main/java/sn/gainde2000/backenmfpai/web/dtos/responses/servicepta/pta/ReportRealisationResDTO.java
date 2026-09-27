package sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.ReportRealisationDTO;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 28/08/2024-11:25
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReportRealisationResDTO  extends ReportRealisationDTO {

    private Long id;
     private Set<File> files;
}
