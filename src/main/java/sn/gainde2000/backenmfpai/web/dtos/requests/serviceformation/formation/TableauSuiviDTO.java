package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UtilisateurDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.CentralLevelDTO;

@Data
public class TableauSuiviDTO {

    private Long id;

    private Integer presences;

    private Integer abscences;

    private Formation formation;

    private Long formationId;

    private Integer nbrSession;

}
