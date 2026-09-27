package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DossierAgentResponseDto {

    //private DeconcentratedLevel deconcentratedLevel;
    private Utilisateur utilisateur;

    protected Long id;
    protected boolean isDeleted;
    protected List<Diplome> diplomes = new ArrayList<>();
    protected List<Diplome> diplomesNoPiecejointes = new ArrayList<>();

//    protected List<Avancement> Avancements = new ArrayList<>();
//    protected List<Avancement> AvancementsNoPiecesjointes = new ArrayList<>();

    protected List<EtatCivil> etatCivil = new ArrayList<>();
    protected List<EtatCivil> etatCivilsNoPiecesjointes = new ArrayList<>();

    protected List<SituationAdministrative> situationAdministrative = new ArrayList<>();
    protected List<SituationAdministrative> situationAdministrativeNoPiecesjointes = new ArrayList<>(); ;

    protected Agent agId;
   // private String situationMatrimoniale;
   // private LocalDate dateRecrutement;
   // private String posteActuel;

    // 👇 NOUVEAUX CHAMPS À AJOUTER 👇
   private boolean hasDossier;
   private boolean canCreateDossier;
}
