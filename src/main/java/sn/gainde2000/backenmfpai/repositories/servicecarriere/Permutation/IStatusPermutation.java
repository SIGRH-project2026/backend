package sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.StatusPermutation;
@Repository
public interface IStatusPermutation extends JpaRepository<StatusPermutation, Long>, QuerydslPredicateExecutor<StatusPermutation> {
    StatusPermutation findByCode(String code);
}
