package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import org.springframework.data.domain.Page;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.AgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.AgentResponseDto;

/**
 * @author bsdieme
 */
public interface IAgent {

    Agent getAgent(Long id);

    Page<Agent> getAllAgents(int page, int size, String filter, boolean sortByDescending);

    public Agent createAgent(AgentRequestDto agent);
    AgentResponseDto deleteAgent(Long id);

    AgentResponseDto getOneAgent(Long id);

}
