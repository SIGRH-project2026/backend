package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_TypeAG", schema = "schema_carriere")
@SequenceGenerator(name = "seq_AG", initialValue = 100, allocationSize = 2, sequenceName = "seq_typeAG")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeAG {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_typeAG")
    @Column(name = "typeAG_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "typeAG_Code")
    private String code;

    @Size(max = 100)
    @Column(name = "typeAG_libelle")
    private String libelle;

    @Column(name = "type_sortie")
    private String typeSortie;
}
