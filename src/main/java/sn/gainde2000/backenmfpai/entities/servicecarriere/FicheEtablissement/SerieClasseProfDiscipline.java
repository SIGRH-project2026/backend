package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TR_SerieClasseProfDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_SerieClasseProfDiscipline", initialValue = 100, allocationSize = 2, sequenceName = "seq_SerieClasseProfDiscipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SerieClasseProfDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_serieClasseProfDiscipline")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie  serie;

    @Size(max = 20)
    @Column(name = "serieClaProfDisc_nomClasse")
    private String nomClasse;

    @Column(name = "serieClaProfDisc_quantum")
    private int quantum;

    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfDiscipline> profDiscipline = new ArrayList<>();
}
