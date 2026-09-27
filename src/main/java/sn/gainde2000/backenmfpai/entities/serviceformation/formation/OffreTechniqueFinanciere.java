package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import java.util.Set;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_OffreTechniqueFinanciere", schema = "schema_formation")
public class OffreTechniqueFinanciere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 2000)
    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "chefeff_id")
    private DeconcentratedLevel chefeff;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<File> files;

    @ManyToOne
    @JoinColumn(name = "statut_offre_technique_id")
    private StatutOffreTechnique statutOffreTechnique;

}
