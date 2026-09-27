package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAG;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;

import java.time.LocalDate;

@Entity
@Table(name = "TD_Situation_Administrative", schema = "schema_carriere")
@SequenceGenerator(name = "seq_situation_administrative", initialValue = 100, allocationSize = 2, sequenceName = "seq_situation_administrative")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SituationAdministrative {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_situation_administrative")
    @Column(name = "sit_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "numero_acte", nullable = false, length = 20)
    private String numeroActe;

    @ManyToOne
    @JoinColumn(name = "typeAG_id")
    private TypeAA acteAA;

    @ManyToOne
    @JoinColumn(name = "typeAA_id")
    private TypeAG acteAG;

    @ManyToOne
    @JoinColumn(name = "type_acte_id")
    private TypeActe typeActe;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private File pieceJointes;

    @Column(name = "dip_date_obtention", nullable = false)
    private LocalDate dateActe;

    @Column(name = "sit_is_deleted",nullable = false, updatable = true, unique = false)
    private boolean isDeleted = false;

    public void setIsDeleted(boolean bool){
        this.isDeleted = bool;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

}
