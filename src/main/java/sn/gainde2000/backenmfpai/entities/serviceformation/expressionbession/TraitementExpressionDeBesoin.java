package sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_TraitementExpressionDeBesoin", schema = "schema_formation")
public class TraitementExpressionDeBesoin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TraitExp_Id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "TraitExp_utilisateur")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "TraitExp_ExpressionDeBesoin")
    private ExpressionDeBesoin expressionDeBesoin;

    @Column(name = "TraitExp_Activated")
    boolean activated;


    @Column(name = "TraitExp_DateTraitement")
    LocalDate  dateTraitement = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "TraitExp_StatutExpressionDeBesoin")
    private StatutExpressionDeBesoin statutExpressionDeBesoin;

    @Column(name = "TraitExp_ThemeProvisoire", nullable = true, length = 100)
    private String themeProvisoire;

}
