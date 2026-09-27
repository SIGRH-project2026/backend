package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;

/**
 * @author Abdou Karim CISSOKHO
 * @created 30/05/2024-14:57
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_EEFMinistere", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_eefmi", initialValue = 100, allocationSize = 2, sequenceName = "seq_eefmi")
public class EEFMinistere {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_eefmi")
    @Column(name = "eefmi_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "eefmi_code")
    private String code;

    @Size(max = 100)
    @Column(name = "eefmi_libelle")
    private String label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eefmi_region_id")
    private Region region;
}
