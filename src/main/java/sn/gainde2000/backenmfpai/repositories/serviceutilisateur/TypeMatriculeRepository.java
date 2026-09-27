package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeMatricule;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypePoste;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-16:23
 * @project backend_mfpai
 */
public interface TypeMatriculeRepository extends JpaRepository<TypeMatricule, Long>, QuerydslPredicateExecutor<TypeMatricule> {
    Optional<TypeMatricule> findByCode(String code);

}
