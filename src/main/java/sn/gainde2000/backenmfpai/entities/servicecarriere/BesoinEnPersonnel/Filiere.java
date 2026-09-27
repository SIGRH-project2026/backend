package sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TP_Filiere", schema = "schema_carriere")
@SequenceGenerator(name = "seq_filiere", initialValue = 100, allocationSize = 2, sequenceName = "seq_filiere")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_filiere")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 80)
    @Column(name = "Fil_code")
    private String code;

    @Size(max = 80)
    @Column(name = "Fil_libelle")
    private String libelle;

}
