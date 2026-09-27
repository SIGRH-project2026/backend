package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Diplomes;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-09:50
 * @project backend_mfpai
 */
public interface DiplomeRepository  extends JpaRepository<Diplomes, Long>, JpaSpecificationExecutor<Diplomes>, QuerydslPredicateExecutor<Diplomes> {
    Optional<Diplomes> findByCode(String code);
}
