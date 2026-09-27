package sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TR_BEPFiliereDiscipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_BEP_F_D", initialValue = 1, allocationSize = 2, sequenceName = "seq_BEP_F_D")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BEPFiliereDiscipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_BEP_F_D")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

//    @ManyToOne
//    @JoinColumn(name = "filiere_id")
//    private Filiere filiere;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BesoinEnNombreDiscipline> besoinEnNombreDisciplines = new ArrayList<>();



}
