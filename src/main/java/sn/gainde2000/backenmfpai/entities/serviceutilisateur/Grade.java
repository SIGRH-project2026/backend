package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 03/03/2024-15:46
 * @project backend_mfpai
 */

@Entity
@Table(name = "TP_Grade", schema = "schema_utilisateur")
//@SequenceGenerator(name = "seq_grade", initialValue = 300, allocationSize = 2, sequenceName = "seq_grade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Grade {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_grade")
    @Column(name = "grade_id",nullable = false, updatable = false, unique = true)
    private Long  id;

    @Size(max = 200)
    @Column(name = "grade_code", unique = true, nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "grade_libelle")
    private String label;

    @ManyToOne
    @JoinColumn(name = "corps_id")
    private CorpsGrade corpsGrade;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(id, grade.id) && Objects.equals(code, grade.code) && Objects.equals(label, grade.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label);
    }
}
