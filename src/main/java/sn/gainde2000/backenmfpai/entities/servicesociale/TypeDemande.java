package sn.gainde2000.backenmfpai.entities.servicesociale;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_TypePriseEnCharge", schema = "schema_affairesociale")
@SequenceGenerator(name = "seq_typepec", initialValue = 100, allocationSize = 2, sequenceName = "seq_typepec")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_typepec")
    @Column(name = "typepec_id",nullable = false, updatable = false, unique = true)
    private Long id;
    @Size(max = 100)
    @Column(name = "type_code")
    private String code;

    @Size(max = 100)
    @Column(name = "type_libelle")
    private String libelle;
}
