package sn.gainde2000.backenmfpai.entities.servicepta.pta;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:14
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_InitialPTA", schema = "schema_pta")
@SequenceGenerator(name = "seq_initial_pta_travail", initialValue = 100, allocationSize = 2, sequenceName = "seq_initial_pta_travail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitialPTA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_initial_pta_travail")
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;
    @Size(max = 75)
    @Column(name = "pta_number")
    private String numeroPTA;

    @Size(max = 100)
    @Column(name = "init_pta_name")
    private String nomPTA;


    @Column(name = "init_pta_date")
    private LocalDate date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "init_pta_direction_id")
    private Direction direction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InitialPTA that = (InitialPTA) o;
        return Objects.equals(id, that.id) && Objects.equals(numeroPTA, that.numeroPTA) && Objects.equals(nomPTA, that.nomPTA) && Objects.equals(date, that.date) && Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroPTA, nomPTA, date, direction);
    }
}
