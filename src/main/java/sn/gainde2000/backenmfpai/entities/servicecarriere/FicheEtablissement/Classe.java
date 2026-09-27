package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TP_Classe", schema = "schema_carriere")
@SequenceGenerator(name = "seq_classe", initialValue = 100, allocationSize = 2, sequenceName = "seq_classe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_classe")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 10)
    @Column(name = "Cla_code")
    private String code;

    @Size(max = 30)
    @Column(name = "Cla_libelle")
    private String libelle;
}
