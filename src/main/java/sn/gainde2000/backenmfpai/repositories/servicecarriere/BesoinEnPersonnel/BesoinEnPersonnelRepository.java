
package sn.gainde2000.backenmfpai.repositories.servicecarriere.BesoinEnPersonnel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnel;


public interface BesoinEnPersonnelRepository extends JpaRepository<BesoinEnPersonnel, Long>, QuerydslPredicateExecutor<BesoinEnPersonnel> {
   // Response<Object> findBesoinEnPersonnelByUtilisateur(Utilisateur utilisateur, StatutBEP statut);

   @Query("SELECT COUNT(bep) FROM BesoinEnPersonnel bep WHERE  bep.ia.code = :iaCode")
   long countAllBepIa(String iaCode);
    @Query("SELECT COUNT(bep) FROM BesoinEnPersonnel bep WHERE  bep.ief.code = :iefCode")
    long countAllBepIef(String iefCode);

    @Query("SELECT COUNT(bep) FROM BesoinEnPersonnel bep ")
    long countAllBep();

}

