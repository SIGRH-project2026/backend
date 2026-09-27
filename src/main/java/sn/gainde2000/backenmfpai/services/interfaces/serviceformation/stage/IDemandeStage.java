package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AuthorizedDemandeStageDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DemandeStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.ImputationRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IDemandeStage {
    Response<Object> saveDemandeStage(DemandeStageRequest demandeStageRequest);
    Response<Object> autoriserDemande(long id);
    public Response<Object> nePasAutoriserDemande(long id, String avis);

    Response<Object> getAllDemandeStage(int page, int size, String filter, String numero, String prenomDemandeur, String nomDemandeur, String discipline, String dateDebut, String dateFin, String statut);

    Response<Object> editDemandeStage(long id, DemandeStageRequest demandeStageRequest);

    Response<Object> detailDemandeStage(long id);

    Response<Object> imputationDemandeStage(long id, ImputationRequest imputationRequest);

    Response<Object> isCurrentUserInDFRBureau();

    Response<Object> authorisationStage(AuthorizedDemandeStageDTO authorizedDemandeStageDTO);

    Response<Object> nombreDemandeStageAutoriserNonAutoriserEnregistrer();
}
