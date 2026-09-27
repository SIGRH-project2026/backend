package sn.gainde2000.backenmfpai.entities.serviceformation.formation;


import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_PVEXAMEN", schema = "schema_formation")
public class PvExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

    @OneToOne
    @JoinColumn(name = "rapport_file_id")
    private File file;

}
