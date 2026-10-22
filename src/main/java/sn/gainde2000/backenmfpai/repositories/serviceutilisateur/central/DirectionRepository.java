package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 30/01/2024-11:39
 * @project backend_mfpai
 */
public interface DirectionRepository extends JpaRepository<Direction, Long>, QuerydslPredicateExecutor<Direction> {
    Optional<Direction> findByCode(String code);

    List<Direction> findDirectionByCode(String code);

    // Libellé comparé sans tenir compte de la casse ni des espaces de début/fin
    @Query("SELECT e FROM Direction e WHERE LOWER(TRIM(e.label)) = LOWER(TRIM(:label))")
    List<Direction> findByLibelleNormalise(@Param("label") String label);
}
