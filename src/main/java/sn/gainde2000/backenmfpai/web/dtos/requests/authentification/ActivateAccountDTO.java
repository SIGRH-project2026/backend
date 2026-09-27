package sn.gainde2000.backenmfpai.web.dtos.requests.authentification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record ActivateAccountDTO(
        @NotBlank(message = "Le matricule est obligatoire") String matricule,
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email n'est pas valide") String email) {
}
