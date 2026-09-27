package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

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
@Table(name = "TP_Diplome_ACA", schema = "schema_utilisateur")
//@SequenceGenerator(name = "seq_dip_aca", initialValue = 100, allocationSize = 2, sequenceName = "seq_dip_aca")
public class DiplomeACA {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dip_aca")
    @Column(name = "dip_aca_id", nullable = false, updatable = false)
    private Long id;

    @Size(max = 10)
    @Unique
    @Column(name = "dip_aca_code", unique = true,  nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "dip_aca_Libelle")
    private String label;

    @Column(name = "dip_aca_statut", columnDefinition = "boolean default true")
    private Boolean statut;



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DiplomeACA that = (DiplomeACA) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(label, that.label) && Objects.equals(statut, that.statut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label, statut);
    }
}
