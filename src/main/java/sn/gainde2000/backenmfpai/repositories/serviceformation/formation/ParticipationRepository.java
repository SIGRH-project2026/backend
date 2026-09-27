package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participation;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByFormationId(Long formationId);
}
