package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author G2k R&D
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_Profile", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_profile", initialValue = 100, allocationSize = 2, sequenceName = "seq_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profile")
    @Column(name = "pro_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 50)
    @Column(name = "pro_code", unique = true, nullable = false, length = 10)
    private String code;

    @Size(max = 100)
    @Column(name = "pro_Libelle")
    private String label;

    @Column(name = "pro_type")
    private String typeProfile;

    @Column(name = "pro_type_division")
    private String typeProfileDivision;

    @Column(name = "pro_type_bureau")
    private String typeProfileBureau;

    @Column(name = "pro_type_direction")
    private String typeProfileDirection;


    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ProfileMenuSousMenu> menus = new HashSet<>();

  /*  @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(code, profile.code)
                && Objects.equals(label, profile.label) && Objects.equals(menus, profile.menus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label, menus);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", label='" + label + '\'' +
                ", menus=" + menus +
                '}';
    }*/
}
