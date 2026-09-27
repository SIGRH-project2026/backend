package sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.TraitementCampagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.TraitementExpressionDeBesoin;

import java.util.List;

public interface TraitementExpressionDeBesoinRepository extends JpaRepository<TraitementExpressionDeBesoin, Long>, QuerydslPredicateExecutor<TraitementExpressionDeBesoin> {
//    findByExp_IdAndActivatedTrue
    List<TraitementExpressionDeBesoin> findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(long id);
    List<TraitementExpressionDeBesoin> findTraitementExpressionDeBesoinByExpressionDeBesoin_Id(long id);
//    List<TraitementCampagne> findTraitementCampagneByCampagne_IdAndActivatedTrue(long idCampagne);

//    List<TraitementExpressionDeBesoin> findTraitementExpressionDeBesoinByExpressionDeBesoin_IdAndActivatedTrue(long id);
}
