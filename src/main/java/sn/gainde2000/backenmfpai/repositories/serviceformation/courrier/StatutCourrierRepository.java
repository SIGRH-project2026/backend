package sn.gainde2000.backenmfpai.repositories.serviceformation.courrier;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.StatutCourrier;

import java.util.Optional;

public interface StatutCourrierRepository extends JpaRepository<StatutCourrier, Long> {
    Optional<StatutCourrier> findByCode(String code);
}
