package sn.gainde2000.backenmfpai.web.dtos.responses.autentification;

/**
 * @author G2k R&D
 */

public record JwtDTO(String username, String token, String refreshToken, String type) {
}
