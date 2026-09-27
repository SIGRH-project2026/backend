package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypeDiplome;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-09:50
 * @project backend_mfpai
 */
public interface TypeDiplomeRepository extends JpaRepository<TypeDiplome, Integer> {

    Optional<TypeDiplome> findByCode(String code);
}
