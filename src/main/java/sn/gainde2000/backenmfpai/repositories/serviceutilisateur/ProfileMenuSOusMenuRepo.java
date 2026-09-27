package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ProfileMenuSousMenu;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;

public interface ProfileMenuSOusMenuRepo  extends JpaRepository<ProfileMenuSousMenu, Long> {
  //  ProfileMenuSousMenu findProfileMenuSousMenuByProfileIdAndMenuId(Long profileId, Long menuId);

    @Query(value = "SELECT m.menu_id AS menId, m.men_path AS menPath, m.men_ytitle AS menTitle, m.men_type AS menType, m.men_iconType AS menIconType, " +
            "sm.menu_id AS sousMenId, sm.men_path AS sousMenPath, sm.men_ytitle AS sousMenTitle, sm.men_type AS sousMenType, sm.men_iconType AS sousMenIconType " +
            "FROM schema_utilisateur.TP_Menu m " +
            "LEFT JOIN schema_utilisateur.TR_ProfileMenuSousMenu pmsm ON m.menu_id = pmsm.Men_id " +
            "LEFT JOIN schema_utilisateur.TP_Menu sm ON sm.menu_id = ANY(pmsm.Smn_id) " +
            "WHERE pmsm.Profile_id = ?1", nativeQuery = true)
    List<Object[]> findMenusAndSousMenusByProfileId(Long profileId);

}
