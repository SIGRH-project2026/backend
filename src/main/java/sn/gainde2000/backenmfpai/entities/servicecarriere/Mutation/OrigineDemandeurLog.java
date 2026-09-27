package sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation;

import jakarta.persistence.*;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

@Table(name = "TD_OrigineDemandeurLog", schema = "schema_carriere")
@SequenceGenerator(name = "seq_OrigineDemandeurLog", initialValue = 100, allocationSize = 2, sequenceName = "seq_OrigineDemandeurLog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class OrigineDemandeurLog {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "seq_OrigineDemandeurLog")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;

    @Column(name = "OrigineDemLog_type")
    private String origineUserType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reg_id")
    private Region region;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ia_id")
    private IA ia;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ief_id")
    private IEF ief;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "etablissement_id")
    private Etablissement etablissement;

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "bureau_id")
    private Bureau bureau;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "division_id")
    private Division division;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services service;

}
