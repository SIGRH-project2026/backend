package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur;


import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ActivateAccountDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ResetOrForgetFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UserManagerRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

/**
 * @author G2k R&D
 */

public interface IAuthentification {

 //  Response<Object> registreUtilisateur(UtilistateurDTO utilisateurDTO);

    Response<Object> saveUtilisateurManager(UserManagerRequestDTO utilisateurDTO, boolean isRegister);
    Response<Object> singIn(LoginFormDTO loginFormDTO);
    Response<Object> activateAccount(ActivateAccountDTO form);
    Response<Object> refreshToken(String token);
    Response<Object> authenticateUserWithFirstUrlConnexion(ResetOrForgetFormDTO formRequest);
    Response<Object> authenticateUserWithForgetPasswordUrlConnexion(ResetOrForgetFormDTO formRequest);
    Response<Object> reinitPassword(String login);
  //  Response<Object> editUserInfos(EditMonCompteDTO req);
    Response<Object> updatePasswordFromInterface(ResetOrForgetFormDTO form);

    Utilisateur getCurrentConnectedUser();


}
