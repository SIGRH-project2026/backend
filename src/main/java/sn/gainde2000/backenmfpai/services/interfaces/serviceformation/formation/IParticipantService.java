package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participant;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IParticipantService {
    Participant createParticipant(MultipartFile file, ParticipantDTO participantDTO);

    Participant getParticipantByFormationId(Long formationId);

    void deleteParticipant(Long id);

}
