package sn.gainde2000.backenmfpai.entities.serviceformation.stage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_AttestationStage", schema = "schema_formation")
public class AttestationStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Attestation_Id")
    private long id;


    @ManyToOne
    @JoinColumn(name = "Attestation_Utilisateur")
    private CentralLevel utilisateur;

    @OneToOne
    @JoinColumn(name = "Attestation_DemandeStage")
    private DemandeStage demandeStage;
    @OneToOne
    File piecesJoint;

    @Column(name = "Attestation_Commentaire")
    @Lob
    private String commentaire;
}
