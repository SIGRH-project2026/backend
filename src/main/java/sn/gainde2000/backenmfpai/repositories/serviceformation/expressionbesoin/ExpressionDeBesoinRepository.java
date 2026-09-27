package sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.ExpressionDeBesoin;

import java.util.List;
import java.util.Optional;

public interface ExpressionDeBesoinRepository extends JpaRepository<ExpressionDeBesoin, Long>, QuerydslPredicateExecutor<ExpressionDeBesoin> {
    Optional<ExpressionDeBesoin> findByIdAndDeletedFalse(long id);
    List<ExpressionDeBesoin> findByCampagne_IdAndDeletedFalse(long id);
}
