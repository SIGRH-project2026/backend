package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeEtablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;

/**
 * @author Abdou Karim CISSOKHO
 * @created 07/08/2024-10:11
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EtablissementDTO {
    private String code;
    private String label;
    private IA ia;
    private IEF ief;
    private TypeEtablissement typeEtablissement;
    private Structure structure;
    private TypeSystemeEnseignement typeSystemeEnseignement;
}
