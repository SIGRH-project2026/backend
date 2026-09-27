package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Rapport;

public interface RapportRepository extends JpaRepository<Rapport, Long> {

    Optional<Rapport> findByFormationId(Long formationId);

}
