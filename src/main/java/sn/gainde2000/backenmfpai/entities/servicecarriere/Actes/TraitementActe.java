package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import java.time.LocalDate;

@Entity
@Table(name = "TD_TraitementActe", schema = "schema_carriere")
@SequenceGenerator(name = "seq_traitement_acte", initialValue = 100, allocationSize = 2, sequenceName = "seq_traitement_acte")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TraitementActe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_traitement_acte")
    @Column(name = "traitement_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acte_id")
    private Acte acte;


    @Column(name = "traitement_etatActuel")
    private String traitement;

    @ManyToOne
    @JoinColumn(name = "traitant_id")
    private Utilisateur traitrant;


    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "traitement_date")
    private LocalDate dateTraitement;







}
