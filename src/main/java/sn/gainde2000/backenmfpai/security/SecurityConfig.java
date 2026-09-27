package sn.gainde2000.backenmfpai.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sn.gainde2000.backenmfpai.security.jwt.JwtAuthEntryPoint;
import sn.gainde2000.backenmfpai.security.jwt.JwtAuthTokenFilter;
import sn.gainde2000.backenmfpai.security.services.UtilisateurDetailsSerciveImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author G2k R&D
 */

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig  {

    //private final UtilisateurDetailsSerciveImpl utilisateurDetailsSercive;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private static final RequestMatcher[] AUTH_WHITELIST = {
            new AntPathRequestMatcher("/auth/**"),
          /*  new AntPathRequestMatcher("/auth/login/**"),*/
            new AntPathRequestMatcher("/utilisateur/manager-user/**"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/WEB-INF/classes/images/**"),
            new AntPathRequestMatcher("/static/**"),
            new AntPathRequestMatcher("/images/**"),
            new AntPathRequestMatcher("/api/formations/allformationcontinueordiplomante/**"),
            new AntPathRequestMatcher("/api/formations/allFormationByType/**"),
//            new AntPathRequestMatcher("/api/formations/allformationdiplomante/**"),
            new AntPathRequestMatcher("/api/formations/**"),
            new AntPathRequestMatcher("/plan-formation/listEnCours/**"),
            new AntPathRequestMatcher("/plan-formations/**"),
            new AntPathRequestMatcher("/themeformations/byPlanFormation/**"),
            new AntPathRequestMatcher("/actualite/listActive/**"),
            new AntPathRequestMatcher("/actualite/one/**"),
            new AntPathRequestMatcher("/actualite/listActuOrRecrutementActive/**"),
            new AntPathRequestMatcher("/utilisateur/getAll/**"),
            new AntPathRequestMatcher("/filieres/list/**"),
            new AntPathRequestMatcher("/contacts/add/**"),
            new AntPathRequestMatcher("/ws/**"),
            new AntPathRequestMatcher("/static/etablissement"),

            // ⬇️⬇️⬇️ NOUVEAUX ENDPOINTS À AJOUTER ⬇️⬇️⬇️
            // Endpoints pour le niveau central
            new AntPathRequestMatcher("/utilisateur/references/directions"),
            new AntPathRequestMatcher("/utilisateur/references/services/**"),
            new AntPathRequestMatcher("/utilisateur/references/divisions/**"),
            new AntPathRequestMatcher("/utilisateur/references/bureaus/**"),
            new AntPathRequestMatcher("/utilisateur/personnel/niveau-central"),

    };

    @Bean
    public SecurityFilterChain securiConfigFiltre(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              /*  .sessionManagement( manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/auth/login?expired")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/auth/login?timeout")


                )

               */

               // .authenticationProvider(authenticationProvider())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //Make the below setting as * to allow connection from any hos
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200","http://10.3.130.200:32135/", "http://10.42.3.184:8080/", "https://sirh-formation.sec.gouv.sn/"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

/*    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(utilisateurDetailsSercive);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }*/


}
