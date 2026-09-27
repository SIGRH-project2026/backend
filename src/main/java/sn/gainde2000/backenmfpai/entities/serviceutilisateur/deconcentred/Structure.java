package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:46
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_Structure", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_str", initialValue = 100, allocationSize = 2, sequenceName = "seq_str")
public class Structure {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_str")
    @Column(name = "str_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "str_code")
    private String code;

    @Size(max = 100)
    @Column(name = "str_libelle")
    private String label;

  /*  @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "str_region_id")
    private Region region;

   */

}
