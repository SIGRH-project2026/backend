package sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.OrigineDemandeurLog;

public interface IOrigineDemandeurLogRepository extends JpaRepository<OrigineDemandeurLog, Long>, QuerydslPredicateExecutor<OrigineDemandeurLog> {
}
