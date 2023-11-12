package com.app.managementmicroservice.security.jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

    /**
     * Configura el filtro JWT FILTER para que resuelva el token antes de cada request.
     */

        private final TokenProvider tokenProvider;

        @Override
        public void configure( HttpSecurity http ) {
            JwtFilter customFilter = new JwtFilter(tokenProvider);
            http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        }


}
