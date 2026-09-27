package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-12:04
 * @project backend_mfpai
 */


@Entity
@Table(name = "TD_TDSequence", schema = "schema_pta")
@SequenceGenerator(name = "seq_td_sequence", initialValue = 1, allocationSize = 2, sequenceName = "seq_td_sequence")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TDSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_td_sequence")
    @Column(nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(nullable = false, name = "seq_annee")
    private Integer annee;
    @Column(nullable = false, name = "seq_numero")
    private Integer numero;
}
