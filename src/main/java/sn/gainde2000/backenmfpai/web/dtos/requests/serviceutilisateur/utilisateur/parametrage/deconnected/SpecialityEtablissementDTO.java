package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/08/2024-16:18
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpecialityEtablissementDTO {

    private List<Speciality> specialities;

    private Etablissement etablissement;

}
