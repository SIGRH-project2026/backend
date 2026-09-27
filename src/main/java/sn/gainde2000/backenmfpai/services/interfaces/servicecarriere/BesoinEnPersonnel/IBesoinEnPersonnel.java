package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.BesoinEnPersonnel;

import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IBesoinEnPersonnel {
    Response<Object> createBEP (BesoinEnPersonnelDTO besoinEnPersonnelDTO);
    Response<Object> listBEP(Long userId, int page, int size, String statut, String matricule, String etablissement,

                             String region, String ia, String ief, String prenom, String nom, Long reference ) ;
    Response<Object> getOneBEP (Long idBEP);
    Response<Object> indicateurBEP(String codeProfile);



}
