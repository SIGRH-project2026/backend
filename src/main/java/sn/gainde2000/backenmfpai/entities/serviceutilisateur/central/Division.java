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
@Table(name = "TP_Division", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_division", initialValue = 100, allocationSize = 2, sequenceName = "seq_division")
@ToString
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_division")
    @Column(name = "div_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 20)
    @Column(name = "div_code", unique = true)
    private String code;

    @Size(max = 100)
    @Column(name = "div_libelle")
    private String label;

    @Column(name = "div_statut", columnDefinition = "boolean default true")
    private Boolean statut;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

}
