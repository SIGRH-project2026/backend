package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_StatutTraitementActe", schema = "schema_carriere")
@SequenceGenerator(name = "seq_statutTraitement_acte", initialValue = 100, allocationSize = 2, sequenceName = "seq_statutTraitement_acte")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatutTraitementActe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_statutTraitement_acte")
    @Column(name = "statTraitement_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "statTraitement_niveau")
    private String niveauTraitement;

    @Size(max = 100)
    @Column(name = "statTraitement_Code")
    private String code;

    @Size(max = 100)
    @Column(name = "statTraitement_Libelle")
    private String libelle;
}
