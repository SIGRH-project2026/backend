package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;

import java.util.List;
import java.util.Optional;

public interface ActeRepository extends JpaRepository<Acte, Long>, QuerydslPredicateExecutor<Acte> {
    Optional<Acte> findActeById(long id);
    List<Acte> findByStatutActe(StatutActe statut);



    Optional<Acte> findActeByReferenceActe(String ref);
    //List<Acte> findByTypeAA_TypeSortieOrTypeAG_TypeSortie(String type, String typea);

   // Page<Acte> findByTypeAA_TypeSortieOrTypeAG_TypeSortie(String type, String typea, Predicate predicate, Pageable pageable);
   // Page<Acte> findByTypeAA_TypeSortieOrTypeAG_TypeSortie( Predicate predicate, Pageable pageable, String type, String typea);

     List<Acte> findByTypeAA_TypeSortieOrTypeAG_TypeSortie(String type, String typea,  Pageable pageable);


   @Query(value = "SELECT acte.* FROM schema_carriere.td_acte as acte inner join schema_carriere.tp_typeaa aa ON aa.typeaa_id = acte.typeaa_id " +
          // " where aa.type_sortie = 'STEM' and acte.acte_dateDebut is not null union " +
           " SELECT acte.* FROM schema_carriere.td_acte as acte inner join schema_carriere.tp_typeag ag ON ag.typeag_id = acte.typeag_id "
           /*" where  ag.type_sortie = 'STEM' and acte.acte_dateDebut is not null "*/, nativeQuery = true)
    Page<Acte> getPageSortieTemporaire(Pageable pageable);

   @Query(value = "SELECT acte.* FROM schema_carriere.td_acte as acte inner join schema_carriere.tp_typeaa aa ON aa.typeaa_id = acte.typeaa_id " +
             " where aa.type_sortie = 'STEM' and acte.acte_dateDebut is not null" +
            " SELECT acte.* FROM schema_carriere.td_acte as acte inner join schema_carriere.tp_typeag ag ON ag.typeag_id = acte.typeag_id "+
            " where  ag.type_sortie = 'STEM' and acte.acte_dateDebut is not null ", nativeQuery = true)
    List<Acte> getAllSortieTemporaire();







//PARTIE STATISTIQUES


    //representant IA


