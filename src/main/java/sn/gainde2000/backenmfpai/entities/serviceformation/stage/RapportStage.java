package sn.gainde2000.backenmfpai.entities.serviceformation.stage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_RapportStage", schema = "schema_formation")
public class RapportStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Rapport_Id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "Rapport_Utilisateur")
    private CentralLevel utilisateur;

    @OneToOne
    @JoinColumn(name = "Rapport_DemandeStage")
    private DemandeStage demandeStage;
    @OneToOne
    File piecesJoint;

    @Column(name = "Rapport_Commentaire", length = 1000)
    @Lob
    private String commentaire;
}
