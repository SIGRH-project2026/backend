package sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "Tp_status_permutation", schema = "schema_carriere")
@SequenceGenerator(name = "seq_status_permu", initialValue = 100, allocationSize = 2, sequenceName = "seq_status_permu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusPermutation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_status_permu")
    @Column(name = "status_permutation_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 50)
    @Column(name = "status_code", nullable = false)
    private String code;

    @Size(max = 50)
    @Column(name = "status_libelle", nullable = false)
    private String libelle;

}
