package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Imputation;

import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Imputation.ImputationRequestdto;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationResponseDto;

import java.io.FileNotFoundException;

public interface IImputation {

    Page<ImputationOuBulletin> getAllImputationFromDashbaord(int page, int size, String region, String matricule, String nom, String prenom, String date, String typeDemande);

    public Page<ImputationOuBulletin> getAllImputation(int page, int size, String region, String matricule, String nom, String prenom, String date, String typeDemande);

    public ImputationOuBulletin createimputation(ImputationRequestdto dto);

    public ImputationResponseDto deleteImputation(long id);

    ImputationOuBulletin generateimputation(long id) throws JRException, FileNotFoundException;

    public ImputationResponseDto getOneImputation(long id);

    public ImputationResponseDto recherche (String matricule);

    /*
    * les indicateurs*/
    Response<Object> indicateurImputationOrBulletin(String codeProfile);

    // À ajouter dans IImputation.java
public Page<ImputationOuBulletin> getMesCreations(int page, int size, String typeDemande);
}
