
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;

import java.util.List;
import java.util.Optional;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:26
 * @project backend_mfpai
 */
public interface IARepository extends JpaRepository<IA, Long>, QuerydslPredicateExecutor<IA> {
    List<IA> findByRegion_Code(String code);

    Optional<IA> findByCode(String code);
    Optional<IA> findByLabel(String code);

    //indicateurs IA
    @Query("SELECT COUNT(ia) FROM IA ia ")
    long countAllIa();
}
