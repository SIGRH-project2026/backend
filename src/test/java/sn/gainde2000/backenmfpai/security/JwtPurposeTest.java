package sn.gainde2000.backenmfpai.security;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import sn.gainde2000.backenmfpai.security.jwt.JwtProvider;
import static org.junit.jupiter.api.Assertions.*;
class JwtPurposeTest {
    @Test void onlyAccessTokensAuthenticateAndOnlyRefreshTokensRenew() {
        JwtProvider provider = new JwtProvider();
        ReflectionTestUtils.setField(provider, "jwtSecret", "test-only-key-of-at-least-thirty-two-random-bytes-123456");
        ReflectionTestUtils.setField(provider, "jwtExpiration", 60000);
        for (String purpose : new String[] {"access", "refresh", "mail"}) {
            String token = Jwts.builder().setSubject("user@example.test").claim("token_use", purpose)
                    .signWith(provider.getSignatureKey()).compact();
            assertEquals("access".equals(purpose), provider.validationAccessToken(token));
            assertEquals("refresh".equals(purpose), provider.validationRefreshToken(token));
            if ("refresh".equals(purpose)) assertTrue(provider.validationAccessToken(provider.generateAccessTokenFromRefreshToken(token)));
            else assertThrows(JwtException.class, () -> provider.generateAccessTokenFromRefreshToken(token));
        }
    }
    @Test void originsCannotContainWildcardsPathsOrCredentials() {
        for (String origin : new String[]{"*", "https://example.test/path", "https://user:secret@example.test", ""}) {
            assertThrows(IllegalArgumentException.class, () -> new AllowedOrigins(origin));
        }
        assertEquals(java.util.List.of("https://example.test"), new AllowedOrigins("https://example.test/").values());
    }
}
