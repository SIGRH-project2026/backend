package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RapportDTO {

    private Formation formation;
    private String commentaire;

}