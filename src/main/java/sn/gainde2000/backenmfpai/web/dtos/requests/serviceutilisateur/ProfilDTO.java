package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur;


import java.util.Set;

/**
 * @author G2k R&D
 */

public record ProfilDTO(Long id, String code, String label, Set<MenuDTO> menu) {
}

