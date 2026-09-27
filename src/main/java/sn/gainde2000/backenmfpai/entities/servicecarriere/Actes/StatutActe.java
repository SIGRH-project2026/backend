package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_StatutActe", schema = "schema_carriere")
@SequenceGenerator(name = "seq_statut_acte", initialValue = 100, allocationSize = 2, sequenceName = "seq_statut_acte")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatutActe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_statut_acte")
    @Column(name = "stat_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "stat_Code")
    private String code;

    @Size(max = 100)
    @Column(name = "stat_Libelle")
    private String libelle;


    public StatutActe StatutActe(long i, String c, String s) {
        return builder().id(i)
                .code(c)
                .libelle(s)
                .build();
    }
}


