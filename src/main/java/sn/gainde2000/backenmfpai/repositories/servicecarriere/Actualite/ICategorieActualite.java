package sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.actualite.CategorieActualite;

public interface ICategorieActualite extends JpaRepository<CategorieActualite, Long>, QuerydslPredicateExecutor<CategorieActualite> {
}
