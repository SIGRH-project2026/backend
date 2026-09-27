package sn.gainde2000.backenmfpai.repositories.servicepta.parametre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;

public interface ParametreRepository extends JpaRepository<Parametre, Long>, QuerydslPredicateExecutor<Parametre> {
}
