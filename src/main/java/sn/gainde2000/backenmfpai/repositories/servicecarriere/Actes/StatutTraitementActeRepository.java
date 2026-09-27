package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutTraitementActe;

import java.util.Optional;

public interface StatutTraitementActeRepository  extends JpaRepository<StatutTraitementActe, Long>, QuerydslPredicateExecutor<StatutTraitementActe> {
    Optional<StatutTraitementActe> findStatutTraitementActeById(long id);
    Optional<StatutTraitementActe> findStatutTraitementActeByCode(String code);
    Optional<StatutTraitementActe> findByCode(String code);
}
