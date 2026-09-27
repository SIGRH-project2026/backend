package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:14
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_PlanDeTravail", schema = "schema_pta")
@SequenceGenerator(name = "seq_plan_travail", initialValue = 100, allocationSize = 2, sequenceName = "seq_plan_travail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanDeTravail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_plan_travail")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pta_inial_pta_id")
    private InitialPTA initialPTA;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "pta_action_id")
    private List<ActionPTA> actionPTAs;


}
