package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;


import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TD_DossierAgent", schema = "schema_carriere")
@SequenceGenerator(name = "seq_dossier_agent", initialValue = 100, allocationSize = 2, sequenceName = "seq_dossier_agent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierAgent {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dossier_agent")
    @Column(name = "dos_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserId")
    private Utilisateur utilisateur;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dossier_agent_id")  // nom de la colonne pour lier avec Acte
    private List<Acte> actes = new ArrayList<>();

    @Column(name = "dos_is_deleted")
    private boolean isDeleted = false;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_agent_id")  // nom de la colonne pour lier avec Diplome
    private List<Diplome> diplomes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_agent_id")  // nom de la colonne pour lier avec EtatCivil
    private List<EtatCivil> etatCivil = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "dossier_agent_id")  // nom de la colonne pour lier avec SituationAdministrative
    private List<SituationAdministrative> situationAdministrative = new ArrayList<>();

    public void setIsDeleted(boolean bool) {
        this.isDeleted = bool;
    }
}
