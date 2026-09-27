package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 30/01/2024-11:40
 * @project backend_mfpai
 */
public interface DivisionRepository extends JpaRepository<Division, Long>, QuerydslPredicateExecutor<Division> {
    Optional<Division> findByCode(String code);

    List<Division> findByDirection_Code(String code);
}
