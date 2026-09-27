package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AttestationStageRequest {
    private String commentaire;
    private Long demandeStageId;
}
