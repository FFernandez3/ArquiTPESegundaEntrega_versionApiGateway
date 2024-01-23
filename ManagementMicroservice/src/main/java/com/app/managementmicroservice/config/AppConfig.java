 package com.app.managementmicroservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import javax.sql.DataSource;

@Configuration
@EnableRetry // Agrega esta anotación para habilitar la funcionalidad de reintentos
@EnableTransactionManagement
@EnableMongoRepositories(basePackages=("com.app.managementmicroservice.repository"))
@EntityScan(basePackages = "com.app.managementmicroservice.domain")
public class AppConfig {

    @Bean("clientRest")
    public RestTemplate restTemplateRegistration(){
        return new RestTemplate();

    }
    @Bean("OpenAPI")
    public OpenAPI customOpenAPI(@Value("${application-description}") String description,
                                 @Value("${application-version}") String version) {
        return new OpenAPI()
                .info(new Info().title("Administration API")
                        .version(version)
                        .description(description)
                        .license(new License().name("Administration API Licence")));
    }

    @Bean
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 7000)) // Intenta 5 veces con un retardo de 7 segundos entre intentos
    public MongoClient mongo() {
        String mongoIpAddress = "mongo";
        ConnectionString connectionString = new ConnectionString("mongodb://root:root@" + mongoIpAddress + ":27017/management?authSource=admin");
        //ConnectionString connectionString = new ConnectionString("mongodb://root:root@localhost:27017/management?authSource=admin");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "management");
    }


}
