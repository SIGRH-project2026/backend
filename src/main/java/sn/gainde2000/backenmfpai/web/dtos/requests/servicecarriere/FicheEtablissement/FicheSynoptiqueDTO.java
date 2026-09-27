package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.FicheEtablissement;

import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.*;

import java.util.List;

public record FicheSynoptiqueDTO(Long userId, List<FiliereDiscipline> filiereDisciplines , List<ClasseProfDiscipline> classeProfDisciplines,
                                 List<SerieNiveauDiscipline> serieNiveauDisciplines, List<SerieClasseProfDiscipline> serieClasseProfDisciplines,
                                     List<FormationProfessionel> formationProfessionels) {
}
