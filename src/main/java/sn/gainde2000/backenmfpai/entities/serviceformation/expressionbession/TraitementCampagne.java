package sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_TraitementCampagne", schema = "schema_formation")
public class TraitementCampagne {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TraitCam_Id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "TraitCam_utilisateur")
    private Utilisateur utilisateur;


    @ManyToOne
    @JoinColumn(name = "TraitCam_Campagne")
    private Campagne campagne;

    @Column(name = "TraitCam_Activated")
    boolean activated;

    @ManyToOne
    @JoinColumn(name = "TraitCam_StatutCampagne")
    private StatutCampagne statutCampagne;

}
