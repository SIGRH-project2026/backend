package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProfileMenuSousMenuId  {

    @EmbeddedId
    private ProfileMenuSousMenuId id; // clé composite

    @Column(name = "Men_id")
    private Long menuId;

    @Column(name = "Smn_id")
    private Long sousMenuId;

    @Column(name = "Profile_id")
    private Long profileId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileMenuSousMenuId that = (ProfileMenuSousMenuId) o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(sousMenuId, that.sousMenuId) && Objects.equals(profileId, that.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, sousMenuId, profileId);
    }
}
