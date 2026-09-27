package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TraitementActe;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 05/02/2024-16:30
 * @project backend_mfpai
 */
public interface TraitementActeRepository extends JpaRepository<TraitementActe, Long>, QuerydslPredicateExecutor<TraitementActe> {
    //List<TraitementActe> findTraitementActeByActe_Id(Long id);
}
