package sn.gainde2000.backenmfpai.repositories.servicepta.parametre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.StatutParametre;

import java.util.Optional;

public interface StatutParametreRepository extends JpaRepository<StatutParametre, Long>, QuerydslPredicateExecutor<StatutParametre> {
  Optional<StatutParametre> findByCode(String code);
}
