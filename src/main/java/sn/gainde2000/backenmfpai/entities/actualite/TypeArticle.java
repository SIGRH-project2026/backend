package sn.gainde2000.backenmfpai.entities.actualite;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_Type_Article", schema = "schema_carriere")
@SequenceGenerator(name = "seq_type_art", initialValue = 100, allocationSize = 2, sequenceName = "seq_type_art")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_type_art")
    @Column(name = "type_art_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "libelle")
    @Size(max = 50)
    private String libelle;

    @Column(name = "codeArticle")
    @Size(max = 20)
    private String code;

}
