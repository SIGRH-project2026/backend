
package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.UserManager;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UserManagerRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.CentralLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DeconcentratedLevelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.DuplicateMatriculeReportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.UserImportResultDTO;

/**
 * @author G2k R&D
 */

public interface IUtilisateur {

        // Page<Utilisateur> getPageUsers(int page, int size, String sortBy, boolean
        // sortByDescending);

        long getAllUsersCount();

        CentralLevel saveUtilisateurCL(CentralLevelDTO dto);

        CentralLevel saveUtilisateurCLBis(CentralLevelDTO dto);

        CentralLevel updateUtilisateurCL(Long id, CentralLevelDTO dto);

        Page<CentralLevel> getPageCentral(int page, int size, String sortBy, boolean sortByDescending);

        Response<Object> getPageCentralWithFilter(int page, int size, String filter);

        Response<Object> getPageCentralWithFilterAdvanced(int page, int size, String filter, String profile,
                        String matricule, String prenom, String nom, String direction);

        Response<Object> getPageDeconectedWithFilterAdvanced(int page, int size, String filter, String profile,
                        String matricule, String prenom, String nom, String region, String ia, String ief, String typeSystemeEnseignement, String etablissement); 

        Response<Object> getPageDeconectedWithFilter(int page, int size, String filter);

        CentralLevel getUserCentral(Long id);

       // boolean getPrioritaires(int n);

        DeconcentratedLevel getUserDeconected(Long id);

        Page<DeconcentratedLevel> getPageDecocentred(int page, int size, String sortBy, boolean sortByDescending);

        DeconcentratedLevel saveUtilisateurDL(DeconcentratedLevelDTO dto);

        /**
         * Import en masse d'utilisateurs de niveau déconcentré à partir d'un
         * fichier Excel (.xlsx/.xls) ou CSV. L'import est tolérant : les champs
         * manquants sont ignorés (seul le nom est requis pour identifier la ligne)
         * et chaque ligne est traitée indépendamment.
         *
         * @param file fichier téléversé
         * @return récapitulatif de l'import (lignes importées / en erreur)
         */
        UserImportResultDTO importUtilisateursDL(MultipartFile file);

        /**
         * Import en masse d'utilisateurs de niveau central à partir d'un
         * fichier Excel (.xlsx/.xls) ou CSV. Comme pour le niveau déconcentré,
         * l'import est tolérant : les champs manquants sont ignorés, les doublons
         * (matricule/email/CNI) sont bloqués ligne par ligne et chaque ligne est
         * traitée indépendamment.
         *
         * @param file fichier téléversé
         * @return récapitulatif de l'import (lignes importées / en erreur)
         */
        UserImportResultDTO importUtilisateursCL(MultipartFile file);

        /**
         * Détecte les matricules en doublon (niveau déconcentré) sans rien supprimer.
         */
        DuplicateMatriculeReportDTO findDuplicateMatriculesDL();

        /**
         * Supprime les doublons de matricule (niveau déconcentré) en conservant,
         * pour chaque matricule, l'enregistrement le plus ancien (id le plus petit).
         */
        DuplicateMatriculeReportDTO removeDuplicateMatriculesDL();

        DeconcentratedLevel updateUtilisateurDL(Long id, DeconcentratedLevelDTO dto);

        UserManager saveUtilisateurManager(UserManagerRequestDTO dto, boolean b);

        Utilisateur getCurrentUser();

        Utilisateur getUserId(Long id);

        Utilisateur changeStatut(Long id);

        List<CentralLevel> getUsersByDirection(String code);

        List<Utilisateur> listUtilisateurByCodeProfile(String code);

        Response<Object> getPageCentralAdvanced(int page, int size, String region, String direction,
                        String division, String bureau, String specialite, String corps, String grade, String matricule,
                        String prenom, String nom, String dateNaissance,
                        String cni, String telephone, String email);

        Response<Object> getPageDecoAdvanced(int page, int size, String region, String ia, String ief,
                        String etablissement, String typeSystemeEnseignement, String specialite, String corps, String grade, String matricule,
                        String prenom, String nom, String dateNaissance,
                        String cni, String telephone, String email);

        Response<Object> getPersonnels(int page, int size, String region, String structureCode, String iaCode,
                        String iefCode, String etablissementCode);

    /* COK*/
    List<DeconcentratedLevel> getPrioritaires(int n);

    boolean getPrioritaire(int n, String speciality);

    Utilisateur searchUser(String filter);

    Utilisateur switchUserType(Long userId);

    // ⬇️⬇️⬇️ AJOUTEZ CES NOUVELLES MÉTHODES ⬇️⬇️⬇️
    
    /**
     * Récupère la liste du personnel du niveau central avec filtres
     * @param page numéro de la page
     * @param size taille de la page
     * @param directionCode code de la direction
     * @param serviceCode code du service
     * @param divisionCode code de la division
     * @param bureauCode code du bureau
     * @return Response contenant la liste paginée du personnel central
     */
    Response<Object> getPersonnelsNiveauCentral(int page, int size, String directionCode, 
                                                  String serviceCode, String divisionCode, 
                                                  String bureauCode);
    
    /**
     * Récupère toutes les directions (pour le select dans le frontend)
     * @return Response contenant la liste des directions
     */
    Response<List<Direction>> getAllDirections();
    
    /**
     * Récupère les services d'une direction spécifique
     * @param directionCode code de la direction
     * @return Response contenant la liste des services
     */
    Response<List<Services>> getServicesByDirection(String directionCode);
    
    /**
     * Récupère les divisions d'une direction spécifique
     * @param directionCode code de la direction
     * @return Response contenant la liste des divisions
     */
    Response<List<Division>> getDivisionsByDirection(String directionCode);
    
    /**
     * Récupère les bureaux d'une division spécifique
     * @param divisionCode code de la division
     * @return Response contenant la liste des bureaux
     */
    Response<List<Bureau>> getBureausByDivision(String divisionCode);
}

