package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.ArrayList;
import java.util.List;

@Table(name = "TD_FicheSynoptique", schema = "schema_carriere")
@SequenceGenerator(name = "seq_ficheSynoptique", initialValue = 100, allocationSize = 2, sequenceName = "seq_ficheSynoptique")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FicheSynoptique {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ficheSynoptique")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @OneToOne
    @JoinColumn(name="DeconcentredLevelId")
    private DeconcentratedLevel chefEtablissemnt;
    //formation profession et technique
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FiliereDiscipline> filiereDisciplines = new ArrayList<>();
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClasseProfDiscipline> classeProfDisciplines = new ArrayList<>();

    //formation profession et secondaire
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SerieNiveauDiscipline> serieNiveauDisciplines = new ArrayList<>();
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SerieClasseProfDiscipline> serieClasseProfDisciplines = new ArrayList<>();


}
