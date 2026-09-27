package sn.gainde2000.backenmfpai.entities.serviceformation.courrier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_TraitementCourrier", schema = "schema_formation")
public class TraitementCourrier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TraitCou_Id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "TraitCou_utilisateur")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "TraitCou_Courrier")
    private Courrier courrier;


    @Column(name = "TraitCam_Activated")
    boolean activated;

    @ManyToOne
    @JoinColumn(name = "TraitCou_StatutCourrier")
    private StatutCourrier statutCourrier;
}
