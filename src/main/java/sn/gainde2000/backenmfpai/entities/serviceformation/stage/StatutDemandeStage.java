package sn.gainde2000.backenmfpai.entities.serviceformation.stage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_StatutDemandeStage", schema = "schema_formation")
public class StatutDemandeStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "StatuDeman_Id")
    private long id;

    @Column(length = 20, unique = true, name = "StatuDeman_Code")
    private String code;

    @Column(length = 100,name = "StatuDeman_Libelle")
    private String libelle;

    @Column(name = "StatuDeman_Deleted")
    private boolean isDeleted = false;
}
