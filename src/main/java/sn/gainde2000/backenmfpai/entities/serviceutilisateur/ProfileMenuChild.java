package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 18/07/2024-15:38
 * @project backend_mfpai
 */


@Entity
@Table(name = "TP_Profile_Menu_Child", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_profile_menu_child", initialValue = 100, allocationSize = 2, sequenceName = "seq_profile_menu_child")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProfileMenuChild {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_params_corgrade_pk")
    @Column(name = "pmc_id",nullable = false, updatable = false, unique = true)
    private Long  id;

    @ManyToOne
    @JoinColumn(name = "Men_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "Smn_id")
    private Menu child;

    @ManyToOne
    @JoinColumn(name = "pro_id")
    private Profile profile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileMenuChild that = (ProfileMenuChild) o;
        return Objects.equals(id, that.id) && Objects.equals(menu, that.menu) && Objects.equals(child, that.child) && Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menu, child, profile);
    }
}
