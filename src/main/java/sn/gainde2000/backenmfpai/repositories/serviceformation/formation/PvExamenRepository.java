package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PvExamen;

public interface PvExamenRepository extends JpaRepository<PvExamen, Long> {

    List<PvExamen> findByFormationId(Long formationId);
}
