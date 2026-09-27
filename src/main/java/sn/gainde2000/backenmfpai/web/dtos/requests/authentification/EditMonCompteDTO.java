package sn.gainde2000.backenmfpai.web.dtos.requests.authentification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;

public record EditMonCompteDTO(
        @NotBlank(message = NOM_OBLIGATOIRE)
        @Size(min = 2, max = 20, message = TAILLE_NOM)
        String nom,
        @NotBlank(message = PRENOM_OBLIGATOIRE)
        @Size(min = 3, max = 60, message = TAILLE_PRENOM)
        String prenom,
        String adresse,
        @NotBlank(message = TELEPHONE_OBLIGATOIRE)
        String telephone
) {
}
