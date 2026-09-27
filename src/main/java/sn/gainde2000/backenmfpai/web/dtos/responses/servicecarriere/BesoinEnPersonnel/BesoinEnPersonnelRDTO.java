
package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BEPFiliereDiscipline;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.StatutBEP;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BesoinEnPersonnelRDTO {
    private Long id;
   // private Long nbrPersonne;
    private String commentaire;
    private String annee;
    private int deficit;
    private Utilisateur utilisateur;
    private StatutBEP statut;
    private CorpsGrade corps;
    private Grade grade;
    private Region region;
    private IA ia;
    private IEF ief;
    private Etablissement etablissement;
    //private List<Filiere> filieres = new ArrayList<>();
    private List<BEPFiliereDiscipline> bepFiliereDisciplines;
}

