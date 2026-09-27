package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.DiplomeBaseDTO;

import java.util.Objects;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-11:12
 * @project backend_mfpai
 */


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TD_Diplome", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_dip", initialValue = 100, allocationSize = 2, sequenceName = "seq_dip")
public class Diplomes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_dip")
    @Column(name = "dip_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 15)
    @Unique
    @Column(name = "dip_code", unique = true,  nullable = false, length = 15)
    private String code;

    @Size(max = 100)
    @Column(name = "dip_Libelle")
    private String label;

    @Column(name = "dip_statut", columnDefinition = "boolean default true")
    private Boolean statut;

     @ManyToOne
     @JoinColumn(name = "type_diplome_id")
     private TypeDiplome typeDiplome;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Diplomes diplome = (Diplomes) o;
        return Objects.equals(id, diplome.id) && Objects.equals(code, diplome.code) && Objects.equals(label, diplome.label) && Objects.equals(statut, diplome.statut) && Objects.equals(typeDiplome, diplome.typeDiplome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, label, statut, typeDiplome);
    }
}
