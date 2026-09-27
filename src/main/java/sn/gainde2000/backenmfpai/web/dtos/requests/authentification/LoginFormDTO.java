package sn.gainde2000.backenmfpai.web.dtos.requests.authentification;

import jakarta.validation.constraints.NotBlank;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.CONNEXION_LOGIN_PASSWORD_NON_VIDE;
import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.EMAIL_OBLIGATOIRE;

/**
 * @author G2k R&D
 */

public record LoginFormDTO(
        @NotBlank(message = EMAIL_OBLIGATOIRE)
        String login,
        @NotBlank(message = CONNEXION_LOGIN_PASSWORD_NON_VIDE)
        String password
) {
}
