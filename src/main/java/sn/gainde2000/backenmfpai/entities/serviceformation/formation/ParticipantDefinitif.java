package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_PARTICIPANT_DEFINITIF", schema = "schema_formation")
public class ParticipantDefinitif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "numero_demande")
    private String numeroDemande;

    @Size(max = 200)
    @Column(name = "matricule")
    private String matricule;

    @Size(max = 200)
    @Column(name = "nom_participant")
    private String nom;

    @Size(max = 200)
    @Column(name = "direction")
    private String direction;

    @Size(max = 200)
    @Column(name = "commentaire")
    private String commentaire;

    @Size(max = 200)
    @Column(name = "division")
    private String division;

    @Column(name = "admis")
    private boolean admis;

    @Column(name = "assidu")
    private boolean assidu;

    @Column(name = "competences")
    private boolean competences;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
}
