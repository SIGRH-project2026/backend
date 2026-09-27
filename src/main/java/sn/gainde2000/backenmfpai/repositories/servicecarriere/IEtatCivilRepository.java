package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.EtatCivil;

public interface IEtatCivilRepository extends JpaRepository<EtatCivil,Long>, QuerydslPredicateExecutor<EtatCivil> {
}
