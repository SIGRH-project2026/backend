
package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-15:20
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_DeconcentratedLevel", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_dec_level", initialValue = 100, allocationSize = 2, sequenceName = "seq_dec_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeconcentratedLevel extends Utilisateur {

  @Column(name = "user_quantum_horaire")
  private Integer quantumHoraire;


  @ManyToOne
  @JoinColumn(name = "ia_id")
  private IA ia;

  @ManyToOne
  @JoinColumn(name = "ief_id")
  private IEF ief;

  @ManyToOne
  @JoinColumn(name = "structure_id")
  private Structure structure;

  @ManyToOne
  @JoinColumn(name = "etablissement_id")
  private Etablissement etablissement;

  @ManyToOne
  @JoinColumn(name = "typeSystemeEns_id")
  private TypeSystemeEnseignement typeSystemeEnseignement;


  @Column(name = "user_date_etan")
  private LocalDate dateEntreEtablissement;
}
