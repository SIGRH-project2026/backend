package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 07/08/2024-10:20
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_Etablissement_Type", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_type_etablissement", initialValue = 100, allocationSize = 2, sequenceName = "seq_type_etablissement")

public class TypeEtablissement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_etablissement")
    @Column(name = "eta_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "eta_code")
    private String code;

    @Size(max = 100)
    @Column(name = "eta_libelle")
    private String label;

}
