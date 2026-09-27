package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bsdieme
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierAgentRequestDto {

    protected Long id;
    protected boolean isDeleted;
    protected List<Diplome> diplomes = new ArrayList<>();
    protected List<Avancement> avancements = new ArrayList<>();
    protected List<EtatCivil> etatCivil = new ArrayList<>();

    protected List<SituationAdministrative> situationAdministrative = new ArrayList<>();
    protected Agent agId;
    //private String situationMatrimoniale;
    private long utilisateurId;

    // private LocalDate dateRecrutement;
   // private String posteActuel;
    //private DeconcentratedLevel deconcentredLevel;

}
