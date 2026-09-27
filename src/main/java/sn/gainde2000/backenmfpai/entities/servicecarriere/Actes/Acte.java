package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.StatutBEP;
import sn.gainde2000.backenmfpai.entities.servicecarriere.PieceJointes;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Agent;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TD_Acte", schema = "schema_carriere")
@SequenceGenerator(name = "seq_acte", initialValue = 100, allocationSize = 2, sequenceName = "seq_acte")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Acte {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acte")
    @Column(name = "acte_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "acte_referenceActe")
    private String referenceActe;

    @Column(name = "acte_niveau")
    private int niveau;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "acte_dateDemandeActe")
    private LocalDate dateDemandeActe;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "acte_dateDebut")
    private LocalDate dateDebut;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @LastModifiedDate
    @Column(name = "acte_dateFin")
    private LocalDate dateFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_ag_id")
    private Utilisateur agent;

    @Column(name = "acte_ProfilDevantTraiter")
    @Size(max = 25)
    private String profilDevantTraiter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_div_id")
    private Division division;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_dir_id")
    private Direction direction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_ser_id")
    private Services service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_bur_id")
    private Bureau bureau;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_ia_id")
    private IA ia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_ief_id")
    private IEF ief;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_cfp_id")
    private CFP cfp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_eff_id")
    private EEF eff;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acte_etab_id")
    private Etablissement etablissement;

    @ManyToOne
    @JoinColumn(name = "type_acte_id")
    private TypeActe typeActe;

    @ManyToOne
    @JoinColumn(name = "typeAG_id")
    private TypeAA typeAA;

    @ManyToOne
    @JoinColumn(name = "typeAA_id")
    private TypeAG typeAG;

    @ManyToOne
    @JoinColumn(name = "statut_acte_id")
    private StatutActe statutActe;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "acte_id")
    private List<File> pieceJointes = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Borderau currentBordereau=new Borderau();

    @OneToOne(cascade = CascadeType.ALL)
    private Borderau predBordereau =new Borderau();

    @Column(name = "acte_typeEtab")
    private String typeEtablissement;

    @Column(name = "acte_typeAgent")
    private String typeAgent;

    @Column(name = "acte_isDeleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "acte_isActivated")
    private boolean isActivated;

    @Column(name = "acte_commentaire", length = 255)
    private String commentaire;

    @Column(name = "acte_motifRejet",length = 255)
    private String motifRejetDemande;

    @Column(name = "acte_motifModification",length = 255)
    private String motifModification;

    @Column(name = "acte_emailsTraitant")
    private Set<String> emailsTraitant =new HashSet<>();

}
