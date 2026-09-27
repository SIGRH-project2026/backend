package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.TraitementDemandeStage;

import java.util.List;

public interface TraitementDemandeStageRepository extends JpaRepository<TraitementDemandeStage, Long>, QuerydslPredicateExecutor<TraitementDemandeStage> {
    List<TraitementDemandeStage> findByDemandeStage_Id(long id);
    List<TraitementDemandeStage> findByDemandeStage_IdAndActivatedTrue(long id);

}
