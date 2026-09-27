package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineDeficitaire {
    Discipline discipline;
    Etablissement etablissement;
    int totalHeuresAttribuees;
    int totalHeuresDispensee ;
}
