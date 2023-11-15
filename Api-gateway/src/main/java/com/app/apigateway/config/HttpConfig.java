package com.app.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebFluxSecurity
@EnableTransactionManagement
public class HttpConfig {

    @Bean("clientRest")
    public RestTemplate restTemplateRegistration(){
        return new RestTemplate();
    }
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain( ServerHttpSecurity http ) {
        return http
                .csrf( ServerHttpSecurity.CsrfSpec::disable )
                .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll() )
                .build();
    }

}
