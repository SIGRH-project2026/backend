package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_FORMATION", schema = "schema_formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull(message = "ne doit pas être nul")
    @ManyToOne
    @JoinColumn(name = "type_formation_id")
    private TypeFormation typeFormation;

    // @NotNull(message = "ne doit pas être nul")
    @ManyToOne
    @JoinColumn(name = "theme_formation_id")
    private ThemeFormation themeFormation;

    @Size(max = 200)
    @Column(name = "intitule")
    private String intitule;

    @Size(max = 2000)
    @Column(name = "reference")
    private String reference;

    // @NotNull(message = "ne doit pas être nul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    // @NotNull(message = "ne doit pas être nul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_envoi")
    private Date dateEnvoi;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_reception")
    private Date dateReception;

    @Size(max = 200)
    @Column(name = "duree")
    private String duree;

    @Size(max = 200)
    @Column(name = "prestataires")
    private String prestataires;

    @Size(max = 200)
    @Column(name = "cout")
    private String cout;

    @Size(max = 2000)
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "statut_formation_id")
    private StatutFormation statutFormation;

    @OneToOne
    @JoinColumn(name = "cahier_charge_id")
    private File cahierCharge;

    @Size(max = 200)
    @Column(name = "eff_code")
    private String effCode;

    @Size(max = 200)
    @Column(name = "specialite_code")
    private String specialiteCode;

    //@Size(max = 200)
    @Column(name = "nombre_place")
    private Integer nombrePlace;

}
