package sn.gainde2000.backenmfpai.web.controllers.serviceformation.formation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation.ITableauSuiviService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.TableauSuiviDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tableauxsuivi")
public class TableauSuiviController {

    private final ITableauSuiviService tableauSuiviService;

    @Autowired
    public TableauSuiviController(ITableauSuiviService tableauSuiviService) {
        this.tableauSuiviService = tableauSuiviService;
    }

    @PostMapping("/add")
    public ResponseEntity<TableauSuiviDTO> createTableauSuivi(@RequestBody TableauSuiviDTO tableauSuiviDTO) {
        TableauSuiviDTO createdTableauSuivi = tableauSuiviService.createTableauSuivi(tableauSuiviDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTableauSuivi);
    }

    @GetMapping("/byFormation/{formationId}")
    public ResponseEntity<List<TableauSuiviDTO>> getTableauxSuiviByFormationId(@PathVariable Long formationId) {
        List<TableauSuiviDTO> tableauxSuivi = tableauSuiviService.getTableauxSuiviByFormationId(formationId);
        return ResponseEntity.ok(tableauxSuivi);
    }

    @GetMapping("/all")
    public ResponseEntity<MFPAIResponse> getAllTableauxSuivi() {

        List<TableauSuiviDTO> tableauxSuivi = tableauSuiviService.getAllTableauxSuivi();

        MFPAIResponse response = MFPAIResponse.success(tableauxSuivi);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableauSuiviDTO> getTableauSuiviById(@PathVariable Long id) {
        TableauSuiviDTO tableauSuivi = tableauSuiviService.getTableauSuiviById(id);
        if (tableauSuivi != null) {
            return ResponseEntity.ok(tableauSuivi);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
