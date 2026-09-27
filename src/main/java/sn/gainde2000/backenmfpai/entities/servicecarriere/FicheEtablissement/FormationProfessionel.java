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

@Table(name = "TP_Formation_Prof", schema = "schema_carriere")
@SequenceGenerator(name = "seq_formation", initialValue = 100, allocationSize = 2, sequenceName = "seq_formation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FormationProfessionel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_formation")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Size(max = 10)
    @Column(name = "Form_code")
    private String code;

    @Size(max = 50)
    @Column(name = "Form_libelle")
    private String libelle;
}

