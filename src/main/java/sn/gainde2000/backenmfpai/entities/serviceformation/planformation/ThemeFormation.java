package sn.gainde2000.backenmfpai.entities.serviceformation.planformation;

import lombok.Getter;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "TD_THEMEFORMATION", schema = "schema_formation")

public class ThemeFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "libelle")
    private String libelle;

    @NotNull(message = "ne doit pas être nul")
    @Size(max = 100)
    private String duree;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "modalite")
    private String modalites;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "operateur")
    private String operateur;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "bailleur")
    private String bailleur;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "budget")
    private String budget;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "id_plan_formation")
    private PlanFormation planFormation;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TR_THEME_FORMATION_PROFILE", schema = "schema_formation",
            joinColumns = @JoinColumn(name = "theme_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    protected Set<Profile> profils = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "id_respponsable_suivi")
    private Utilisateur responsableSuivi;

}
