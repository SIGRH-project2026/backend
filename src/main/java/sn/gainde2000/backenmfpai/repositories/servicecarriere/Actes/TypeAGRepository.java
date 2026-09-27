package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAG;

import java.util.List;
import java.util.Optional;

public interface TypeAGRepository extends JpaRepository<TypeAG, Long>, QuerydslPredicateExecutor<TypeAG> {
    Optional<TypeAG> findByCode(String code);

    Optional<TypeAG> findTypeAGByCode(String code);
    Optional<TypeAG> findByLibelle(String libelle);
    Optional<TypeAG> findFirstByLibelleIgnoreCaseOrderByIdAsc(String libelle);

    Optional<TypeAG> findTypeAGByLibelle(String code);
}
