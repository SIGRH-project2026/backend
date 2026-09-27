package sn.gainde2000.backenmfpai.services.interfaces.servicesociale;

import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicesociale.PriseEnChargeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IPriseEnChargeService {

    Response<Object> createPEC(PriseEnChargeDTO priseEnChargeDTO);

    Response<Object> ListPECFiltreAvances(int page, int size, long typeUserId,String numero,String matricule, String nom, String prenom, String region, String ia,String objet, String date,String statut,String type);
    Response<Object> getPECById(long id);

    Response<Object> traiterPEC(long id, long idResponsableTraitement,String traitement,String motifRejet,String motifModif);

    Response<Object> updatePEC(long id,PriseEnChargeDTO priseEnChargeDTO);

    Response<Object> deletePEC(long id);

    Response<Object> getPecStatistiques(String codeProfile);
}
