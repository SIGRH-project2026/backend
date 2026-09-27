package sn.gainde2000.backenmfpai.web.dtos.responses.autentification;


import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;

import java.util.Set;

/**
 * @author G2k R&D
 */

public record UtilisateurInfo(Long id, String email, String matricule, String prenom, String nom, Set<Profile> profil, boolean status) {
}
