
package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UtilisateurDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-16:52
 * @project backend_mfpai
 */


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeconcentratedLevelDTO  extends UtilisateurDTO {

       //  @NotNull
       //  private Region region;
        // @NotNull
        // private Speciality speciality;
        // @NotNull
         private IA ia;
       //  @NotNull
         private IEF ief;
        // @NotNull
       //  private CFP cfp;
        // @NotNull
         private Etablissement etablissement;
        // @NotNull
       // private StructureMFPAA structureMFPAA;

        private Integer quantumHoraire;
       // private LocalDate dateEntreEnseignement;

        private LocalDate dateEntreEtablissement;


        private TypeSystemeEnseignement typeSystemeEnseignement;


   // private EEFMinistere eefMinistere;
    private Structure structure;
        /* @NotBlank(message = PASSWORD_OBLIGATOIRE)
         @NotNull
         private String motPasse;

         private    Boolean status;

          */
         // Region region,

}

