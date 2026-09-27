package sn.gainde2000.backenmfpai.entities.serviceformation.stage;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_NiveauScolaire", schema = "schema_formation")
public class NiveauScolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Niveau_Id")
    private long id;

    @Column(length = 20, unique = true, name = "Niveau_Code")
    private String code;

    @Column(length = 100,name = "Niveau_Libelle")
    private String libelle;

    @Column(name = "Niveau_Deleted")
    private boolean isDeleted = false;

}
