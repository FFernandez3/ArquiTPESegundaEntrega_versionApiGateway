package com.app.usermicroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages=("com.app.usermicroservice.userRepository"))
@EntityScan(basePackages = "com.app.usermicroservice.userDomain")
public class AppConfig {
    @Bean("clientRest")
    public RestTemplate restTemplateRegistration(){
        return new RestTemplate();
    }
    @Bean("OpenAPI")
    public OpenAPI customOpenAPI(@Value("${application-description}") String description,
                                 @Value("${application-version}") String version) {
        return new OpenAPI()
                .info(new Info().title("User API")
                        .version(version)
                        .description(description)
                        .license(new License().name("User API Licence")));
    }
}
