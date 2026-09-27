package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes;


import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.PieceJointesDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IPieceJointes {
    Response<Object> ajouterPj(PieceJointesDTO pieceJointesDTO);
}
