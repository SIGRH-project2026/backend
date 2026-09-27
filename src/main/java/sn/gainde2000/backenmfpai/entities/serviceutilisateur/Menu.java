
package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author G2k R&D
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "TP_Menu", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_menu", initialValue = 100, allocationSize = 1, sequenceName = "seq_menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_menu")
    @Column(name = "menu_id", nullable = false, updatable = false, unique = true)
    private Long menId;

    @Column(name = "men_path")
    private String menPath;

    @Column(name = "men_ytitle")
    private String menTitle;

    @Column(name = "men_type")
    private String menType;

    @Column(name = "men_iconType", columnDefinition = "TEXT")
    private String menIconType;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Tr_Men_Sous_menu", schema = "schema_utilisateur",
            joinColumns = @JoinColumn(name = "Men_id"),
            inverseJoinColumns = @JoinColumn(name = "Smn_id")
            )
    private Set<Menu> children = new HashSet<>();



    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Menu menu = (Menu) o;
        return getMenId().equals(menu.getMenId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getMenId());
    }

}
