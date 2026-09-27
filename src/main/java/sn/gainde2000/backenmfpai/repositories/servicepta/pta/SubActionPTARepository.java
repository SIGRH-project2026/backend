package sn.gainde2000.backenmfpai.repositories.servicepta.pta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.SubActionPTA;


import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-11:50
 * @project backend_mfpai
 */
public interface SubActionPTARepository extends JpaRepository<SubActionPTA, Long>, QuerydslPredicateExecutor<SubActionPTA> {

    Page<SubActionPTA> findByResultPTA_Id(Long subActionId, Pageable pageable);
}
