package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;


import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 31/01/2024-09:07
 * @project backend_mfpai
 */
public interface CorpsGradeRepository extends JpaRepository<CorpsGrade, Long>, QuerydslPredicateExecutor<CorpsGrade> {
    Optional<CorpsGrade> findByCode(String code);
    Optional<CorpsGrade> findByLabel(String code);
    List<CorpsGrade> findByTypeMatricule(String code);

    CorpsGrade findTopByOrderByIdDesc();
}
