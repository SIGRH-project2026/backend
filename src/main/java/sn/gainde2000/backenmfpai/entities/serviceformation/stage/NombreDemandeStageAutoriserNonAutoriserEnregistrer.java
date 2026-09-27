package sn.gainde2000.backenmfpai.entities.serviceformation.stage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NombreDemandeStageAutoriserNonAutoriserEnregistrer {
    private int nombreDemandeStageEnregistrer;
    private int nombreDemandeStageAutoriser;
    private int nombreDemandeStageNonAutoriser;
}
