package sn.gainde2000.backenmfpai.repositories.servicepta.pta;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ResultPTA;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:30
 * @project backend_mfpai
 */
public interface ResultPTARepository extends JpaRepository<ResultPTA, Long> , QuerydslPredicateExecutor<ResultPTA> {

    @Query(value = "SELECT * FROM schema_pta.td_resultatpta as res " +
            " where res.result_act_pta_id = ?1", nativeQuery = true)
    Page<ResultPTA> getResultFromAction(Pageable pageable, Long id);
}
