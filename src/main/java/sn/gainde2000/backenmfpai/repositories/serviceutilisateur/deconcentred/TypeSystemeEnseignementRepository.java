package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeSystemeEnseignement;

public interface TypeSystemeEnseignementRepository extends JpaRepository<TypeSystemeEnseignement, Long>,
 QuerydslPredicateExecutor<TypeSystemeEnseignement> {

    Optional<TypeSystemeEnseignement> findByCode(String code);

    List<TypeSystemeEnseignement> findByLabelIgnoreCase(String label);

}
