package sn.gainde2000.backenmfpai.entities.actualite;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_CategorieActualite", schema = "schema_carriere")
@SequenceGenerator(name = "seq_cat_actu", initialValue = 100, allocationSize = 2, sequenceName = "seq_cat_actu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategorieActualite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_cat_actu")
    @Column(name = "cat_actu_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "libelle")
    @Size(max = 50)
    private String libelle;

    @Column(name = "codeCategorie")
    @Size(max = 50)
    private String code;
}
