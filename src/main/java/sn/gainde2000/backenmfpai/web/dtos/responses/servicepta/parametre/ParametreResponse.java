package sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.parametre;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.enums.ResponsableActivite;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ParametreResponse {
  private long id;
  private String libelle;
  private ResponsableActivite responsableActivite;
  private List<Bureau> bureaux;
  private List<Division> divisions;
  private String statut;
  private String numero;
  private LocalDate date;

  private String listDivisions;

  private String listBureaux;
}
