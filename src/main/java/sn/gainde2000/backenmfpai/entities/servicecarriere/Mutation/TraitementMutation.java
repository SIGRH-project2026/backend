package sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@Table(name = "TD_TraitementMutation", schema = "schema_carriere")
@SequenceGenerator(name = "seq_TraitementMutation", initialValue = 100, allocationSize = 2, sequenceName = "seq_TraitementMutation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class TraitementMutation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_TraitementMutation")

    @Column(nullable = false,updatable = false, unique = true)
    protected Long id;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur traiteur;


    @Column(name = "traitMut_idMutation")
    private Long idmutation;
    @ManyToOne
    @JoinTable(
            name = "traitementMutation_statut",
            joinColumns = @JoinColumn(name = "traitement_mutation_id"),
            inverseJoinColumns = @JoinColumn(name = "statutMut_id")
    )

    private StatutMutation statut = new StatutMutation();

    @Column(name = "traitMut_date")
    private LocalDate dateTraitementMutation    ;

    @Size(max = 100)
    @Column(name = "traitMut_motif")
    private String motif;

    @Column(name = "traitmut_dossier_signe")
    private String dossierSigne;

}
