package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.stage;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DemandeStageResponse {
    private long id;
    private String numero;
    private String prenomDemandeur;
    private String nomDemandeur;
    private LocalDate dateNaissance;
    private String lieuDeNaissance;
    private String mail;
    private String tel;
    private String adresse;
    private String objet;
    private NiveauScolaire niveauScolaire;
    private String disciplineStage;
    private Direction direction;
    private Division division;
    private Services service;
    private Bureau bureau;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String commentaire;
    private StatutDemandeStage statutDemandeStage;
    private List<File> justificatfs;
    private boolean haveRapport;
    private boolean haveAttestation;
    private RapportStageResponse rapportStageResponse;
    private AttestationStageResponse attestationStageResponse;
    private List<File> justificatfsAuthorisationStage;
    private boolean haveAuthorisationStage;

}
