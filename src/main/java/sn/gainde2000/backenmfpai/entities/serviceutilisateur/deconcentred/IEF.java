package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-16:42
 * @project backend_mfpai
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TP_IEF", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_ief", initialValue = 100, allocationSize = 2, sequenceName = "seq_ief")
public class IEF {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ief")
    @Column(name = "seq_ief",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "ief_code")
    private String code;

    @Size(max = 100)
    @Column(name = "ief_libelle")
    private String label;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ia_id")
    private IA ia;

    @Column(name = "ief_statut", columnDefinition = "boolean default true")
    private Boolean statut;


}
