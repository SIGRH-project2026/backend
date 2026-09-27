package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CampagneRequestDTO {
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
//    private String isFilePresent;
}
