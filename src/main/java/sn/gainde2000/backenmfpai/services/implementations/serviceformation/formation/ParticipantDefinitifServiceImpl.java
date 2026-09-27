package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.ParticipantDefinitif;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.TableauSuivi;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ParticipantDefinitifRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IParticipantDefinitifService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipantDefinitifDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TableauSuiviDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantDefinitifServiceImpl implements IParticipantDefinitifService {

    private final ParticipantDefinitifRepository tableauSuiviRepository;
    private final FormationRepository formationRepository;

    @Override
    public ParticipantDefinitifDTO createParticipantDefinitif(ParticipantDefinitifDTO participantDefinitifDTO) {
       // System.out.println(participantDefinitifDTO);

        ParticipantDefinitif participantDefinitif = new ParticipantDefinitif();
        // Set formation and centralLevel using their IDs
        // Formation formation = tableauSuiviDTO.getFormation();
        Formation formation = formationRepository.findById(participantDefinitifDTO.getFormationId()).orElse(null);

       // System.out.println(formation);
        participantDefinitif.setFormation(formation);

        participantDefinitif.setNom(participantDefinitifDTO.getNom());

        participantDefinitif.setDirection(participantDefinitifDTO.getDirection());

        participantDefinitif.setDivision(participantDefinitifDTO.getDivision());

        participantDefinitif.setMatricule(participantDefinitifDTO.getMatricule());

        participantDefinitif.setCommentaire(participantDefinitifDTO.getCommentaire());

        participantDefinitif.setCompetences(participantDefinitifDTO.isCompetences());

        participantDefinitif.setAdmis(participantDefinitifDTO.isAdmis());

        participantDefinitif.setAssidu(participantDefinitifDTO.isAssidu());

        participantDefinitif.setNumeroDemande(participantDefinitifDTO.getNumeroDemande());

        ParticipantDefinitif savedTableauSuivi = tableauSuiviRepository.save(participantDefinitif);

        return mapToDTO(savedTableauSuivi);
    }

    @Override
    public List<ParticipantDefinitifDTO> getParticipantDefinitifByFormationId(Long formationId) {
        List<ParticipantDefinitif> tableauxSuivi = tableauSuiviRepository.findByFormationId(formationId);
        return tableauxSuivi.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipantDefinitifDTO> getParticipantDefinitifByMatricule(String matricule) {
        List<ParticipantDefinitif> tableauxSuivi = tableauSuiviRepository.findByMatricule(matricule);
        return tableauxSuivi.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipantDefinitifDTO> getAllParticipantDefinitif() {
        List<ParticipantDefinitif> tableauxSuivi = tableauSuiviRepository.findAll();
        return tableauxSuivi.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantDefinitifDTO getParticipantDefinitifById(Long id) {
        Optional<ParticipantDefinitif> tableauSuivi = tableauSuiviRepository.findById(id);
        return tableauSuivi.map(this::mapToDTO).orElse(null);
    }

    @Override
    public long countFormedAgentsByDirection(String direction) {
        return tableauSuiviRepository.countByDirection(direction);
    }

    @Override
    public long countFormedAgents() {
        return tableauSuiviRepository.count();
    }

    @Override
    public ParticipantDefinitifDTO updatePartialParticipantDefinitif(Long id,
            ParticipantDefinitifDTO participantDefinitifDTO) {
        Optional<ParticipantDefinitif> participantOptional = tableauSuiviRepository.findById(id);
        if (participantOptional.isPresent()) {
            ParticipantDefinitif participant = participantOptional.get();

            if (participantDefinitifDTO.getNom() != null) {
                participant.setNom(participantDefinitifDTO.getNom());
            }
            if (participantDefinitifDTO.getDirection() != null) {
                participant.setDirection(participantDefinitifDTO.getDirection());
            }
            if (participantDefinitifDTO.getDivision() != null) {
                participant.setDivision(participantDefinitifDTO.getDivision());
            }
            if (participantDefinitifDTO.getMatricule() != null) {
                participant.setMatricule(participantDefinitifDTO.getMatricule());
            }
            if (participantDefinitifDTO.getCommentaire() != null) {
                participant.setCommentaire(participantDefinitifDTO.getCommentaire());
            }
            participant.setCompetences(participantDefinitifDTO.isCompetences());
            participant.setAdmis(participantDefinitifDTO.isAdmis());
            participant.setAssidu(participantDefinitifDTO.isAssidu());

            ParticipantDefinitif updatedParticipant = tableauSuiviRepository.save(participant);

            return mapToDTO(updatedParticipant);
        } else {
            throw new EntityNotFoundException("ParticipantDefinitif not found");
        }
    }

    private ParticipantDefinitifDTO mapToDTO(ParticipantDefinitif tableauSuivi) {
        ParticipantDefinitifDTO tableauSuiviDTO = new ParticipantDefinitifDTO();

        tableauSuiviDTO.setId(tableauSuivi.getId());

        tableauSuiviDTO.setFormation(tableauSuivi.getFormation());

        tableauSuiviDTO.setNom(tableauSuivi.getNom());

        tableauSuiviDTO.setDirection(tableauSuivi.getDirection());

        tableauSuiviDTO.setDivision(tableauSuivi.getDivision());

        tableauSuiviDTO.setMatricule(tableauSuivi.getMatricule());

        tableauSuiviDTO.setCommentaire(tableauSuivi.getCommentaire());

        tableauSuiviDTO.setCompetences(tableauSuivi.isCompetences());

        tableauSuiviDTO.setAdmis(tableauSuivi.isAdmis());

        tableauSuiviDTO.setAssidu(tableauSuivi.isAssidu());

        tableauSuiviDTO.setNumeroDemande(tableauSuivi.getNumeroDemande());

        // Ajouter d'autres attributs si nécessaire
        return tableauSuiviDTO;
    }

}
