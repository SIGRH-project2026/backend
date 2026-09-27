package sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation;

import lombok.Getter;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;

import java.util.Set;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "TD_STATUT_PLANFORMATION", schema = "schema_formation")
public class StatutPlanFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle;

    @Column(nullable = false, unique = true)
    private String code;

    @OneToMany(mappedBy = "statutPlanFormation", fetch = FetchType.LAZY)
    private Set<PlanFormation> plansFormation;

}