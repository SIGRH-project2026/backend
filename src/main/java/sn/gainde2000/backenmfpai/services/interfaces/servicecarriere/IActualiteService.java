package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.actualite.Actualite;
import sn.gainde2000.backenmfpai.entities.actualite.CategorieActualite;
import sn.gainde2000.backenmfpai.entities.actualite.TypeArticle;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.IOException;
import java.util.List;

public interface IActualiteService{

    Actualite create (Actualite actu, MultipartFile file) throws IOException;

    Actualite getOne (long id);

    Response<Object> getAll (int page, int size, String statut);

    Response<Object> getAllActivate();

    Response<Object> getAllActuOrRecrutementActivate(String code);

    Response<Object> getLastActu();

    Response<Object> getLastRecrutement();

    Actualite delete (long id);

    List<TypeArticle> getAllTypeArticle();

    List<CategorieActualite> getAllCategorieActualite();

    Actualite changeStatus(long id);
}
