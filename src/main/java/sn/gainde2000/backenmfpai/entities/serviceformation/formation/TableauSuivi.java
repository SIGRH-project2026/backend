package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_TABLEAU_SUIVI", schema = "schema_formation")
public class TableauSuivi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "presences")
    private Integer presences;

    @NotNull(message = "ne doit pas être nul")
    @Column(name = "abscences")
    private Integer abscences;

    @Column(name = "nbrSession")
    private Integer nbrSession;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;
}
