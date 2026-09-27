package sn.gainde2000.backenmfpai.entities.serviceformation.stage;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_DemandeStage", schema = "schema_formation")
public class DemandeStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Demande_Id")
    private long id;

    @Column(name = "Demande_numero", unique = true)
    private String numero;

    @Column(name = "Demande_PrenomDemandeur", length = 80, nullable = false)
    private String prenomDemandeur;

    @Column(name = "Demande_NomDemandeur", length = 80, nullable = false)
    private String nomDemandeur;

    @Column(name = "Demande_DateDeNaissance", nullable = false)
    private LocalDate dateNaissance;

    @Column(name = "Demande_LieuDeNaissance", nullable = false)
    private String lieuDeNaissance;

    @Email
    @Column(name = "Demande_Mail", length = 100, nullable = false)
    private String mail;

    @Column(name = "Demande_Tel", length = 30, nullable = false)
    private String tel;

    @Column(name = "Demande_Adresse", nullable = false)
    private String adresse;

    @Column(name = "Demande_Objet",length = 255, nullable = false)
//    @Lob
    private String objet;

    @ManyToOne
    @JoinColumn(name = "Demande_NiveauScolaire")
    private NiveauScolaire niveauScolaire;

    @Column(name = "Demande_Discipline", length = 80)
    private String disciplineStage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Demande_direction")
    private Direction direction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Demande_division")
    private Division division;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Demande_service")
    private Services service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Demande_bureau")
    private Bureau bureau;

    @Column(name = "Demande_DateDebut", nullable = true)
    private LocalDate dateDebut;

    @Column(name = "Demande_DateFin", nullable = true)
    private LocalDate dateFin;

    @Column(name = "Demande_justificatif", nullable = true)
    @OneToMany(fetch = FetchType.EAGER)
    private List<File> justificatfs = new ArrayList<>();

    @Column(name = "Demande_justificatfsAuthorisationStage", nullable = true)
    @OneToMany(fetch = FetchType.EAGER)
    private List<File> justificatfsAuthorisationStage = new ArrayList<>();

    @Column(name = "Demande_Commentaire", length = 255)
//    @Lob
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "Demande_status")
    private StatutDemandeStage statutDemandeStage;

    @ManyToOne
    @JoinColumn(name = "Demande_centralLevel")
    private CentralLevel centralLevel;
    private boolean deleted = false;

    @Column(name = "Demande_haveRapport")
    private boolean haveRapport = false;

    @Column(name = "Demande_haveAttestation")
    private boolean haveAttestation = false;

    @Column(name = "Demande_haveAuthorisationStage")
    private boolean haveAuthorisationStage = false;



}
