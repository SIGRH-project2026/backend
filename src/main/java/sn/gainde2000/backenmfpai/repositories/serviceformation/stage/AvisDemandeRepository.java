package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.AvisDemandeStage;

public interface AvisDemandeRepository extends JpaRepository<AvisDemandeStage, Long>, QuerydslPredicateExecutor<AvisDemandeStage> {
}
