
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-12:37
 * @project backend_mfpai
 */
public interface SpecialityRepository extends JpaRepository<Speciality, Long>, QuerydslPredicateExecutor<Speciality> {
    Optional<Speciality> findByCode(String code);
    Optional<Speciality> findByLabel(String code);
}
