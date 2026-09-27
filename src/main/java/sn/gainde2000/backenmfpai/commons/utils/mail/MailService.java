
package sn.gainde2000.backenmfpai.commons.utils.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.SpringTemplateEngine;
import sn.gainde2000.backenmfpai.entities.other.FailedMail;

import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IFailedMailRepository;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import org.thymeleaf.context.Context;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

/**
 * @author G2k R&D
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${smtp.server.host}")
    private String mailHost;

    @Value("${smtp.server.password}")
    private String password;

    @Value("${smtp.server.from}")
    private String from;
    // Nom affiché comme expéditeur dans le client mail du destinataire, à la
    // place de l'adresse email brute (ex. "Plateforme SIGRH <compte@gmail.com>"
    // au lieu de "compte@gmail.com").
    private static final String SENDER_NAME = "Plateforme SIGRH";
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final IFailedMailRepository failedMailRepository;
    @Value("${upload.path}")
    private String uploadDirectory;

    @Transactional
    @Async
    public void sendASynchronousMail(MailInfosDTO mailInfosDTO) {
        if (!hasRecipient(mailInfosDTO)) {
            LOGGER.warn("Envoi du mail ignoré : aucun destinataire n'est renseigné");
            return;
        }
        LOGGER.debug("Envoi mail en cours d'initialisation !");

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", mailHost);
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.trust", mailHost);
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.starttls.required", "false");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, SENDER_NAME));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailInfosDTO.destinataire()));
            msg.setSubject(mailInfosDTO.subject());
            msg.setText(mailInfosDTO.text(), "utf-8", "html");
            Transport.send(msg);

            manageFailedMailInDatabase(mailInfosDTO, "DELETE");

        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'envoi du mail à {}: {}", mailInfosDTO.destinataire(), e.getMessage());
            manageFailedMailInDatabase(mailInfosDTO, "CREATE_OR_EDIT");
        }
    }

    @Transactional
    @Async
    public void sendMail(MailInfosDTO mailInfosDTO) {
        if (!hasRecipient(mailInfosDTO)) {
            LOGGER.warn("Envoi du mail ignoré : aucun destinataire n'est renseigné");
            return;
        }
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", mailHost);
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.trust", mailHost);
            props.put("mail.smtp.starttls.enable", "false");
            props.put("mail.smtp.starttls.required", "false");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(from, SENDER_NAME));
            helper.setTo(new InternetAddress(mailInfosDTO.destinataire()));
            helper.setSubject(mailInfosDTO.subject());

            Context context = new Context();
            context.setVariable("content", mailInfosDTO.originalText());
            // context.setVariable("anonimous", mailInfosDTO.anonimous()); // ← Ajoutez si
            // besoin
            // context.setVariable("name", mailInfosDTO.name()); // ← Ajoutez si besoin

            String html = templateEngine.process("email", context);
            helper.setText(html, true);

            Transport.send(message);

            LOGGER.info("Email envoyé avec succès à {}", mailInfosDTO.destinataire());

        } catch (Exception e) {
            LOGGER.error("Erreur lors de l'envoi du mail: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void manageFailedMailInDatabase(MailInfosDTO mailInfosDTO, String action) {
        LOGGER.info("Mise à jour de la table FailedEmail en cours !");
        try {
            if (action.equalsIgnoreCase("CREATE_OR_EDIT")) {
                FailedMail failedMail = FailedMail.builder()
                        .email(mailInfosDTO.destinataire())
                        .subject(mailInfosDTO.subject())
                        .text(mailInfosDTO.originalText())
                        .createdDate(new Date())
                        .isSent(false)
                        .build();
                if (Objects.nonNull(mailInfosDTO.id()))
                    failedMail = failedMailRepository.findById(mailInfosDTO.id()).orElse(failedMail);

                failedMailRepository.save(failedMail);
            } else if (action.equalsIgnoreCase("DELETE") && Objects.nonNull(mailInfosDTO.id()))
                failedMailRepository.deleteById(mailInfosDTO.id());

        } catch (Exception exception) {
            LOGGER.error("Echec insertion dans la base de données: ");
        }
        LOGGER.info("La mise à jour a été faite avec succès ! => {}", mailInfosDTO.destinataire());
    }

    @Transactional
    @Async
    public void sendMailWithPJ(MailInfosDTO mailInfosDTO, String pathToAttachment) {
        if (!hasRecipient(mailInfosDTO)) {
            LOGGER.warn("Envoi du mail avec pièce jointe ignoré : aucun destinataire n'est renseigné");
            return;
        }
        try {
            // Créer un message MIME en utilisant JavaMailSender
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            // Configurer les détails de l'e-mail
            helper.setFrom(new InternetAddress(from, SENDER_NAME));
            helper.setTo(new InternetAddress(mailInfosDTO.destinataire()));
            helper.setSubject(mailInfosDTO.subject());

            // Charger le fichier comme une ressource
            Path filePath = Paths.get(uploadDirectory).resolve(pathToAttachment).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Ajouter la pièce jointe
            helper.addAttachment(Objects.requireNonNull(resource.getFilename()), resource);

            // Ajouter le contexte et le contenu HTML
            Context context = new Context();
            context.setVariable("content", mailInfosDTO.originalText());
            String html = templateEngine.process("email.html", context);
            helper.setText(html, true);

            // Envoyer l'e-mail en utilisant JavaMailSender
            mailSender.send(message);

        } catch (Exception e) {
            System.out.println("error =================");
            System.out.println(e.getMessage());
        }
    }

    public void sendMailOS(MailInfosDTO mailInfos, String os) {
    }

    private boolean hasRecipient(MailInfosDTO mailInfosDTO) {
        return mailInfosDTO != null
                && mailInfosDTO.destinataire() != null
                && !mailInfosDTO.destinataire().isBlank();
    }
}
