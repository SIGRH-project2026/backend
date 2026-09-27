package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-16:42
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_ia", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_ia", initialValue = 105, allocationSize = 2, sequenceName = "seq_ia")
public class IA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ia")
    @Column(name = "ia_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 20)
    @Column(name = "ia_code")
    private String code;

    @Size(max = 100)
    @Column(name = "ia_libelle")
    private String label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "ia_statut", columnDefinition = "boolean default true")
    private Boolean statut;


}
