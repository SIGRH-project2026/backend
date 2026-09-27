package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAG;

import java.util.Optional;

public interface TypeAARepository extends JpaRepository<TypeAA, Long>, QuerydslPredicateExecutor<TypeAA> {

    Optional<TypeAA> findByCode(String code);
    Optional<TypeAA> findTypeAAByCode (String code);
    Optional<TypeAA> findByLibelle(String libelle);
    Optional<TypeAA> findFirstByLibelleIgnoreCaseOrderByIdAsc(String libelle);
    Optional<TypeAA> findTypeAAByLibelle(String code);

}
