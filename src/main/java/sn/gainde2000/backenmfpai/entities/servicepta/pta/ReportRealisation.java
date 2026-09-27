package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 28/08/2024-11:13
 * @project backend_mfpai
 */


@Entity
@Table(name = "TD_ReportRealisation", schema = "schema_pta")
@SequenceGenerator(name = "seq_report_realisation", initialValue = 100, allocationSize = 2, sequenceName = "seq_report_realisation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportRealisation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_report_realisation")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;


    @Column(name = "report_date_debut")
    private LocalDate dateDebut;

    @Column(name = "report_date_fin")
    private LocalDate dateFin;


    @Column(name = "report_resume")
    private String resume;


    @Column(name = "report_target", columnDefinition = "integer default 100")
    private Integer cible;


    @Column(name = "report_rate_acheived")
    private Integer tauxAtteint;


    @Column(name = "report_observation", columnDefinition = "TEXT")
    private String observation;

    @OneToMany(
            cascade = CascadeType.ALL,
       orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "report_files_id")
    private Set<File> files;

}
