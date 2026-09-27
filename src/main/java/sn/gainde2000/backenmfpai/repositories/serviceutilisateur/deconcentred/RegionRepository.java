
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-12:37
 * @project backend_mfpai
 */
public interface RegionRepository extends JpaRepository<Region, Long>, QuerydslPredicateExecutor<Region> {
    Optional<Region> findByCode(String code);

}

