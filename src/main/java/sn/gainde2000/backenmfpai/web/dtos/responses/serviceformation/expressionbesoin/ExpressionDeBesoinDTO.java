package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpressionDeBesoinDTO {
    private String besoin;
    private String motif;
   // private LocalDate date;
}
