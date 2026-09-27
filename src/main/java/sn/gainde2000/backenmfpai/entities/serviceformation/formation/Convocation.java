package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.file.File;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_CONVOCATION", schema = "schema_formation")
public class Convocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "tdr_id")
    private File tdr;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "nom_fichier")
    private String nom;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
}
