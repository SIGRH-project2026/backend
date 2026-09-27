package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

public record MutationDTO(String destinataireType, String commentaire, Region regionSouhaitee, IA iaSouhaitee, IEF iefSouhaitee, Etablissement etablissementSouhaitee, Long idUserdemandeur,
                          Bureau bureauSouhaite, Direction directionSouhaitee, Division divisionSouhaitee,
                          Services serviceSouhaite) {
}
