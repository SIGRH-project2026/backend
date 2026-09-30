package sn.gainde2000.backenmfpai.services.implementations.serviceformation.formation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Formation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ParticipationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.formation.ParticipantDefinitifRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class FormationNotifications {
    private final BusinessNotificationService notifications;
    private final ParticipationRepository participations;
    private final ParticipantDefinitifRepository participants;
    private final IUtilisateurRepository users;

    public void notifyConcerned(Formation formation, String action) {
        Map<Long, Utilisateur> recipients = new LinkedHashMap<>();
        if (formation.getThemeFormation() != null) {
            add(recipients, formation.getThemeFormation().getResponsableSuivi());
            if (formation.getThemeFormation().getPlanFormation() != null) {
                add(recipients, formation.getThemeFormation().getPlanFormation().getCreatedBy());
            }
        }
        participations.findByFormationId(formation.getId()).forEach(p -> add(recipients, p.getCentralLevel()));
        participants.findByFormationId(formation.getId()).forEach(p -> {
            if (p.getMatricule() != null && !p.getMatricule().isBlank()) {
                users.findUtilisateurByMatricule(p.getMatricule()).ifPresent(u -> add(recipients, u));
            }
        });
        recipients.values().forEach(u -> notifications.notify(u, "Suivi de formation",
                "Formation " + formation.getReference() + " : " + action + "."));
    }

    private void add(Map<Long, Utilisateur> recipients, Utilisateur user) {
        if (user != null && user.getId() != null) recipients.put(user.getId(), user);
    }
}
