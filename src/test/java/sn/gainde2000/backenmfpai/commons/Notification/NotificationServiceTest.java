package sn.gainde2000.backenmfpai.commons.Notification;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sn.gainde2000.backenmfpai.security.services.UtilisateurPrinciple;
import sn.gainde2000.backenmfpai.web.dtos.responses.autentification.UtilisateurInfo;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class NotificationServiceTest {
    private final NotificationRepository repository = mock(NotificationRepository.class);
    private final SimpMessagingTemplate socket = mock(SimpMessagingTemplate.class);
    private final NotificationService service = new NotificationService(repository, socket);

    private void login(long id) {
        var principal = new UtilisateurPrinciple(new UtilisateurInfo(id, "user@example.test", null,
                "User", "Test", Set.of(), true), "", List.of(new SimpleGrantedAuthority("DRH")), Set.of());
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
    }
    @AfterEach void clear() { SecurityContextHolder.clearContext(); }

    @Test void reusedNotificationCreatesSeparateRowsAndDoesNotBroadcastContent() {
        when(repository.save(any(Notification.class))).thenAnswer(call -> {
            Notification n = call.getArgument(0); n.setId(100L); return n;
        });
        Notification source = Notification.builder().idUser(1L).message("private").build();
        service.notifyUser(source);
        source.setIdUser(2L);
        service.notifyUser(source);
        var saved = ArgumentCaptor.forClass(Notification.class);
        verify(repository, times(2)).save(saved.capture());
        assertNotSame(saved.getAllValues().get(0), saved.getAllValues().get(1));
        assertEquals(1L, saved.getAllValues().get(0).getIdUser());
        assertEquals(2L, saved.getAllValues().get(1).getIdUser());
        assertNull(source.getId());
        verify(socket, times(2)).convertAndSend("/topic/notifications", "{}");
    }

    @Test void collectiveReadBelongsOnlyToCurrentUser() {
        login(1L);
        Notification shared = Notification.builder().id(10L).idUser(0L).build();
        when(repository.findForReading(10L)).thenReturn(Optional.of(shared));
        when(repository.exists(any(com.querydsl.core.types.Predicate.class))).thenReturn(true);
        service.notifyIsRead(10L);
        service.notifyIsRead(10L);
        assertEquals(Set.of(1L), shared.getReaders());
        assertFalse(shared.isRead());
        assertFalse(shared.getReaders().contains(2L));
    }

    @Test void cannotListAnotherAccountsNotifications() {
        login(1L);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> service.getListNotifiesByUser(2L, "DRH"));
        verifyNoInteractions(repository);
    }

    @Test void cannotReadNotificationOutsideAudience() {
        login(1L);
        Notification personal = Notification.builder().id(10L).idUser(2L).build();
        when(repository.findForReading(10L)).thenReturn(Optional.of(personal));
        when(repository.exists(any(com.querydsl.core.types.Predicate.class))).thenReturn(false);
        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> service.notifyIsRead(10L));
        assertTrue(personal.getReaders().isEmpty());
    }
    @Test void historyProjectsReadStateWithoutChangingSharedNotification() {
        login(2L);
        Notification shared = Notification.builder().id(10L).idUser(0L).isRead(true).build();
        shared.getReaders().add(1L);
        when(repository.count(any(com.querydsl.core.types.Predicate.class))).thenReturn(1L);
        when(repository.findAll(any(com.querydsl.core.types.Predicate.class),
                any(org.springframework.data.domain.Sort.class))).thenReturn(List.of(shared));
        var response = service.getListNotifiesByUser(2L, "");
        Notification displayed = (Notification) ((List<?>) response.getPayload()).get(0);
        assertFalse(displayed.isRead());
        assertEquals(1L, displayed.getNotReads());
        assertTrue(shared.isRead());
        assertNotSame(shared, displayed);
    }

    @Test void emptyPageRetainsUnreadTotalWithoutIndexError() {
        login(1L);
        var page = new org.springframework.data.domain.PageImpl<Notification>(List.of(),
                org.springframework.data.domain.PageRequest.of(2, 10), 12);
        when(repository.findAll(any(com.querydsl.core.types.Predicate.class),
                any(org.springframework.data.domain.Pageable.class))).thenReturn(page);
        var response = service.getNotifiesByUser(2, 10, 1L, "");
        assertTrue(((List<?>) response.getPayload()).isEmpty());
    }

}
