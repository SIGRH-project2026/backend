package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_SESSION", schema = "schema_formation")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @Size(max = 2000)
    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToOne
    @JoinColumn(name = "rapport_file_id")
    private File file;

}