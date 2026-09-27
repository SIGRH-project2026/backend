package sn.gainde2000.backenmfpai.entities.serviceformation.planformation;

import lombok.Getter;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TD_PLANFORMATION", schema = "schema_formation")
@JsonIgnoreProperties({ "files" })
public class PlanFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull(message = "ne doit pas être nul")
    @Size(max = 100)
    @Column(name = "ref")
    private String reference;

    @NotNull(message = "ne doit pas être nul")
    @Size(max = 100)
    private String titre;

    // @NotNull(message = "ne doit pas être nul")
    @Size(max = 100)
    @Column(name = "commentaire")
    private String commentaire;

    @NotNull(message = "ne doit pas être nul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @NotNull(message = "ne doit pas être nul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @NotNull(message = "ne doit pas être nul")
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Utilisateur createdBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<File> files;

    @ManyToOne
    @JoinColumn(name = "statut_plan_formation_id")
    private StatutPlanFormation statutPlanFormation;

    @OneToMany(mappedBy = "planFormation", cascade = CascadeType.ALL)
    private Set<ThemeFormation> themesFormation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_publication")
    private Date datePublication;

}
