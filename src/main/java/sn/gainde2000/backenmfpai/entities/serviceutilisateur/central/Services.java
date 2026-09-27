package sn.gainde2000.backenmfpai.entities.serviceutilisateur.central;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

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
@Table(name = "TP_Service", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_service", initialValue = 100, allocationSize = 2, sequenceName = "seq_service")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_service")
    @Column(name = "ser_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "ser_code", unique = true)
    private String code;

    @Size(max = 100)
    @Column(name = "ser_libelle")
    private String label;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

}
