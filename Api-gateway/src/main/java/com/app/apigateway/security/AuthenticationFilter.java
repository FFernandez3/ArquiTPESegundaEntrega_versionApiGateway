package com.app.apigateway.security;

import com.app.apigateway.dto.ValidateTokenDTO;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.function.Predicate;

/**
 * Authentication pre-filter for API gateway.
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final String _AuthHeader = "Authorization";
    List<String> excludedUrls = List.of( "api/employees/authenticate" , "api/employees/register", "api/employees", "api/users/scooters/latitude/**", "api/users/**");
    /*private final WebClient.Builder webClientBuilder;*/
    private final RestTemplate restTemplate;

    public AuthenticationFilter(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }
   /* public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }*/

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst(_AuthHeader);

            if (this.isSecured.test(request)) {
                HttpHeaders headers = new HttpHeaders();
                headers.set(_AuthHeader, bearerToken);
                HttpEntity<String> httpEntity = new HttpEntity<>(headers);

                ResponseEntity<ValidateTokenDTO> responseEntity = restTemplate.exchange(
                        "http://localhost:8084/api/employees/validate",
                        HttpMethod.GET,
                        httpEntity,
                        ValidateTokenDTO.class
                );

                ValidateTokenDTO response = responseEntity.getBody();

                if (response != null && !response.isAuthenticated()) {
                    throw new BadCredentialsException("JWT invalido");
                }

                return chain.filter(exchange);
            }

            return chain.filter(exchange);
        };
    }
    /*public GatewayFilter apply( Config config ) {
        return (exchange, chain) -> {      /*exchange representa la solicitud y la respuesta HTTP, y chain es la cadena de filtros restantes que se deben ejecutar.
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst( _AuthHeader );

            if( this.isSecured.test( request ) ) {
                return webClientBuilder.build().get()
                        .uri("http://localhost:8084/api/employees/validate")
                        .header( _AuthHeader, bearerToken )
                        .retrieve().bodyToMono( ValidateTokenDTO.class )   /*Realiza la solicitud y convierte la respuesta a un objeto ValidateTokenDTO utilizando el método bodyToMono de WebClient.
                        .map( response -> {
                            if( ! response.isAuthenticated() )
                                throw new BadCredentialsException( "JWT invalido" );
                            return exchange;
                        })
                        .flatMap(chain::filter);
            }
            return chain.filter(exchange);
        };
    }*/

    private final Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri) );


    /**
     * Required by AbstractGatewayFilterFactory
     */
    @NoArgsConstructor
    public static class Config {}

}

