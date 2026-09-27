package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DiplomeList;

@Repository
public interface IDiplomeListRepository extends JpaRepository<DiplomeList,Long>, QuerydslPredicateExecutor<DiplomeList> {

}
