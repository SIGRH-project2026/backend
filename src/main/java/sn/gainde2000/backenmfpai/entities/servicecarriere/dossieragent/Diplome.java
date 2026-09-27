package sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.file.File;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "TD_Diplome", schema = "schema_carriere")
@SequenceGenerator(name = "seq_diplome", initialValue = 100, allocationSize = 2, sequenceName = "seq_diplome")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_diplome")
    @Column(name = "dip_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 50)
    @Column(name = "dip_nom", nullable = false)
    private String dipNom;

    @OneToOne(cascade = CascadeType.ALL)
    private File pieceJointes;
   /* @Size(max = 50)
    @Column(name = "dip_filename", nullable = false)
    private File filename;*/

    @Column(name = "dip_is_deleted")
    private boolean isDeleted;

    @Column(name = "dip_date_obtention", nullable = false)
    private LocalDate dipDateObtention;



    public void setIsDeleted(Boolean bool){
        this.isDeleted = bool;
    }

    public boolean getIsDeleted(){
        return this.isDeleted;
    }

}
