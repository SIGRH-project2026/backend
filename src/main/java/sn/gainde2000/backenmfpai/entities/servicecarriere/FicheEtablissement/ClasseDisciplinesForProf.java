package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClasseDisciplinesForProf {
    private  String nomClasse;
    private List<DisciplineQuantum> disciplineQuantum = new ArrayList<>();
}
