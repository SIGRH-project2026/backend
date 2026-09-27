package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Borderau;

import java.util.Optional;

public interface BordereauRepository extends JpaRepository<Borderau, Long>, QuerydslPredicateExecutor<Borderau> {

    Optional<Borderau> findBorderauByAgent_IdAndActeId(long idAgent,long idActe);

    Optional<Borderau> findBorderauById(long id);
}
