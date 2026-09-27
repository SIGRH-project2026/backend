package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-15:59
 * @project backend_mfpai
 */


@Entity
@Table(name = "TD_Parametre_CorpsGrade_PK", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_params_corgrade_pk", initialValue = 100, allocationSize = 2, sequenceName = "seq_params_corgrade_pk")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ParametreCorpsGradePk {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_params_corgrade_pk")
    @Column(name = "params_cg_id",nullable = false, updatable = false, unique = true)
    private Long  id;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;


    @ManyToOne
    @JoinColumn(name = "type_matricule_id")
    private TypeMatricule typeMatricule;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private ParametreCorpsGrade parametreCorpsGrade;



}
