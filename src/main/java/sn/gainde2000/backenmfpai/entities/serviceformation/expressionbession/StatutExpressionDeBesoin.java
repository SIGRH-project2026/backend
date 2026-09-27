package sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_StatutExpressionDeBesoin", schema = "schema_formation")
public class StatutExpressionDeBesoin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "StExp_Id")
    private long id;

    @NotNull(message = "Veuillez renseigner le code du statut de la campagne")
    @Column(length = 20, unique = true, name = "StExp_Code")
    private String code;

    @NotNull(message = "Veuillez renseigner le libellé du statut de la campagne")
    @Column(length = 100,name = "StExp_Libelle")
    private String libelle;
    @Column(name= "StExp_Deleted")
    private boolean isDeleted = false;
}
