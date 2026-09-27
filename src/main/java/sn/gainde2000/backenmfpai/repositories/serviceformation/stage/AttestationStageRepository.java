package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.AttestationStage;

import java.util.Optional;

public interface AttestationStageRepository extends JpaRepository<AttestationStage, Long>, QuerydslPredicateExecutor<AttestationStage> {
    Optional<AttestationStage> findAttestationStageByDemandeStage_Id(long id);
}
