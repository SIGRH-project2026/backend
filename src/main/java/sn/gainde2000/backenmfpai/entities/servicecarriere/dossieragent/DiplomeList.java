package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TP_Diplome_list", schema = "schema_carriere")
@SequenceGenerator(name = "seq_diplome_list", initialValue = 100, allocationSize = 2, sequenceName = "seq_diplome_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiplomeList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_diplome_list")
    @Column(name = "dip_id",nullable = false, updatable = false)
    private Long id;
    @Size(max = 50)
    @Column(name = "nom_diplome", nullable = false)
    private String nomDiplome;
    @Size(max = 150)
    @Column(name = "description")
    private String description;

}

