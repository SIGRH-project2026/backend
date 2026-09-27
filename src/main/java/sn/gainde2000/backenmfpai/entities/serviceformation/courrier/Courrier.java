package sn.gainde2000.backenmfpai.entities.serviceformation.courrier;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.repositories.serviceformation.courrier.NomTypeCourrier;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_Courrier", schema = "schema_formation")
public class Courrier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Courrier_Id")
    private long id;
    @Column(name = "Courrier_reference", unique = true)
    private String reference;
    @Column(name = "Courrier_createdAt")
    private LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "Courrier_TypeDemande", nullable = true)
    private TypeDemandeCourrier typeDemande;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Courrier_direction")
    private Direction direction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Courrier_division")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "Courrier_centralLevel")
    private CentralLevel centralLevel;
    private boolean deleted = false;

    @Column(name = "Cou_statut")
    private String statut;

    @Column(name = "Cou_other", nullable = true)
    private String otherField;
    @Column(length = 20,name = "Cou_nomTypeCourrier", nullable = true)
    @Enumerated(EnumType.STRING)
    private NomTypeCourrier nomTypeCourrier;

    @Column(length = 20,name = "Cou_TypeCourrier")
    @Enumerated(EnumType.STRING)
    private NomTypeCourrier typeCourrier;
}
