package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutCampagneEnum;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.UtilisateurResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CampagneResponseDTO {
    private long id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutCampagneEnum statut;
    private int numberOfExpressionDeBesoin;
    private String cardBackground;
    private UtilisateurResponseDTO utilisateurResponseDTO;
    private List<File> pieceJoint = new ArrayList<>();
}
