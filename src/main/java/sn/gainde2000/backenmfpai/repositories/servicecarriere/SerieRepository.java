package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Serie;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 09/05/2024-10:59
 * @project backend_mfpai
 */
public interface SerieRepository extends JpaRepository<Serie, Long> , QuerydslPredicateExecutor<Serie> {

    List<Serie> findByFormationProfessionel_Code(String code);
}
