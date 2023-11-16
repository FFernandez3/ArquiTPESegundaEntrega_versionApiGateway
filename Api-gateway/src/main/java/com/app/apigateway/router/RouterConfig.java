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
                .route("change disponibility", r -> r.path( "/api/scooters/isAvailable/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8082"))
                .route("allScooters", r -> r.path( "/api/scooters/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8082"))
                .route("maintenance report", r -> r.path( "/api/scooters/allUpdated" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8082"))
                .route("availability scooters", r -> r.path( "/api/scooters/availabilityQuantity" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8082"))
                .route("validate", r -> r.path("/api/employees/validate")
                        .filters(f ->
                                f.filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("http://localhost:8084"))
                .route("allEmployees", r -> r.path("/api/employees" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8084"))
                .route("scooters near", r -> r.path("/api/users/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8083"))
                .route("cancel accounts", r -> r.path("/api/accounts/isCanceled/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8083"))
                .route("stops", r -> r.path("/api/stops/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8082"))
                .route("price", r -> r.path("/api/prices/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8081"))
                .route("travels", r -> r.path("/api/travels" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8081"))
                .route("travels", r -> r.path("/api/travels/invoced/month1/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8081"))
                .build();
    }

}

