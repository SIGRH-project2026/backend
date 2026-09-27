package sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TD_ImputationOuBulletin", schema = "schema_carriere")
@SequenceGenerator(name = "seq_imp", initialValue = 100, allocationSize = 2, sequenceName = "seq_imp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImputationOuBulletin {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_imp")
    @Column(name = "imp_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 50)
    @Column(name = "type_demande", nullable = false)
    private String typeDemande;

    @Size(max = 50)
    @Column(name = "nom_beneficiere", nullable = false)
    private String nomBeneficiere;

    @Size(max = 50)
    @Column(name = "prenom_beneficiere", nullable = false)
    private String prenomBeneficiere;

    @Size(max = 50)
    @Column(name = "status_beneficiere", nullable = false)
    private String statusBeneficiere;

    @Size(max = 50)
    @Column(name = "imputation_generee")
    private String imputationGeneree;

    @Column(name = "date_imputation",nullable = false, updatable = true)
    private LocalDate dateImputation;

    @Column(name = "numero_demande")
    private long numeroDemande;

    @Column(name = "dos_is_deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "UserId", referencedColumnName = "id", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imp_id")
    private List<File> justificatifs = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Utilisateur createdBy;

    public void setIsDeleted(boolean bool){
        this.isDeleted = bool;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

    


}
