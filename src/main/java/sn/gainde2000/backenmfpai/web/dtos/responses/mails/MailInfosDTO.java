package sn.gainde2000.backenmfpai.web.dtos.responses.mails;

/**
 * @author G2k R&D
 */

public record MailInfosDTO(Long id, String  originalText, String subject, String text, String destinataire) {
}
