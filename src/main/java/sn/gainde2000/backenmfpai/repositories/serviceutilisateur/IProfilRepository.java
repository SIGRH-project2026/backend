package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;

import java.util.List;
import java.util.Optional;


/**
 * @author G2k R&D
 */

@Repository
public interface IProfilRepository extends JpaRepository<Profile, Long>, QuerydslPredicateExecutor<Profile> {

    Optional<Profile> findByCode(String code);

    List<Profile> findByTypeProfileDivision(String divion);

    List<Profile> findByTypeProfileBureau(String divion);
    List<Profile> findByTypeProfile(String type);

    List<Profile> findByTypeProfileDirection(String divion);
    Profile findProfileByCode(String code);

    List<Profile> findAllByCodeIgnoreCaseOrderByIdDesc(String code);
}
