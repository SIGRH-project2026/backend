
//package sn.gainde2000.backenmfpai.repositories;
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-12:36
 * @project backend_mfpai
 */
public interface CentralLevelRepository
        extends JpaRepository<CentralLevel, Long>, QuerydslPredicateExecutor<CentralLevel> {
    Optional<CentralLevel> findByEmail(String email);

    List<CentralLevel> findByDirectionId(Long directionId);
    List<CentralLevel> findByDivision_Code(String code);

    List<CentralLevel> findByService_Code(String code);

    List<CentralLevel> findByDirection_Code(String code);

    List<CentralLevel>  findCentralLevelByProfilsContains(Profile profil);


}
