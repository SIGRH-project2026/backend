package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;

import java.util.Optional;

public interface StatutActeRepository  extends JpaRepository<StatutActe, Long>, QuerydslPredicateExecutor<StatutActe> {
    Optional<StatutActe> findStatutActeById(long id);
    Optional<StatutActe> findStatutActeByCode(String code);

    Optional<StatutActe> findByCode(String code);



}
