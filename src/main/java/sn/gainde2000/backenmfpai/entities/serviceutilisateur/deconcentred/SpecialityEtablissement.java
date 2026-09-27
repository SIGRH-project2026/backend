package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/08/2024-16:12
 * @project backend_mfpai
 */

@Entity
@Table(name = "TR_Speciality_Etablissement", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_speciality_etablissement", initialValue = 100, allocationSize = 2, sequenceName = "seq_speciality_etablissement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SpecialityEtablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_speciality_etablissement")
    @Column(name = "spec_etab_id",nullable = false, updatable = false, unique = true)
    private Long  id;


    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @ManyToOne
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissement;

    @Transient
    private List<Speciality> specialities;
}
