package sn.gainde2000.backenmfpai.entities.serviceformation.stage;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TD_DisciplineStage", schema = "schema_formation")
public class DisciplineStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Discipline_Id")
    private long id;

    @Column(length = 20, unique = true, name = "Discipline_Code")
    private String code;

    @Column(length = 100,name = "Discipline_Libelle")
    private String libelle;

    @Column(name = "Discipline_Deleted")
    private boolean isDeleted = false;
}
