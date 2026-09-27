package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeMatricule;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-16:04
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParametreCorpsGradeDTO {

    private CorpsGrade corpsGrade;
    private Set<Grade> grades;
    private Speciality speciality;
    private TypeMatricule typeMatricules;


}
