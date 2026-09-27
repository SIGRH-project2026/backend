package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByFormationId(Long formationId);
}
