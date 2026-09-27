package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.PieceJointes;

public interface PieceJointesRepository extends JpaRepository<PieceJointes, Long>, QuerydslPredicateExecutor<PieceJointes> {

}
