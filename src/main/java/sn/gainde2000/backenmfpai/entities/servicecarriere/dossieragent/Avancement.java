package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.file.File;

import java.time.LocalDate;

/**
 * @author bsdieme
 */
@Entity
@Table(name = "TD_Avancement", schema = "schema_carriere")
@SequenceGenerator(name = "seq_avancement", initialValue = 100, allocationSize = 2, sequenceName = "seq_avancement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Avancement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avancement")
    @Column(name = "avan_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "avan_datePriseService",nullable = false, updatable = true)
    private LocalDate datePriseService;

    @Size(max = 100)
    @Column(name = "avan_posteOccupe")
    private String posteOccupe;

    @Size(max = 100)
    @Column(name = "avan_ordreService")
    private String ordreService;

    @Column(name = "avan_isDeleted")
    private boolean isDeleted=false;

    @OneToOne(cascade = CascadeType.ALL)
    private File pieceJointes;



  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dos_id")
    private DossierAgent dossierAgent;*/

    public void setIsDeleted(boolean bool){
        this.isDeleted = bool;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

}
