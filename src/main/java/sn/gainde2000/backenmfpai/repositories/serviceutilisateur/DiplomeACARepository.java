package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomeACA;



import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-11:59
 * @project backend_mfpai
 */
public interface DiplomeACARepository extends JpaRepository<DiplomeACA, Long>, QuerydslPredicateExecutor<DiplomeACA> {
    Optional<DiplomeACA> findByCode(String code);

    DiplomeACA findTopByOrderByIdDesc();


    @Query("SELECT COALESCE(MAX(d.id), 0) FROM DiplomeACA d")
    Long findMaxId();
}
