package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.SituationAdministrative;

/**
 * @author bsdieme
 */

public interface ISituationAdministrativeRepository extends JpaRepository<SituationAdministrative, Long>, QuerydslPredicateExecutor<DossierAgent> {

}
