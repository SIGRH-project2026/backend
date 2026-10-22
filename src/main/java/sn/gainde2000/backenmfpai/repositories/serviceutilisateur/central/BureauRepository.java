package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;


import java.util.List;
import java.util.Optional;


/**
 * @author Abdou Karim CISSOKHO
 * @created 30/01/2024-11:38
 * @project backend_mfpai
 */
public interface BureauRepository extends JpaRepository<Bureau, Long>, QuerydslPredicateExecutor<Bureau> {
    Optional<Bureau> findByCode(String code);

    List<Bureau> findByDivision_Code(String code);

    // Libellé comparé sans tenir compte de la casse ni des espaces de début/fin
    @Query("SELECT e FROM Bureau e WHERE LOWER(TRIM(e.label)) = LOWER(TRIM(:label))")
    List<Bureau> findByLibelleNormalise(@Param("label") String label);
}
