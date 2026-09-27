package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiliereRDTO {
    private Long id;
    private  String code;
    private  String libelle;
}
