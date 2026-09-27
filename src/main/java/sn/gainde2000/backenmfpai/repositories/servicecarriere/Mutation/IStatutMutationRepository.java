package sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.StatutMutation;

public interface IStatutMutationRepository  extends JpaRepository<StatutMutation, Long>, QuerydslPredicateExecutor<StatutMutation> {
    StatutMutation findByCode(String code);
}
