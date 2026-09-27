package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ParametreCorpsGrade;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-16:10
 * @project backend_mfpai
 */
public interface ParametreCorpsGradeRepository extends JpaRepository<ParametreCorpsGrade, Long> , QuerydslPredicateExecutor<ParametreCorpsGrade> {

    Optional<ParametreCorpsGrade> findParametreCorpsGradeByCorpsGrade_Code(String libelleCorps);


    @Query(value = "SELECT count(*) FROM schema_utilisateur.td_parametre_corpsgrade as  act where act.params_cg_id = ?1", nativeQuery = true)
    int getParamsId(Long id);
}
