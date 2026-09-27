package sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TP_StatutBesoinEnPersonnel", schema = "schema_carriere")
@SequenceGenerator(name = "seq_statutBEP", initialValue = 100, allocationSize = 2, sequenceName = "seq_statutBEP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StatutBEP {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_statutBEP")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;
    @Size(max = 10)
    @Column(name = "StatutBEP_code")
    private String code;
    @Size(max = 30)
    @Column(name = "StatutBEP_libelle")
    private String libelle;
}
