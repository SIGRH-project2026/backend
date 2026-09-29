package sn.gainde2000.backenmfpai.commons.Notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.security.services.UtilisateurPrinciple;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static sn.gainde2000.backenmfpai.commons.Notification.QNotification.notification;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService implements INotification {
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Response<Object> notifyUser(Notification source) {
        // Callers may reuse the same object for several recipients. Always insert a new row.
        Notification saved = notificationRepository.save(Notification.builder()
                .idUser(source.getIdUser()).codeProfile(source.getCodeProfile())
                .objet(source.getObjet()).message(source.getMessage())
                .date(LocalDateTime.now()).isRead(false).build());
        // This public topic carries only an invalidation signal, never private message content.
        Runnable publish = () -> messagingTemplate.convertAndSend("/topic/notifications", "{}");
        if (org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive()) {
            org.springframework.transaction.support.TransactionSynchronizationManager.registerSynchronization(
                    new org.springframework.transaction.support.TransactionSynchronization() {
                        @Override public void afterCommit() { publish.run(); }
                    });
        } else {
            publish.run();
        }
        return Response.ok().setPayload(saved).setMessage("Notification envoyée avec succès");
    }

    private UtilisateurPrinciple currentUser(Long requestedId) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UtilisateurPrinciple user)
                || (requestedId != null && !Objects.equals(requestedId, user.getUtilisateurInfo().id()))) {
            throw new org.springframework.security.access.AccessDeniedException("Accès aux notifications interdit");
        }
        return user;
    }

    private BooleanExpression audience(UtilisateurPrinciple user) {
        Long id = user.getUtilisateurInfo().id();
        var profiles = user.getAuthorities().stream().map(a -> a.getAuthority()).toList();
        // A named recipient takes precedence over any profile on the notification.
        var collective = notification.idUser.isNull().or(notification.idUser.eq(0L));
        var publicMessage = notification.codeProfile.isNull().or(notification.codeProfile.eq(""));
        return notification.idUser.eq(id).or(collective.and(publicMessage.or(notification.codeProfile.in(profiles))));
    }

    private BooleanExpression unread(Long id) {
        // Preserve historical personal read flags. Collective reads are now per account.
        return notification.readers.contains(id).not().and(notification.idUser.isNull()
                .or(notification.idUser.eq(0L)).or(notification.isRead.isFalse()));
    }

    private Notification forUser(Notification source, Long id, long count) {
        boolean read = source.getReaders().contains(id)
                || (Objects.equals(source.getIdUser(), id) && source.isRead());
        return Notification.builder().id(source.getId()).idUser(source.getIdUser())
                .codeProfile(source.getCodeProfile()).objet(source.getObjet()).message(source.getMessage())
                .date(source.getDate()).isRead(read).notReads(count).build();
    }

    @Override
    public Response<Object> notifyIsRead(Long idNotification) {
        var user = currentUser(null);
        Long id = user.getUtilisateurInfo().id();
        Notification saved = notificationRepository.findForReading(idNotification).orElseThrow(
                () -> new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
        if (!notificationRepository.exists(audience(user).and(notification.id.eq(idNotification)))) {
            throw new org.springframework.security.access.AccessDeniedException("Accès aux notifications interdit");
        }
        saved.getReaders().add(id);
        return Response.ok().setPayload(forUser(saved, id, 0)).setMessage("Notification lue avec succès");
    }

    @Override
    @Transactional(readOnly = true)
    public Response<Object> getNotifiesByUser(int page, int pageSize, Long idUser, String codeProfile) {
        var user = currentUser(idUser);
        var conditions = audience(user).and(unread(idUser));
        Page<Notification> result = notificationRepository.findAll(conditions,
                PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        var metadata = Response.PageMetadata.builder().size(result.getSize()).totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements()).number(result.getNumber()).build();
        return Response.ok().setPayload(result.getContent().stream()
                .map(n -> forUser(n, idUser, result.getTotalElements())).toList())
                .setMetadata(metadata).setMessage("Liste des notifications");
    }

    @Override
    @Transactional(readOnly = true)
    public Response<Object> getListNotifiesByUser(Long idUser, String codeProfile) {
        var user = currentUser(idUser);
        long count = notificationRepository.count(audience(user).and(unread(idUser)));
        List<Notification> result = new ArrayList<>();
        notificationRepository.findAll(audience(user), Sort.by(Sort.Direction.DESC, "id"))
                .forEach(n -> result.add(forUser(n, idUser, count)));
        return Response.ok().setPayload(result).setMessage("Liste des notifications");
    }
}
