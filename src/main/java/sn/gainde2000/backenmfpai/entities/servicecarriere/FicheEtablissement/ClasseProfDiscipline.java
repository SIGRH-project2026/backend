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

@Table(name = "TR_ClasseProfDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_classeProfDiscipline", initialValue = 100, allocationSize = 2, sequenceName = "seq_classeProfDiscipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClasseProfDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_classeProfDiscipline")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 20)
    @Column(name = "claProfDisc_nomClasse")
    private String nomClasse;

    @Column(name = "claProfDisc_quantum")
    private int quantum;
    @OneToMany(fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfDiscipline> profDiscipline = new ArrayList<>();

}
