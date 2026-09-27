package sn.gainde2000.backenmfpai.entities.serviceutilisateur.central;

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
@Table(name = "TP_Direction", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_direction", initialValue = 100, allocationSize = 2, sequenceName = "seq_direction")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_direction")
    @Column(name = "dir_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "dir_code", unique = true)
    private String code;

    @Size(max = 100)
    @Column(name = "dir_libelle")
    private String label;


    @Column(name = "dir_statut", columnDefinition = "boolean default true")
    private Boolean statut;



    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

}
