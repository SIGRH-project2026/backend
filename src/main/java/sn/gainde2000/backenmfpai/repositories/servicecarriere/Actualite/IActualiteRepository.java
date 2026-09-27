package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.actualite.Actualite;

import java.util.List;

public interface IActualiteRepository extends JpaRepository<Actualite, Long>, QuerydslPredicateExecutor<Actualite> {

    @Query("SELECT actu FROM Actualite actu where actu.typeArticle.code = :typeArticle And actu.isActivated = true and actu.isDeleted= false order by actu.id desc LIMIT 3")
    List<Actualite> findByTypeArticle(@Param("typeArticle") String typeArticle);

    @Query("SELECT actu FROM Actualite actu where actu.isActivated = true and actu.isDeleted= false order by actu.id desc LIMIT 3")
    List<Actualite> findLastActu();
}
