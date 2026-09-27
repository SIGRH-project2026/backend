package sn.gainde2000.backenmfpai.entities.Contacts;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "TD_Contacts", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_cont", initialValue = 100, allocationSize = 2, sequenceName = "seq_cont")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_cont")
    @Column(name = "contact_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "cont_nomComplet")
    @Size(max = 150)
    private String nomComplet;

    @Column(name = "cont_email")
    @Size(max = 100)
    private String email;

    @Column(name="cont_commentaire")
    @Size(max = 250)
    private String commentaire;

    @Size(max = 20)
    @Column(name = "cont_telephone")
    private String telephone;
}
