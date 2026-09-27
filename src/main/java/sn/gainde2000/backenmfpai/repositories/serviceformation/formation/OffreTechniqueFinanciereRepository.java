package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.OffreTechniqueFinanciere;

public interface OffreTechniqueFinanciereRepository extends JpaRepository<OffreTechniqueFinanciere, Long> {
    List<OffreTechniqueFinanciere> findByFormationId(Long formationId);
}
