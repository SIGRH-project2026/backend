package sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.FicheEtablissement;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FicheSynoptiqueRDTO {
    private Long id;
    private DeconcentratedLevel chefEtablissemnt;

    private List<FiliereDiscipline> filiereDisciplines ;
    private List<ClasseProfDiscipline> classeProfDisciplines = new ArrayList<>();
    private List<SerieNiveauDiscipline> serieNiveauDisciplines = new ArrayList<>();

    private List<SerieClasseProfDiscipline> serieClasseProfDisciplines = new ArrayList<>();

    private List<FormationProfessionel>  formationProfessionels = new ArrayList<>();

}
