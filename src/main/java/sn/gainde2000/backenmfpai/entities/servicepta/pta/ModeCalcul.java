package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-15:57
 * @project backend_mfpai
 */


@Entity
@Table(name = "TD_ModeCalcul", schema = "schema_pta")
@SequenceGenerator(name = "seq_mode_calcul", initialValue = 100, allocationSize = 2, sequenceName = "seq_mode_calcul")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModeCalcul {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mode_calcul")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;


    @Size(max = 100)
    @Column(name = "mode_calcul_mode")
    private String modeCalcul;

    @Size(max = 100)
    @Column(name = "mode_calcul_source")
    private String sourceDonnees;

    @Size(max = 100)
    @Column(name = "mode_calcul_frequence")
    private String frequenceProd;


    @Size(max = 100)
    @Column(name = "mode_calcul_collect")
    private String methodCollecte;

    @OneToMany
    @JoinColumn(name = "sub_action_division_id")
    private Set<Division> divisions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "result_id")
    private ResultPTA resultAction;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeCalcul that = (ModeCalcul) o;
        return Objects.equals(id, that.id) && Objects.equals(modeCalcul, that.modeCalcul) && Objects.equals(frequenceProd, that.frequenceProd) && Objects.equals(methodCollecte, that.methodCollecte) && Objects.equals(divisions, that.divisions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, modeCalcul, frequenceProd, methodCollecte, divisions);
    }
}
