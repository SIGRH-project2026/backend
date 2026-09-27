package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_TypeAA", schema = "schema_carriere")
@SequenceGenerator(name = "seq_typeAA", initialValue = 100, allocationSize = 2, sequenceName = "seq_typeAA")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeAA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_typeAA")
    @Column(name = "typeAA_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "typeAA_Code")
    private String code;

    @Size(max = 100)
    @Column(name = "typeAA_libelle")
    private String libelle;

    @Column(name = "type_sortie")
    private String typeSortie;
}
