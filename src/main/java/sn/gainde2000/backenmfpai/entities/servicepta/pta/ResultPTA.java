package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:14
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_ResultatPTA", schema = "schema_pta")
@SequenceGenerator(name = "seq_reseulta_pta", initialValue = 100, allocationSize = 2, sequenceName = "seq_reseulta_pta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultPTA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reseulta_pta")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "result_label")
    private String libelleResultat;

    @Size(max = 100)
    @Column(name = "result_num")
    private String numResult;

    @Column(name = "result_rate_acheived")
    private Integer tauxAtteint;

    @Column(name = "result_target", columnDefinition = "integer default 100")
    private Integer cible;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultPTA resultPTA = (ResultPTA) o;
        return Objects.equals(id, resultPTA.id) && Objects.equals(libelleResultat, resultPTA.libelleResultat) && Objects.equals(tauxAtteint, resultPTA.tauxAtteint) && Objects.equals(cible, resultPTA.cible);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleResultat, tauxAtteint, cible);
    }
}
