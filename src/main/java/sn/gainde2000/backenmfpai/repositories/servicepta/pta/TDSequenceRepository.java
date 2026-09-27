package sn.gainde2000.backenmfpai.repositories.servicepta.pta;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.TDSequence;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/05/2024-12:09
 * @project backend_mfpai
 */
public interface TDSequenceRepository extends JpaRepository<TDSequence, Integer> {

    TDSequence findTopByOrderByIdDesc();
}
