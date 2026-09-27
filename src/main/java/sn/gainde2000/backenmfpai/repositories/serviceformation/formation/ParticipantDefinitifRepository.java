package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.ParticipantDefinitif;

public interface ParticipantDefinitifRepository extends JpaRepository<ParticipantDefinitif, Long> {

    List<ParticipantDefinitif> findByFormationId(Long formationId);

    List<ParticipantDefinitif> findByMatricule(String matricule);

    long countByDirection(String direction);

}