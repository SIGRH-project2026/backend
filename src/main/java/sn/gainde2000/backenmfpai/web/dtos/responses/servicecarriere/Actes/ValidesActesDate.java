package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidesActesDate {
    private LocalDate dateDebut;
    private LocalDate dateFin;
}
