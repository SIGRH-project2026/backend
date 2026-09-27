package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel;



import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BEPFiliereDiscipline;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

import java.util.List;

public record BesoinEnPersonnelDTO(String commentaire, List<BEPFiliereDiscipline> bepFiliereDisciplines, Region region, IA ia,
                                    int deficit,     String annee,    Grade grade, CorpsGrade corps, IEF ief, Etablissement etablissement, Long userId) {
}
