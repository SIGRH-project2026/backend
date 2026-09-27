package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.StatutDemandeStage;

import java.util.Optional;

public interface StatutDemandeRepository extends JpaRepository<StatutDemandeStage, Long>, QuerydslPredicateExecutor<StatutDemandeStage> {
    Optional<StatutDemandeStage> findByCode(String code);
}
