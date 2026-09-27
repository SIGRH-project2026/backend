package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TR_ProfDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_profDiscipline", initialValue = 100, allocationSize = 2, sequenceName = "seq_profDiscipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProfDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profDiscipline")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisciplineQuantum> disciplineQuantums = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "deconcentratedLevel_id")
    private DeconcentratedLevel professeur;
/*
    @Column(name = "profDisc_quantum")
    private int quantum;*/
}
