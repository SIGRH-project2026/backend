package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Avancement;

import java.util.List;

public interface IAvancement {

    Avancement getOneAvancement(long id);

    List<Avancement> getAllAvancement();

    Avancement deleteAvancement(long id);

    Avancement createAvance(Avancement avancement);

    Avancement updateAvancement(Avancement avancement);
}
