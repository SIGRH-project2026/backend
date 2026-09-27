package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.FormationProfessionel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Niveau;

import java.util.List;


/**
 * @author Abdou Karim CISSOKHO
 * @created 09/05/2024-11:01
 * @project backend_mfpai
 */
public interface NiveauRepository extends JpaRepository<Niveau, Long>, QuerydslPredicateExecutor<Niveau> {

    List<Niveau> findByFormationProfessionel_Code(String code);
}
