package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.QAgent;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.AgentMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.IAgentRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IAgent;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.AgentRequestDto;

import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;

import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.AgentResponseDto;

import java.util.Optional;



/**
 * @author bsdieme
 */
@Service
public class AgentImpl implements IAgent {

    @Autowired
    private IAgentRepository agentRepository;

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public Agent getAgent(Long id) {
        try{
            Optional<Agent> agent = agentRepository.findById(id);
            if(agent.isPresent()){
                return agent.get();
            }
            else{
                throw new EntityNotFoundException("agent introuvable");
            }
        }catch (Exception e){
            System.out.println("erreur == "+e);
            return null;
        }
    }

    @Override
    public Page <Agent> getAllAgents(int page, int size, String filter, boolean sortByDescending){
        try{
            System.out.println("#### ici try ######");
            BooleanBuilder builder = new BooleanBuilder();
            QAgent agent = QAgent.agent;
            builder.and(
                    agent.isDeleted.isFalse()
            );

            Sort sort = sortByDescending ? Sort.by(filter).descending() : Sort.by(filter).ascending();
            PageRequest pageRequest = PageRequest.of(page, size, sort);
            return agentRepository.findAll(builder, pageRequest);

        } catch (Exception e){
            System.out.println("#### ici catch ######"+e);
            return null;
        }
    }

    @Override
    public Agent createAgent(AgentRequestDto agentDto){
        try{
            Agent agent = agentMapper.toEntity(agentDto);

            return agentRepository.save(agent);
        }catch (Exception e){
            System.out.println(" Error create Agent");
            return null;
        }
    }

    @Override
    public AgentResponseDto deleteAgent(Long id) {

        Agent agent = agentRepository.findById(id).get();
        agent.setIsDeleted(true);
        agentRepository.save(agent);
        AgentResponseDto agentDto = agentMapper.toDto(agent);
        return agentDto;
    }

    @Override
    public AgentResponseDto getOneAgent(Long id) {

        Agent agent = agentRepository.findById(id).orElseThrow(
                () -> new MFPAIException(MFPAIMessage.NOT_FOUND, "with id = " + id));
        return agentMapper.toDto(agent);
    }

}
