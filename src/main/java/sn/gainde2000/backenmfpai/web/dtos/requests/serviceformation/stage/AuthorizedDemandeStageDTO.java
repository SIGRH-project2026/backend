package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorizedDemandeStageDTO {
  private Long id;
  private LocalDate dateDebut;
  private LocalDate dateFin;
}
