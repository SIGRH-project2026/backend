
package sn.gainde2000.backenmfpai.mappers.serviceutilisateur;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Menu;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.MenuDTO;

/**
 * @author G2k R&D
 */

@Mapper
public interface IMenuMapper {
    MenuDTO menuToMenuDTO(Menu menu);

    Menu menuDtoToMenu(MenuDTO menuDTO);
}

