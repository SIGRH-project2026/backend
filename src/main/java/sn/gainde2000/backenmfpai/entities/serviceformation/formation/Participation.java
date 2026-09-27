package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;


@Data
@Getter
@Setter
@Entity
@Table(name = "TD_PARTICIPATION", schema = "schema_formation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 200)
    @Column(name = "numero_demande")
    private String numeroDemande;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @ManyToOne
    @JoinColumn(name = "central_level_id")
    private Utilisateur centralLevel;

}