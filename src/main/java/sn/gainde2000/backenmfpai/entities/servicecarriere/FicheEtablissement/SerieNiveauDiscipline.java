package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Table(name = "TR_SerieNiveauDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_SerieNiveauDiscipline", initialValue = 100, allocationSize = 2, sequenceName = "seq_SerieNiveauDiscipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SerieNiveauDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_SerieNiveauDiscipline")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie  serie;
    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau  niveau;

    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisciplineQuantum> disciplineQuantums = new ArrayList<>();

    @Column(name = "SerieNivDisc_quantum")
    private int quantum;
}
