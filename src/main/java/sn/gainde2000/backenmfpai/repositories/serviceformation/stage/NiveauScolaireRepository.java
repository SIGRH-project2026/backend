package sn.gainde2000.backenmfpai.repositories.serviceformation.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.NiveauScolaire;

import java.util.Optional;

public interface NiveauScolaireRepository extends JpaRepository<NiveauScolaire, Long>, QuerydslPredicateExecutor<NiveauScolaire> {
    Optional<NiveauScolaire> findByCode(String code);
}
