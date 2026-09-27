package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoraireProfesseurs {
    private DeconcentratedLevel professeur;
    private  int nbreClasse ;
    private  int nbrDiscipline ;
    private  int heuresDispensees;
    private  List<ClasseDisciplinesForProf> classeDisciplinesForProfs = new ArrayList<>();
}
