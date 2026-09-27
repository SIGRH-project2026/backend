package sn.gainde2000.backenmfpai.repositories.servicesociale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicesociale.StatutPriseEnCharge;

import java.util.Optional;

public interface StatutPriseEnchargeRepository extends JpaRepository<StatutPriseEnCharge, Long>, QuerydslPredicateExecutor<StatutPriseEnCharge> {

    Optional<StatutPriseEnCharge> findStatutPriseEnChargeById(long id);
    Optional<StatutPriseEnCharge> findStatutPriseEnChargeByCode(String code);

    Optional<StatutPriseEnCharge> findByCode(String code);
}
