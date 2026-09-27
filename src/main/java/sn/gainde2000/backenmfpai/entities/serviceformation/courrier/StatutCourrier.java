package sn.gainde2000.backenmfpai.entities.serviceformation.courrier;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_StatutCourrier", schema = "schema_formation")
public class StatutCourrier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "StCou_Id")
    private long id;

    @NotNull(message = "Veuillez renseigner le code du statut du Courrier")
    @Column(length = 20, unique = true, name = "StCou_Code")
    private String code;

    @NotNull(message = "Veuillez renseigner le libellé du statut du Courrier")
    @Column(length = 100,name = "StCou_Libelle")
    private String libelle;

    private boolean isDeleted = false;
}
