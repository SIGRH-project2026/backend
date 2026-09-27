package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation;

import lombok.Data;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;

import java.util.List;

@Data
public class ParticipantDTO {

    private Long id;

    // private List<Long> participantIds;

    private Formation formation;
}
