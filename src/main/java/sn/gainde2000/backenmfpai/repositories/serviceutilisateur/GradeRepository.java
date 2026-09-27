package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 03/03/2024-19:52
 * @project backend_mfpai
 */
public interface GradeRepository extends JpaRepository<Grade, Long>, QuerydslPredicateExecutor<Grade> {
    Optional<Grade> findByCode(String code);
    List<Grade> findByCorpsGrade_CodeOrderByIdAsc(String code);
    Grade findTopByOrderByIdDesc();

    @Query(value = "select DISTINCT (grad.grade_libelle), grad.grade_id, grad.grade_code, grad.corps_id from schema_utilisateur.tp_grade grad", nativeQuery = true)
    List<Grade> getGradeUniqueLabel();
}
