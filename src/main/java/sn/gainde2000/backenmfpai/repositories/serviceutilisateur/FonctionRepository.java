package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;


import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 31/01/2024-08:56
 * @project backend_mfpai
 */
public interface FonctionRepository extends JpaRepository<Fonction, Long>, QuerydslPredicateExecutor<Fonction> {
    Optional<Fonction> findByCode(String code);
    Optional<Fonction> findByLabel(String code);

    // Libellé comparé sans tenir compte de la casse ni des espaces de début/fin
    @Query("SELECT e FROM Fonction e WHERE LOWER(TRIM(e.label)) = LOWER(TRIM(:label))")
    List<Fonction> findByLibelleNormalise(@Param("label") String label);
}
