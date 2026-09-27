package sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Abdou Karim CISSOKHO
 * @created 09/05/2024-10:25
 * @project backend_mfpai
 */

@Table(name = "TP_Serie", schema = "schema_carriere")
@SequenceGenerator(name = "seq_serie", initialValue = 100, allocationSize = 2, sequenceName = "seq_serie")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_serie")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 10)
    @Column(name = "Serie_code")
    private String code;

    @Size(max = 30)
    @Column(name = "Serie_libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "formation_pro_id")
    private FormationProfessionel formationProfessionel;

}

