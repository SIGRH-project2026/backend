package sn.gainde2000.backenmfpai.entities.serviceformation.formation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_TYPE_FORMATION", schema = "schema_formation")
public class TypeFormation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String libelle;

    @Column(nullable = false, unique = true)
    private String code;

}