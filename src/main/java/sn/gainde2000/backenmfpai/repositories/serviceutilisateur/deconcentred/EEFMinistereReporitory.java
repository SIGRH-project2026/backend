package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEFMinistere;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 30/05/2024-15:30
 * @project backend_mfpai
 */
public interface EEFMinistereReporitory extends JpaRepository<EEFMinistere, Long> {
    List<EEFMinistere> findByRegion_Code(String code);

    Optional<EEFMinistere> findByCode( String code);
}
