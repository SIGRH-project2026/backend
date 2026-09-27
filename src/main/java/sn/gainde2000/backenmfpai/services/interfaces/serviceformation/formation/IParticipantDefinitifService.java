package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.List;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDefinitifDTO;

public interface IParticipantDefinitifService {

    ParticipantDefinitifDTO createParticipantDefinitif(ParticipantDefinitifDTO participantDefinitifDTO);

    List<ParticipantDefinitifDTO> getParticipantDefinitifByFormationId(Long formationId);

    List<ParticipantDefinitifDTO> getParticipantDefinitifByMatricule(String matricule);

    List<ParticipantDefinitifDTO> getAllParticipantDefinitif();

    ParticipantDefinitifDTO getParticipantDefinitifById(Long id);

    ParticipantDefinitifDTO updatePartialParticipantDefinitif(Long id, ParticipantDefinitifDTO participantDefinitifDTO);

    long countFormedAgentsByDirection(String direction);

    long countFormedAgents();

}