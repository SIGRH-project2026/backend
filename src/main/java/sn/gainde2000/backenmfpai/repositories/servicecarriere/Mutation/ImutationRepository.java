package sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;

public interface ImutationRepository extends JpaRepository<Mutation, Long>, QuerydslPredicateExecutor<Mutation>  {
   //representant IA
@Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.ia.code = :iaCode")
long countValidRejectMutationsIa(@Param("iaCode") String iaCode,@Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.origineDemandeurLog.ia.code = :iaCode")
    long countAllMutationsIa(String iaCode);

    //repreesentant IEF
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.ief.code = :iefCode")
    long countValidRejectMutationsIef(String iefCode, String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.origineDemandeurLog.ief.code = :iefCode")
    long countAllMutationsIef(String iefCode);

    //chef etablissement
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.etablissement.code = :etabCode")
    long countValidRejectMutationsEtab(String etabCode, String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE  mut.origineDemandeurLog.etablissement.code = :etabCode")
    long countAllMutationsEtab(String etabCode);

    //all mutations
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode")
    long countValidRejectedMutations(String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut")
    long countAllMutations();
    //chef bureau
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.bureau.code = :burCode")
    long countValidRejectMutationsBureau(String burCode, String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE  mut.origineDemandeurLog.bureau.code = :burCode")
    long countAllMutationsBureau(String burCode);
   //chef service
   @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.service.code = :servCode")
   long countValidRejectMutationsService(String servCode, String statutCode);
   @Query("SELECT COUNT(mut) FROM Mutation mut WHERE  mut.origineDemandeurLog.service.code = :servCode")
   long countAllMutationsService(String servCode);
   //chef division
   @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.division.code = :divCode")
   long countValidRejectMutationsDivision(String divCode, String statutCode);
   @Query("SELECT COUNT(mut) FROM Mutation mut WHERE  mut.origineDemandeurLog.division.code = :divCode")
   long countAllMutationsDivision(String divCode);

}
