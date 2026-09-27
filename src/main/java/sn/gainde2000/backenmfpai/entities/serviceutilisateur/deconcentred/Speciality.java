package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-16:42
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_Speciality", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_speciality", initialValue = 100, allocationSize = 2, sequenceName = "seq_speciality")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_speciality")
    @Column(name = "spe_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 20)
    @Column(name = "spe_code")
    private String code;

    @Size(max = 100)
    @Column(name = "spe_libelle")
    private String label;

    @Column(name = "spe_statut", columnDefinition = "boolean default true")
    private Boolean statut;



}
