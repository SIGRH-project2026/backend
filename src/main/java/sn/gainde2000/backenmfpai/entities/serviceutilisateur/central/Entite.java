package sn.gainde2000.backenmfpai.entities.serviceutilisateur.central;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

/**
 * @author G2k R&D
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TP_Entity", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_entity", initialValue = 100, allocationSize = 2, sequenceName = "seq_entity")
public  abstract class Entite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entity")
    @Column(name = "ent_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "ent_code")
    private String code;

    @Size(max = 100)
    @Column(name = "ent_libelle")
    private String label;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entite entity = (Entite) o;
        return Objects.equals(id, entity.id) && Objects.equals(code, entity.code) && Objects.equals(label, entity.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label);
    }
}
