package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 03/06/2024-10:20
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_EEF", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_eef", initialValue = 100, allocationSize = 2, sequenceName = "seq_eef")

public class EEF {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_eef")
    @Column(name = "eef_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "eef_code")
    private String code;

    @Size(max = 100)
    @Column(name = "eef_libelle")
    private String label;


}
