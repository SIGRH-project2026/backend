package sn.gainde2000.backenmfpai.web.dtos.requests.authentification;


import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;


/**
 * @author G2k R&D
 */

public record UtilisateurInfo(long id, String email, Profile profil, boolean status) {
}
