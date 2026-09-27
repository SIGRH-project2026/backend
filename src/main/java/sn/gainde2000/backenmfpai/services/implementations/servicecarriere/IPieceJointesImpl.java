package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.PieceJointes;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Actes.ActeMapper;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.PieceJointesMapper;
import sn.gainde2000.backenmfpai.services.implementations.shared.FileService;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IPieceJointes;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.PieceJointesDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class IPieceJointesImpl implements IPieceJointes {
    private final PieceJointesMapper pieceJointesMapper;
    private final FileService iFile;
    @Value("${upload.path}")
    private String uploadPath;
    @Override
    public Response<Object> ajouterPj(PieceJointesDTO pieceJointesDTO) {
        PieceJointes pieceJointes=pieceJointesMapper.toEntity(pieceJointesDTO);
        for (File f:pieceJointes.getFiles()
             ) {
            iFile.storeFile((MultipartFile) f,uploadPath,false);
        }
        return Response.ok()
                .setPayload(pieceJointesMapper.toDto(pieceJointes))
                .setMessage("Création acte avec Succés");

    }
}
