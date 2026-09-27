package sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutExpressionDeBesoinEnum;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_ExpressionDeBesoin", schema = "schema_formation")
public class ExpressionDeBesoin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Exp_Id")
    private long id;

    @NotNull(message = "Veuillez renseigner le Besoin en compétence/ en formation")
    @Column(name = "Exp_Besoin")
    private String besoin;

    @NotNull(message = "Veuillez renseigner le Motif du choix")
    @Column(name = "Exp_Motif", length = 250)
    private String motif;

    @NotNull(message = "Veuillez renseigner la date")
    @Column(name = "Exp_Date")
    private LocalDate date;

    private boolean deleted = false;

    @Column(name = "Exp_Reference", nullable = true, length = 50)
//    @NotNull(message = "Veuillez renseigner référence")
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Exp_Campagne")
    private Campagne campagne;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Exp_Utilisateur")
    private Utilisateur utilisateur; //demandeur

    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private StatutExpressionDeBesoinEnum statutExpression;
}
