package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:46
 * @project backend_mfpai
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_CFP", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_cfp", initialValue = 100, allocationSize = 2, sequenceName = "seq_cfp")

public class CFP {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cfp")
    @Column(name = "cfp_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "cfp_code")
    private String code;

    @Size(max = 100)
    @Column(name = "cfp_libelle")
    private String label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ief_id")
    private IEF ief;
}
