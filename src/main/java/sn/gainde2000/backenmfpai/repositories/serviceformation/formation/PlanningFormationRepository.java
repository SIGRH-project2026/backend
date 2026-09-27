package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.PlanningFormation;

public interface PlanningFormationRepository extends JpaRepository<PlanningFormation, Long> {
    List<PlanningFormation> findByFormationId(Long formationId);
}
