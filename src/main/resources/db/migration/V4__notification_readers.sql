-- Apply before deploying the notification update when schema auto-update is disabled.
CREATE TABLE IF NOT EXISTS schema_utilisateur.notification_readers (
    notification_id BIGINT NOT NULL REFERENCES schema_utilisateur.td_notification(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (notification_id, user_id)
);
