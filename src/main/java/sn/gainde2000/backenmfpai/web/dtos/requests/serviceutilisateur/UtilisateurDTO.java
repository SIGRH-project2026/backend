package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;


/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-10:41
 * @project backend_mfpai
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDTO {

    @NotBlank(message = NOM_OBLIGATOIRE)
    @Size(min = 2, max = 20, message = TAILLE_NOM)
    protected    String nom;
    @NotBlank(message = PRENOM_OBLIGATOIRE)
    @Size(min = 3, max = 60, message = TAILLE_PRENOM)
    protected    String prenom;
    @NotBlank(message = EMAIL_OBLIGATOIRE)
    @Email(message = EMAIL_NON_VALIDE)
    protected      String email;
    @NotBlank(message = TELEPHONE_OBLIGATOIRE)
    protected      String telephone;
    protected      String adresse;
    protected      String situationMatrimoniale;

   // @NotBlank(message = DATE_ENTRRE_OBLIGATOIRE)
    protected LocalDate dateDEntree;

    protected Region region;

    protected Speciality speciality;


    protected  String typeUser;
  //  @NotBlank(message = MATRICULE_OBLIGATOIRE)
    protected      String matricule;
    protected String sexe;
    @NotNull(message = UTILISATEUR_PROFIL_OBLIGATOIRE)
    protected Set<Profile> profils;

    @NotNull(message = UTILISATEUR_CorpsGrade_OBLIGATOIRE)
    protected CorpsGrade corpsGrade;

    @NotNull(message = UTILISATEUR_Grade_OBLIGATOIRE)
    protected Grade grade;

    @NotNull(message = UTILISATEUR_Fonction_OBLIGATOIRE)
    protected Fonction fonction;
   /* @NotNull
    protected String motPasse;*/
   protected   Boolean status;


    /***
     *   New fields
     */

    protected String matriculeFonctionnaire;
    protected String matriculeContratuel;
    protected String cni;
    protected String nationalite;
    protected TypeMatricule typeMatricule;
    protected  LocalDate dateCorp;
    protected  LocalDate dateDEntreeFonctionPub;
    protected  LocalDate dateEntreEnseignement;

    protected String lieuDeNaissance;
    protected   LocalDate dateNaissance;

  //  @NotNull(message = UTILISATEUR_Diplome_PROF_OBLIGATOIRE)
    protected DiplomePROF diplomePROF;
   // @NotNull(message = UTILISATEUR_Diplome_PED_OBLIGATOIRE)
    protected DiplomePED diplomePED;
  //  @NotNull(message = UTILISATEUR_Diplome_ACA_OBLIGATOIRE)
    protected DiplomeACA diplomeACA;
    @NotNull(message = UTILISATEUR_Type_POSTE_OBLIGATOIRE)
    protected TypePoste typePoste;
    protected int nombreEnfants;

    protected String matriculeVacataire;
    protected String matriculeDecisionnaire;

    protected LocalDate dateEntreService;



    public static UtilisateurDTO toDto(Utilisateur utilisateur) {
        UtilisateurDTO utilistateurDTO = new UtilisateurDTO();

        utilistateurDTO.setAdresse(utilisateur.getAdresse());
        utilistateurDTO.setNom(utilisateur.getNom());
        utilistateurDTO.setPrenom(utilisateur.getPrenom());
        utilistateurDTO.setEmail(utilisateur.getEmail());
        utilistateurDTO.setProfils(new HashSet<>(utilisateur.getProfils()));

        return  utilistateurDTO;
    }



  //  protected Long id;



}
