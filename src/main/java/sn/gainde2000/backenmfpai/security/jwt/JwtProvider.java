package sn.gainde2000.backenmfpai.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sn.gainde2000.backenmfpai.security.services.UtilisateurPrinciple;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailConnexionInfosDTO;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;




@Component
public class JwtProvider {
    @Value("${projet.securite.jwtSecret}")
    private String jwtSecret;
    @Value("${projet.securite.jwtExpiration}")
    private int jwtExpiration;
    @Value("${projet.securite.jwtRefreshToken}")
    private int jwtRefreshToken;

    private static final  String INFOS= "infos";

    public String generateToken(Authentication authentication) {
        UtilisateurPrinciple utilisateurPrinciple = (UtilisateurPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(utilisateurPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration ))
                //.setExpiration(new Date(System.currentTimeMillis() + (long) jwtExpiration * 60 * 1000))
                .claim("token_use", "access")
                .claim("authorities", authentication.getAuthorities())
                .claim(INFOS, utilisateurPrinciple.getUtilisateurInfo())
                .signWith(getSignatureKey())
                .compact();
    }

    public String generateJwtMailToken(MailConnexionInfosDTO infos) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                //.setExpiration(new Date(System.currentTimeMillis() + (long) jwtExpiration * 60 * 1000))
                .claim("token_use", "mail")
                .claim(INFOS, infos)
                .signWith(getSignatureKey())
                .compact();
    }

    public String generateRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshToken ))
                //.setExpiration(new Date(System.currentTimeMillis() + (long) jwtRefreshToken * 60 * 1000))
                .claim("token_use", "refresh")
                .claim("authorities", claims.get("authorities"))
                .claim(INFOS, claims.get(INFOS))
                .signWith(getSignatureKey())
                .compact();
    }

    public String generateAccessTokenFromRefreshToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignatureKey()).require("token_use", "refresh").build().parseClaimsJws(token).getBody();
        return Jwts.builder()
                .setSubject(claims.getSubject())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("token_use", "access")
                .claim("authorities", claims.get("authorities"))
                .claim(INFOS, claims.get(INFOS))
                .signWith(getSignatureKey())
                .compact();
    }

    public boolean validationAccessToken(String token) {
        return hasPurpose(token, "access");
    }

    public boolean validationRefreshToken(String token) {
        return hasPurpose(token, "refresh");
    }

    private boolean hasPurpose(String token, String purpose) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignatureKey()).require("token_use", purpose)
                    .build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public boolean validationJwtToken(String authtoken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(authtoken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException e) {
            return false;
        }
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignatureKey()).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public SecretKey getSignatureKey() {
        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

}
