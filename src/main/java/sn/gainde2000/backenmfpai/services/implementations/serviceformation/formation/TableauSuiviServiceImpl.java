package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participation;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.TableauSuivi;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.mappers.serviceformation.formation.FormationMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.FormationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ParticipationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.TableauSuiviRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.ITableauSuiviService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.ParticipationDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TableauSuiviDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableauSuiviServiceImpl implements ITableauSuiviService {

    private final TableauSuiviRepository tableauSuiviRepository;
    private final FormationRepository formationRepository;
    private final FormationMapper formationMapper;

    @Override
    public TableauSuiviDTO createTableauSuivi(TableauSuiviDTO tableauSuiviDTO) {
        System.out.println(tableauSuiviDTO);

        TableauSuivi tableauSuivi = new TableauSuivi();
        // Set formation and centralLevel using their IDs
        // Formation formation = tableauSuiviDTO.getFormation();
        Formation formation = formationRepository.findById(tableauSuiviDTO.getFormationId()).orElse(null);

        System.out.println(formation);
        tableauSuivi.setFormation(formation);

        tableauSuivi.setPresences(tableauSuiviDTO.getPresences());

        tableauSuivi.setAbscences(tableauSuiviDTO.getAbscences());

        tableauSuivi.setNbrSession(tableauSuiviDTO.getNbrSession());

        TableauSuivi savedTableauSuivi = tableauSuiviRepository.save(tableauSuivi);

        return mapToDTO(savedTableauSuivi);
    }

    @Override
    public List<TableauSuiviDTO> getTableauxSuiviByFormationId(Long formationId) {
        List<TableauSuivi> tableauxSuivi = tableauSuiviRepository.findByFormationId(formationId);
        return tableauxSuivi.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TableauSuiviDTO> getAllTableauxSuivi() {
        List<TableauSuivi> tableauxSuivi = tableauSuiviRepository.findAll();
        return tableauxSuivi.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TableauSuiviDTO getTableauSuiviById(Long id) {
        Optional<TableauSuivi> tableauSuivi = tableauSuiviRepository.findById(id);
        return tableauSuivi.map(this::mapToDTO).orElse(null);
    }

    private TableauSuiviDTO mapToDTO(TableauSuivi tableauSuivi) {
        TableauSuiviDTO tableauSuiviDTO = new TableauSuiviDTO();

        tableauSuiviDTO.setId(tableauSuivi.getId());

        tableauSuiviDTO.setFormation(tableauSuivi.getFormation());

        tableauSuiviDTO.setPresences(tableauSuivi.getPresences());

        tableauSuiviDTO.setAbscences(tableauSuivi.getAbscences());

        tableauSuiviDTO.setNbrSession(tableauSuivi.getNbrSession());

        // Ajouter d'autres attributs si nécessaire
        return tableauSuiviDTO;
    }

}