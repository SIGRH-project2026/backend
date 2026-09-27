
package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.BesoinEnPersonnel;

// import sn.gainde2000.backenmfpai.entities.servicecarrire.BesoinEnPersonnel.Filiere;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.FiliereDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;

public interface IFiliere {

    Response<Object> createFiliere(FiliereDTO filiereDTO);

    Response<Object> listeFiliere();
}
