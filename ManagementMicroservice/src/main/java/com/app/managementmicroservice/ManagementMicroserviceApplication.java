package com.app.managementmicroservice;

import com.app.managementmicroservice.config.SecurityConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ManagementMicroserviceApplication {
    @Autowired
    SecurityConfig securityConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(ManagementMicroserviceApplication.class, args);
    }
    @PostConstruct
    public void init() throws IOException {
        securityConfiguration.databasePopulator();
    }

}
