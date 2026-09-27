package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.StatutDemandeStage;

import java.util.List;
import java.util.Map;

public interface DemandeStageRepository extends JpaRepository<DemandeStage, Long>, QuerydslPredicateExecutor<DemandeStage> {

    List<DemandeStage> findDemandeStageByStatutDemandeStageAndDivision_Code(StatutDemandeStage enregistrer, String code);

    List<DemandeStage> findDemandeStageByStatutDemandeStage(StatutDemandeStage enregistrer);
}
