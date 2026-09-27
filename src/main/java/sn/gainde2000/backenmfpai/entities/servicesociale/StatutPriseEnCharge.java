package sn.gainde2000.backenmfpai.entities.servicesociale;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;

@Entity
@Table(name = "TP_StatutPriseEnCharge", schema = "schema_affairesociale")
@SequenceGenerator(name = "seq_statut_pec", initialValue = 100, allocationSize = 2, sequenceName = "seq_statut_pec")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatutPriseEnCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_statut_pec")
    @Column(name = "stat_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "stat_Code")
    private String code;

    @Size(max = 100)
    @Column(name = "stat_Libelle")
    private String libelle;


}