    //ValidRejectAA

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.ia.code = :iaCode " +
            "AND act.typeActe.codeActe = 'aa' AND act.typeAA.code = :codeTypeActe")
    long countValidRejectActesAAIa(@Param("iaCode") String iaCode,@Param("statutCode")String statutCode,@Param("codeTypeActe")  String codeTypeActe);


    //ValidRejectAG
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.ia.code = :iaCode " +
            "AND act.typeActe.codeActe = 'ag' AND act.typeAG.code = :codeTypeActe")
    long countValidRejectActesAGIa(@Param("iaCode") String iaCode,@Param("statutCode")  String statutCode,@Param("codeTypeActe")  String codeTypeActe);


    //InProcess

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code!= 'VALIDEDGCAA'  " +
            "and act.statutActe.code not like  'INVALIDE%' " +
            "and  act.ia.code = :iaCode and ((act.typeActe.codeActe= 'AA' " +
            "and  act.typeAA.code = :codeTypeActe) OR (act.typeActe.codeActe= 'AG' and  act.typeAG.code = :codeTypeActe))")
    long countInProcessActesIa(@Param("iaCode") String iaCode,@Param("codeTypeActe")  String codeTypeActe);

    //All Acte AA Ia
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.ia.code = :iaCode " +
            "and act.typeActe.codeActe= 'aa' and  act.typeAA.code = :codeTypeActe")
    long countAllActesAAIa(@Param("iaCode") String iaCode,@Param("codeTypeActe") String codeTypeActe);

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.ia.code = :iaCode " +
            "and act.typeActe.codeActe= 'ag' and  act.typeAG.code = :codeTypeActe")
    long countAllActesAGIa(@Param("iaCode") String iaCode,@Param("codeTypeActe") String codeTypeActe);




    //representant IEF

    //ValidRejectAA
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.ief.code = :iefCode " +
            "AND act.typeActe.codeActe= 'aa' and  act.typeAA.code = :codeTypeActe")
    long countValidRejectActesAAIef(@Param("iefCode")String iefCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);

    //ValidRejectAG
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.ief.code = :iefCode " +
            "AND act.typeActe.codeActe= 'ag' and  act.typeAG.code = :codeTypeActe")
    long countValidRejectActesAGIef(@Param("iefCode")String iefCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);



    @Query("SELECT COUNT(act) FROM Acte act WHERE act.statutActe.code!= 'VALIDEDGCAA'  and act.statutActe.code not like  'INVALIDE%' and  act.ief.code = :iefCode")
    long countInProcessActesIef(@Param("iefCode") String iefCode);

    //All AA
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.ief.code = :iefCode " +
            " AND act.typeActe.codeActe= 'aa' and  act.typeAA.code = :codeTypeActe")
    long countAllActesAAIef(@Param("iefCode")String iefCode,@Param("codeTypeActe") String codeTypeActe);


    //All AG
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.ief.code = :iefCode " +
            " AND act.typeActe.codeActe= 'ag' and  act.typeAG.code = :codeTypeActe")
    long countAllActesAGIef(@Param("iefCode")String iefCode,@Param("codeTypeActe") String codeTypeActe);



    //chef etablissement

    // AA
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.etablissement.code = :etabCode " +
            "AND act.typeActe.codeActe= 'aa' and  act.typeAA.code = :codeTypeActe")
    long countValidRejectActesAAEtab(@Param("etabCode")String etabCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);

    // AG
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.etablissement.code = :etabCode " +
            "AND act.typeActe.codeActe= 'ag' and  act.typeAG.code = :codeTypeActe")
    long countValidRejectActesAGEtab(@Param("etabCode")String etabCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);

    @Query("SELECT COUNT(act) FROM Acte act WHERE act.statutActe.code!= 'VALIDEDGCAA'  and act.statutActe.code not like  'INVALIDE%' and  act.etablissement.code = :etabCode")
    long countInProcessActesEtab(@Param("etabCode") String etabCode);

    //All AA
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE  act.etablissement.code = :etabCode " +
            "AND act.typeActe.codeActe= 'aa' and  act.typeAA.code = :codeTypeActe")
    long countAllActesAAEtab(@Param("etabCode") String etabCode,@Param("codeTypeActe") String codeTypeActe);


    //All AG
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE  act.etablissement.code = :etabCode " +
            "AND act.typeActe.codeActe= 'ag' and  act.typeAG.code = :codeTypeActe")
    long countAllActesAGEtab(@Param("etabCode") String etabCode,@Param("codeTypeActe") String codeTypeActe);





    //all actes ValideRejectAA

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code LIKE CONCAT(:statutCode ,'%')" +
            "AND act.typeActe.codeActe = 'aa' AND act.typeAA.code = :codeTypeActe ")
    long countValidRejectActesAA( @Param("statutCode") String statutCode,@Param("codeTypeActe") String codeTypeActe);


    //all actes ValideRejectAG

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code LIKE CONCAT(:statutCode ,'%')" +
            "AND act.typeActe.codeActe = 'ag' AND act.typeAG.code = :codeTypeActe ")
    long countValidRejectActesAG( @Param("statutCode") String statutCode,@Param("codeTypeActe") String codeTypeActe);



 //Acte Administartifs encours

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code != 'VALIDEDGCAA' " +
            "AND act.statutActe.code NOT LIKE 'INVALIDE%' " +
            "AND act.typeActe.codeActe = 'aa' AND act.typeAA.code = :codeTypeActe")
    long countAllInProcessActesAA( @Param("codeTypeActe") String codeTypeActe);

    //Acte Gestion encours

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code != 'VALIDEDGCAA' " +
            "AND act.statutActe.code NOT LIKE 'INVALIDE%' " +
            "AND act.typeActe.codeActe = 'ag' AND act.typeAA.code = :codeTypeActe")
    long countAllInProcessActesAG( @Param("codeTypeActe") String codeTypeActe);



    //Liste de tous les actes Administratifs

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe " )
    long countAllActesAA(@Param("codeTypeActe") String codeTypeActe);

    //Liste de tous les actes de Gestion

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe " )
    long countAllActesAG(@Param("codeTypeActe") String codeTypeActe);



    //Liste de tous les actes AA en cours de traitement

    @Query("SELECT act " +
            "FROM Acte act " +
            "WHERE act.statutActe.code!= 'VALIDEDGCAA'  " +
            "AND act.statutActe.code not like  'INVALIDE%' " +
            "AND  act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe")
    List<Acte> listAllInProcessActesAA(@Param("codeTypeActe") String codeTypeActe);




    //chef bureau

    //Actes ValideReject AA


    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "AND act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe " +
            "AND act.bureau.code = :burCode")
    long countValidRejectActesAABureau(@Param("burCode")String burCode, @Param("statutCode")String statutCode,@Param("codeTypeActe")String codeTypeActe);

   //Actes ValideReject AG
 @Query("SELECT COUNT(act) " +
         "FROM Acte act " +
         "WHERE act.statutActe.code  LIKE :statutCode% " +
         "AND act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe " +
         "AND act.bureau.code = :burCode")
 long countValidRejectActesAGBureau(@Param("burCode")String burCode, @Param("statutCode")String statutCode,@Param("codeTypeActe")String codeTypeActe);

    @Query("SELECT COUNT(act) FROM Acte act WHERE act.statutActe.code!= 'VALIDEDGCAA'  and act.statutActe.code not like  'INVALIDE%'  and  act.bureau.code = :burCode")
    long countInProcessActesBureau(@Param("burCode") String burCode);

   //Acte AA Bureau
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE  act.bureau.code = :burCode " +
            "AND act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe")
    long countAllActesAABureau(@Param("burCode")String burCode,@Param("codeTypeActe")String codeTypeActe);


    // Acte AG Bureau

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE  act.bureau.code = :burCode " +
            "AND act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe")
    long countAllActesAGBureau(@Param("burCode")String burCode,@Param("codeTypeActe")String codeTypeActe);



    //chef service

    //ValidRejectAA

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "AND  act.direction.code = :servCode "+
            "AND act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe"
    )
    long countValidRejectActesAAService(@Param("servCode")String servCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);


    //ValidRejectAG

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "AND  act.direction.code = :servCode "+
            "AND act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe"
    )
    long countValidRejectActesAGService(@Param("servCode")String servCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);

    @Query("SELECT COUNT(act) FROM Acte act WHERE act.statutActe.code!= 'VALIDEDGCAA'  and act.statutActe.code not like  'INVALIDE%' and  act.direction.code = :servCode")
    long countInProcessActesService(@Param("servCode") String servCode);

    //All AA Service

    @Query("SELECT COUNT(act)" +
            "FROM Acte act " +
            "WHERE  act.direction.code = :servCode " +
            "AND act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe")
    long countAllActesAAService(@Param("servCode")String servCode,@Param("codeTypeActe") String codeTypeActe);

    //  All AG service

    @Query("SELECT COUNT(act)" +
            "FROM Acte act " +
            "WHERE  act.direction.code = :servCode " +
            "AND act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe")
    long countAllActesAGService(@Param("servCode")String servCode,@Param("codeTypeActe") String codeTypeActe);



    //chef division

    //VALIDREJECETAA

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.division.code = :divCode " +
            "AND act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe ")
    long countValidRejectActesAADivision(@Param("divCode")String divCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE act.statutActe.code  LIKE :statutCode% " +
            "and  act.division.code = :divCode " +
            "AND act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe ")
    long countValidRejectActesAGDivision(@Param("divCode")String divCode, @Param("statutCode")String statutCode,@Param("codeTypeActe") String codeTypeActe);

    @Query("SELECT COUNT(act) FROM Acte act WHERE act.statutActe.code!= 'VALIDEDGCAA'  and act.statutActe.code not like  'INVALIDE%' and  act.division.code = :divCode")
    long countInProcessActesDivision(@Param("divCode") String divCode);


    //ALL AA Division

    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE  act.division.code = :divCode " +
            "AND act.typeActe.codeActe= 'aa' AND act.typeAA.code = :codeTypeActe")
    long countAllActesAADivision(@Param("divCode")String divCode,@Param("codeTypeActe") String codeTypeActe);

    //All AG Division
    @Query("SELECT COUNT(act) " +
            "FROM Acte act " +
            "WHERE  act.division.code = :divCode " +
            "AND act.typeActe.codeActe= 'ag' AND act.typeAG.code = :codeTypeActe")
    long countAllActesAGDivision(@Param("divCode")String divCode,@Param("codeTypeActe") String codeTypeActe);

    //Liste actes encours de traitemeent Division








    //Statistiques Sorties

 //Chef-Etablissement

 @Query("SELECT COUNT(act) FROM Acte act WHERE " +
         "((act.typeActe.codeActe = 'aa' AND act.typeAA.typeSortie = :typeSortie) OR " +
         "(act.typeActe.codeActe = 'ag' AND act.typeAG.typeSortie = :typeSortie)) AND " +
         "act.etablissement.code = :etabCode")
 long countSortiesEtablissement(@Param("etabCode") String etabCode, @Param("typeSortie") String typeSortie);



