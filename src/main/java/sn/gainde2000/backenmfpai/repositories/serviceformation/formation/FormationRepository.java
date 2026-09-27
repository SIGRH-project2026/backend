package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;

public interface FormationRepository extends JpaRepository<Formation, Long>, QuerydslPredicateExecutor<Formation> {

    @Query("SELECT COUNT(t) FROM Formation t WHERE t.statutFormation.code = :statusCode")
    long countByStatus(@Param("statusCode") String statusCode);

    Optional<Formation> findByReference(String reference);
/*
    @Query("""
          SELECT f FROM Formation f
                 inner join  TypeFormation tf ON f.typeFormation.id = tf.id
                 where tf.code = :typeFormation
        """)
    Page<Formation> getFormtionDiplomante(@Param("typeFormation") String typeFormation, Predicate predicate, Pageable pageable);
*/

    @Query("""
          SELECT f FROM Formation f
                 inner join  TypeFormation tf ON f.typeFormation.id = tf.id
                 where tf.code = :typeFormation
        """)
    Page<Formation> getFormtionDiplomante(@Param("typeFormation") String typeFormation,  Pageable pageable);

    @Query("""
          SELECT f FROM Formation f
                 inner join  TypeFormation tf ON f.typeFormation.id = tf.id
                 where tf.code = :typeFormation 
                 AND f.statutFormation.code = :statusCode1
                 OR f.statutFormation.code = :statusCode2                         
        """)
    Page<Formation> getFormtionByTypeFormationPublisher(@Param("typeFormation") String typeFormation, @Param("statusCode1") String statusCode1,  @Param("statusCode2") String statusCode2,  Pageable pageable);


    @Query("""
          SELECT f FROM Formation f
                 inner join  TypeFormation tf ON f.typeFormation.id = tf.id
                 where tf.code = :typeFormation
        """)
    List<Formation> getFormtionDiplomanteList(@Param("typeFormation") String typeFormation,   Pageable pageable);

    List<Formation> findByThemeFormation(ThemeFormation themeFormation);
}