package sn.gainde2000.backenmfpai.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sn.gainde2000.backenmfpai.security.jwt.JwtAuthEntryPoint;
import sn.gainde2000.backenmfpai.security.jwt.JwtAuthTokenFilter;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final AllowedOrigins allowedOrigins;
    private static final String[] ADMINS = {"Admin-General", "ADMIN-DRH"};
    // Gestion du personnel déconcentré : le Representant-IA y a accès, limité à
    // son IA par UtilisateurImpl (contrôle de périmètre).
    private static final String[] GESTION_DECONCENTRE = {"Admin-General", "ADMIN-DRH", "Directeur-DRH", "Representant-IA"};

    @Bean
    public SecurityFilterChain securiConfigFiltre(HttpSecurity http) throws Exception {
        // Stateless bearer tokens only; no cookie-based authentication.
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/activate-account", "/auth/refresh",
                                "/auth/signin-with-url-connexion", "/auth/signin-with-forget-password-url-connexion",
                                "/contacts/add").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/forgot-password", "/auth/refresh-token",
                                "/actualite/listActive/**", "/actualite/one/**", "/actualite/listActuOrRecrutementActive/**",
                                "/api/formations", "/api/formations/{id:[0-9]+}",
                                "/api/formations/allformationcontinueordiplomante/**", "/api/formations/allFormationByType/**",
                                "/plan-formation/list", "/plan-formation/listEnCours", "/plan-formation/{id:[0-9]+}",
                                "/themeformations/byPlanFormation/**", "/filieres/list/**", "/images/**",
                                "/actuator/health",
                                // Compteurs de la page d'accueil (totaux uniquement).
                                "/static/etablissement/count", "/utilisateur/getAll").permitAll()
                        // The notification topic carries only a refresh signal, never notification contents.
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                                "/menus/create", "/profiles/**", "/profils/**",
                                "/utilisateur/switch-user/**").hasAnyAuthority(ADMINS)
                        .requestMatchers(HttpMethod.POST, "/utilisateur/deconected/add",
                                "/utilisateur/deconected/import").hasAnyAuthority(GESTION_DECONCENTRE)
                        .requestMatchers(HttpMethod.PUT, "/utilisateur/deconected/update/**",
                                "/utilisateur/change-status/**").hasAnyAuthority(GESTION_DECONCENTRE)
                        .requestMatchers(HttpMethod.POST, "/utilisateur/**", "/notifications/add").hasAnyAuthority(ADMINS)
                        .requestMatchers(HttpMethod.PUT, "/utilisateur/**").hasAnyAuthority(ADMINS)
                        .requestMatchers(HttpMethod.PATCH, "/utilisateur/**").hasAnyAuthority(ADMINS)
                        .requestMatchers(HttpMethod.DELETE, "/utilisateur/**").hasAnyAuthority(ADMINS)
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthEntryPoint)
                        // Un refus de droits ne doit pas être confondu avec une session
                        // expirée : sans ce handler, Spring redirige vers /error, répond 401
                        // et le front tente un refresh qui échoue (« token invalide »).
                        .accessDeniedHandler((request, response, denied) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");
                            new ObjectMapper().writeValue(response.getOutputStream(), Map.of(
                                    "success", false,
                                    "status", "403000",
                                    "message", "Vous n'êtes pas autorisé à effectuer cette action."));
                        }))
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins.values());
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        config.setAllowCredentials(false);
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
