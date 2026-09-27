package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:24
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_ActionPTA", schema = "schema_pta")
@SequenceGenerator(name = "seq_acton_pta", initialValue = 100, allocationSize = 2, sequenceName = "seq_acton_pta")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionPTA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acton_pta")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "act_label_action")
    private String libelleAction;


    @Size(max = 100)
    @Column(name = "act_num_action")
    private String numAction;


     @OneToMany(fetch = FetchType.EAGER)
     @JoinColumn(name = "result_act_pta_id")
     private List<ResultPTA> resultActions = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionPTA actionPTA = (ActionPTA) o;
        return Objects.equals(id, actionPTA.id) && Objects.equals(libelleAction, actionPTA.libelleAction) && Objects.equals(resultActions, actionPTA.resultActions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelleAction, resultActions);
    }
}
