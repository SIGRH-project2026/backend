package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.TableauSuivi;

public interface TableauSuiviRepository extends JpaRepository<TableauSuivi, Long> {
    List<TableauSuivi> findByFormationId(Long formationId);
}
