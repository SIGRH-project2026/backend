package sn.gainde2000.backenmfpai.entities.servicesociale;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import sn.gainde2000.backenmfpai.entities.file.File;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TD_PriseEnCharge", schema = "schema_affairesociale")
@SequenceGenerator(name = "seq_pec", initialValue = 100, allocationSize = 2, sequenceName = "seq_pec")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PriseEnCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pec")
    @Column(name = "pec_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "pec_numeroDemande")
    private String numeroDemande;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "pec_dateDemande")
    private LocalDate dateDemande;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "pec_dateMofifiied")
    private LocalDate lastModified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_utilisateur_id")
    private Utilisateur utilisateur;

    @Column(name = "pec_objetDemande")
    private String objetDemande;

    @Column(name = "pec_motifRejet")
    private String motifRejetDemande;

    @Column(name = "pec_motifModification")
        private String motifModification;

    @ManyToOne
    @JoinColumn(name = "pec_stat_id")
    private StatutPriseEnCharge statutPriseEnCharge;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_div_id")
    private Division division;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_dir_id")
    private Direction direction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_ser_id")
    private Services service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_bur_id")
    private Bureau bureau;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_ia_id")
    private IA ia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_region_id")
    private Region region;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_ief_id")
    private IEF ief;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_cfp_id")
    private CFP cfp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_eff_id")
    private EEF eff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pec_etab_id")
    private Etablissement etablissement;

    @ManyToOne
    @JoinColumn(name = "pec_type_id")
    private TypeDemande typeDemandePeec;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pec_piece_id")
    private List<File> pieceJointes = new ArrayList<>();
}