//Representant-Ia


 @Query("SELECT COUNT(act) FROM Acte act WHERE " +
         "((act.typeActe.codeActe = 'aa' AND act.typeAA.typeSortie = :typeSortie) OR " +
         " (act.typeActe.codeActe = 'ag' AND act.typeAG.typeSortie = :typeSortie)) AND " +
         "act.ia.code = :iaCode")
 long countSortiesIa(@Param("iaCode") String iaCode, @Param("typeSortie") String typeSortie);

 //Representant-IEF

 @Query("SELECT COUNT(act) FROM Acte act WHERE ((act.typeActe.codeActe= 'aa' and" + " act.typeAA.typeSortie= :typeSortie) or "+
         " (act.typeActe.codeActe= 'ag' and" + " act.typeAG.typeSortie= :typeSortie)) and "+
         "  act.ief.code = :iefcode")
 long countSortiesIef(@Param("iefcode") String iefcode,@Param("typeSortie")  String typeSortie);


//Chef-Division
@Query("SELECT COUNT(act) FROM Acte act WHERE " +
        "((act.typeActe.codeActe = 'AA' AND act.typeAA.typeSortie = :typeSortie) OR " +
        "(act.typeActe.codeActe = 'AG' AND act.typeAG.typeSortie = :typeSortie)) AND " +
        "act.division.code = :divCode")
