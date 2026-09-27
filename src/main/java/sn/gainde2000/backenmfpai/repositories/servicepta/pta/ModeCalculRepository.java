package sn.gainde2000.backenmfpai.repositories.servicepta.pta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ModeCalcul;


/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-11:49
 * @project backend_mfpai
 */
public interface ModeCalculRepository extends JpaRepository<ModeCalcul, Long>, QuerydslPredicateExecutor<ModeCalcul> {
}
