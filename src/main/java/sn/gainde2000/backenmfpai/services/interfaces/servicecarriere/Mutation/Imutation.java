package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Mutation;

import net.sf.jasperreports.engine.JRException;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.MutationDTO;

import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.FileNotFoundException;

public interface Imutation {
    Response<Object> demandeMutation(MutationDTO mutationDTO);
    Response<Object> traitementMutation(Long idMutation, MultipartFile bordereau, String traitementMutationDTOs, MultipartFile dossierSigne);
    Response<Object>  validerMutation(Long idMutation, Long idTraitant, MultipartFile file);
    Response<Object> listMutation(int page, int pageSize,String statutMutation, String region, String ia, String ief, String etablissement,
                                  String bureau,  String direction, String division,String service,
                                  String numeroRef,Long userId, boolean pourTraitement, String filtre );
    Response<Object> getOneMutation(Long idMutation);
    Response<Object> updateMutation(Long idMutation, MutationDTO mutationDTO);
    Response<Object> genererMutation(boolean allMutationAccepted, Long idMutation )throws JRException, FileNotFoundException ;

    Response<Object> indicateurMutation(String codeProfile) ;

}

