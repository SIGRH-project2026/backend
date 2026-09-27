package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.List;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipationDTO;

public interface IParticipationService {
    ParticipationDTO createParticipation(ParticipationDTO participationDTO);

    List<ParticipationDTO> getParticipationsByFormationId(Long formationId);

    List<ParticipationDTO> getAllParticipations();

    ParticipationDTO getParticipationById(Long id);
}
