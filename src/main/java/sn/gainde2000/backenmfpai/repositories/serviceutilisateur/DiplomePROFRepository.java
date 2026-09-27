package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePROF;


import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-12:00
 * @project backend_mfpai
 */
public interface DiplomePROFRepository extends JpaRepository<DiplomePROF, Long> , QuerydslPredicateExecutor<DiplomePROF> {
    Optional<DiplomePROF> findByCode(String code);

    DiplomePROF findTopByOrderByIdDesc();


    @Query("SELECT COALESCE(MAX(d.id), 0) FROM DiplomePROF d")
    Long findMaxId();
}
