package sn.gainde2000.backenmfpai.repositories.servicepta.pta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ActionPTA;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.ResultPTA;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:29
 * @project backend_mfpai
 */
public interface ActionPTARepository  extends JpaRepository<ActionPTA, Long> , QuerydslPredicateExecutor<ActionPTA> {

    @Query(value = "SELECT count(*) FROM schema_pta.td_actionpta as  act where act.pta_action_id = ?1", nativeQuery = true)
    int getActionPTAForAPTA(Long id);

    @Query(value = "SELECT * FROM schema_pta.td_actionpta as act " +
            " where act.pta_action_id = ?1   ORDER BY act.id DESC", nativeQuery = true)
    List<ActionPTA> getListAction(Long id);


    @Query(value = """
            select acta.* from schema_pta.td_actionpta  as acta 
            join schema_pta.td_plandetravail as pta   on pta.id = acta.pta_action_id
            where pta.id = ?1                                         
                """, nativeQuery = true)
    List<ActionPTA> countActionPTAByPtaId(Long id);



}
