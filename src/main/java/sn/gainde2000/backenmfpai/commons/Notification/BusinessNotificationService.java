package sn.gainde2000.backenmfpai.commons.Notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.util.HtmlUtils;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;

/** Notifications métier personnelles : persistance atomique et e-mail après commit. */
@Service
@RequiredArgsConstructor
@Transactional
public class BusinessNotificationService {
    private final INotification notifications;
    private final IUtilisateurRepository users;
    private final MailService mailService;

    public void notify(Utilisateur recipient, String subject, String message) {
        if (recipient == null || recipient.getId() == null || recipient.getId() <= 0) {
            throw new IllegalArgumentException("Le destinataire de la notification doit être un utilisateur enregistré");
        }
        if (alreadyNotified(recipient.getId(), subject, message)) return;
        notifications.notifyUser(Notification.builder().idUser(recipient.getId())
                .objet(subject).message(message).build());
        emailAfterCommit(recipient.getEmail(), subject, message);
    }

    private record Delivered(Long userId, String subject, String message) implements TransactionSynchronization {}

    private boolean alreadyNotified(Long userId, String subject, String message) {
        if (!TransactionSynchronizationManager.isSynchronizationActive()) return false;
        Delivered event = new Delivered(userId, subject, message);
        if (TransactionSynchronizationManager.getSynchronizations().contains(event)) return true;
        TransactionSynchronizationManager.registerSynchronization(event);
        return false;
    }

    public void notifyUser(Notification notification) {
        if (notification.getIdUser() == null || notification.getIdUser() <= 0) {
            throw new IllegalArgumentException("Une notification métier doit avoir un destinataire personnel");
        }
        Utilisateur recipient = users.findById(notification.getIdUser()).orElseThrow();
        notify(recipient, notification.getObjet(), notification.getMessage());
    }

    /** Conserve aussi les destinataires externes (par exemple les stagiaires sans compte). */
    public void sendMail(MailInfosDTO mail) {
        if (mail.destinataire() == null || mail.destinataire().isBlank()) {
            return;
        }
        users.findUtilisateurByEmailIgnoreCase(mail.destinataire().trim()).ifPresentOrElse(
                user -> notify(user, mail.subject(), mail.originalText()),
                () -> emailAfterCommit(mail.destinataire(), mail.subject(), mail.originalText()));
    }

    public void notifyMatricule(String matricule, String subject, String message) {
        if (matricule != null && !matricule.isBlank()) {
            users.findUtilisateurByMatricule(matricule).ifPresent(user -> notify(user, subject, message));
        }
    }

    private void emailAfterCommit(String email, String subject, String message) {
        if (email == null || email.isBlank()) {
            return;
        }
        String html = "<p>" + HtmlUtils.htmlEscape(message).replace("\n", "<br>") + "</p>";
        MailInfosDTO mail = new MailInfosDTO(null, message, subject, html, email.trim());
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override public void afterCommit() { mailService.sendASynchronousMail(mail); }
            });
        } else {
            mailService.sendASynchronousMail(mail);
        }
    }
}
