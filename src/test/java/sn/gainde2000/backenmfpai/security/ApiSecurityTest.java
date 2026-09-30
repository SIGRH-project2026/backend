package sn.gainde2000.backenmfpai.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.security.jwt.*;
import sn.gainde2000.backenmfpai.security.services.UtilisateurDetailsSerciveImpl;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nTranslate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ApiSecurityTest.Endpoints.class,
    properties = {"app.security.allowed-origins=https://portal.example.test,https://admin.example.test"})
@Import({SecurityConfig.class, AllowedOrigins.class, JwtAuthTokenFilter.class, JwtAuthEntryPoint.class, ApiSecurityTest.Endpoints.class})
class ApiSecurityTest {
    @MockBean org.springframework.data.jpa.mapping.JpaMetamodelMappingContext jpaMappingContext;
    @Autowired MockMvc mvc;
    @MockBean JwtProvider provider;
    @MockBean UtilisateurDetailsSerciveImpl users;
    @MockBean I18nTranslate translation;

    @RestController static class Endpoints {
        @GetMapping("/api/formations") String publicList() { return "ok"; }
        @PostMapping("/api/formations/create") String create() { return "ok"; }
        @PostMapping("/utilisateur/manager-user") String manager() { return "ok"; }
        @GetMapping("/utilisateur/getAll") String people() { return "ok"; }
    }
    @Test void publicReadIsAccessible() throws Exception {
        mvc.perform(get("/api/formations")).andExpect(status().isOk());
    }
    @Test void anonymousCannotMutateOrReadPersonnel() throws Exception {
        mvc.perform(post("/api/formations/create")).andExpect(status().isUnauthorized());
        mvc.perform(get("/utilisateur/getAll")).andExpect(status().isUnauthorized());
        mvc.perform(post("/utilisateur/manager-user")).andExpect(status().isUnauthorized());
    }
    @Test @WithMockUser(authorities = "Agent") void agentCannotCreateAdministrator() throws Exception {
        mvc.perform(post("/utilisateur/manager-user")).andExpect(status().isForbidden());
    }
    @Test @WithMockUser(authorities = "Admin-General") void administratorCanManageUsers() throws Exception {
        mvc.perform(post("/utilisateur/manager-user")).andExpect(status().isOk());
    }
    @Test void allowedOriginCanPreflight() throws Exception {
        mvc.perform(options("/utilisateur/manager-user").header("Origin", "https://admin.example.test")
                .header("Access-Control-Request-Method", "POST").header("Access-Control-Request-Headers", "Authorization"))
                .andExpect(status().isOk()).andExpect(header().string("Access-Control-Allow-Origin", "https://admin.example.test"));
    }
    @Test void unknownOriginCannotPreflight() throws Exception {
        mvc.perform(options("/utilisateur/manager-user").header("Origin", "https://untrusted.example.test")
                .header("Access-Control-Request-Method", "POST")).andExpect(status().isForbidden());
    }
}
