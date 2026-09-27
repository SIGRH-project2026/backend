package sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.TraitementPermutation;

public interface TraitementPermutationRepository extends JpaRepository<TraitementPermutation,Long>, QuerydslPredicateExecutor<TraitementPermutation> {
}
