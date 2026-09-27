package sn.gainde2000.backenmfpai.entities.serviceformation.formation;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import sn.gainde2000.backenmfpai.entities.file.File;

@Data
@Getter
@Setter
@Entity
@Table(name = "TD_PARTICIPANT", schema = "schema_formation")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id")
    private File fileParticipant;

    // @ManyToMany
    // @JoinTable(name = "participant_utilisateur", joinColumns = @JoinColumn(name =
    // "participant_id"), inverseJoinColumns = @JoinColumn(name = "utilisateur_id"))
    // private List<CentralLevel> listeParticipants;

    @OneToOne
    @JoinColumn(name = "formation_id")
    private Formation formation;

}
