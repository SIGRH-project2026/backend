package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-11:12
 * @project backend_mfpai
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_Type_Poste", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_type_poste", initialValue = 100, allocationSize = 2, sequenceName = "seq_type_poste")
public class TypePoste {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_type_poste")
    @Column(name = "type_poste_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "type_poste_code", unique = true, nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "type_poste_Libelle")
    private String label;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePoste that = (TypePoste) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label);
    }
}
