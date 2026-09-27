package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;

import java.util.Optional;
import java.util.List;

public interface TypeActeRepository  extends JpaRepository<TypeActe, Long>, QuerydslPredicateExecutor<TypeActe> {

    Optional<TypeActe> findTypeActeByCodeActe(String code);
    List<TypeActe> findAllByCodeActeIgnoreCaseOrderByIdDesc(String code);
    Optional<TypeActe> findByCodeActe(String code);
    Optional<TypeActe> findTypeActeById(long id);
}
