package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Structure;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:57
 * @project backend_mfpai
 */
public interface StructureRepository extends JpaRepository<Structure, Long>, QuerydslPredicateExecutor<Structure> {
  //  List<Structure> findByIef_Code(String code);

    Optional<Structure> findByCode(String code);
}

