package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePED;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.DiplomePROF;


import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-12:00
 * @project backend_mfpai
 */
public interface DiplomePEDRepository extends JpaRepository<DiplomePED, Long>, QuerydslPredicateExecutor<DiplomePED> {
    Optional<DiplomePED> findByCode(String code);

    DiplomePED findTopByOrderByIdDesc();


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COALESCE(MAX(d.id), 0) FROM DiplomePED d")
    Long findMaxId();
}
