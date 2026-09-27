package sn.gainde2000.backenmfpai.entities.servicecarriere.Actes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TP_TypeActe", schema = "schema_carriere")
@SequenceGenerator(name = "seq_type_acte", initialValue = 100, allocationSize = 2, sequenceName = "seq_type_acte")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeActe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_type_acte")
    @Column(name = "type_id",nullable = false, updatable = false, unique = true)
    private Long id;

    @Size(max = 100)
    @Column(name = "type_Code")
    private String codeActe;

    @Size(max = 100)
    @Column(name = "type_libelle")
    private String libelleActe;
















}
