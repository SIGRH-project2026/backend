package sn.gainde2000.backenmfpai.web.controllers.serviceutilisateur.auth;

import
        io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IAuthentification;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.EditMonCompteDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ActivateAccountDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ResetOrForgetFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.auth.LoginRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.auth.TokenRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.auth.LogOutResponse;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.auth.LoginResponseDTO;

/**
 * @author G2k R&D
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Data
public class AuthentificationController {
    private final IAuthentification iAuthentification;

   private   final PasswordEncoder encoder;


    @PostMapping("/login")
    public Response<Object> authenticateUser(@Valid @RequestBody LoginFormDTO loginRequest) {
        return iAuthentification.singIn(loginRequest);
    }

    @Operation(summary = "Activer son compte avec son matricule et le mot de passe par défaut")
    @PostMapping("/activate-account")
    public Response<Object> activateAccount(@Valid @RequestBody ActivateAccountDTO form) {
        return iAuthentification.activateAccount(form);
    }

    @GetMapping("/refresh-token")
    public Response<Object> refreshToken(@RequestParam String token) {
        return iAuthentification.refreshToken(token);
    }

    @PostMapping("/refresh")
    public Response<Object> refreshToken(@RequestBody TokenRequest tokenRequest) {
        return iAuthentification.refreshToken(tokenRequest.token());
    }

    @Operation(summary = "Endpoint pour s'authentifier à partir d'un lien mail pour une première connexion")
    @PostMapping("/signin-with-url-connexion")
    public Response<Object> authenticateUserWithFirstUrlConnexion(@Valid @RequestBody ResetOrForgetFormDTO formRequest) {

         return iAuthentification.authenticateUserWithFirstUrlConnexion(formRequest);
    }

    @Operation(summary = "Endpoint pour s'authentifier à partir d'un lien mail apres mot de passe oublié")
    @PostMapping("/signin-with-forget-password-url-connexion")
    public Response<Object> authenticateUserWithForgetPasswordUrlConnexion(@Valid @RequestBody ResetOrForgetFormDTO formRequest) {
        return iAuthentification.authenticateUserWithForgetPasswordUrlConnexion(formRequest);
    }

    @Operation(summary = "Endpoint envoie mail pour reinitialiser le mot de passe oublié")
    @GetMapping("/forgot-password")
    public Response<Object> forgotPassword(@RequestParam("login") String login) {
        return iAuthentification.reinitPassword(login);
    }

// /*   @Operation(summary = "Endpoint pour modifier les informations d'un utilisateur")
//    @PostMapping("/edit-user-infos")
//    public Response<Object> editUserInfos(@Valid @RequestBody EditMonCompteDTO req) {
//        return iAuthentification.editUserInfos(req);
//    }*/

    @Operation(summary = "Endpoint pour modifier le mot de passe d'un utilisateur")
    @PostMapping("/edit-user-password")
    public Response<Object> editUserPassword(@Valid @RequestBody ResetOrForgetFormDTO req) {
        return iAuthentification.updatePasswordFromInterface(req);
    }


}
