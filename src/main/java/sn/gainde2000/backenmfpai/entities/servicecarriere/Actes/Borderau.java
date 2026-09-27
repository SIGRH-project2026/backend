package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

@Entity
@Table(name = "TP_Bordereau", schema = "schema_carriere")
@SequenceGenerator(name = "seq_bord", initialValue = 100, allocationSize = 2, sequenceName = "seq_bord")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Borderau {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bord")
    @Column(name = "bord_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "bord_acte")
    private Long acteId;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private Utilisateur agent;

    @Column(name = "bord_fic")
    @Size(max = 100)
    private String fileName;
}
