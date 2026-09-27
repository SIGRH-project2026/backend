package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-19:44
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_TDSequence_ref", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_sequence_ref", initialValue = 1, allocationSize = 2, sequenceName = "seq_sequence_ref")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TDSequenceRef {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sequence_ref")
    @Column(nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(nullable = false, name = "seq_ref_annee")
    private Integer annee;
    @Column(nullable = false, name = "seq_ref_numero")
    private Integer numero;
}
