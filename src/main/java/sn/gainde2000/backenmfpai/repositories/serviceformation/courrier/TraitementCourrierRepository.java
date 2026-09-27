package sn.gainde2000.backenmfpai.repositories.serviceformation.courrier;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.TraitementCourrier;

import java.util.Optional;

public interface TraitementCourrierRepository extends JpaRepository<TraitementCourrier, Long> {
    Optional<TraitementCourrier> findByActivatedTrue();
}
