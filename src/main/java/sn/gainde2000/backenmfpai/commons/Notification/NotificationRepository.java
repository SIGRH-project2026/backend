package sn.gainde2000.backenmfpai.commons.Notification;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository  extends JpaRepository<Notification, Long>, QuerydslPredicateExecutor<Notification> {
    @Query("SELECT COUNT(not) FROM Notification not WHERE not.isRead = :isRead ")
    Long notificationsNotRead(boolean isRead);
}
