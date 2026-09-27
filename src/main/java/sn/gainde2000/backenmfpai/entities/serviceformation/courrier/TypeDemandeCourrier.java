package sn.gainde2000.backenmfpai.entities.serviceformation.courrier;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.repositories.serviceformation.courrier.NomTypeCourrier;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TP_TypeDemandeCourrier", schema = "schema_formation")
public class TypeDemandeCourrier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "TypeDemCou_Id")
    private long id;

    @Column(length = 20, unique = true, name = "TypeDemCou_Code")
    private String code;

    @Column(length = 100,name = "TypeDemCou_Libelle")
    private String libelle;

    @Column(length = 20,name = "TypeDemCou_nomTypeCourrier")
    @Enumerated(EnumType.STRING)
    private NomTypeCourrier nomTypeCourrier;

    @Column(name = "TypeDemCou_Deleted")
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "TypeDemCou_Division")
    private Division division;
}
