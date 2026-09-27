package sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.enums.StatutCampagneEnum;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_StatutCampagne", schema = "schema_formation")
public class StatutCampagne {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "StCam_Id")
    private long id;

    @NotNull(message = "Veuillez renseigner le code du statut de la campagne")
    @Column(length = 20, unique = true, name = "StCam_Code")
    private String code;

    @NotNull(message = "Veuillez renseigner le libellé du statut de la campagne")
    @Column(length = 100,name = "StCam_Libelle")
    private String libelle;

    private boolean isDeleted = false;
}
