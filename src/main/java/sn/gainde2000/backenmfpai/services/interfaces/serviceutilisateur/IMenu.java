package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ProfileMenuSousMenu;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

public interface IMenu {
    Response<Object> attribuerMenu(ProfileMenuSousMenu profileMenuSousMenu);
}
