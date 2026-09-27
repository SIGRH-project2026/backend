package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DisciplineStage;

import java.util.Optional;

public interface DisciplineRepository extends JpaRepository<DisciplineStage,Long>, QuerydslPredicateExecutor<DisciplineStage> {
    Optional<DisciplineStage> findByCode(String code);
}
