package sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.StatutMutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@Entity
@Table(name = "TD_traitement_permutation", schema = "schema_carriere")
@SequenceGenerator(name = "seq_trait_perm", initialValue = 100, allocationSize = 2, sequenceName = "seq_trait_perm")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraitementPermutation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_trait_perm")
    @Column(name = "traite_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur traiteur;

    @Column(name = "trait_idPermutation")
    private Long idPermutation;

    @ManyToOne
    @JoinColumn(name = "statutPermu_id")
  /*  @JoinTable(

            name = "traitementPermutation_statut",
            joinColumns = @JoinColumn(name = "traitement_permutation_id"),
            inverseJoinColumns = @JoinColumn(name = "statutPermu_id")
    )*/

    private StatusPermutation statut = new StatusPermutation();

    @Column(name = "traitPermu_date")
    private LocalDate dateTraitementMutation    ;

    @Size(max = 100)
    @Column(name = "traitPermu_motif")
    private String motif;

    @OneToOne(cascade = CascadeType.ALL)
    private File bordereauValidation;
}
