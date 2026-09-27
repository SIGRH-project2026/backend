package sn.gainde2000.backenmfpai.commons.Notification;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "TD_Notification", schema = "schema_utilisateur")
@SequenceGenerator(name = "seq_notification", initialValue = 100, allocationSize = 2, sequenceName = "seq_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@jakarta.persistence.Entity
@Builder

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notification")
    private Long id;
    private String codeProfile;
    private String objet;
    private String message;
    private Long idUser ;
    private boolean isRead;
    private LocalDateTime date;
    private Long notReads;
}

