package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes;

import net.sf.jasperreports.engine.JRException;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ValidesActesDate;

import java.io.FileNotFoundException;
import java.util.List;

public interface IActe {

    Response<Object> createActe(ActeDTO acteDTO/*, MultipartFile[] files*/);
    Response<Object> filtreAvances(int page, int size, long typeUserId,String reference, String date, String typeActe,String codeTypeActe, String statut,String matricule);
    Response<Object> listActesInProcess(int page, int size, long typeUserId,String reference, String date, String typeActe, String codeTypeActe,String matricule);

    Response<Object> getActeByActeId(Long id);

    Response<Object> traiterActe(long id, long idResponsableTraitement);

    Response<Object> updateActe(long id,ActeDTO acteDTO);

    Response<Object> deleteActe(long id);


    Response<Object> traiterActe(long idActe, long idAgent,String traitement,String motifModification, String motifRejet) throws JRException, FileNotFoundException;

    Response<Object> envoyerFP(List<Long> actes, long idAgent) throws JRException, FileNotFoundException;
    void envoyerAlerte();

    Response<Object> validerCen(long idActe, long idAgent, ValidesActesDate validesActesDate) throws JRException, FileNotFoundException;

  //  Response<Object> sotieTemplaire(int page, int size, long typeUserId,String reference, String date, String typeActe, String statut);

    Response<Object> findBySortiePage(String  code, int page, int size,String filter, String type);

  //  Response<Object> findBySortieBisPage( int page, int size,String filter, String type);

//  List<Acte> findBySortie(String  code);

   // Response<Object> findBySortiePage(String  code, int page, int size,String filter, String type);

   // Response<Object> findBySortieBisPage( int page, int size,String filter, String type);*/


    Response<Object> getActeStatistiques(String codeProfile,String codeTypeActe);
    Response<Object> listEnCours(String codeProfile);

    Response<Object> getAllAA();
    Response<Object> getAllAG();
    Response<Object> getAllTypeActe();






}
