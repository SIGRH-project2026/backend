package sn.gainde2000.backenmfpai.entities.serviceformation.stage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_TraitementDemandeStage", schema = "schema_formation")
public class TraitementDemandeStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TraitDeman_Id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "TraitDeman_utilisateur")
    private CentralLevel centralLevel;
    @ManyToOne
    @JoinColumn(name = "TraitDeman_DemandeStage")
    private DemandeStage demandeStage;

    @Column(name = "TraitDeman_Activated")
    boolean activated;

    @ManyToOne
    @JoinColumn(name = "TraitExp_statutDemandeStage")
    private StatutDemandeStage statutDemandeStage;
}
