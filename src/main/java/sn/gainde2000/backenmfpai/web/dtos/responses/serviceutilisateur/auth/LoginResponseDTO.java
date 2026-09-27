package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.auth;

/**
 * @author Abdou Karim CISSOKHO
 * @created 26/01/2024-16:44
 * @project backend_mfpai
 */
public record LoginResponseDTO(String access_token,
                               String expires_in,
                               String refresh_expires_in,
                               String refresh_token,

                               String token_type,

                               String session_state,
                               String scope
                               ) {
}
