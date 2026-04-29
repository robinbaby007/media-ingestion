package com.mi.geteway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutingConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("event-api-service", r -> r
                        // Matches both /api/events and /api/events/anything
                        .path("/api/events", "/api/events/**")
                        // Forwards to the load balancer
                        .uri("lb://EVENT-API-SERVICE"))
                .build();
    }
}
