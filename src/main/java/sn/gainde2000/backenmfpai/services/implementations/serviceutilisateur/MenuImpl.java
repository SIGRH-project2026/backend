package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Menu;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ProfileMenuSousMenu;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IMenuRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.ProfileMenuSOusMenuRepo;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IMenu;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuImpl  implements IMenu {

    private final ProfileMenuSOusMenuRepo profileMenuSOusMenuRepo;

    @Override
    public Response<Object> attribuerMenu(ProfileMenuSousMenu profileMenuSousMenu) {
        /*try {
            try {*/
               // ProfileMenuSousMenu profileMenuSousMenu1 = profileMenuSOusMenuRepo.findProfileMenuSousMenuByProfileIdAndMenuMenId(profileMenuSousMenu.getProfile().getId(), profileMenuSousMenu.getMenu().getMenId());
//                Set<Menu> sousMenus = profileMenuSousMenu1.getChildren();
//                for (Menu menu : profileMenuSousMenu.getChildren()) {
//                    sousMenus.add(menu);
//                }
   //             profileMenuSousMenu1.setChildren(sousMenus);
                return Response.ok().setPayload(profileMenuSOusMenuRepo.save(profileMenuSousMenu)).setMessage("attirbution par modification");
        /*    }catch (Exception e) {
                return Response.ok().setPayload(profileMenuSOusMenuRepo.save(profileMenuSousMenu)).setMessage("Attribution menus à un nouveau profile");
            }
        }catch (Exception e) {
           return Response.exception().setMessage("Une erreur est survenue");
        }*/
    }

    public ArrayList<Menu> listMenuByProfile( Long proId) {
        List<Object[]> results = profileMenuSOusMenuRepo.findMenusAndSousMenusByProfileId(proId);
        Map<Long, Menu> menuMap = new HashMap<>();
        for (Object[] result : results) {
            Long menuId = (Long) result[0];
            Menu menu = menuMap.get(menuId);
            if (menu == null) {
                menu = new Menu();
                menu.setMenId((Long) result[0]);
                menu.setMenPath((String) result[1]);
                menu.setMenTitle((String) result[2]);
                menu.setMenType((String) result[3]);
                menu.setMenIconType((String) result[4]);
                menuMap.put(menuId, menu);
            }

            if (result[5] != null) {
                Menu sousMenu = new Menu();
                sousMenu.setMenId((Long) result[5]);
                sousMenu.setMenPath((String) result[6]);
                sousMenu.setMenTitle((String) result[7]);
                sousMenu.setMenType((String) result[8]);
                sousMenu.setMenIconType((String) result[9]);
                menu.getChildren().add(sousMenu);
            }
        }
        return new ArrayList<Menu>(menuMap.values());
    }
}
