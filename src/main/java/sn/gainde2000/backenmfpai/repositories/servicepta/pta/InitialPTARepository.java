package sn.gainde2000.backenmfpai.repositories.servicepta.pta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.InitialPTA;


/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-11:48
 * @project backend_mfpai
 */
public interface InitialPTARepository extends JpaRepository<InitialPTA, Long>, QuerydslPredicateExecutor<InitialPTA> {
}
