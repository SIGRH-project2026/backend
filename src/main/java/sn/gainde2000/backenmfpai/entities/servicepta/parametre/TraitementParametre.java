package sn.gainde2000.backenmfpai.entities.servicepta.parametre;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_TraitementParametre", schema = "schema_pta")
public class TraitementParametre {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "TraitParam_Id")
  private long id;

  @ManyToOne
  @JoinColumn(name = "TraitParam_utilisateur")
  private Utilisateur utilisateur;

  @ManyToOne
  @JoinColumn(name = "TraitParam_parametre")
  private Parametre parametre;


  @Column(name = "TraitParam_Activated")
  boolean activated;

  @ManyToOne
  @JoinColumn(name = "TraitParam_StatutCourrier")
  private StatutParametre statutParametre;
}
