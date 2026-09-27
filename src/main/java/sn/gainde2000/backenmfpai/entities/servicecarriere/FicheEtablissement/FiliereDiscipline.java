package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.Filiere;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TR_FiliereDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_filiereDiscipline", initialValue = 100, allocationSize = 2, sequenceName = "seq_filiereDiscipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FiliereDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_filiereDiscipline")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
    @ManyToOne
    @JoinColumn(name = "niveau_id")
    private Niveau  niveau;
    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisciplineQuantum> disciplineQuantums = new ArrayList<>();

    @Column(name = "FilDisc_quantum")
    private int quantum;

}
