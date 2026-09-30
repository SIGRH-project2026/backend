package sn.gainde2000.backenmfpai.commons.Notification;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "notification_readers", schema = "schema_utilisateur",
            joinColumns = @JoinColumn(name = "notification_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"notification_id", "user_id"}))
    @Column(name = "user_id", nullable = false)
    @Builder.Default
    private Set<Long> readers = new HashSet<>();
}

