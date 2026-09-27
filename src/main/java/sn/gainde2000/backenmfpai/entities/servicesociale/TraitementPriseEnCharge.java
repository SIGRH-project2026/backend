package sn.gainde2000.backenmfpai.entities.servicesociale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

import java.time.LocalDate;

@Entity
@Table(name = "TD_TraitementPriseEnCharge", schema = "schema_affairesociale")
@SequenceGenerator(name = "seq_traitement_pec", initialValue = 100, allocationSize = 2, sequenceName = "seq_traitement_pec")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraitementPriseEnCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_traitement_pec")
    @Column(name = "traitement_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "traitement_dernierEtatPec")
    private String dernierEtatPec;

    @Column(name = "traitement_etatActuel")
    private String traitement;

    @ManyToOne
    @JoinColumn(name = "traitant_id")
    private Utilisateur traitant;


    @Column(name = "pec_id")
    private long priseEnCharge;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "traitement_date")
    private LocalDate dateTraitement;
}
