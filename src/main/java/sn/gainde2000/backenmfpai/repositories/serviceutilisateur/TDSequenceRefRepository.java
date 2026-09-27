package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TDSequenceRef;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-19:45
 * @project backend_mfpai
 */
public interface TDSequenceRefRepository extends JpaRepository<TDSequenceRef, Long> {
    TDSequenceRef findTopByOrderByIdDesc();
}
