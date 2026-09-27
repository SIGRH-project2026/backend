package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Permutation.PermutationRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PermutationResponseDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface IPermutationService {

    public Permutation addPermutation(PermutationRequestDto permutationRequestDto);

    public Response<Object> getAllPermutation(int page, int size, String matricule, String date, String statut, String nom, String prenom, String type, String ia, String ief, String etablissement);

    public PermutationResponseDto getOnePermutation(long id);
    public DeconcentratedLevel getUserByMatricule(String matricule);
    public PermutationResponseDto traiterPermutation(long id,String action,String motif,String type);

    public Permutation genererPermutationOS(long id) throws JRException, FileNotFoundException;

    Response<Object> genererPermutationAllOS()throws JRException, FileNotFoundException;

    //uplaod OS de validation
    Response<Object> UploadPermutation(long idPermutation, long idTraitant, MultipartFile file);

    /*
    * les indicateurs
    * */
    Response<Object> indicateurPermutation(String codeProfile);
}
