package com.app.managementmicroservice.config;


import com.app.managementmicroservice.security.jwt.JwtConfigurer;
import com.app.managementmicroservice.security.jwt.TokenProvider;
import com.app.managementmicroservice.service.AuthorityConstant;

import lombok.RequiredArgsConstructor;


import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
        // AGREGAMOS NUESTRA CONFIG DE JWT.
        http
                .apply( securityConfigurerAdapter() );
        http
                .csrf( AbstractHttpConfigurer::disable )
                // MANEJAMOS LOS PERMISOS A LOS ENDPOINTS.
                .authorizeHttpRequests( auth -> auth
                        
                        //.requestMatchers("/api/employees").hasRole(AuthorityConstant.ADMIN)
                                .requestMatchers(new AntPathRequestMatcher("/api/employees/register")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/employees/authenticate")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/employees")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/employees/authority")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/api/employees/allAuthorities")).permitAll()
                        //.anyRequest().authenticated()


                )
                .anonymous( AbstractHttpConfigurer::disable )
                .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
        http

                .httpBasic( Customizer.withDefaults() );
        return http.build();
    }


    private JwtConfigurer securityConfigurerAdapter() {
        return new JwtConfigurer(tokenProvider);
    }

    /*Carga de datos*/
   @Bean
    public ResourceDatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("db_manager.json"));
        populator.addScript(new ClassPathResource("db_authorities.json"));
        return populator;
    }


}

