package com.app.apigateway.router;

import com.app.apigateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator routes( RouteLocatorBuilder builder, AuthenticationFilter authFilter ) {
        return builder.routes()
                .route("autenticacion", r -> r.path("/api/employees/authenticate" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8084"))
                .route("registro", r -> r.path("/api/employees/register" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8084"))
                .route("micro-a-product", r -> r.path( "/api/employees/administrators/scooters" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8084"))
                .route("micro-a-product", r -> r.path("/api/products/**")
                        .filters(f ->
                                f.filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("http://localhost:8082"))
                .build();
    }

}

