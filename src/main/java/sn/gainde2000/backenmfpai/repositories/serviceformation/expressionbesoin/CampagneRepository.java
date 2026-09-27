package sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;

import java.util.List;
import java.util.Optional;

public interface CampagneRepository extends JpaRepository<Campagne, Long>, QuerydslPredicateExecutor<Campagne> {
    Optional<Campagne> findByIdAndDeletedFalse(long id);
    List<Campagne> findCampagneByStatutAndDeletedFalse(String statut);
//    List<Campagne> findByDeletedFalseAndTraitementCampagne_StatutCampagne_Code(String code);
}
