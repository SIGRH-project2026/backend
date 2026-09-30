# Notification read state

Deploy backend and frontend together: `/topic/notifications` now publishes only a refresh signal.

For databases using `ddl-auto: none` or `validate`, execute `V4__notification_readers.sql` before starting the updated backend, or include it in `spring.sql.init.schema-locations` with initialization enabled. The local application.yaml includes this entry; application.yaml is not tracked in this repository. With `ddl-auto: update`, Hibernate creates the readers table.

Existing personal read flags are preserved. Historical collective read flags cannot identify the reader, so collective notifications initially appear unread for each account until that account reads them.

Verify with two accounts: personal messages stay private, public messages reach both, profile messages reach matching profiles, and reading a collective message does not change the other account's unread count. Also check an empty list, several recipients, and reconnecting the socket.
