package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-14:58
 * @project backend_mfpai
 */

@Entity
@Table(name = "TP_Corps", schema = "schema_utilisateur")
//@SequenceGenerator(name = "seq_corps_grade", initialValue = 200, allocationSize = 2, sequenceName = "seq_corps_grade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CorpsGrade {

    @Id
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_corps_grade")
    @Column(name = "cor_id",nullable = false, updatable = false, unique = true)
    private Long  id;

    @Size(max = 200)
    @Column(name = "cor_code", unique = true, nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "cor_libelle")
    private String label;

    @Size(max = 100)
    @Column(name = "cor_type_matricule")
    private String  typeMatricule;

    @Column(name = "cor_type_statut")
    private Boolean statut;


   /* @ManyToOne
    @JoinColumn(name = "type_matricule_id")
    private TypeMatricule  typeMatricule;

    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorpsGrade that = (CorpsGrade) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label);
    }
}
