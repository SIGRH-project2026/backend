package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/02/2024-10:51
 * @project backend_mfpai
 */

@Entity
@Table(name = "TD_UserManager", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_user_manager", initialValue = 100, allocationSize = 2, sequenceName = "seq_user_manager")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserManager extends Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_manager")
    @Column(nullable = false, updatable = false, unique = true)
    protected Long id;
}
