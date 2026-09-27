package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TP_Discipline", schema = "schema_carriere")
@SequenceGenerator(name = "seq_discipline", initialValue = 100, allocationSize = 2, sequenceName = "seq_discipline")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Discipline {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_discipline")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 10)
    @Column(name = "Disc_code")
    private String code;

    @Size(max = 30)
    @Column(name = "Disc_libelle")
    private String libelle;
}
