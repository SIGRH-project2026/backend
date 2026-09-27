
package sn.gainde2000.backenmfpai.entities.serviceutilisateur.central;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-15:20
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_CentralLevel", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_central_level", initialValue = 100, allocationSize = 2, sequenceName = "seq_central_level")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CentralLevel extends Utilisateur {

    @ManyToOne

    @JoinColumn(name = "bureau_id")
    private Bureau bureau;

    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;


    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;



}

