package sn.gainde2000.backenmfpai.web.controllers.serviceutilisateur;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Menu;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ProfileMenuSousMenu;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.ProfileMenuSOusMenuRepo;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IMenu;

import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
@Tag(name = "Gestion menu Controller", description = "Permet de gérer les menus")
public class MenuController {
    private final IMenu iMenu;
    private final ProfileMenuSOusMenuRepo profileMenuSOusMenuRepo;
    @PostMapping("/create")
    @Operation(description = "Endpoint d'attribution de menus à un profil'.")
    public Response<Object> attribuerMenu(@RequestBody ProfileMenuSousMenu profileMenuSousMenu) {
        return iMenu.attribuerMenu(profileMenuSousMenu);
    }
    @GetMapping("/listByProfile/{proId}")
    @Operation(description = "Endpoint de récupération'.")
    public ArrayList<Menu> listByProfile(@PathVariable Long proId) {
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
