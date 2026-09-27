package sn.gainde2000.backenmfpai.entities.serviceformation.courrier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NombreCourrierTaiterNonTraiter {
    private int nombreCourrierTraiter;
    public int nombreCourrierNonTraiter;
}
