package sn.gainde2000.backenmfpai.services.interfaces.files;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.IOException;
import java.util.List;

public interface IFile {

    File uploadImage(MultipartFile image) throws IOException;

    Response<Object> uploadSingleFile(MultipartFile file, long id, String type);

    Response<Object> uploadSingleFile(MultipartFile file, long id);

    Response<Object> uploadSingleDiplomeFile(MultipartFile file, long id);
    Response<Object> uploadFileForCampagne(List<MultipartFile> files, long id);

    Response<Object> uploadSingleImputationFile(MultipartFile file, long id);
    Response<Object> uploadSinglePermutationBordereauFile(MultipartFile file, long id);

    Response<Object> uploadSingleAvancementFile(MultipartFile file, long id);

    Response<Object> uploadSingleEtatCivilFile(MultipartFile file, long id);
    Response<Object> uploadSingleActeFile(MultipartFile file, long id);


    Response<Object> uploadFiles(List<MultipartFile> files, long id);
    Response<Object> uploadFiles(List<MultipartFile> files, long id, String type);
    Response<Object> uploadFiles_(List<MultipartFile> files, long id);
    Response<Object> uploadFilesForRapport(MultipartFile file, long id);
    Response<Object> uploadFilesForAttestation(MultipartFile file, long id);
    ResponseEntity<Resource> downloadFIle(String fileName);

    Response<Object> uploadFilesImputation(List<MultipartFile> files, long id);

    Response<Object> uploadActesBordereaux(MultipartFile file, long id);

    File getFileByGeneratedName(String generatedName);

    Response<Object> uploadPermutationOs(MultipartFile file, Permutation permutation, Utilisateur utilisateur);

    Response<Object> deleteFileForCampagne(long idCampagne, long idFile);
}
