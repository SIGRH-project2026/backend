package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.ParametreCorpsGradeDTO;

import java.time.LocalDate;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-16:05
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParametreCorpsGradeRspDTO  extends ParametreCorpsGradeDTO {
    private Long id;
    private String noRef;
    private LocalDate dateParam;
}
