package sn.gainde2000.backenmfpai.services.interfaces.servicepta.parametre;

import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.parametre.ParametreRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.parametre.ParametreResponse;

import java.util.List;

public interface IParametre {
  Response<Object> saveParametre(ParametreRequest parametreRequest);
  Response<Object> editParametre(ParametreRequest parametreRequest, Long id);


  Response<Object> findParametre(long id);
  Parametre getParametre(Long id);

  Response<Object> enableOrDisableParametre(long id);

  Response<Object> getAllParametre(int page, int size, String filter, String numero, String libelle, String date, String responsableActivite, String statut, String divisions);

  List<ParametreResponse> getListParametre();
}
