package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 03/06/2024-10:21
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_EFSpeciality", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_eef_spec", initialValue = 100, allocationSize = 2, sequenceName = "seq_eef_spec")
public class EEFSpeciality {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_eef_spec")
    @Column(name = "eef_spec_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "eef_spec_code")
    private String code;

    @Size(max = 100)
    @Column(name = "eef_spec_libelle")
    private String label;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eef_id")
    private EEF eef;
}
