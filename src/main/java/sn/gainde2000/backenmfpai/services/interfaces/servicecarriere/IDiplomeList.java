package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DiplomeList;

import java.util.List;

public interface IDiplomeList {
    public DiplomeList getOne(long id);
    public List<DiplomeList> getAll();
}
