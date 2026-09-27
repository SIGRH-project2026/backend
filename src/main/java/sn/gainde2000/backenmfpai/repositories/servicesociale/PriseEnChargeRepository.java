package sn.gainde2000.backenmfpai.repositories.servicesociale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.entities.servicesociale.PriseEnCharge;

import java.util.Optional;

public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge,Long>, QuerydslPredicateExecutor<PriseEnCharge> {
    Optional<PriseEnCharge> findPriseEnChargeById(long id);

    //Representant-Ia
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode and  pec.ia.code = :iaCode")
    long countValidRejectPecIa(@Param("iaCode") String iaCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.ia.code = :iaCode")
    long countAllPecIa(@Param("iaCode") String iaCode);

   //Representant-Ief
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode and  pec.ief.code = :iefCode")
    long countValidRejectPecIef(@Param("iefCode") String iefCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.ief.code = :iefCode")
    long countAllPecIef(@Param("iefCode") String iefCode);

    //Chef-Etablissement

    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode and  pec.etablissement.code = :etabCode")
    long countValidRejectPecEtab(@Param("etabCode") String etabCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.etablissement.code = :etabCode")
    long countAllPecEtab(@Param("etabCode") String etabCode);


    //Chef-Bureau

    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode and  pec.bureau.code = :burCode")
    long countValidRejectPecBureau(@Param("burCode") String burCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.bureau.code = :burCode")
    long countAllPecBureau(@Param("burCode") String burCode);


    //Chef-Service
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode and  pec.service.code = :serCode")
    long countValidRejectPecService(@Param("serCode") String serCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.service.code = :serCode")
    long countTotalPecService(@Param("serCode") String serCode);

    //Chef-Division
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode and  pec.division.code = :divCode")
    long countValidRejectPecDivision(@Param("divCode") String divCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.division.code = :divCode")
    long countTotalPecDivision(@Param("divCode") String divCode);

    //Toutes les demandes

    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec WHERE pec.statutPriseEnCharge.code= :statutCode")
    long countValidReject( @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(pec) FROM PriseEnCharge pec")
    long countTotalPec();



}
