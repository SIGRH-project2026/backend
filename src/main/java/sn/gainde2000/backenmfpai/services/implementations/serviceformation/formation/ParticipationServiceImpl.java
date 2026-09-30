package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.mappers.serviceformation.formation.FormationMapper;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.central.CentralLevelMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ParticipationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.IParticipationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipationDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationServiceImpl implements IParticipationService {
    private final BusinessNotificationService businessNotifications;


    private final ParticipationRepository participationRepository;
    private final FormationRepository formationRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final FormationMapper formationMapper;
    private final CentralLevelMapper centralLevelMapper;
    private final IUtilisateurRepository utilisateurRepository;

    @Override
    public ParticipationDTO createParticipation(ParticipationDTO participationDTO) {
        System.out.println(participationDTO);

        Participation participation = new Participation();
        // Set formation and centralLevel using their IDs
        Formation formation = formationRepository.findById(participationDTO.getFormationId()).orElse(null);
        System.out.println(formation);
        participation.setFormation(formation);

        Utilisateur centralLevel = utilisateurRepository.findById(participationDTO.getCentralLevelId()).orElse(null);
        System.out.println(centralLevel);
        participation.setCentralLevel(centralLevel);

        participation.setNumeroDemande(participationDTO.getNumeroDemande());

        Participation savedParticipation = participationRepository.save(participation);
        businessNotifications.notify(centralLevel, "Inscription à une formation",
                "Votre participation à la formation " + formation.getReference() + " a été enregistrée.");
        return mapToDTO(savedParticipation);
    }

    @Override
    public List<ParticipationDTO> getParticipationsByFormationId(Long formationId) {
        List<Participation> participations = participationRepository.findByFormationId(formationId);
        return participations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // private ParticipationDTO mapToDTO(Participation participation) {
    // ParticipationDTO participationDTO = new ParticipationDTO();
    // participationDTO.setFormationId(participation.getFormation().getId());
    // participationDTO.setCentralLevelId(participation.getCentralLevel().getId());
    // // Ajouter d'autres attributs si nécessaire
    // return participationDTO;
    // }

    @Override
    public List<ParticipationDTO> getAllParticipations() {
        List<Participation> participations = participationRepository.findAll();
        return participations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationDTO getParticipationById(Long id) {
        Optional<Participation> participation = participationRepository.findById(id);
        return participation.map(this::mapToDTO).orElse(null);
    }

    private ParticipationDTO mapToDTO(Participation participation) {
        ParticipationDTO participationDTO = new ParticipationDTO();

        participationDTO.setId(participation.getId());
        participationDTO.setFormationId(participation.getFormation().getId());
        participationDTO.setCentralLevelId(participation.getCentralLevel().getId());

        // Récupérer et définir les objets FormationDTO et CentralLevelDTO complets
        Formation formation = participation.getFormation();
        participationDTO.setFormation(formationMapper.entityToDto(formation));

        Utilisateur centralLevel = participation.getCentralLevel();
        // participationDTO.setCentralLevel(centralLevelMapper.toDto((CentralLevel)
        // centralLevel));
        participationDTO.setCentralLevel(centralLevel);

        participationDTO.setNumeroDemande(participation.getNumeroDemande());

        // Ajouter d'autres attributs si nécessaire
        return participationDTO;
    }
}