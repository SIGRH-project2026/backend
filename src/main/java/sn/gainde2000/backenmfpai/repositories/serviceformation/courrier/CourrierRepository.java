package sn.gainde2000.backenmfpai.repositories.serviceformation.courrier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.Courrier;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourrierRepository extends JpaRepository<Courrier, Long>, QuerydslPredicateExecutor<Courrier> {
    Optional<Courrier> findCourrierByReference(String reference);

    List<Courrier> findCourrierByStatutAndDivision_Code(String traiter, String code);

    List<Courrier> findCourrierByStatut(String statut);
}
