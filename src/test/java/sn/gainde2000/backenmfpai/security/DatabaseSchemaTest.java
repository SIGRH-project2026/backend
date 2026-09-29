package sn.gainde2000.backenmfpai.security;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.ActiveProfiles;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("schema-verification")
@EnabledIfEnvironmentVariable(named = "SIGRH_SCHEMA_TEST_URL", matches = ".+")
class DatabaseSchemaTest {
    @DynamicPropertySource static void database(DynamicPropertyRegistry properties) {
        properties.add("spring.datasource.url", () -> System.getenv("SIGRH_SCHEMA_TEST_URL"));
        properties.add("spring.datasource.username", () -> System.getenv().getOrDefault("SIGRH_SCHEMA_TEST_USER", "schema_test"));
        properties.add("spring.datasource.password", () -> "");
        properties.add("spring.jpa.hibernate.ddl-auto", () -> System.getenv().getOrDefault("SIGRH_SCHEMA_DDL", "validate"));
        properties.add("spring.jpa.hibernate.naming.physical-strategy", () -> "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
        properties.add("spring.sql.init.mode", () -> "never");
    }
    @Test void schemaMatchesCurrentEntities() { }
}
