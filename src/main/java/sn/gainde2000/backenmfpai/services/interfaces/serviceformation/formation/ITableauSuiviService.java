package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import java.util.List;

import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TableauSuiviDTO;

public interface ITableauSuiviService {
    TableauSuiviDTO createTableauSuivi(TableauSuiviDTO tableauSuiviDTO);

    List<TableauSuiviDTO> getTableauxSuiviByFormationId(Long formationId);

    List<TableauSuiviDTO> getAllTableauxSuivi();

    TableauSuiviDTO getTableauSuiviById(Long id);
}