package sn.gainde2000.backenmfpai.entities.servicepta.parametre;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_StatutParametre", schema = "schema_pta")
public class StatutParametre {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "StuParam_Id")
  private long id;

  @NotNull(message = "Veuillez renseigner le code du statut")
  @Column(length = 20, unique = true, name = "StuParam_Code")
  private String code;

  @NotNull(message = "Veuillez renseigner le libellé du statut du Courrier")
  @Column(length = 100,name = "StuParam_Libelle")
  private String libelle;

  private boolean isDeleted = false;
}
