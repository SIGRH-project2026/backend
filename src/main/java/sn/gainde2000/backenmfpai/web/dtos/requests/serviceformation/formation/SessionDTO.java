package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private Date dateFin;
    private Date dateDebut;
    private String commentaire;
    private Formation formation;
}
