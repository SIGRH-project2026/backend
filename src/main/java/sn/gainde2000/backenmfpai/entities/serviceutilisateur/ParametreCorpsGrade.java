package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-15:59
 * @project backend_mfpai
 */


@Entity
@Table(name = "TD_Parametre_CorpsGrade", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_params_corgrade", initialValue = 100, allocationSize = 2, sequenceName = "seq_params_corgrade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ParametreCorpsGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_params_corgrade")
    @Column(name = "params_cg_id",nullable = false, updatable = false, unique = true)
    private Long  id;


    @Column(name = "params_cg_statut")
    private Boolean statut;


    @Column(name = "params_date_param")
    private LocalDate dateParam;



    @Column(name = "params_cg_ref")
    private String noRef;


    @ManyToOne
    @JoinColumn(name = "corps_grade_id")
    private CorpsGrade corpsGrade;


    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @Transient
    private Set<Grade> grades;


    @Transient
    private TypeMatricule typeMatricules;






}
