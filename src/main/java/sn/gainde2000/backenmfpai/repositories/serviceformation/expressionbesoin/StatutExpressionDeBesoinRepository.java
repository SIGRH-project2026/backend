package sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.StatutExpressionDeBesoin;

import java.util.Optional;

public interface StatutExpressionDeBesoinRepository extends JpaRepository<StatutExpressionDeBesoin,Long>, QuerydslPredicateExecutor<StatutExpressionDeBesoin> {

    Optional<StatutExpressionDeBesoin> findByCode(String code);
}
