package sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TD_Permutation", schema = "schema_carriere")
@SequenceGenerator(name = "seq_permu", initialValue = 100, allocationSize = 2, sequenceName = "seq_permu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permutation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_permu")
    @Column(name = "permu_id", nullable = false, updatable = false, unique = true)
    private Long id;

    // Demandeur
    @ManyToOne
    @JoinColumn(name = "User1_Id", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur1;
    // Receveur
    @ManyToOne
    @JoinColumn(name = "User2_Id", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur2;

    @ManyToOne
    @JoinColumn(name = "Ia_demandeur", referencedColumnName = "ia_id", nullable = false)
    private IA iaDemandeur;

    @ManyToOne
    @JoinColumn(name = "Ia_receveur", referencedColumnName = "ia_id", nullable = false)
    private IA iaReceveur;

    @ManyToOne
    @JoinColumn(name = "etab_demandeur", referencedColumnName = "eta_id", nullable = false)
    private Etablissement etablissementDemandeur;

    @ManyToOne
    @JoinColumn(name = "etab_receveur", referencedColumnName = "eta_id", nullable = false)
    private Etablissement etablissementReceveur;

    @ManyToOne
    @JoinColumn(name = "ief_demandeur", referencedColumnName = "seq_ief")
    private IEF iefDemandeur;

    @ManyToOne
    @JoinColumn(name = "ief_receveur", referencedColumnName = "seq_ief")
    private IEF iefReceveur;

    @Column(name = "date_permutation",nullable = false)
    private LocalDate datePermutation;

    @Column(name = "motif_permutation", length = 500)
    private String motifPermutation;

    @Column(name = "date_validation")
    private LocalDate dateValidation;

    @Column(name = "ordre_service")
    @Size(max = 150)
    private String ordreService;

    @Column(name = "perm_is_deleted")
    private boolean isDeleted = false;

    @Column(name ="isActive")
    private boolean isActive = true;

    @Column(name ="validateIaDemandeur")
    private boolean validateIaDemandeur = false;

    @Column(name ="validateIaReceveur")
    private boolean validateIaReceveur = false;

    @Column(name ="validateIefDemandeur")
    private boolean validateIefDemandeur = false;

    @Column(name ="validateIefReceveur")
    private boolean validateIefReceveur = false;

    @Column(name ="validateEtabDemandeur")
    private boolean validateEtabDemandeur = false;

    @Column(name ="validateEtabReceveur")
    private boolean validateEtabReceveur = false;

    @OneToOne(cascade = CascadeType.ALL)
    private TraitementPermutation traitementPermutation;

    @Column(name ="permu_niveau")
    private int niveau = 0;

    @Column(name ="permu_email_traitant")
    private List<String> emailTraitant = new ArrayList<>();

}
