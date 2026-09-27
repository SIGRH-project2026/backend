package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.UserManager;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/02/2024-11:12
 * @project backend_mfpai
 */
public interface UserManagerRepository extends JpaRepository<UserManager, Long>, QuerydslPredicateExecutor<UserManager> {

    Optional<UserManager> findUserManagerByEmail(String email);
}
