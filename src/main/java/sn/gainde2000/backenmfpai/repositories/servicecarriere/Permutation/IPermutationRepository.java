package sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;

import java.util.Optional;

public interface IPermutationRepository extends JpaRepository<Permutation, Long> , QuerydslPredicateExecutor<Permutation> {
    Optional<Permutation> findById(long id);

    // representant IA
    @Query("SELECT COUNT(per) FROM Permutation per WHERE per.iaDemandeur.code = :iaCode AND per.niveau > 3")
    long countPermutationIaDemandeur(String iaCode);

    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.traitementPermutation.statut.code = :statutCode " +
            "AND per.iaDemandeur.code = :iaCode  " +
            "AND per.niveau > 3")
    long countPermutationByStatutDemandeurIa(@Param("iaCode") String iaCode, @Param("statutCode")  String statutCode);

    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.iaReceveur.code = :iaCode  " +
            "AND per.niveau > 3 " +
            "AND per.iaDemandeur.code != per.iaReceveur.code")
    long countPermutationIaReceveur(String iaCode);

    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.traitementPermutation.statut.code = :statutCode " +
            "AND per.iaReceveur.code = :iaCode  " +
            "AND per.niveau > 3 " +
            "AND per.iaDemandeur != per.iaReceveur")
    long countPermutationByStatutReceveurIa(@Param("iaCode") String iaCode, @Param("statutCode")  String statutCode);

    // representant IEF
    @Query("SELECT COUNT(per) FROM Permutation per " +
            "WHERE per.iefDemandeur.code = :iefCode  " +
            "AND per.niveau > 2")
    long countPermutationIefDemandeur(String iefCode);

    @Query("SELECT COUNT(per) FROM Permutation per " +
            "WHERE per.traitementPermutation.statut.code = :statutCode " +
            "AND per.iefDemandeur.code = :iefCode  " +
            "AND per.niveau > 2")
    long countPermutationByStatutDemandeurIef(@Param("iefCode") String iefCode, @Param("statutCode")  String statutCode);

    @Query("SELECT COUNT(per) FROM Permutation per " +
            "WHERE per.iefReceveur.code = :iefCode  " +
            "AND per.niveau > 2 AND per.iefDemandeur.code != per.iefReceveur.code")
    long countPermutationIefReceveur(String iefCode);

    @Query("SELECT COUNT(per) FROM Permutation per " +
            "WHERE per.traitementPermutation.statut.code = :statutCode " +
            "AND per.iefReceveur.code = :iefCode  AND per.niveau > 2 " +
            "AND per.iefDemandeur.code != per.iefReceveur.code")
    long countPermutationByStatutReceveurIef(@Param("iefCode") String iefCode, @Param("statutCode")  String statutCode);

    // representant Etablissement
    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.etablissementDemandeur.code = :etabCode  " +
            "AND per.niveau > 1")
    long countPermutationEtabDemandeur(String etabCode);
    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.etablissementReceveur.code = :etabCode " +
            "AND per.niveau > 1")
    long countPermutationEtabReceveur(String etabCode);

    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.traitementPermutation.statut.code = :statutCode " +
            "AND per.etablissementDemandeur.code = :etabCode " +
            "AND per.niveau > 1")
    long countPermutationByStatutDemandeurEtab(@Param("etabCode") String etabCode, @Param("statutCode")  String statutCode);
    @Query("SELECT COUNT(per) " +
            "FROM Permutation per " +
            "WHERE per.traitementPermutation.statut.code = :statutCode " +
            "AND per.etablissementReceveur.code = :etabCode  " +
            "AND per.niveau > 1")
    long countPermutationByStatutReceveurEtab(@Param("etabCode") String etabCode, @Param("statutCode")  String statutCode);

    //Chef division dgpeec
    @Query("SELECT COUNT(per) FROM Permutation per WHERE per.niveau > :niveau")
    long countPermutationForChefDivision(@Param("niveau") long niveau);

    //all permutations
    @Query("SELECT COUNT(distinct per) FROM Permutation per WHERE per.traitementPermutation.statut.code = :statutCode")
    long countAllPermutationsByStatut(String statutCode);
    @Query("SELECT COUNT(distinct per) FROM Permutation per")
    long countAllPermutations();

    // chef division



  /*  //chef service
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.service.code = :servCode")
    long countValidRejectMutationsService(String servCode, String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE  mut.origineDemandeurLog.service.code = :servCode")
    long countAllMutationsService(String servCode);
    //chef division
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE mut.traitementMutation.statut.code = :statutCode and  mut.origineDemandeurLog.division.code = :divCode")
    long countValidRejectMutationsDivision(String divCode, String statutCode);
    @Query("SELECT COUNT(mut) FROM Mutation mut WHERE  mut.origineDemandeurLog.division.code = :divCode")
    long countAllMutationsDivision(String divCode);*/

}
