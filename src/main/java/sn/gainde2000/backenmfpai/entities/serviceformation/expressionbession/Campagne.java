package sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_Campagne", schema = "schema_formation")
public class Campagne {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Cam_Id")
    private long id;

    @NotNull(message = "Veuillez saisir le nom de la campagne")
    @Column(name = "Cam_Nom")
    private String nom;

    @NotNull(message = "Veuillez renseigner la date de début de la campagne")
    @Column(name = "Cam_DateDebut", nullable = true)
    private LocalDate dateDebut;

    @NotNull(message = "Veuillez renseigner la date de fin de la campagne")
    @Column(name = "Cam_DateFin")
    private LocalDate dateFin;

    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnore
    private CentralLevel centralLevel;

    @OneToMany(mappedBy = "campagne")
    private Set<ExpressionDeBesoin> expressionDeBesoins = new HashSet<>();

    @Column(name = "Cam_statut")
    private String statut;

    @OneToMany
    @JoinColumn(name = "Cam_pieceJoint")
    private List<File> pieceJoint = new ArrayList<>();
}
