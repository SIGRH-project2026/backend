package sn.gainde2000.backenmfpai.entities.servicecarriere;

import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TD_PieceJoint", schema = "schema_carriere")
@SequenceGenerator(name = "seq_pj", initialValue = 100, allocationSize = 2, sequenceName = "seq_pj")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PieceJointes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pj")
    @Column(name = "pj_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @OneToMany
    @Column(name = "file_id")
    private List<File> files = new ArrayList<>();


}
