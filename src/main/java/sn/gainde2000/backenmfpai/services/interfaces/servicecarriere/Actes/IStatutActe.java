package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes;

import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;

public interface IStatutActe {
    StatutActe createStatutActe(String statut);
    StatutActe updateStatutActe(long id,String statut);

    StatutActe getStatutActeByCode(String code);
}
