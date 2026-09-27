package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;

import java.util.Optional;

//@Repository
public interface IAgentRepository extends JpaRepository<Agent,Long>, QuerydslPredicateExecutor<Agent> {
    Optional<Agent> findAgentById(long id);
}