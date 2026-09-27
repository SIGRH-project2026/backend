package sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "TD_Mutation", schema = "schema_carriere")
@SequenceGenerator(name = "seq_mutation", initialValue = 100, allocationSize = 2, sequenceName = "seq_mutation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Mutation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mutation")

    @Column(nullable = false,updatable = false, unique = true)
    protected Long id;

    @Size(max = 20)
    @Column(name = "mut_numeroRef")
    private String numeroRef;

    @Column(name = "mut_destinataireType")
    private String destinataireType;

    @Column(name = "mut_0rdreService")
    @Size(max = 100)
    private String ordreService;

    @Column(name = "mut_bordereauTransmissionCE")
    @Size(max = 100)
    private String btCE;
    @Column(name = "mut_bordereauTransmissionEFF")
    @Size(max = 100)
    private String btEFF;
    @Column(name = "mut_bordereauTransmissionCFP")
    @Size(max = 100)
    private String btCFP;
    @Column(name = "mut_bordereauTransmissionIEF")
    @Size(max = 100)
    private String btIEF;
    @Column(name = "mut_bordereauTransmissionIA")
    @Size(max = 100)
    private String btIA;
    @Column(name = "mut_bordereauTransmissionDRH")
    @Size(max = 100)
    private String btDRH;
    @Column(name = "mut_bordereauTransmissionDGPEEC")
    @Size(max = 100)
    private String btDGPEEC;
    @Column(name = "mut_bordereauTransmissionChefBur")
    @Size(max = 100)
    private String btCB;
    @Column(name = "mut_bordereauTransmissionChefServ")
    @Size(max = 100)
    private String btCS;
    @Column(name = "mut_bordereauTransmissionChefDiv")
    @Size(max = 100)
    private String btCD;
    @Column(name = "mut_currentBordereauTransmission")
    @Size(max = 100)
    private String currentBordereauTransmission;

    @Column(name = "mut_Osgenerated")
    private boolean osgenerated;
  

    @Column(name = "mut_ProfilDevantTraiter")
    @Size(max = 25)
    private String profilDevantTraiter;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur demandeur;

    @OneToOne(cascade = CascadeType.ALL)
    private TraitementMutation traitementMutation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reg_id")
    private Region regionSouhaitee;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ia_id")
    private IA iaSouhaitee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ief_id")
    private IEF iefSouhaitee;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissementSouhaitee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bureau_id")
    private Bureau bureauSouhaite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direction_id")
    private Direction directionSouhaitee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "division_id")
    private Division divisionSouhaitee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services serviceSouhaite;

    @OneToOne
    private OrigineDemandeurLog origineDemandeurLog;

    @Size(max = 100)
    @Column(name = "mut_commentaire")
    private String commentaire;

    @Column(name = "mut_dossier_signe")
    private String dossierSigne;

    @Column(name = "mut_dateDemande")
    private LocalDate dateDemande;
    @Column(name = "mut_emailsTraitant")
    private Set<String> emailsTraitant =new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mutation_id")
    private List<File> pieceJointes = new ArrayList<>();

}
