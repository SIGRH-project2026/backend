package sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "TP_TypeEtablissement", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_etablissement", initialValue = 100, allocationSize = 2, sequenceName = "seq_etablissement")

public class Etablissement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_etablissement")
    @Column(name = "eta_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 10)
    @Column(name = "eta_code")
    private String code;

    @Size(max = 100)
    @Column(name = "eta_libelle")
    private String label;

   /* @Size(max = 100)
    @Column(name = "eta_type_etablissement")
    private String typeEtablissement;

    */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ief_id")
    private IEF ief;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ia_id")
    private IA ia;


    @Column(name = "eta_statut", columnDefinition = "boolean default true")
    private Boolean statut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "structure_id")
    private Structure structure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeEtablissement_id")
    private TypeEtablissement typeEtablissement;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "typeSystemeEns_id")
    private TypeSystemeEnseignement typeSystemeEnseignement;




}

