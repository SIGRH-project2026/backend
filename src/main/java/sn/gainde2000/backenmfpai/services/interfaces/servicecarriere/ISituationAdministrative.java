package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.SituationAdministrative;

import java.util.List;

public interface ISituationAdministrative {

    SituationAdministrative AddSituation (SituationAdministrative situationAdministrative);

    SituationAdministrative deleteSituation (long id);

    SituationAdministrative getOneSituation(Long id);

    List<SituationAdministrative> getAllSituations ();

    SituationAdministrative updateSituationAdministrative(SituationAdministrative situationAdministrative);
}
