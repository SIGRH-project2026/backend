package sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.TraitementCampagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutCampagneEnum;

import java.util.List;
import java.util.Optional;

public interface TraitementCampagneRepository extends JpaRepository<TraitementCampagne, Long>, QuerydslPredicateExecutor<TraitementCampagne> {
    List<TraitementCampagne> findTraitementCampagneByCampagne_IdAndActivatedTrue(long idCampagne);
    List<TraitementCampagne> findTraitementCampagneByActivatedTrueAndCampagne_Id(long idCampagne);

    List<TraitementCampagne> findTraitementCampagneByCampagne_Id(long id);
    List<TraitementCampagne> findTraitementCampagneByActivatedTrueAndStatutCampagne_Code(String statutCampagneEnum);
}