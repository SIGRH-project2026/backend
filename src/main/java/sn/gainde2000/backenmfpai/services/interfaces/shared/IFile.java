package sn.gainde2000.backenmfpai.services.interfaces.shared;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 08/11/2023-16:20
 * @project gestion_courriers
 */
public interface IFile {
    FileRspDTO storeFile(MultipartFile file, String directory, boolean checkRegexSplitter) throws MFPAIException;

    List<FileRspDTO> storeMultipleFiles(MultipartFile[] files, String directory, boolean checkRegexSplitter,long id) throws MFPAIException;

    Resource load(String filename, HttpServletRequest request) throws MFPAIException;

    void deleteAll();

    List<Path> loadAll() throws MFPAIException;
}