long countSortiesDivision(@Param("divCode") String divCode, @Param("typeSortie") String typeSortie);


 //Chef-Bureau
 @Query("SELECT COUNT(act) FROM Acte act WHERE ((act.typeActe.codeActe= 'aa' and" + " act.typeAA.typeSortie= :typeSortie) or "+
         " (act.typeActe.codeActe= 'ag' and" + " act.typeAG.typeSortie= :typeSortie)) and "+
         "  act.bureau.code = :burCode")
 long countSortiesBureau(@Param("burCode") String burCode,@Param("typeSortie")  String typeSortie);



 //Chef-Service
 @Query("SELECT COUNT(act) FROM Acte act WHERE ((act.typeActe.codeActe= 'aa' and" + " act.typeAA.typeSortie= :typeSortie) or "+
         " (act.typeActe.codeActe= 'ag' and" + " act.typeAG.typeSortie= :typeSortie)) and "+
         "  act.direction.code = :serCode")
 long countSortiesService(@Param("serCode") String serCode,@Param("typeSortie")  String typeSortie);


 //DRH et Chef DGCAA

 @Query("SELECT COUNT(act) FROM Acte act WHERE " +
         "(act.typeActe.codeActe = 'AA' AND act.typeAA.typeSortie = :typeSortie) OR " +
         "(act.typeActe.codeActe = 'AG' AND act.typeAG.typeSortie = :typeSortie)")
 long countAllSorties(@Param("typeSortie") String typeSortie);


}
