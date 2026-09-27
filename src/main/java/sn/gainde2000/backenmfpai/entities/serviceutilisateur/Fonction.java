package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

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
@Builder
@Entity
@Table(name = "TP_Fonction", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_fonction", initialValue = 100, allocationSize = 2, sequenceName = "seq_fonction")
public class Fonction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_fonction")
    @Column(name = "fon_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 20)
    @Column(name = "pro_code", unique = true, nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "pro_Libelle")
    private String label;


    @Column(name = "dir_statut", columnDefinition = "boolean default true")
    private Boolean statut;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fonction fonction = (Fonction) o;
        return Objects.equals(id, fonction.id) && Objects.equals(code, fonction.code) && Objects.equals(label, fonction.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label);
    }
}

