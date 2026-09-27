package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere;

import jakarta.persistence.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileRspDTO;

import java.util.ArrayList;
import java.util.List;

public class PieceJointesReponseDTO {

    private Long id;
    private List<FileRspDTO> files = new ArrayList<>();
}
