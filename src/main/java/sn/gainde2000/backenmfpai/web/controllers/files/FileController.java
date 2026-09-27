package sn.gainde2000.backenmfpai.web.controllers.files;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.services.interfaces.files.IFile;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "files")
public class FileController {

    private final IFile iFile;
    @Value("${upload.path}")
    private String uploadDirectory;

    @PostMapping(value = "/file/upload/{idAppartenance}/{type}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadFile(
            @RequestParam("files") List<MultipartFile> files, @PathVariable  long idAppartenance,@PathVariable  String type) {
        return ResponseEntity.ok(iFile.uploadFiles(files,idAppartenance,type));
    }

    @PostMapping(value = "/file/singleUpload/{idAppartenance}/{type}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadSingleFile(
            @RequestParam("files") MultipartFile file, @PathVariable  long idAppartenance,@PathVariable  String type) {
        return ResponseEntity.ok(iFile.uploadSingleFile(file,idAppartenance,type));
    }

    @PostMapping(value = "/file/uploadImputation/{idAppartenance}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadFileImputation(
            @RequestParam("files") List<MultipartFile> files, @PathVariable  long idAppartenance) {
        return ResponseEntity.ok(iFile.uploadFilesImputation(files,idAppartenance));
    }

    @PostMapping(value = "/file/bordereau/{idAppartenance}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadBordereau(
            @RequestParam("files") MultipartFile file, @PathVariable  long idAppartenance) {
        System.out.println("le controller est appelé");
        return ResponseEntity.ok(iFile.uploadActesBordereaux(file,idAppartenance));
    }

    @PostMapping(value = "/file/upload_diplome/{idDiplome}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadDiplomeFile(
            @RequestParam("file") MultipartFile file, @PathVariable  long idDiplome) {
        System.out.println("voir fichier +++++++++++++"+file);
        return ResponseEntity.ok(iFile.uploadSingleDiplomeFile(file,idDiplome));
    }

    @PostMapping(value = "/file/campagne/{idCampagne}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadFileForCampagne(
            @RequestParam("files") List<MultipartFile> files, @PathVariable  long idCampagne) {
        return ResponseEntity.ok(iFile.uploadFileForCampagne(files,idCampagne));
    }

    @DeleteMapping(value = "/file/campagne/{idCampagne}/{idFile}")
    public ResponseEntity<Response<Object>> deleteFileForCampagne(
            @PathVariable  long idCampagne,@PathVariable  long idFile) {
        return ResponseEntity.ok(iFile.deleteFileForCampagne(idCampagne,idFile));
    }

    @PostMapping(value = "/file/upload_avancement/{idAvancement}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadAvancementFile(
            @RequestParam("file") MultipartFile file, @PathVariable  long idAvancement) {
        System.out.println("voir fichier +++++++++++++"+file);
        return ResponseEntity.ok(iFile.uploadSingleAvancementFile(file,idAvancement));
    }

    @PostMapping(value = "/file/upload_etat_civil/{idEtat}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadEtatCivilFile(
            @RequestParam("file") MultipartFile file, @PathVariable  long idEtat) {
        System.out.println("voir id Etat Civil +++++++++++++"+idEtat);
        return ResponseEntity.ok(iFile.uploadSingleEtatCivilFile(file,idEtat));
    }

    @PostMapping(value = "/file/upload_acte/{idActe}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadActeFile(
            @RequestParam("file") MultipartFile file, @PathVariable  long idActe) {
        System.out.println("voir id Acte +++++++++++++"+idActe);
        return ResponseEntity.ok(iFile.uploadSingleActeFile(file,idActe));
    }

    @PostMapping(value = "/file/upload_imputation/{idImputation}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadimputationFile(
            @RequestParam("file") MultipartFile file, @PathVariable  long idImputation) {
        System.out.println("voir id Imputation +++++++++++++"+idImputation);
        return ResponseEntity.ok(iFile.uploadSingleImputationFile(file,idImputation));
    }

    @PostMapping(value = "/file/bordereauPermutation/{idTraitement}", consumes = {"multipart/form-data"})
    public ResponseEntity<Response<Object>> uploadPermutationBordereauFile(
            @RequestParam("file") MultipartFile file, @PathVariable  long idTraitement) {
        System.out.println("voir id traitement +++++++++++++"+idTraitement);
        return ResponseEntity.ok(iFile.uploadSinglePermutationBordereauFile(file,idTraitement));
    }



@GetMapping("/file/file/{generatedName}")
public ResponseEntity<File> getFileByGeneratedName(@PathVariable String generatedName){
    System.out.println("++++++++++++++++++++getting file++++++++++++++++++++++++");
    return ResponseEntity.ok(iFile.getFileByGeneratedName(generatedName));
}


    @GetMapping("/file/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // Load file as a Resource
        Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());

        } catch (MalformedURLException e) {

            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

        // Check if the file exists
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Set content disposition as attachment to force download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        // Set the content type based on file extension or default to application/octet-stream
        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            String detectedType = Files.probeContentType(filePath);
            if (detectedType != null) contentType = MediaType.parseMediaType(detectedType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(contentType)
                .body(resource);
    }


    /*@GetMapping("/file/view/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable  String fileName) {
        try {
            System.out.println("location :"+fileStorageLocation);
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found " + fileName, e);
        }
    }*/

    @GetMapping("/file/view/{fileName:.+}")
    public ResponseEntity<byte[]> getPdf(@PathVariable  String fileName) throws IOException {

        Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();

        Resource resource = new UrlResource(filePath.toUri());
        byte[] pdfBytes = Files.readAllBytes(resource.getFile().toPath());

        HttpHeaders headers=new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");

        //Application avec PDF
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        //headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }



}


