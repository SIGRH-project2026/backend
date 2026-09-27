package sn.gainde2000.backenmfpai.web.dtos.requests.authentification;

import jakarta.validation.constraints.NotBlank;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;

public record ResetOrForgetFormDTO(
        @NotBlank(message = EMAIL_OBLIGATOIRE)
        String login,
        @NotBlank(message = CONNEXION_LOGIN_PASSWORD_NON_VIDE)
        String password,
        @NotBlank(message = CONNEXION_LOGIN_NEW_PASSWORD_NON_VIDE)
        String newPassword,
        String passwordConfirmed) {


}
