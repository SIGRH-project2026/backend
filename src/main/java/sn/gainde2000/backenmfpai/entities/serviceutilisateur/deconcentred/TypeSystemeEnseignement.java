package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tp_type_systeme_enseignement", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_type_systeme_enseignement", initialValue = 100, allocationSize = 2, sequenceName = "seq_type_systeme_enseignement")


public class TypeSystemeEnseignement {
  
  @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_type_systeme_enseignement")
    @Column(name = "typeSystemeEns_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "typeSystemeEns_code")
    private String code;

    @Size(max = 100)
    @Column(name = "typeSystemeEns_libelle")
    private String label;
}
