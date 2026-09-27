package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.audit.Auditable;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

/**
 * @author G2k R&D
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TD_UTILISATEUR", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_user", initialValue = 100, allocationSize = 2, sequenceName = "seq_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Utilisateur extends Auditable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 50)
    @Column(name = "user_firstName")
    protected String prenom;

    @Size(max = 25)
    @Column(name = "user_lastName", nullable = false)
    protected String nom;

    @Size(max = 200)
    @Column(name = "user_pmail")
    protected String email;

    @Column(name = "user_date_entree")
    protected LocalDate dateDEntree;

    @Column(name = "user_password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    // ignoring password à revoir
    protected String password;

    @Size(max = 10)
    @Column(name = "user_user_type")
    protected String typeUser;

    @Column(name = "Uti_FirstLog", columnDefinition = "boolean default true")
    private Boolean firstLog = true;

    @Column(name = "user_status", columnDefinition = "boolean default true")
    protected Boolean status;

    @Size(max = 20)
    @Column(name = "user_phoneNumber")
    protected String telephone;

    @Size(max = 150)
    @Column(name = "user_adresse")
    protected String adresse;

    @Size(max = 20)
    @Column(name = "user_matricule")
    protected String matricule;

    @Size(max = 10)
    @Column(name = "user_sexe")
    protected String sexe;

    @Size(max = 50)
    @Column(name = "user_marital_status")
    protected String situationMatrimoniale;

    // @NotNull
    @ManyToOne
    @JoinColumn(name = "corp_id")
    protected CorpsGrade corpsGrade;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    protected Grade grade;

    // @NotNull
    @ManyToOne
    @JoinColumn(name = "fonction_id")
    protected Fonction fonction;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    protected Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "region_id")
    protected Region region;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TR_USER_PROFILE",
            schema = "schema_utilisateur",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    protected Set<Profile> profils = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BesoinEnPersonnel> besoinsEnPersonnel = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImputationOuBulletin> imputationOuBulletins = new ArrayList<>();

    // *********************************
    // NEW FIELDS
    // *********************************

    @Size(max = 20)
    @Column(name = "user_matricule_solde")
    protected String matriculeFonctionnaire;

    @Size(max = 20)
    @Column(name = "user_matricule_contractuel")
    protected String matriculeContratuel;

    @Size(max = 20)
    @Column(name = "user_cni")
    protected String cni;

    @Size(max = 20)
    @Column(name = "user_nationalite")
    protected String nationalite;

    @ManyToOne
    @JoinColumn(name = "type_mat_id")
    protected TypeMatricule typeMatricule;

    @Column(name = "user_date_corp")
    protected LocalDate dateCorp;

    @Column(name = "user_date_fonc_pub")
    protected LocalDate dateDEntreeFonctionPub;

    @Column(name = "user_date_enseign")
    protected LocalDate dateEntreEnseignement;

    @Column(name = "user_date_service")
    protected LocalDate dateEntreService;

    @ManyToOne
    @JoinColumn(name = "dip_prof_id")
    protected DiplomePROF diplomePROF;

    @ManyToOne
    @JoinColumn(name = "dip_ped_id")
    protected DiplomePED diplomePED;

    @ManyToOne
    @JoinColumn(name = "dip_aca_id")
    protected DiplomeACA diplomeACA;

    @ManyToOne
    @JoinColumn(name = "dip_type_poste_id")
    protected TypePoste typePoste;

    @Column(columnDefinition = "integer default 0")
    protected int nombreEnfants;

    @Column(name = "user_is_fonctionnaire", columnDefinition = "boolean default false")
    protected Boolean isFonctionnaire;

    @Size(max = 100)
    @Column(name = "user_lieu_naissance")
    protected String lieuDeNaissance;

    @Column(name = "user_date_naissance")
    protected LocalDate dateNaissance;

    @Size(max = 20)
    @Column(name = "user_matricule_vac")
    protected String matriculeVacataire;

    @Size(max = 20)
    @Column(name = "user_matricule_deci")
    protected String matriculeDecisionnaire;

}
