package sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.StatutCampagne;

import java.util.List;
import java.util.Optional;

public interface StatutCampagneRepository extends JpaRepository<StatutCampagne, Long>, QuerydslPredicateExecutor<StatutCampagne> {
    Optional<StatutCampagne> findStatutCampagneByCode(String code);

    List<StatutCampagne> findStatutCampagneByIsDeletedFalse();
}
