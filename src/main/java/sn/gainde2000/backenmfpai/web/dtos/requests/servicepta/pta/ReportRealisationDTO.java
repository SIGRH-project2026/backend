package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.SubActionPTA;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 28/08/2024-11:24
 * @project backend_mfpai
 */


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReportRealisationDTO {

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String resume;
    private Integer cible;
    private Integer tauxAtteint;
    private String observation;
    private SubActionPTA subAction;

}
