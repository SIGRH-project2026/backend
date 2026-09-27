package sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "TP_StatutMutation", schema = "schema_carriere")
@SequenceGenerator(name = "seq_statutMut", initialValue = 100, allocationSize = 2, sequenceName = "seq_statutMut")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StatutMutation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_statutMut")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;
    @Size(max = 10)
    @Column(name = "StatutMut_code")
    private String code;
    @Size(max = 30)
    @Column(name = "StatutMut_libelle")
    private String libelle;
}
