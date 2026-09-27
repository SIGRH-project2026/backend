package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;


@Entity
@Table(name = "TD_EtatCivil", schema = "schema_carriere")
@SequenceGenerator(name = "seq_etat_civil", initialValue = 100, allocationSize = 2, sequenceName = "seq_etat_civil")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtatCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_etat_civil")
    @Column(name = "etat_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 50)
    @Column(name = "nom_fichier", nullable = false)
    private String nomFichier;

    @Column(name = "taille", nullable = false)
    private long taille;

    @Size(max = 150)
    @Column(name = "nom_doc", nullable = false)
    private String nomDoc;

    @Column(name = "etat_is_deleted")
    private boolean isDeleted;

    @OneToOne(cascade = CascadeType.ALL)
    private File piecejointes ;

    public void setIsDeleted(Boolean bool){
        this.isDeleted = bool;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

}
