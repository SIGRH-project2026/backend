package sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.parametre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParametreRequest {
  private String libelle;
  private List<String> divisionCodes;
  private List<String> bureauCodes;
}
