package sn.gainde2000.backenmfpai.repositories.servicecarriere.FicheEtablissement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Discipline;

public interface IDisciplineRepository extends JpaRepository<Discipline, Long>, QuerydslPredicateExecutor<Discipline> {

}
