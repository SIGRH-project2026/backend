package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import java.util.Set;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_PlanningFormation", schema = "schema_formation")
public class PlanningFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 2000)
    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<File> files;

}