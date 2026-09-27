package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TD_DisciplineQuantum", schema = "schema_carriere")
@SequenceGenerator(name = "seq_disciplineQuantum", initialValue = 100, allocationSize = 2, sequenceName = "seq_disciplineQuantum")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DisciplineQuantum {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_disciplineQuantum")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline  discipline;
    @Column(name = "DiscQuan_quantum")
    private int quantum;

}
