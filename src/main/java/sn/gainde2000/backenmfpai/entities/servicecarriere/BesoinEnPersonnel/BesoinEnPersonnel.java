
package sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.CorpsGrade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Grade;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;


import java.util.ArrayList;
import java.util.List;

@Table(name = "TD_BesoinEnPersonnel", schema = "schema_carriere")
@SequenceGenerator(name = "seq_expBR", initialValue = 1, allocationSize = 2, sequenceName = "seq_expBR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BesoinEnPersonnel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_BesoinEnPer")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

  /*  @Column(name = "BesoinEnPer_nombreDePersonne")
    private Long nbrPersonne;*/

    @Size(max = 100)
    @Column(name = "BesoinEnPer_commentaire")
    private String commentaire;

    @Size(max = 100)
    @Column(name = "BesoinEnPer_annee")
    private String annee;

    @Column(name = "BesoinEnPer_deficit")
    private int deficit;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;

  /*  @Size(max = 60)
    @Column(name = "BesoinEnPer_etab    lissemnt")
    protected String etablissement;*/


/*    @ManyToMany
    @JoinTable(
            name = "besoin_en_personnel_filieres",
            joinColumns = @JoinColumn(name = "besoin_en_personnel_id"),
            inverseJoinColumns = @JoinColumn(name = "filiere_id")
    )
    private List<Filiere> filieres = new ArrayList<>();*/

    //gestion de la relation nombre de personnes par filiére
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BEPFiliereDiscipline> bepFiliereDisciplines = new ArrayList<>();


/*
    @ManyToOne
    @JoinTable(
            name = "besoin_en_personnel_statut",
            joinColumns = @JoinColumn(name = "besoin_en_personnel_id"),
            inverseJoinColumns = @JoinColumn(name = "statutBEP_id")
    )
    private StatutBEP statut = new StatutBEP();
*/


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cor_id")
    private CorpsGrade corps;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reg_id")
    private Region region;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ia_id")
    private IA ia;

  //  @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ief_id")
    private IEF ief;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissement;


}
