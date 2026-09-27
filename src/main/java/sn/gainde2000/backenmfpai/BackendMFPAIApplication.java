
package sn.gainde2000.backenmfpai;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author G2k R&D
 */
@Slf4j
@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableJpaAuditing
@EnableTransactionManagement
public class BackendMFPAIApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BackendMFPAIApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendMFPAIApplication.class, args);
    }   
}
