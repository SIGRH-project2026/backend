package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

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
@Table(name = "TP_Region", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_region", initialValue = 100, allocationSize = 2, sequenceName = "seq_region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_region")
    @Column(name = "reg_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "reg_code", unique = true, nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "reg_libelle")
    private String label;



}
