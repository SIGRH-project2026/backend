package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UtilisateurResponseDTO {
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private String adresse;
    private String matricule;
    private String telephone;
    private String sexe;
    private Fonction fonction;
    private Services service;
    private Direction direction;
    private Set<Profile> profils = new HashSet<>();
//    private boolean canShowPiecesJointe = false;

}
