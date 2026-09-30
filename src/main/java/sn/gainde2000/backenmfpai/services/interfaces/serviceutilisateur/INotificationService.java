package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;

/**
 * @author G2k R&D
 */

public interface INotificationService {
   void sendNotificationDossierCreated(String email);

   void sendNotificationToNewUserRegistred(LoginFormDTO loginFormDTO, String action);

   /**
    * Variante non bloquante (envoi asynchrone) de {@link #sendNotificationToNewUserRegistred},
    * utilisée notamment lors des imports en masse pour ne pas ralentir le traitement
    * en cas de lenteur/indisponibilité du serveur SMTP.
    */
   void sendNotificationToNewUserRegistredAsync(LoginFormDTO loginFormDTO, String action);

   void sendAccountActivationInstructions(String email, String matricule, String defaultPassword);


 void sendNotificationMailOS(LoginFormDTO loginFormDTO, String file);

 void sendNotificationToNewUserRegistredByAdmin(LoginFormDTO loginFormDTO, String action);
    void sendNotificationToUserEdited(LoginFormDTO loginFormDTO, String action);
    void sendNotificationToUserForgetPassword(LoginFormDTO loginFormDTO, String action);
    void sendEmail(MailInfosDTO mailInfosDTO);
    void sendNotificationStatut(Utilisateur utilisateur);

    /**
     * Notifie par email un agent dont le compte est déjà activé que ses
     * informations viennent d'être complétées/modifiées par un administrateur.
     * Ne doit être appelée que si le compte est déjà activé (premier login
     * déjà effectué) : un compte pas encore activé n'a pas besoin de cette
     * notification, il recevra/a déjà reçu le lien d'activation initial.
     * Envoi asynchrone pour ne pas ralentir la requête de mise à jour.
     */
    void sendNotificationProfilMisAJourParAdmin(String email, String prenom);

    /*
    * Permutation : envoie notification par email au demandeur */
    void sendNotificationDemandeurPermutation(LoginFormDTO loginFormDTO, String traitant, long id, String statut);


 void sendEmailOS(MailInfosDTO mailInfosDTO, String os);
}
