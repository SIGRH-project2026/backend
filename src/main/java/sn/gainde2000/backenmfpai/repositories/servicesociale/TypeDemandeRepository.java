package sn.gainde2000.backenmfpai.repositories.servicesociale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicesociale.StatutPriseEnCharge;
import sn.gainde2000.backenmfpai.entities.servicesociale.TypeDemande;

import java.util.Optional;

public interface TypeDemandeRepository extends JpaRepository<TypeDemande, Long>, QuerydslPredicateExecutor<TypeDemande> {
    Optional<TypeDemande> findTypeDemandeById(long id);
    Optional<TypeDemande> findTypeDemandeByCode(String code);

    Optional<TypeDemande> findByCode(String code);


}
