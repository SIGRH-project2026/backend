package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TR_ProfileMenuSousMenu", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_pmsm", initialValue = 100, allocationSize = 2, sequenceName = "seq_pmsm")
public class ProfileMenuSousMenu  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pmsm")
    @Column(name = "pmsm_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "Men_id")
    private Long menuId;

    @Column(name = "Smn_id")
    private Set<Long> sousMenuId;

    @Column(name = "Profile_id")
    private Long profileId;

}
