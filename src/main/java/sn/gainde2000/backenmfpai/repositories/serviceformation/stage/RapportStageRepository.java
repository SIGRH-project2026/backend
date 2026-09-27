package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.RapportStage;

import java.util.Optional;

public interface RapportStageRepository extends JpaRepository<RapportStage, Long>, QuerydslPredicateExecutor<RapportStage> {
    Optional<RapportStage> findByDemandeStage_Id(Long aLong);
}
