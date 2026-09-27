package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 30/01/2024-11:40
 * @project backend_mfpai
 */
public interface ServiceRepository extends JpaRepository<Services, Long>, QuerydslPredicateExecutor<Services> {
    Optional<Services> findByCode(String code);

    List<Services> findByDirection_Code(String  code);
}
