package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;

@Data
public class ParticipantDefinitifDTO {

    private Long id;

    private String numeroDemande;

    private String nom;

    private String matricule;

    private String direction;

    private String division;

    private String commentaire;

    private boolean competences;

    private boolean admis;

    private boolean assidu;

    private Formation formation;

    private Long formationId;

}