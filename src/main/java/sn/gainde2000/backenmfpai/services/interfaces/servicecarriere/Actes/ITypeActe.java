package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes;

import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;

public interface ITypeActe {
    TypeActe createTypeActe(String statut);
    TypeActe getTypeActeByCode(String type);
}
