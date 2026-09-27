package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineDeficitaireAllEtablisemment {
    //private Etablissement etablissement;
    private List<DisciplineDeficitaire> disciplineDeficitaires;
}



