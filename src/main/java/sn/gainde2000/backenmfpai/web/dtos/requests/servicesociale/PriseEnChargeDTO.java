package sn.gainde2000.backenmfpai.web.dtos.requests.servicesociale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor

public class PriseEnChargeDTO {

    @NotBlank(message = "La date de demande d'acte est obligatoire")
    private LocalDate dateDemande=LocalDate.now();

    private LocalDate lastModified;

    private long idUtilisateur;

    private String objetDemande;
    private String motifRejetDemande;
    private String motifModification;

    private String codeTypeDemande;


}
