package sn.gainde2000.backenmfpai.repositories.servicepta.pta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.PlanDeTravail;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:29
 * @project backend_mfpai
 */
public interface PlanDeTravailRepository extends JpaRepository<PlanDeTravail, Long>, QuerydslPredicateExecutor<PlanDeTravail> {

    @Query("""
            SELECT p  FROM PlanDeTravail p
            order by p.id desc limit 1 
          """)
    PlanDeTravail getLastPTA();
}
