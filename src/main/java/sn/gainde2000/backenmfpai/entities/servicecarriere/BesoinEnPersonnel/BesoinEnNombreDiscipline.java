package sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Discipline;

@Table(name = "TR_BesoinEnNombreDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_BENbre_Disc", initialValue = 1, allocationSize = 2, sequenceName = "seq_BENbre_Disc")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BesoinEnNombreDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_BENbre_Disc")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @Column(name = "BesoinEnNbreDisc_nombreDePersonne")
    private Long nombreDePersonne;
}
