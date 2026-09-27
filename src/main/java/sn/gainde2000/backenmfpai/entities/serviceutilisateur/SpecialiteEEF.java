package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

/**
 * @author Abdou Karim CISSOKHO
 * @created 16/05/2024-18:56
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_SpecialiteEEF", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_specialiteEEF", initialValue = 100, allocationSize = 2, sequenceName = "seq_specialiteEEF")

public class SpecialiteEEF {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_specialiteEEF")
    @Column(name = "speeef_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissement;
}
