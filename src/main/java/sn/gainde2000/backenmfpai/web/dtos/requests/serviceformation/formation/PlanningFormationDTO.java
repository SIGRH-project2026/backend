package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.StatutOffreTechnique;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanningFormationDTO {

    private Long id;
    private Formation formation;
    private String commentaire;
    private Set<File> files;
}