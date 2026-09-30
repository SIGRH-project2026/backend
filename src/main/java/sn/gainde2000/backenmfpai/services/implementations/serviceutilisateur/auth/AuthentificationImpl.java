package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sn.gainde2000.backenmfpai.commons.utils.UtilityClass;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nTranslate;
import sn.gainde2000.backenmfpai.commons.utils.password.PasswordGenerator;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.UserManager;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.UserManagerMapper;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.security.jwt.JwtProvider;
import sn.gainde2000.backenmfpai.security.services.LoginAttemptService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IAuthentification;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.INotificationService;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ActivateAccountDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.ResetOrForgetFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UserManagerRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UtilisateurDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.Status;
import sn.gainde2000.backenmfpai.web.dtos.responses.autentification.JwtDTO;

import java.util.Optional;
import java.util.HashSet;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;
import static sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur.UtilisateurImpl.FIRST_CONNEXION;

/**
 * @author G2k R&D
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthentificationImpl implements IAuthentification {

    private final AuthenticationManager authenticationManager;
    // private final UtilisateurImpl utilisateurService;

    private final JwtProvider jwtProvider;
    private final I18nTranslate i18nTranslat;
    private final LoginAttemptService loginAttemptService;
    private final IUtilisateurRepository utilisateurRepository;
    private final IProfilRepository profilRepository;
    private final PasswordEncoder encoder;
    private final INotificationService notificationService;
    private final UserManagerMapper userManagerMapper;
    public static final String BEARER = "Bearer";
    public static final String REFRESH_TOKEN = "Refresh token";
    private static final String RESET_PASSWORD = "RESET_PASSWORD";
    private static final String DEFAULT_AGENT_PROFILE = "Agent";

    @Transactional
    @Override
    public Response<Object> saveUtilisateurManager(UserManagerRequestDTO utilisateurDTO, boolean isRegister) {

        Optional<Utilisateur> utilisateurExisting = utilisateurRepository
                .findUtilisateurByEmail(utilisateurDTO.getEmail());
        if (utilisateurExisting.isPresent()) {
            throw new MFPAIException(i18nTranslat.toTranslate(EMAIL_DEJA_UTILISE));
        }
        UserManager utilisateur = userManagerMapper.toEntity(utilisateurDTO);
        String password = PasswordGenerator.generateRandomString();
        utilisateur.setPassword(password);

        utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));
        utilisateur.setFirstLog(true);
        utilisateur.setStatus(true);
        utilisateurRepository.saveAndFlush(utilisateur);
        if (isRegister) {
            notificationService.sendNotificationToNewUserRegistred(new LoginFormDTO(utilisateur.getEmail(), password),
                    FIRST_CONNEXION);
        } else {
            notificationService.sendNotificationToNewUserRegistredByAdmin(
                    new LoginFormDTO(utilisateur.getEmail(), password), FIRST_CONNEXION);
        }

        return Response.ok().setPayload(userManagerMapper.toDto(utilisateur))
                .setMessage(i18nTranslat.toTranslate(UTILISATEUR_RECEIVE_EMAIL));
    }

    @Override
    public Response<Object> singIn(LoginFormDTO loginFormDTO) {
        Authentication authentication;
        String identifier = loginFormDTO.login().trim();
        try {

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(identifier, loginFormDTO.password()));
            // System.out.println("log "+ authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String jwt = jwtProvider.generateToken(authentication);
            String refreshToken = jwtProvider.generateRefreshToken(jwt);
            JwtDTO response = new JwtDTO(userDetails.getUsername(), jwt, refreshToken, BEARER);
            loginAttemptService.loginSucceeded(identifier);
            return Response.ok().setPayload(response).setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_CORRECT));
        } catch (BadCredentialsException e) {
            loginAttemptService.loginFailed(identifier);
            return Response.wrongCredentials().setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_INCORRECT));
        }
    }

    @Override
    @Transactional
    public Response<Object> activateAccount(ActivateAccountDTO form) {
        Optional<Utilisateur> optionalUser = utilisateurRepository
                .findUtilisateurByNormalizedMatricule(form.matricule().trim());
        if (optionalUser.isEmpty()) {
            return Response.wrongCredentials().setMessage("Matricule ou email incorrect");
        }

        Utilisateur user = optionalUser.get();
        // Un compte importé est volontairement inactif tant que son propriétaire
        // n'a pas terminé sa première activation. Un compte déjà activé puis
        // désactivé par un administrateur reste, lui, bloqué.
        if (Boolean.FALSE.equals(user.getStatus()) && Boolean.FALSE.equals(user.getFirstLog())) {
            return Response.disabledAccount().setMessage("Ce compte est désactivé. Veuillez contacter l'administrateur");
        }
        if (Boolean.FALSE.equals(user.getFirstLog())) {
            return Response.badRequest().setMessage("Ce compte est déjà activé");
        }
        String activationEmail = form.email().trim();
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            Optional<Utilisateur> userWithSameEmail = utilisateurRepository
                    .findUtilisateurByEmailIgnoreCase(activationEmail);
            if (userWithSameEmail.isPresent()
                    && !userWithSameEmail.get().getId().equals(user.getId())) {
                return Response.badRequest().setMessage("Cette adresse email est déjà utilisée");
            }
            user.setEmail(activationEmail);
        } else if (!user.getEmail().trim().equalsIgnoreCase(activationEmail)) {
            return Response.badRequest().setMessage(
                    "Une adresse email est déjà associée à ce dossier. Veuillez contacter l'administrateur");
        }

        // Un administrateur peut avoir complété le dossier et attribué le
        // profil métier avant que l'agent n'active son compte. Ne jamais écraser
        // cette attribution pendant l'activation. Le profil Agent reste
        // uniquement la valeur par défaut des imports sans profil renseigné.
        if (user.getProfils() == null || user.getProfils().isEmpty()) {
            Profile agentProfile = profilRepository.findByCode(DEFAULT_AGENT_PROFILE)
                    .orElseThrow(() -> new MFPAIException(
                            "Le profil Agent requis pour l'activation n'est pas configuré"));
            user.setProfils(new HashSet<>());
            user.getProfils().add(agentProfile);
        }
        String defaultPassword = PasswordGenerator.generateRandomString();
        user.setPassword(encoder.encode(defaultPassword));
        user.setFirstLog(true);
        user.setStatus(true);
        utilisateurRepository.save(user);
        notificationService.sendAccountActivationInstructions(
                user.getEmail().trim(), user.getMatricule(), defaultPassword);
        return Response.ok().setMessage(
                "Un email contenant votre mot de passe provisoire vous a été envoyé");
    }

    @Override
    public Response<Object> refreshToken(String token) {
        if (!jwtProvider.validationRefreshToken(token)) {
            throw new MFPAIException(MFPAIMessage.CONNEXION_TOKEN_INVALIDE);
        }
        String accessToken = jwtProvider.generateAccessTokenFromRefreshToken(token);
        JwtDTO response = new JwtDTO(jwtProvider.getUserNameFromJwtToken(token), accessToken, token, BEARER);
        return Response.ok().setPayload(response).setMessage(REFRESH_TOKEN);
    }

    @Override
    @Transactional
    public Response<Object> authenticateUserWithFirstUrlConnexion(ResetOrForgetFormDTO formRequest) {
        if (new UtilityClass.PasswordUtility().validate(formRequest.newPassword())) {

        try {

                Optional<Utilisateur> resp = utilisateurRepository.findUtilisateurByEmail(formRequest.login());
                if (resp.isEmpty())
                    return Response.notFound()
                            .setMessage(i18nTranslat.toTranslate(UTILISATEUR_ABSENT) + " : " + formRequest.login());

                Utilisateur user = resp.get();
                if (Boolean.FALSE.equals(user.getFirstLog()))
                    return Response.badRequest().setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_RESET_FAIT));

                if (Boolean.FALSE.equals(user.getStatus()))
                    return Response.disabledAccount()
                            .setMessage(i18nTranslat.toTranslate(I18nKeys.CONNEXION_LOGIN_TENTATIVE));

                // System.out.println(formRequest);
                Response<Object> response = updatePassword(formRequest);
                // System.out.println("statu"+ response.getStatus());
                if (response.getStatus().equals(Status.OK)) {
                    // System.out.println("ici");
                    LoginFormDTO loginForm = new LoginFormDTO(formRequest.login(), formRequest.newPassword());

                    return singIn(loginForm);
                }
                return response;
            } catch(BadCredentialsException e){
                loginAttemptService.loginFailed(formRequest.login());
                return Response.wrongCredentials().setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_INCORRECT));
            }
        }

        throw new MFPAIException(MFPAIMessage.NON_STRONG_PASSWORD);
    }

    @Override
    @Transactional
    public Response<Object> authenticateUserWithForgetPasswordUrlConnexion(ResetOrForgetFormDTO formRequest) {

        if(!formRequest.newPassword().equals(formRequest.passwordConfirmed())) {
            throw new MFPAIException(MFPAIMessage.NON_MATCH_PASSWORD_CONFIRMED);
        }
        if (new UtilityClass.PasswordUtility().validate(formRequest.newPassword())) {
            Optional<Utilisateur> resp = utilisateurRepository.findUtilisateurByEmail(formRequest.login());
            if (resp.isEmpty())
                Response.notFound().setMessage(i18nTranslat.toTranslate(UTILISATEUR_ABSENT) + " : " + formRequest.login());
            Response<Object> response = updatePassword(formRequest);

            if (response.getStatus().equals(Status.OK)) {
                LoginFormDTO loginForm = new LoginFormDTO(formRequest.login(), formRequest.newPassword());

                // notificationService.sendNotificationToUserForgetPassword(new
                // LoginFormDTO(loginForm.login(), loginForm.password()), RESET_PASSWORD);
                return singIn(loginForm);
            }

            return response;
        }
        throw new MFPAIException(MFPAIMessage.NON_STRONG_PASSWORD);
    }

    @Override
    public Response<Object> reinitPassword(String login) {

        Optional<Utilisateur> resp = utilisateurRepository.findUtilisateurByEmail(login);
        if (resp.isPresent()) {
            Utilisateur utilisateur = resp.get();
            notificationService.sendNotificationToUserForgetPassword(
                    new LoginFormDTO(utilisateur.getEmail(), utilisateur.getPassword()), RESET_PASSWORD);
            return Response.ok().setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_RESET))
                    .setPayload(UtilisateurDTO.toDto(utilisateur));
        }
        return Response.notFound().setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_NOT_EXIST));
    }

    // public static UtilistateurDTO toDto(Utilisateur utilisateur) {
    // UtilistateurDTO utilistateurDTO = new UtilistateurDTO();
    //
    // utilistateurDTO.setAdresse(utilisateur.getPassword());
    // utilistateurDTO.setNom(utilisateur.getNom());
    // utilistateurDTO.setEmail(utilisateur.getEmail());
    // utilistateurDTO.setProfils(utilisateur.getProfils().stream().collect(Collectors.toSet()));
    //
    // return utilistateurDTO;
    // }
    /*
     * @Override
     * 
     * @Transactional
     * public Response<Object> editUserInfos(EditMonCompteDTO req) {
     * UtilisateurInfo infosDTO =
     * AuthUtils.getAuthenticateUtilisateur().orElseThrow(() -> new
     * GenericApiException(i18nTranslat.toTranslate(UTILISATEUR_NOT_AUTHENTICAD)));
     * 
     * Optional<Utilisateur> resp = utilisateurRepository.findById(infosDTO.id());
     * if (resp.isPresent()) {
     * Utilisateur user = resp.get();
     * user.setPrenom(req.prenom());
     * user.setNom(req.nom());
     * user.setTelephone(req.telephone());
     * user.setAdresse(req.adresse());
     * 
     * Utilisateur updatedUser = utilisateurRepository.save(user);
     * return Response.ok().setMessage(i18nTranslat.toTranslate(
     * UTILISATEUR_MODIFIER_AVEC_SUCCES)).setPayload(iUtilisateurMapper.
     * uTilisateurToUtilisateurDto(updatedUser));
     * }
     * 
     * return
     * Response.notFound().setMessage(i18nTranslat.toTranslate(UTILISATEUR_ABSENT));
     * }
     */

    @Override
    @Transactional
    public Response<Object> updatePasswordFromInterface(ResetOrForgetFormDTO form) {
        if (new UtilityClass.PasswordUtility().validate(form.newPassword())) {
            Optional<Utilisateur> resp = utilisateurRepository.findUtilisateurByEmail(form.login());
            if (resp.isPresent() && !encoder.matches(form.password(), resp.get().getPassword()))
                return Response.badRequest().setMessage(i18nTranslat.toTranslate(PASSWORD_OLD_PASSWORD_ARE_NOT_IDENTIQUE));

            else if (resp.isPresent()) {
                Utilisateur utilisateur = resp.get();
                if (encoder.matches(form.newPassword(), utilisateur.getPassword()))
                    return Response.badRequest().setMessage(i18nTranslat.toTranslate(PASSWORD_NEW_PASSWORD_ARE_IDENTIQUE));

                utilisateur.setPassword(encoder.encode(form.newPassword()));
                Utilisateur updatedUser = utilisateurRepository.save(utilisateur);
                return Response.ok().setMessage(i18nTranslat.toTranslate(MOT_DE_PASSE_MODIFIER_AVEC_SUCCES))
                        .setPayload(UtilisateurDTO.toDto(updatedUser));
            }
            return Response.notFound().setMessage(i18nTranslat.toTranslate(UTILISATEUR_ABSENT));
        }
        throw new MFPAIException(MFPAIMessage.NON_STRONG_PASSWORD);
    }

    @Override
    public Utilisateur getCurrentConnectedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return utilisateurRepository.findUtilisateurByEmailIgnoreCase(username)
                .or(() -> utilisateurRepository.findUtilisateurByNormalizedMatricule(username))
                .orElseThrow();

    }

    private Response<Object> updatePassword(ResetOrForgetFormDTO form) {
        // if (new PasswordValidator().validate(form.newPassword())) {
        // return
        // Response.validationException().setMessage(i18nTranslat.toTranslate(CONNEXION_LOGIN_INCORRECT)).setErrors(i18nTranslat.toTranslate(CONNEXION_LOGIN_INCORRECT));
        // }
        Optional<Utilisateur> resp = utilisateurRepository.findUtilisateurByEmail(form.login());
        if (resp.isPresent()) {
            Utilisateur utilisateur = resp.get();
            utilisateur.setPassword(encoder.encode(form.newPassword()));
            utilisateur.setFirstLog(false);

            // System.out.println("utilisateur"+ utilisateur.getPassword());

            Utilisateur updatedUser = utilisateurRepository.save(utilisateur);
          //  System.out.println(updatedUser.getPassword());
            return Response.ok().setMessage(i18nTranslat.toTranslate(MOT_DE_PASSE_MODIFIER_AVEC_SUCCES))
                    .setPayload(updatedUser);
        }

        return Response.notFound().setMessage(i18nTranslat.toTranslate(UTILISATEUR_ABSENT));
    }

}
