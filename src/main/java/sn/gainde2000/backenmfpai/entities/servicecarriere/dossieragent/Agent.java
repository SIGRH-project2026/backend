package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TD_Agent", schema = "schema_carriere")
@SequenceGenerator(name = "seq_agent", initialValue = 100, allocationSize = 2, sequenceName = "seq_agent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agent")
    @Column(name = "ag_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 50)
    @Column(name = "ag_situation_matrimoniale",nullable = false, updatable = true, unique = false)
    private String agSituationMatrimoniale;

    @Size(max = 15)
    @Column(name = "ag_date_recrutement",nullable = false, updatable = true)
    private LocalDate agDateRecrutement;

    @Size(max = 15)
    @Column(name = "ag_poste_actuel",nullable = false, updatable = true)
    private String agPosteActuel;

    @Size(max = 10)
    @Column(name = "ag_IsDeleted")
    private Boolean isDeleted;

    @OneToOne
    @JoinColumn(name = "dosId")
    private DossierAgent agDossier;

    @OneToOne
    @JoinColumn(name="DeconcentredLevelId")
    private DeconcentratedLevel agDeconcentredLevel;

  /*  @OneToMany(mappedBy = "agent", fetch = FetchType.EAGER)
    @Column(name = "ag_actes")
    private List<Acte> actes = new ArrayList<>();*/

    public void setIsDeleted(boolean b) {
        this.isDeleted=b;
    }
}
