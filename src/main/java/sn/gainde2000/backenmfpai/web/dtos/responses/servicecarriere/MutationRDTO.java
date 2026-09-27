package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.OrigineDemandeurLog;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.TraitementMutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MutationRDTO {
    private Long id;
    private String numeroRef;
    private Utilisateur demandeur;
    private Region regionSouhaitee;
    private IA iaSouhaitee;
    private IEF iefSouhaitee;
    private Etablissement etablissementSouhaitee;
    private String commentaire;
    private String dossierSigne;
    private LocalDate dateDemande;
    private TraitementMutation traitementMutation;
    private OrigineDemandeurLog origineDemandeurLog;
    private Bureau bureauSouhaite;
    private Direction directionSouhaitee;
    private Division divisionSouhaitee;
    private Services serviceSouhaite;
    private String ordreService;
    private String destinataireType;
    private String profilDevantTraiter;
    private boolean osgenerated;
    private String currentBordereauTransmission;
    private String profilView;
    private List<File> pieceJointes;


}
