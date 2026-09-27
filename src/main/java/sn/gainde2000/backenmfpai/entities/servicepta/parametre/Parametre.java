package sn.gainde2000.backenmfpai.entities.servicepta.parametre;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.enums.ResponsableActivite;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_Parametre", schema = "schema_pta")
public class Parametre {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "Param_Id")
  private long id;

  @Column(name = "Param_numero", length = 15, unique = true)
  private String numero;

  @Column(name = "Param_Date")
  private LocalDate date = LocalDate.now();

  @Column(name = "Param_libelle", length = 80, nullable = false)
  private String libelle;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "Param_bureau", joinColumns = @JoinColumn(name = "param_id"),
          inverseJoinColumns = @JoinColumn(name = "bureau_id"))
  private List<Bureau> bureaux;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "Param_division", joinColumns = @JoinColumn(name = "param_id"),
          inverseJoinColumns = @JoinColumn(name = "division_id"))
  private List<Division> divisions;

  @Column(name = "Param_responsableActivite")
  @Enumerated(EnumType.STRING)
  private ResponsableActivite responsableActivite;

  @Column(name = "Param_statut")
  private String statut;

  @Transient
  private String listDivisions;

  @Transient
  private String listBureaux;
}
