package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.*;

/**
 * @author bsdieme
 *
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class AgentRequestDto {
    @NotBlank(message = MATRICULE_OBLIGATOIRE)
    @Size(min = 2, max = 50, message = TAILLE_MATRICULE)
    protected String agMatricule;
    @NotBlank(message = EMAIL_OBLIGATOIRE)
    @Email(message = EMAIL_NON_VALIDE)
    protected String agEmail;

    @NotBlank(message = NOM_OBLIGATOIRE)
    @Size(min = 2, max = 20, message = TAILLE_NOM)
    protected    String nom;
    @NotBlank(message = PRENOM_OBLIGATOIRE)
    @Size(min = 3, max = 60, message = TAILLE_PRENOM)
    protected    String prenom;
    protected String agAdresse;
    @NotBlank(message = TELEPHONE_OBLIGATOIRE)
    protected String agTelephone;

    @NotNull(message = SITUATION_MATRIMONIALE_OBLIGATOIRE)
    private String agSituationMatrimoniale;

    private String agSexe;

    private Date agDateRecrutement;

    private Date agPosteActuel;
}
