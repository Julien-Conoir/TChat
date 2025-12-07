package com.tchat.gateway.config;

import com.tchat.gateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("auth-register", r -> r
                        .path("/auth/register")
                        .uri("http://localhost:8081"))
                .route("auth-login", r -> r
                        .path("/auth/login")
                        .uri("http://localhost:8081"))
                .route("auth-token", r -> r
                        .path("/auth/token")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8081"))

                .route("channels", r -> r
                        .path("/channels/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8082"))

                .route("messages", r -> r
                        .path("/messages/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("http://localhost:8083"))

                .build();
    }
}