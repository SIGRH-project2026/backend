package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-15:47
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_SubAction", schema = "schema_pta")
@SequenceGenerator(name = "seq_sub_acton_pta", initialValue = 100, allocationSize = 2, sequenceName = "seq_sub_acton_pta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubActionPTA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sub_acton_pta")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;


    @Size(max = 100)
    @Column(name = "sub_action_label")
    private String libelleSubAction;

    @Column(name = "sub_action_date_debut")
    private LocalDate dateDebut;

    @Column(name = "sub_action_date_fin")
    private LocalDate dateFin;

    @Column(name = "sub_action_budget")
    private BigDecimal budget;

    @Size(max = 100)
    @Column(name = "sub_action_finacement")
    private String sourceFinancement;


    @Size(max = 100)
    @Column(name = "sub_action_numn")
    private String numAction;



    @Size(max = 100)
    @Column(name = "sub_action_moyen_rhl")
    private String moyenRH;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_action_user_id")
    private Utilisateur utilisateur;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_action_indicateur_id")
    private Parametre indicateur;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_action_mode_id")
    private ModeCalcul modeCalcul;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_action_report_id")
    private ReportRealisation reportRealisation;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_action_result_id")
    private ResultPTA resultPTA;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubActionPTA that = (SubActionPTA) o;
        return Objects.equals(id, that.id) && Objects.equals(libelleSubAction, that.libelleSubAction) && Objects.equals(dateDebut, that.dateDebut) && Objects.equals(dateFin, that.dateFin) && Objects.equals(budget, that.budget) && Objects.equals(sourceFinancement, that.sourceFinancement) && Objects.equals(moyenRH, that.moyenRH) && Objects.equals(utilisateur, that.utilisateur) && Objects.equals(resultPTA, that.resultPTA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleSubAction, dateDebut, dateFin, budget, sourceFinancement, moyenRH, utilisateur, resultPTA);
    }
}
