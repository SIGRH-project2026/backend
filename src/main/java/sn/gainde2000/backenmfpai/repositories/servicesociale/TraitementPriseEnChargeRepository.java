package sn.gainde2000.backenmfpai.repositories.servicesociale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicesociale.PriseEnCharge;
import sn.gainde2000.backenmfpai.entities.servicesociale.TraitementPriseEnCharge;

public interface TraitementPriseEnChargeRepository extends JpaRepository<TraitementPriseEnCharge,Long>, QuerydslPredicateExecutor<TraitementPriseEnCharge> {
}
