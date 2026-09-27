package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Menu;


/**
 * @author G2k R&D
 */

@Repository
public interface IMenuRepository extends JpaRepository<Menu, Long> {
   // ProfileMenuSousMenu findProfileMenuSousMenuByProfileIdAndMenId(Long profileId, Long menuId);
}
