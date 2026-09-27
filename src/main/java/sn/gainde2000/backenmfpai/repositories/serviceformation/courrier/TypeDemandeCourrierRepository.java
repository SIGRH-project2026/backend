package sn.gainde2000.backenmfpai.repositories.serviceformation.courrier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.TypeDemandeCourrier;

import java.util.List;
import java.util.Optional;

public interface TypeDemandeCourrierRepository extends JpaRepository<TypeDemandeCourrier, Long>, QuerydslPredicateExecutor<TypeDemandeCourrier> {
    Optional<TypeDemandeCourrier> findByCode(String codeDemandeCourrier);
    List<TypeDemandeCourrier> findTypeDemandeCourrierByNomTypeCourrierAndDivision_Code(NomTypeCourrier nomTypeCourrier, String code);

}
