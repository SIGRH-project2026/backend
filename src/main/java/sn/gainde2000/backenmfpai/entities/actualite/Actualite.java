package sn.gainde2000.backenmfpai.entities.actualite;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.attoparser.dom.Text;
import sn.gainde2000.backenmfpai.entities.file.File;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TD_Actualite", schema = "schema_carriere")
@SequenceGenerator(name = "seq_actu", initialValue = 100, allocationSize = 2, sequenceName = "seq_actu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actualite {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_actu")
    @Column(name = "actu_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 150)
    @Column(name = "actu_titre")
    private String titre;

    @Size(max = 250)
    @Column(name = "actu_resume")
    private String resume;


    @Column(name = "actu_contenu", nullable = false)
    private Text contenu;

    @Column(name = "actu_date_publication", nullable = false)
    private LocalDate datePublication;

    @Column(name = "actu_is_deleted")
    private boolean isDeleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    private File image;

    @Column(name = "actu_is_activated")
    private boolean isActivated = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_art_id", nullable = false)
    private TypeArticle typeArticle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_actu_id", nullable = false)
    private CategorieActualite categorieActualite;
}
