package sn.gainde2000.backenmfpai.web.controllers.shared;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.services.interfaces.shared.IFile;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-10:59
 * @project backend_mfpai
 */
@RestController
@RequestMapping( "/files")
@Slf4j
@Tag(name = "FILE - MANAGEMENT - API", description = "Permet de charger et de télécharger des fichiers")
@Validated
@RequiredArgsConstructor
public class FileResource {
    private final IFile iFile;

    @PostMapping(value ="/{id}" ,name = "/single", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,  MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE,})
    public ResponseEntity<MFPAIResponse> uploadSingleFile(@PathVariable long id,@RequestPart(name = "file") MultipartFile file, @RequestParam(required = false) String directory) throws MFPAIException {

        FileRspDTO result = iFile.storeFile(file, directory, false);
        MFPAIResponse response = MFPAIResponse.success(result);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping( value = "/multiple/{id}", name = "/multiple", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MFPAIResponse> uploadMultipleFile(@RequestPart(name = "files") MultipartFile[] files, @RequestParam(required = false) String directory,@PathVariable long idAppartenance) throws MFPAIException {

        FileRspDTO [] result = iFile.storeMultipleFiles(files, directory, false, idAppartenance).toArray(new FileRspDTO[0]);
        MFPAIResponse response = MFPAIResponse.success(result);
        return ResponseEntity.ok().body(response);
    }



/*    @PostMapping(name = "/multiple", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SmartCareResponse> uploadMultipleFile(@RequestPart(name = "files") MultipartFile[] files, @RequestParam(required = false) String directory) throws SmartCareException {

        List<FileRspDTO> result = iFile.storeMultipleFiles(files, directory, false);

        SmartCareResponse response = SmartCareResponse.success(result);
        return ResponseEntity.ok().body(response);
    }*/

    @GetMapping("")
    public ResponseEntity<MFPAIResponse> getListFiles() throws MFPAIException {
        List<FileRspDTO> fileInfos = iFile.loadAll()
                .stream()
                .map(this::pathToFileData)
                .collect(Collectors.toList());

        MFPAIResponse response = MFPAIResponse.success(fileInfos);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public void delete() {
        iFile.deleteAll();
    }

    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@RequestParam String filename, HttpServletRequest request) throws MFPAIException, IOException {
        Resource file = iFile.load(filename, request);

        String contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    private FileRspDTO pathToFileData(Path path) {
        FileRspDTO fileData = new FileRspDTO();
        String filename = path.getFileName()
                .toString();
        fileData.setGeneratedName(filename);
        fileData.setDownloadUrl(MvcUriComponentsBuilder.fromMethodName(FileResource.class, "getFile", filename)
                .build()
                .toString());
        try {
            fileData.setFileSize(Files.size(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error: " + e.getMessage());
        }

        return fileData;
    }
}
