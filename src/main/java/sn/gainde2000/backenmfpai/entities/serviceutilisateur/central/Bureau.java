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
@Table(name = "TP_Bureau", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_bureau", initialValue = 100, allocationSize = 2, sequenceName = "seq_bureau")
@ToString
public class Bureau {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bureau")
    @Column(name = "bur_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "bur_code", unique = true)
    private String code;

    @Size(max = 100)
    @Column(name = "bur_libelle")
    private String label;

    @Column(name = "bur_statut", columnDefinition = "boolean default true")
    private Boolean statut;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

}
