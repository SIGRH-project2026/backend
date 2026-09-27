
package sn.gainde2000.backenmfpai.repositories.servicecarriere.BesoinEnPersonnel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.StatutBEP;


public interface StatutBEPRepository extends JpaRepository<StatutBEP, Long>, QuerydslPredicateExecutor<StatutBEP> {
}
