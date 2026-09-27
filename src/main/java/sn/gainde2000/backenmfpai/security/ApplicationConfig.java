package sn.gainde2000.backenmfpai.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
/**
 * @author Abdou Karim CISSOKHO
 * @created 02/02/2024-11:58
 * @project backend_mfpai
 */

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        //config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE"));
       /* config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "Origin",
                "Accept", "Access-Control-Request-Method", "Access-Control-Request-Headers"));

        */
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
       // config.setAllowedOrigins(Collections.singletonList("https://mfpai.gainde2000.sn"));
        config.setAllowedOrigins(Collections.singletonList("http://10.42.3.184:8080"));
        config.setAllowedOrigins(Collections.singletonList("https://sirh-formation.sec.gouv.sn/"));
        config.setAllowedOrigins(Collections.singletonList("http://sirh-formation.sec.gouv.sn/"));
        config.setAllowedOrigins(Collections.singletonList("http://10.42.3.184:8080/sigrh"));
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200/api/v1"));
        config.setAllowedOrigins(Collections.singletonList("http://192.168.2.26:8080/swagger-ui"));
        config.setAllowedOrigins(Collections.singletonList("https://sirh-formation.sec.gouv.sn/projet-api-v2/swagger-ui"));
        config.setAllowedOrigins(Collections.singletonList("http://sirh-formation.sec.gouv.sn/projet-api-v2/swagger-ui"));
        config.setAllowedOrigins(Collections.singletonList("http://localhost:8097/swagger-ui"));
        config.setAllowedHeaders(Arrays.asList(
                ORIGIN,
                CONTENT_TYPE,
                ACCEPT,
                AUTHORIZATION
        ));
        config.setAllowedMethods(Arrays.asList(
                GET.name(),
                POST.name(),
                DELETE.name(),
                PUT.name(),
                PATCH.name()
        ));
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);

    }


    /*   @Bean
       CorsConfigurationSource corsConfigurationSource() {
           CorsConfiguration configuration = new CorsConfiguration();
           configuration.addAllowedOrigin("*");
           configuration.setAllowCredentials(true);
           configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "OPTIONS", "DELETE"));
           configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "Origin",
                   "Accept", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
           UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
           source.registerCorsConfiguration("/**", configuration);
           return source;
       }

     */

}
