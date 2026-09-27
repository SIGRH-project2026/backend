package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ResultPTA;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.SubActionPTA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import java.util.List;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-16:07
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ModeCalculRequestDTO {

    @NotBlank
    @NotNull
    private String modeCalcul;
    private String frequenceProd;
    private String methodCollecte;
    private String sourceDonnees;
    private Set<Division> divisions;
   // @NotNull
    private ResultPTA resultAction;

    private SubActionPTA subAction;

}
