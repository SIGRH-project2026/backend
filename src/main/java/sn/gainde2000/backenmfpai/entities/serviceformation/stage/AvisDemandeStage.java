package sn.gainde2000.backenmfpai.entities.serviceformation.stage;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_AvisDemandeStage", schema = "schema_formation")
public class AvisDemandeStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Avis_Id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "Avis_centralLevel")
    private CentralLevel centralLevel;

    @ManyToOne
    @JoinColumn(name = "Avis_demandeStage")
    private DemandeStage demandeStage;

    @Column(name = "Avis_contenu", nullable = false)
    private String avis;

    @Column(name = "Avis_Date")
    private LocalDate date = LocalDate.now();


}
